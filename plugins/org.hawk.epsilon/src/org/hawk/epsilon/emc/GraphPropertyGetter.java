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
package org.hawk.epsilon.emc;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;
import org.eclipse.epsilon.eol.types.EolOrderedSet;
import org.eclipse.epsilon.eol.types.EolSequence;
import org.hawk.core.IModelIndexer;
import org.hawk.core.graph.IGraphDatabase;
import org.hawk.core.graph.IGraphEdge;
import org.hawk.core.graph.IGraphNode;
import org.hawk.core.util.Utils;
import org.hawk.graph.FileNode;
import org.hawk.graph.ModelElementNode;
import org.hawk.graph.internal.updater.DirtyDerivedAttributesListener;

public class GraphPropertyGetter extends AbstractPropertyGetter {

	public static final String REVERSE_REFNAV_PREFIX = "revRefNav_";

	protected static final int IDX_FLAG_MANY = 1;
	protected static final int IDX_FLAG_ORDERED = 2;
	protected static final int IDX_FLAG_UNIQUE = 3;

	protected static enum PropertyType {
		ATTRIBUTE, DERIVED, REFERENCE, MIXED, INVALID;
		static PropertyType fromCharacter(String s) {
			switch (s) {
			case "d":
				return DERIVED;
			case "r":
				return REFERENCE;
			case "a":
				return ATTRIBUTE;
			case "m":
				return MIXED;
			default:
				return INVALID;
			}
		}
	}

	protected boolean broadcastAccess = false;

	protected IGraphDatabase graph;
	protected EOLQueryEngine m;
	protected IGraphNode featureStartingNodeClassNode = null;
	protected AccessListener accessListener = new AccessListener();

	// Cache: type node -> property name -> property type
	protected Map<IGraphNode, Map<String, PropertyType>> propertyTypeCache = new HashMap<>();
	protected Map<IGraphNode, Map<String, String[]>> propertyTypeFlagsCache = new HashMap<>();

	public GraphPropertyGetter(IGraphDatabase graph2, EOLQueryEngine m) {
		graph = graph2;
		this.m = m;
	}

	@Override
	public Object invoke(Object object, final String property) throws EolRuntimeException {
		if (!(object instanceof GraphNodeWrapper))
			throw new EolRuntimeException("a non GraphNodeWrapper object passed to GraphPropertyGetter!");

		IGraphNode node = ((GraphNodeWrapper) object).getNode();
		Object ret = invokePredefined(property, node);
		if (ret == null) {
			ret = invokeElementProperty(object, property, node);
		}
		if (broadcastAccess)
			broadcastAccess(object, property);

		return ret;
	}

	protected Object invokeElementProperty(final Object obj, final String property, IGraphNode node)
			throws EolRuntimeException, EolIllegalPropertyException {
		PropertyType propertyType = getPropertyType(node, property);
		switch (propertyType) {
		case ATTRIBUTE:
			Object value = node.getProperty(property);
			if (value != null) {
				if (!(isMany(property)))
					return value;
				else
					return new Utils().asList(value);
			} else
				return null;

		case DERIVED:
			Object derivedValue = null;
			for (IGraphEdge r : node.getOutgoingWithType(property)) {
				if (derivedValue != null) {
					throw new EolRuntimeException(String.format(
						"WARNING: a derived property node (arity 1) -- (%s) has more than 1 links in store!", property));
				}

				final IGraphNode nDerived = r.getEndNode();
				derivedValue = nDerived.getProperty(property);
				if (derivedValue == null) {
					List<GraphNodeWrapper> derivedTargets = null;
					for (IGraphEdge edge : nDerived.getOutgoingWithType(EOLQueryEngine.DERIVED_EDGE_PREFIX + property)) {
						if (derivedTargets == null) {
							derivedTargets = new EolSequence<>();
							derivedValue = derivedTargets;
						}
						derivedTargets.add(new GraphNodeWrapper(edge.getEndNode(), m));
					}
				}
			}

			if (derivedValue == null) {
				throw new EolRuntimeException("derived attribute lookup failed for: " + node + " # " + property);
			} else if (derivedValue instanceof String && ((String) derivedValue).startsWith(DirtyDerivedAttributesListener.NOT_YET_DERIVED_PREFIX)) {
				// XXX IDEA: dynamically derive on the spot on access
				System.err.println("attribute: " + property + " is NYD for node: " + node.getId());
			}

			return derivedValue;
		case MIXED:
			final Collection<Object> retCollection = getCollectionForProperty(property);
			if (node.getProperty(property) != null) {
				final List<?> values = new Utils().asList(node.getProperty(property));
				retCollection.addAll(values);
			}
			for (IGraphEdge r : node.getOutgoingWithType(property)) {
				retCollection.add(new GraphNodeWrapper(r.getEndNode(), m));
			}
			return retCollection;

		case REFERENCE:
			GraphNodeWrapper otherNode = null;
			Collection<Object> otherNodes = null;

			// FIXMEdone arity etc in metamodel
			// otherNodes = new EolBag<NeoIdWrapperDebuggable>();
			if (isMany(property)) {
				otherNodes = getCollectionForProperty(property);
			}

			for (IGraphEdge r : node.getOutgoingWithType(property)) {
				if (otherNodes != null)
					otherNodes.add(new GraphNodeWrapper(r.getEndNode(), m));
				else if (otherNode == null)
					otherNode = new GraphNodeWrapper(r.getEndNode(), m);
				else
					throw new EolRuntimeException(
							"A relationship with arity 1 ( " + property + " ) has more than 1 links");
			}

			return otherNodes != null ? otherNodes : otherNode;
		default:
			throw new EolIllegalPropertyException(obj, property, ast, context);
		}
	}

	protected Object invokePredefined(final String property, IGraphNode node) throws EolRuntimeException {
		// avoid using switch (in the outer-most structure) to allow partial
		// matches and method calls in alternatives
		if (property.equals("hawkFile")) {

			String sep = "";
			StringBuilder buff = new StringBuilder(32);
			for (IGraphEdge e : node.getOutgoingWithType(ModelElementNode.EDGE_LABEL_FILE)) {
				buff.append(sep);
				buff.append(e.getEndNode().getProperty(IModelIndexer.IDENTIFIER_PROPERTY).toString());
				sep = ";";

			}
			return buff.toString();

		} else if (property.equals("hawkRepo")) {

			String sep = "";
			StringBuilder buff = new StringBuilder(32);
			for (IGraphEdge e : node.getOutgoingWithType(ModelElementNode.EDGE_LABEL_FILE)) {
				buff.append(sep);
				buff.append(e.getEndNode().getProperty(FileNode.PROP_REPOSITORY).toString());
				sep = ";";

			}
			return buff.toString();

		} else if (property.equals("hawkFiles")) {

			Set<String> files = new HashSet<>();
			for (IGraphEdge e : node.getOutgoingWithType(ModelElementNode.EDGE_LABEL_FILE))
				files.add(e.getEndNode().getProperty(IModelIndexer.IDENTIFIER_PROPERTY).toString());

			return files;

		} else if (property.equals("hawkRepos")) {

			Set<String> repos = new HashSet<>();
			for (IGraphEdge e : node.getOutgoingWithType(ModelElementNode.EDGE_LABEL_FILE))
				repos.add(e.getEndNode().getProperty(FileNode.PROP_REPOSITORY).toString());

			return repos;

		} else if (property.startsWith(REVERSE_REFNAV_PREFIX)) {
			final String referenceName = property.substring(REVERSE_REFNAV_PREFIX.length());

			final EolSequence<GraphNodeWrapper> ret = new EolSequence<GraphNodeWrapper>();
			for (IGraphEdge r : node.getIncomingWithType(referenceName)) {
				ret.add(new GraphNodeWrapper(r.getStartNode(), m));
			}
			for (IGraphEdge r : node.getIncomingWithType(EOLQueryEngine.DERIVED_EDGE_PREFIX + referenceName)) {
				IGraphNode derivedNode = r.getStartNode();
				IGraphNode elementNode = derivedNode.getIncoming().iterator().next().getStartNode();
				ret.add(new GraphNodeWrapper(elementNode, m));
			}

			return ret;
		} else if (property.equals("eContainer")) {
			GraphNodeWrapper ret = null;
			for (IGraphEdge r : node.getIncoming()) {
				if (r.getProperty(ModelElementNode.EDGE_PROPERTY_CONTAINMENT) != null) {
					ret = new GraphNodeWrapper(r.getStartNode(), m);
					break;
				}
			}
			if (ret == null) {
				for (IGraphEdge r : node.getOutgoing()) {
					if (r.getProperty(ModelElementNode.EDGE_PROPERTY_CONTAINER) != null) {
						ret = new GraphNodeWrapper(r.getEndNode(), m);
						break;
					}
				}
			}
			if (ret == null)
				throw new EolRuntimeException("eContainer failed,\n" + node + "\nis not contained");

			return ret;
		} else if (property.equals("eContents")) {
			final List<GraphNodeWrapper> results = new EolSequence<>();
			for (IGraphEdge r : node.getOutgoing()) {
				if (r.getProperty(ModelElementNode.EDGE_PROPERTY_CONTAINMENT) != null) {
					results.add(new GraphNodeWrapper(r.getEndNode(), m));
				}
			}
			for (IGraphEdge r : node.getIncoming()) {
				if (r.getProperty(ModelElementNode.EDGE_PROPERTY_CONTAINER) != null) {
					results.add(new GraphNodeWrapper(r.getStartNode(), m));
				}
			}
			return results;
		} else if (property.equals("hawkIn") || property.equals("hawkOut")) {
			final boolean isIncoming = property.equals("hawkIn");
			final List<GraphNodeWrapper> results = new EolSequence<>();
			final Iterable<IGraphEdge> edges = isIncoming ? node.getIncoming() : node.getOutgoing();
			for (IGraphEdge r : edges) {
				if (ModelElementNode.TRANSIENT_EDGE_LABELS.contains(r.getType())) {
					continue;
				}
				final IGraphNode edgeNode = isIncoming ? r.getStartNode() : r.getEndNode();
				final GraphNodeWrapper edgeNodeWrapper = new GraphNodeWrapper(edgeNode, m);
				results.add(edgeNodeWrapper);
			}
			return results;
		} else if (property.equals("hawkURIFragment")) {
			return node.getProperty(IModelIndexer.IDENTIFIER_PROPERTY);
		}
		else {
			return null;
		}
	}

	protected Collection<Object> getCollectionForProperty(final String property) {
		if (isUnique(property))
			return new EolOrderedSet<Object>();
		else
			return new EolSequence<Object>();
	}

	protected void broadcastAccess(Object object, String property) {
		accessListener.accessed(((GraphNodeWrapper) object) + "", property);
	}

	public void setBroadcastAccess(boolean b) {
		broadcastAccess = b;
	}

	public AccessListener getAccessListener() {
		return accessListener;
	}

	public IGraphDatabase getGraph() {
		return graph;
	}

	public boolean getBroadcastStatus() {
		return broadcastAccess;
	}

	protected boolean canHaveDerivedAttr(IGraphNode node, String property) {
		return getPropertyType(node, property) == PropertyType.DERIVED;
	}

	protected boolean canHaveMixed(IGraphNode node, String property) {
		return getPropertyType(node, property) == PropertyType.MIXED;
	}

	protected boolean canHaveAttr(IGraphNode node, String property) {
		return getPropertyType(node, property) == PropertyType.ATTRIBUTE;
	}

	protected boolean canHaveRef(IGraphNode node, String property) {
		return getPropertyType(node, property) == PropertyType.REFERENCE;
	}

	protected boolean isMany(String ref) {
		return isTypeFlagActive(ref, IDX_FLAG_MANY);
	}

	protected boolean isOrdered(String ref) {
		return isTypeFlagActive(ref, IDX_FLAG_ORDERED);
	}

	protected boolean isUnique(String ref) {
		return isTypeFlagActive(ref, IDX_FLAG_UNIQUE);
	}

	protected PropertyType getPropertyType(IGraphNode node, String property) {
		final Iterator<IGraphEdge> itTypeOf = node.getOutgoingWithType(ModelElementNode.EDGE_LABEL_OFTYPE).iterator();

		if (itTypeOf.hasNext()) {
			featureStartingNodeClassNode = itTypeOf.next().getEndNode();
			Map<String, PropertyType> knownProperties = propertyTypeCache.get(featureStartingNodeClassNode);
			if (knownProperties == null) {
				knownProperties = new HashMap<>();
				propertyTypeCache.put(featureStartingNodeClassNode, knownProperties);
			}

			PropertyType actual = knownProperties.get(property);
			if (actual == null) {
				String value = "_null_hawk_value_error";

				if (featureStartingNodeClassNode.getProperty(property) != null)
					value = ((String[]) featureStartingNodeClassNode.getProperty(property))[0];

				actual = PropertyType.fromCharacter(value);
				if (actual == PropertyType.INVALID) {
					System.err.println("property: " + property + " not found in metamodel for type: "
							+ featureStartingNodeClassNode.getProperty(IModelIndexer.IDENTIFIER_PROPERTY));
				}
				knownProperties.put(property, actual);
			}
			return actual;
		} else {
			System.err.println("type not found for node " + node);
		}

		return PropertyType.INVALID;
	}

	protected boolean isTypeFlagActive(String reference, final int index) {
		if (featureStartingNodeClassNode == null) {
			System.err.println("type not found previously for " + reference);
			return false;
		}

		// Cache type flags
		Map<String, String[]> knownTypeFlags = propertyTypeFlagsCache.get(featureStartingNodeClassNode);
		if (knownTypeFlags == null) {
			knownTypeFlags = new HashMap<>();
			propertyTypeFlagsCache.put(featureStartingNodeClassNode, knownTypeFlags);
		}
		String[] typeFlags = knownTypeFlags.get(reference);
		if (typeFlags == null && featureStartingNodeClassNode.getProperty(reference) != null) {
			typeFlags = (String[]) featureStartingNodeClassNode.getProperty(reference);
			knownTypeFlags.put(reference, typeFlags);
		}
		if (typeFlags != null) {
			return typeFlags[index].equals("t");
		}

		System.err.println("reference: " + reference + " not found in metamodel (isMany) for type: "
				+ featureStartingNodeClassNode.getProperty(IModelIndexer.IDENTIFIER_PROPERTY));
		return false;
	}

	public String debug(GraphNodeWrapper object) {

		IGraphNode node = object.getNode();

		String ret = node.toString();

		for (String p : node.getPropertyKeys()) {

			Object n = node.getProperty(p);
			String temp = "error: " + n.getClass();
			if (n instanceof int[])
				temp = Arrays.toString((int[]) n);
			if (n instanceof long[])
				temp = Arrays.toString((long[]) n);
			if (n instanceof String[])
				temp = Arrays.toString((String[]) n);
			if (n instanceof boolean[])
				temp = Arrays.toString((boolean[]) n);

			ret = ret + ("[" + p + ";" + (((p.equals("class") || p.equals("superclass")))
					? (temp.length() < 1000 ? temp : "[<TOO LONG TO LOG (>1000chars)>]") : (n)) + "] ");
		}

		Collection<String> refs = new HashSet<String>();

		for (IGraphEdge r : node.getOutgoing()) {

			refs.add(r.getType().toString());

		}

		return ret + "\nOF TYPE: " + new MetamodelUtils().typeOfName(node)

				+ "\nWITH OUTGOING REFERENCES: " + refs;
	}

}
