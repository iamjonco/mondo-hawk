/*******************************************************************************
 * Copyright (c) 2011-2015 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Konstantinos Barmpis - initial API and implementation
 ******************************************************************************/
package org.hawk.graph.internal.updater;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.hawk.core.VcsCommitItem;
import org.hawk.core.graph.IGraphChange;
import org.hawk.core.graph.IGraphDatabase;
import org.hawk.core.graph.IGraphEdge;
import org.hawk.core.graph.IGraphIterable;
import org.hawk.core.graph.IGraphNode;
import org.hawk.core.graph.IGraphNodeIndex;
import org.hawk.core.model.IHawkAttribute;
import org.hawk.core.model.IHawkClass;
import org.hawk.core.model.IHawkClassifier;
import org.hawk.core.model.IHawkModelResource;
import org.hawk.core.model.IHawkObject;
import org.hawk.core.model.IHawkReference;
import org.hawk.graph.internal.util.GraphUtil;

public class GraphModelBatchInjector {

	public static final String ROOT_DICT_FILE_KEY = "file";
	public static final String ROOT_DICT_NAME = "rootdictionary";

	// integer array containing the current number of added elements:
	// (element,((ofType)M->MM)reference,((ofKind)M->MM)reference,(unset(M->M))reference)
	private int[] objectCount = { 0, 0, 0, 0 };
	private int unset;

	private static enum ParseOptions {
		MODELELEMENTS, MODELREFERENCES
	};

	private IGraphDatabase graph;

	/*
	 * temporary link between resource uri and graph node for model elements
	 */
	private Hashtable<IHawkObject, IGraphNode> hash;

	IGraphNodeIndex epackageDictionary, fileDictionary, proxyDictionary, rootDictionary;

	long startTime;
	String tempDir;

	LinkedList<IGraphChange> changes = new LinkedList<>();

	public GraphModelBatchInjector(IGraphDatabase g) {
		graph = g;
	}

	public GraphModelBatchInjector(IGraphDatabase g, VcsCommitItem s,
			IHawkModelResource r) throws Exception {

		// System.err.println("resource: "+r);

		// indexer = m;
		// resource = r;
		this.graph = g;

		// databaseloc = graph.getPath();

		startTime = System.nanoTime();

		// System.err.println(graph);

		hash = new Hashtable<IHawkObject, IGraphNode>(8192);

		graph.enterBatchMode();

		// dictionary = index.forNodes("dictionary", MapUtil.stringMap(
		// IndexManager.PROVIDER, "lucene", "type", "exact"));

		epackageDictionary = graph.getMetamodelIndex();
		fileDictionary = graph.getFileIndex();
		proxyDictionary = graph.getOrCreateNodeIndex("proxydictionary");
		rootDictionary = graph.getOrCreateNodeIndex(ROOT_DICT_NAME);

		boolean isNew = false;

		final String repositoryURL = s.getCommit().getDelta().getRepository().getUrl();
		IGraphNode fileNode = null;
		long filerevision = 0L;
		try {
			fileNode = ((IGraphIterable<IGraphNode>) fileDictionary.get(repositoryURL, s.getPath())).getSingle();
			if (fileNode != null)
				filerevision = (Long) fileNode.getProperty("revision");
		} catch (Exception e) {
		}
		if (filerevision == 0L)
			isNew = true;
		// else if (filerevision != s.getRevision())
		// ischanged = true;

		// if(ischanged)deleteAllForUpdate(fileNode, s.getRevision());

		if (isNew) {
			// add file
			if (fileNode == null) {

				Map<String, Object> map = new HashMap<>();
				map.put("id", s.getPath());
				map.put("revision", s.getCommit().getRevision());

				// System.err.println("creating file node: "+s.getPath());
				fileNode = graph.createNode(map, "file");

				Map<String, Object> map2 = new HashMap<>();
				map2.put(repositoryURL, s.getPath());
				fileDictionary.add(fileNode, map2);

				// propagate changes to listeners
				changes.add(new GraphChangeImpl(true, GraphChangeImpl.FILE,
						fileNode.getId().toString(), null, true));
			}

			try {

				// XXX NOTE THIS SPECIFICALLY DOES NOT RESOLVE PROXIES -
				// REMEMBER IT !

				// add model elements

				// Iterator<HawkObject> children = res.getAllContents();

				// Iterator<HawkObject> resc = res.getAllContents();

				HashSet<IHawkObject> children = r.getAllContentsSet();

				startTime = System.nanoTime();

				// System.out.println(r);
				System.out.println("File: " + s.getPath());
				System.out.print("ADDING: ");
				int[] addedElements = parseResource(fileNode,
						ParseOptions.MODELELEMENTS, children);
				System.out
						.println(addedElements[0] + "\nNODES AND "
								+ addedElements[1] + " + " + addedElements[2]
								+ " M->MM REFERENCES! (took ~"
								+ (System.nanoTime() - startTime) / 1000000000
								+ "sec)");

				startTime = System.nanoTime();

				// children = res.getAllContents();

				// add references
				System.out.println("File: " + s.getPath());
				System.out.print("ADDING: ");
				addedElements = parseResource(fileNode,
						ParseOptions.MODELREFERENCES, children);
				setUnset(getUnset() + addedElements[3]);
				System.out
						.println(addedElements[0] + "\nREFERENCES! (took ~"
								+ (System.nanoTime() - startTime) / 1000000000
								+ "sec)");

				System.out
						.println(((IGraphIterable<IGraphNode>) proxyDictionary
								.query("_proxyRef", "*")).size()
								+ " - sets of proxy references left in the store");

			} catch (Exception e) {
				e.printStackTrace();
				System.err
						.println("ParseMResource Exception on file: "
								// +
								// graph.getNodeProperties(fileNode).get("id").toString()
								+ s.getPath()
								+ "\nReverting all changes on that file.");

				new DeletionUtils(graph).deleteAll(repositoryURL, s.getPath());

			}
		}

		// graph.shutdown();
		graph.exitBatchMode();

	}

	/**
	 * Adds the resource to the graph according to whether it is a model or a
	 * metamodel resource
	 * 
	 * @param originatingFile
	 * 
	 * @param parseOption
	 * @param resource
	 * @param graph
	 * @return
	 */
	private int[] parseResource(IGraphNode originatingFile,
			ParseOptions parseOption, HashSet<IHawkObject> children)
			throws Exception {

		graph.enterBatchMode();
		epackageDictionary = graph.getMetamodelIndex();
		fileDictionary = graph.getFileIndex();
		proxyDictionary = graph.getOrCreateNodeIndex("proxydictionary");

		objectCount[0] = 0;
		objectCount[1] = 0;
		objectCount[2] = 0;
		objectCount[3] = 0;

		int lastprint = 0;
		int inthisline = 0;

		long init = System.nanoTime();

		for (IHawkObject child : children) {
			if (child.isProxy()) continue;

			boolean ref = true;
			boolean clas = true;

			switch (parseOption) {
			case MODELELEMENTS:
				addEObject(originatingFile, child);
				break;
			case MODELREFERENCES:
				ref = addEReferences(child);
				break;
			default:
				System.err.println("parse option: " + parseOption
						+ " not recognised!");
			}

			if (ref && clas && objectCount[0] % 50000 == 0
					|| lastprint < objectCount[0] - 50000) {
				if (inthisline > 5) {
					System.out.println("\t");
					inthisline = 0;
				}
				inthisline++;
				lastprint = objectCount[0];
				System.out.print(objectCount[0] + " "
						+ (System.nanoTime() - init) / 1000000000 + "sec ("
						+ (System.nanoTime() - startTime) / 1000000000 + ")\t");
				init = System.nanoTime();
			}
		}

		return objectCount;
	}

	// private void resolveProxy(IHawkObject child, String currentFile)
	// throws Exception {
	//
	// // System.err.println("Resolving references pointing to: " +
	// // currentFile);
	//
	// // System.err.println(proxydictionary.);
	//
	// //
	// //
	// IHawkIterable<IGraphNode> resolvedProxies = (IHawkIterable<IGraphNode>)
	// proxydictionary
	// .get("_proxyRef", currentFile);
	//
	// if (resolvedProxies != null && resolvedProxies.size() > 0) {
	//
	// for (IGraphNode n : resolvedProxies) {
	//
	// String[] proxies = (String[]) n.getProperty("_proxyRef:"
	// + currentFile);
	//
	// //boolean found = false;
	//
	// if (proxies != null)
	// for (int i = 0; i < proxies.length; i = i + 2) {
	//
	// //System.err.println(">>>"+ getRelativeURI(child.getUri()));
	// //System.err.println(">>" + proxies[i]);
	//
	// if (getRelativeURI(child.getUri()).equals(proxies[i])) {
	//
	// resolveProxyRef(n, hash.get(child), proxies[i + 1]);
	//
	// if (proxies.length == 2)
	// n.removeProperty("_proxyRef:" + currentFile);
	// else
	// n.setProperty("_proxyRef:" + currentFile,
	// prune(proxies, i));
	//
	// proxydictionary.remove(n);
	//
	// //found = true;
	//
	// }
	//
	// }
	// else
	// System.err.println("error in proxy resolution, to "
	// + currentFile + " \nnode has property keys: "
	// + n.getPropertyKeys());
	//
	// //if (!found)
	// //throw new Exception("not found: " + getRelativeURI(child.getUri()));
	//
	// }
	//
	// }
	//
	// }

	// private String[] prune(String[] proxies, int index) {
	//
	// ArrayList<String> temp = new ArrayList<>();
	// String[] ret = new String[proxies.length - 2];
	//
	// for (int i = 0; i < proxies.length; i = i + 2) {
	// if (i != index) {
	// temp.add(proxies[i]);
	// temp.add(proxies[i + 1]);
	// }
	// }
	//
	// return temp.toArray(ret);
	// }

	/**
	 * 
	 * @param eObject
	 * @return the URI ID of an eObject
	 */
	private String getEObjectId(IHawkObject eObject) {
		return eObject.getUriFragment();
	}

	/**
	 * 
	 * @param eClass
	 * @return the URI ID of an eClass
	 */
	public String getEObjectId(IHawkClass eClass) {
		return eClass.getUri() + "/" + eClass.getName();
	}

	/**
	 * Creates a node in the graph database with the given eObject's attributes
	 * in it. Also indexes it in the 'dictionary' index.
	 * 
	 * @param eObject
	 * @return the Node
	 * @throws Exception
	 */
	private IGraphNode createEObjectNode(IHawkObject eObject,
			IGraphNode typenode) throws Exception {

		// if(eObject.eIsProxy()){System.err.println("PROXY OBJECT FOUND !");
		// System.err.println(eObject.geturi());
		// System.err.println(eObject.geturifragment());
		// oldSystemdotexit(1);
		// }

		// if (eObject.eClass().getName().equals("TypeDeclaration")) {
		// System.err.println(">>>>>>>>CREATING TYPE DECLARATION");
		// System.err.println("URI: " + eObject.geturi());
		// System.err.println("URIF: " + eObject.geturifragment());
		// }

		// if (eObject.geturi().length() < 2) {
		// System.err.println(">>>>>>>URI SHORT: " + eObject.geturi());
		// System.err.println("CLASS NAME: " + eObject.eClass().getName());
		// }
		// if (eObject.geturifragment().length() < 2) {
		// System.err.println(">>>>>>>>>>>>>>>URI FRAGMENT SHORT: "
		// + eObject.geturi());
		// System.err.println("CLASS NAME: " + eObject.eClass().getName());
		// }

		IGraphNode node = null;

		try {

			// useUUIDs()
			// assignIDsWhileLoading()
			// TODO set these values to true to aid loading non-id models - test

			String eObjectId = getEObjectId(eObject);
			HashMap<String, Object> m = new HashMap<>();
			m.put("id", eObjectId);
			m.put("hashCode", eObject.hashCode());

			LinkedList<IHawkAttribute> normalattributes = new LinkedList<IHawkAttribute>();
			LinkedList<IHawkAttribute> indexedattributes = new LinkedList<IHawkAttribute>();
			// LinkedList<IHawkAttribute> derivedattributes = new
			// LinkedList<IHawkAttribute>();

			for (final IHawkAttribute eAttribute : ((IHawkClass) eObject
					.getType()).getAllAttributes()) {

				if (eObject.isSet(eAttribute)) {

					if (((String[]) typenode.getProperty(eAttribute.getName()))[5]
							.equals("t"))
						indexedattributes.add(eAttribute);

					normalattributes.add(eAttribute);

				} else
				// depracatedTODO currently unset items are not included to may
				// crash eol etc
				{
					// node.setProperty(eAttribute.getName(), "UNSET");
				}
			}

			for (IHawkAttribute a : normalattributes) {

				String type = GraphUtil.toJavaType(a.getType().getName());

				if (!a.isMany()) {

					if (type.equals("String") || type.equals("Boolean")
							|| type.equals("Integer") || type.equals("Real"))
						m.put(a.getName(), eObject.get(a));

					else
						m.put(a.getName(), eObject.get(a).toString());

				}

				else {

					Collection<Object> collection = null;

					if (a.isOrdered() && a.isUnique())
						collection = new LinkedHashSet<Object>();
					else if (a.isOrdered())
						collection = new LinkedList<Object>();
					else if (a.isUnique())
						collection = new HashSet<Object>();
					else
						collection = new LinkedList<Object>();

					for (Object o : (Collection<?>) eObject.get(a)) {

						if (type.equals("String") || type.equals("Boolean")
								|| type.equals("Integer")
								|| type.equals("Real"))
							collection.add(o);

						else
							collection.add(o.toString());

					}

					Object r = null;

					if (type.equals("Integer")) {
						r = Array.newInstance(Integer.class, 1);
					} else if (type.equals("Real")) {
						r = Array.newInstance(Double.class, 1);
					} else if (type.equals("Boolean")) {
						r = Array.newInstance(Boolean.class, 1);
					} else {
						r = Array.newInstance(String.class, 1);
					}

					Object ret = collection.toArray((Object[]) r);

					m.put(a.getName(), ret);

				}

			}

			node = graph.createNode(m, "eobject");

			// propagate changes to listeners
			changes.add(new GraphChangeImpl(true, GraphChangeImpl.INSTANCE,
					node.getId().toString(), null, true));

			for (String s : m.keySet()) {
				Object value = m.get(s);
				changes.add(new GraphChangeImpl(true, GraphChangeImpl.PROPERTY,
						node.getId().toString() + "::" + s, value, true));
			}

			// add derived attrs
			Set<String> attributekeys;
			Hashtable<String, Object> hashed = hashedeclassproperties
					.get(typenode);
			if (hashed == null) {
				attributekeys = typenode.getPropertyKeys();
				System.err
						.println("non-hashed type properties - will slow insert");
			} else
				attributekeys = hashed.keySet();

			for (String attributekey : attributekeys) {

				Object attr = hashed == null ? typenode
						.getProperty(attributekey) : hashed.get(attributekey);

				if (attr instanceof String[]) {

					String[] metadata = (String[]) attr;

					if (metadata[0].equals("d")) {

						// metadata[0] = "d";
						// metadata[1] = (isMany ? "t" : "f");
						// metadata[2] = (isOrdered ? "t" : "f");
						// metadata[3] = (isUnique ? "t" : "f");
						// metadata[4] = attributetype;
						// metadata[5] = derivationlanguage;
						// metadata[6] = derivationlogic;

						m.clear();
						m.put("isMany", metadata[1]);
						m.put("isOrdered", metadata[2]);
						m.put("isUnique", metadata[3]);
						m.put("attributetype", metadata[4]);
						m.put("derivationlanguage", metadata[5]);
						m.put("derivationlogic", metadata[6]);
						m.put(attributekey, "_NYD##" + metadata[6]);

						IGraphNode derivedattributenode = graph.createNode(m,
								"derivedattribute");

						m.clear();
						m.put("isDerived", true);

						graph.createRelationship(node, derivedattributenode,
								attributekey, m);

						addToProxyAttributes(derivedattributenode);

					}

				}

			}

			for (IHawkAttribute a : indexedattributes) {
				IGraphNodeIndex i = graph.getOrCreateNodeIndex(eObject
						.getType().getPackageNSURI()
						+ "##"
						+ eObject.getType().getName() + "##" + a.getName());

				m.clear();
				// graph.setNodeProperty(node,"value",
				// eObject.eGet(a).toString());

				String type = GraphUtil.toJavaType(a.getType().getName());

				if (!a.isMany()) {

					if (type.equals("String") || type.equals("Boolean")
							|| type.equals("Integer") || type.equals("Real"))
						m.put(a.getName(), eObject.get(a));

					else
						m.put(a.getName(), eObject.get(a).toString());

				}

				else {

					Collection<Object> collection = null;

					if (a.isOrdered() && a.isUnique())
						collection = new LinkedHashSet<Object>();
					else if (a.isOrdered())
						collection = new LinkedList<Object>();
					else if (a.isUnique())
						collection = new HashSet<Object>();
					else
						collection = new LinkedList<Object>();

					for (Object o : (Collection<?>) eObject.get(a)) {

						if (type.equals("String") || type.equals("Boolean")
								|| type.equals("Integer")
								|| type.equals("Real"))
							collection.add(o);

						else
							collection.add(o.toString());

					}

					Object r = null;

					if (type.equals("Integer")) {
						r = Array.newInstance(Integer.class, 1);
					} else if (type.equals("Real")) {
						r = Array.newInstance(Double.class, 1);
					} else if (type.equals("Boolean")) {
						r = Array.newInstance(Boolean.class, 1);
					} else {
						r = Array.newInstance(String.class, 1);
					}

					Object ret = collection.toArray((Object[]) r);

					m.put(a.getName(), ret);

				}

				i.add(node, m);
			}

		}

		catch (Exception e) {
			System.err
					.println("GraphModelBatchInjector: createEobjectNode: error in inserting attributes: ");
			e.printStackTrace();
		}

		// dictionary.add(node, "id", eObjectId);

		return node;
	}

	protected void addToProxyAttributes(IGraphNode node) {

		IGraphNodeIndex derivedProxyDictionary = graph
				.getOrCreateNodeIndex("derivedproxydictionary");

		Map<String, Object> m = new HashMap<>();
		m.put("derived", "_");

		derivedProxyDictionary.add(node, m);

	}

	private Hashtable<IHawkClass, IGraphNode> hashedeclasses = new Hashtable<>();
	private Hashtable<IGraphNode, Hashtable<String, Object>> hashedeclassproperties = new Hashtable<>();

	/**
	 * 
	 * @param eClass
	 * @return the ORID of the eClass
	 * @throws Exception
	 */
	private IGraphNode getEClassNode(IHawkClassifier e) throws Exception {

		IHawkClass eClass = null;

		if (e instanceof IHawkClass)
			eClass = ((IHawkClass) e);
		else
			System.err
					.println("getEClassNode called on a non-class classifier:\n"
							+ e);

		IGraphNode classnode = hashedeclasses.get(eClass);

		if (classnode == null) {

			IGraphNode epackagenode = null;
			try {
				epackagenode = epackageDictionary.get("id",
						eClass.getPackageNSURI()).getSingle();
			} catch (NoSuchElementException ex) {

				// graph.exitBatchMode();
				// //
				// addRequiredMetamodels();
				// //
				// graph.enterBatchMode();
				//
				// epackagedictionary = graph.getMetamodelIndex();
				// filedictionary = graph.getFileIndex();
				// proxydictionary =
				// graph.getOrCreateNodeIndex("proxydictionary");
				//
				// epackagenode = epackagedictionary.get("id",
				// eClass.getPackageNSURI()).getSingle();
				//
				throw new Exception(
						"eClass: "
								+ eClass.getName()
								+ "("
								+ eClass.getUri()
								+ ") does not have a Node associated with it in the store, please make sure the relevant metamodel has been inserted");

			} catch (Exception e2) {
				e2.printStackTrace();
			}

			for (IGraphEdge r : epackagenode.getEdges()) {

				IGraphNode othernode = r.getStartNode();

				if (!othernode.equals(epackagenode)
						&& othernode.getProperty("id").equals(eClass.getName())) {
					classnode = othernode;
					break;
				}
			}

			if (classnode != null)
				hashedeclasses.put(eClass, classnode);
			else {
				throw new Exception(
						"eClass: "
								+ eClass.getName()
								+ "("
								+ eClass.getUri()
								+ ") does not have a Node associated with it in the store, please make sure the relevant metamodel has been inserted");

			}

			// cache properties
			Hashtable<String, Object> properties = new Hashtable<>();
			for (String s : classnode.getPropertyKeys()) {
				Object prop = classnode.getProperty(s);
				if (prop instanceof String[])
					properties.put(s, prop);
			}
			hashedeclassproperties.put(classnode, properties);
		}

		return classnode;

	}

	/**
	 * Creates a node with the eObject, adds it to the hash and adds it the the
	 * appropriate eClass in the metatracker collection
	 * 
	 * @param originatingFile
	 * 
	 * @param eObject
	 * @return
	 * @throws Exception
	 */
	protected IGraphNode addEObject(IGraphNode originatingFile, IHawkObject eObject) throws Exception {

		epackageDictionary = graph.getMetamodelIndex();
		fileDictionary = graph.getFileIndex();
		proxyDictionary = graph.getOrCreateNodeIndex("proxydictionary");
		rootDictionary = graph.getOrCreateNodeIndex(ROOT_DICT_NAME);

		IGraphNode eClass = getEClassNode(eObject.getType());
		IGraphNode node = createEObjectNode(eObject, eClass);

		if (hash != null)
			hash.put(eObject, node);

		createReference("typeOf", node, eClass, Collections.emptyMap(), true);
		if (originatingFile != null) {
			createReference("file", node, originatingFile, Collections.emptyMap(), true);
		}
		objectCount[1]++;

		// use metamodel to infer all supertypes for fast search and log em
		for (IHawkClass superType : ((IHawkClass) eObject.getType()).getSuperTypes()) {
			eClass = getEClassNode(superType);
			createReference("kindOf", node, eClass, Collections.emptyMap(), true);
			objectCount[2]++;
		}

		objectCount[0]++;

		if (eObject.isRoot()) {
			rootDictionary.add(node, ROOT_DICT_FILE_KEY, originatingFile.getId().toString());
		}

		return node;
	}

	/**
	 * Creates an edge with the parameters given and links it to the appropriate
	 * nodes
	 * 
	 * @param from
	 * @param to
	 * @param edgelabel
	 * @throws Exception
	 */
	private void addEdge(IHawkObject from, IHawkObject to,
			final String edgelabel, boolean isContainment, boolean isContainer) throws Exception {

		IGraphNode source = null;
		IGraphNode destination = null;
		int error = 0;
		// System.err.println(o);
		try {

			source = hash.get(from);

		} catch (Exception e) {
			error = 1;
		}

		try {

			destination = hash.get(to);

		} catch (Exception e) {
			if (error == 0)
				error = 2;
			else
				error = 3;
		}

		if (error == 0) {

			if (source == null || destination == null) {
				System.err.println("hash error 4, not found from (class: "
						+ (from).getType().getName() + ") and to (class: "
						+ ((IHawkObject) to).getType().getName()
						+ ") on reference: " + edgelabel + " source = "
						+ source + " destination = " + destination);

			} else {

				HashMap<String, Object> props = new HashMap<String, Object>();

				if (isContainment)
					props.put("isContainment", "true");
				if (isContainer)
					props.put("isContainer", "true");

				createReference(edgelabel, source, destination, props, true);

				// new
				// util.ManualReferenceLinking().manualReferenceLinking(graph,
				// edge, source, destination);
				// System.err.println(source+"\n>>>>>>>\n"+edge+"\n>>>>>>\n"+destination);
				// System.err.println(graph.getOutEdges((ODocument)graph.load(source)).size());
				// System.err.println(graph.getInEdges((ODocument)graph.load(source)).size());
				// System.err.println("--------------");
				// System.err.println(graph.getOutEdges((ODocument)graph.load(destination)).size());
				// System.err.println(graph.getInEdges((ODocument)graph.load(destination)).size());
				// System.err.println("-------------------------------");
				// System.err.println(edge);

				objectCount[0]++;
			}
		} else {

			// XXX IMPORTANT NOTE: that for a list of references (auto
			// resolved, nothing
			// to stop it...) if the xmi is not in the changed list, it will
			// crash (as ecore wont be able to resolve the elist of references
			// to return it - ask dimitri)

			if (error == 3) {
				System.err.println("hash error 3, not found from (class: "
						+ (from).getType().getName() + ") and to (class: "
						+ ((IHawkObject) to).getType().getName()
						+ ") on reference: " + edgelabel);
			} else if (error == 1) {
				System.err.println("hash error 1, not found from (class: "
						+ from.getType().getName() + ") on reference: "
						+ edgelabel);
			} else if (error == 2) {
				// System.err.println("hash error 2, not found to (class: "
				// + ((EObject) to).eClass().getName()
				// + ") on reference: " + edgelabel
				// + " [from instance of class: "
				// + from.eClass().getName() + "]");
				System.err
						.println("adding proxy [multi-valued] reference ("
								+ edgelabel
								+ ")... "
								+ (addProxyRef(from, ((IHawkObject) to),
										edgelabel) ? "done" : "failed"));
			}

		}
		// catch (Throwable ee) {
		// // ee.printStackTrace();
		// System.err.println(source + " - " + edgelabel + " - " + destination
		// + " (hash exception (not found))");
		// // System.err.println(((EObject) to).eIsProxy());
		// }

		// System.err.print(edgelabel+".");
	}

	private void createReference(final String edgelabel, IGraphNode source, IGraphNode destination, Map<String, Object> props, boolean isTransient) {
		graph.createRelationship(source, destination, edgelabel, props);

		changes.add(new GraphChangeImpl(true, IGraphChange.REFERENCE,
				source.getId() + "::" + edgelabel,
				destination.getId() + "", isTransient));
	}

	/**
	 * Iterates through all of the references the eObject has and inserts them
	 * into the graph -- not using hash -- for transactional update
	 * 
	 * @param object
	 * @param addedNodesHash
	 * 
	 * @param eObject
	 * @return
	 * @throws Exception
	 */
	protected Set<IGraphChange> addEReferences(IGraphNode node,
			IHawkObject object, Map<String, IGraphNode> addedNodesHash,
			Map<String, IGraphNode> nodes) throws Exception {

		Set<IGraphChange> ret = new HashSet<>();
		try {
			for (final IHawkReference eReference : ((IHawkClass) object.getType()).getAllReferences()) {

				if (object.isSet(eReference)) {

					String edgelabel = eReference.getName();

					Object destinationObject = object.get(eReference, false);

					if (destinationObject instanceof Iterable<?>) {

						for (Object destinationEObject : ((Iterable<?>) destinationObject)) {

							if (!((IHawkObject) destinationEObject).isProxy()) {
								IGraphNode dest = null;
								dest = addedNodesHash
										.get(((IHawkObject) destinationEObject)
												.getUriFragment());
								if (dest == null)
									dest = nodes
											.get(((IHawkObject) destinationEObject)
													.getUriFragment());

								Map<String, Object> props = new HashMap<String, Object>();
								if (eReference.isContainment()) {
									props.put("isContainment", "true");
								}
								if (eReference.isContainer()) {
									props.put("isContainer", "true");
								}

								createReference(edgelabel, node, dest, props, false);
							} else {
								System.err
										.println("adding proxy [iterable] reference ("
												+ edgelabel
												+ ")... "
												+ (addProxyRef(
														node,
														((IHawkObject) destinationEObject),
														edgelabel) ? "done"
														: "failed"));
							}
						}

					} else {

						if (!((IHawkObject) destinationObject).isProxy()) {
							IGraphNode dest = addedNodesHash
									.get(((IHawkObject) destinationObject)
											.getUriFragment());
							if (dest == null)
								dest = nodes
										.get(((IHawkObject) destinationObject)
												.getUriFragment());

							Map<String, Object> props = new HashMap<String, Object>();

							if (eReference.isContainment()) {
								props.put("isContainment", "true");
							}
							if (eReference.isContainer()) {
								props.put("isContainer", "true");
							}

							createReference(edgelabel, node, dest, props, false);
						} else {
							System.err.println("adding proxy reference ("
									+ edgelabel
									+ ")... "
									+ (addProxyRef(node,
											((IHawkObject) destinationObject),
											edgelabel) ? "done" : "failed"));
						}
					}

				}

			}
		} catch (Exception e) {
			System.err
					.println("Error in: addEReference(IGraphNode node, IHawkObject object,	HashMap<String, IGraphNode> nodes):");
			e.printStackTrace();
		}

		return ret;

	}

	/**
	 * Iterates through all of the references the eObject has and inserts them
	 * into the graph -- for batch updates
	 * 
	 * @param originatingFile
	 * 
	 * @param eObject
	 * @throws Exception
	 */
	private boolean addEReferences(IHawkObject eObject) throws Exception {

		boolean atLeastOneSetReference = false;

		for (final IHawkReference eReference : ((IHawkClass) eObject.getType()).getAllReferences()) {
			if (eObject.isSet(eReference)) {
				atLeastOneSetReference = true;

				final String edgelabel = eReference.getName();
				// XXX NOTE THIS SPECIFICALLY DOES NOT RESOLVE PROXIES -
				// REMEMBER IT !
				final Object destinationObject = eObject.get(eReference, false);

				if (destinationObject instanceof Iterable<?>) {
					for (Object destinationEObject : ((Iterable<?>) destinationObject)) {
						final IHawkObject destinationHawkObject = (IHawkObject) destinationEObject;
						if (!destinationHawkObject.isProxy()) {
							addEdge(eObject, destinationHawkObject, edgelabel, eReference.isContainment(), eReference.isContainer());
						} else {
							System.err
									.println("adding proxy [iterable] reference ("
											+ edgelabel
											+ ")... "
											+ (addProxyRef(
													eObject,
													destinationHawkObject,
													edgelabel) ? "done"
													: "failed"));
						}
					}
				} else /* if destination is not iterable */ {
					final IHawkObject destinationHawkObject = (IHawkObject) destinationObject;
					if (!destinationHawkObject.isProxy()) {
						addEdge(eObject, destinationHawkObject, edgelabel, eReference.isContainment(), eReference.isContainer());
					} else {
						System.err.println("adding proxy reference ("
								+ edgelabel
								+ ")... "
								+ (addProxyRef(eObject,
										destinationHawkObject,
										edgelabel) ? "done" : "failed"));
					}
				}

			} else /* if reference is not set */ {
				objectCount[3]++;
			}
		}

		return atLeastOneSetReference;
	}

	private boolean addProxyRef(IHawkObject from,
			IHawkObject destinationObject, String edgelabel) {
		IGraphNode withProxy = hash.get(from);
		return addProxyRef(withProxy, destinationObject, edgelabel);
	}

	private boolean addProxyRef(IGraphNode node, IHawkObject destinationObject,
			String edgelabel) {

		try {
			// proxydictionary.add(graph.getNodeById(hash.get((from))),
			// edgelabel,
			// ((EObject)destinationObject).eIsProxy());

			String uri = destinationObject.getUri();

			// System.err.println(uri.toString().substring(uri.toString().indexOf(".metadata/.plugins/com.google.code.hawk.neo4j/temp/m/")+53));
			// System.err.println(uri.);

			String relativeURI = new DeletionUtils(graph).getRelativeURI(uri
					.toString());
			String relativeFileURI = relativeURI;
			try {
				relativeFileURI = relativeURI.substring(0,
						relativeURI.indexOf("#/"));
			} catch (Exception e) {
				//
			}

			Object proxies = null;
			// if (withProxy.hasProperty("_proxyRef:" + relativeFileURI)) {
			// proxies = withProxy.getProperty("_proxyRef:" + relativeFileURI);
			// }
			// System.err.println(">>>>>>>"+relativeFileURI);

			proxies = node.getProperty("_proxyRef:" + relativeFileURI);
			proxies = new DeletionUtils(graph).add((String[]) proxies,
					relativeURI, edgelabel);

			node.setProperty("_proxyRef:" + relativeFileURI, proxies);

			HashMap<String, Object> m = new HashMap<>();
			m.put("_proxyRef", relativeFileURI);

			proxyDictionary.add(node, m);

		} catch (Exception e) {
			System.err.println("proxydictionary error:");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected boolean resolveProxyRef(IGraphNode n, IGraphNode graphNode,
			String edgelabel) {

		boolean found = false;

		for (IGraphEdge e : n.getOutgoingWithType(edgelabel))
			if (e.getEndNode().getId().equals(graphNode.getId())) {
				found = true;
				break;
			}

		if (found)
			return false;
		else {
			graph.createRelationship(n, graphNode, edgelabel,
					new HashMap<String, Object>());
			return true;
		}
	}

	public int getUnset() {
		return unset;
	}

	private void setUnset(int unset) {
		this.unset = unset;
	}

	public List<IGraphChange> getChanges() {
		return changes;
	}

	public void clearChanges() {
		changes.clear();

	}

}
