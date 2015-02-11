/*******************************************************************************
 * Copyright (c) 2011-2014 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Konstantinos Barmpis - initial API and implementation
 *     Bergmann Gabor		- mondix API
 ******************************************************************************/
package org.hawk.mondix.query;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hawk.core.IAbstractConsole;
import org.hawk.core.graph.IGraphDatabase;
import org.hawk.core.graph.IGraphNode;
import org.hawk.core.graph.IGraphNodeIndex;
import org.hawk.core.graph.IGraphTransaction;
import org.hawk.core.model.IHawkIterable;
import org.hawk.core.util.DefaultConsole;
import org.hawk.mondix.HawkMondixInstance;
import org.hawk.mondix.relations.HawkFileMondixRelation;
import org.hawk.neo4j_v2.Neo4JDatabase;

import eu.mondo.mondix.core.IMondixRelation;
import eu.mondo.mondix.core.IMondixView;

public class HawkMetamodelQueryInstance extends HawkQueryInstance implements
IMondixView {

	protected IGraphDatabase graph;
	private IMondixRelation baseRelation;
	protected List<String> selectedColumnNames = new LinkedList<String>();
	private Map<String, Object> filter = new HashMap<String, Object>();

	private HashSet<List<Object>> contents = null;

	String id = null;
	String nsUri = null;
	private static IAbstractConsole console = new DefaultConsole();

	public static void main(String[] _) throws Exception {

		IGraphDatabase d = new Neo4JDatabase();
		d.run("test_db_fragmented", new File(
				"D:/workspace/_hawk_runtime_example/runtime_data"), console);
		HawkMetamodelQueryInstance h = new HawkMetamodelQueryInstance(
				new HawkFileMondixRelation(new HawkMondixInstance(d)), d);

		// try (IGraphTransaction t = h.graph.beginTransaction()) {
		//
		console.println("start");

		// h.nsUri = "org.amma.dsl.jdt.dom";

		h.getCountOfTuples();
		// h.getAllTuples();

		console.println("done");

		// ((FileOutputConsole) console).flush();
	}

	public HawkMetamodelQueryInstance(IMondixRelation rel, IGraphDatabase graph) {

		super(rel, graph);
		baseRelation = rel;
		this.graph = graph;
		selectedColumnNames = baseRelation.getColumns();

	}

	public HawkMetamodelQueryInstance(IGraphDatabase graph, List<String> cols,
			Map<String, Object> f, IMondixRelation rel) {

		super(graph, cols, f, rel);
		baseRelation = rel;
		this.graph = graph;
		if (cols != null)
			selectedColumnNames = cols;
		else
			selectedColumnNames = baseRelation.getColumns();

		if (f != null)
			filter = f;

		id = (String) filter.get("id");
		nsUri = (String) filter.get("nsUri");

	}

	public IMondixRelation getBaseRelation() {

		return baseRelation;

	}

	public List<String> getSelectedColumnNames() {

		return selectedColumnNames;

	}

	public Map<String, Object> getFilter() {

		return filter;

	}

	public void dispose() {

		graph = null;
		baseRelation = null;
		selectedColumnNames = null;
		filter = null;
		contents = null;

	}

	public int getCountOfTuples() {

		int ret = 0;

		if (contents == null)
			setContents();

		ret = contents.size();

		return ret;

	}

	public Iterable<? extends List<?>> getAllTuples() {

		if (contents == null)
			setContents();

		return contents;

	}

	private void setContents() {

		contents = new HashSet<>();

		try (IGraphTransaction t = graph.beginTransaction()) {
			//
			IGraphNodeIndex metamodelindex = graph.getMetamodelIndex();

			if (id == null && nsUri == null) {
				IHawkIterable<IGraphNode> metamodelnodes = metamodelindex
						.query("id", "*");
				for (IGraphNode n : metamodelnodes)
					addToContents(n.getId() + "", n.getProperty("id"));

			} else if (nsUri != null) {
				IHawkIterable<IGraphNode> filenodes = metamodelindex.get("id",
						nsUri);
				int size = filenodes.size();
				if (size == 1) {
					addToContents(filenodes.getSingle().getId() + "", nsUri);
				} else if (size > 1) {
					System.err
							.println("error in hawkfilequeryinstance setContents() more than 1 file found for uri: "
									+ nsUri
									+ " ignoring this uri (returning no matches)");
				}
			} else {// do nothing, not supported
			}
			t.success();
		} catch (Exception e) {
			System.err
					.println("exception in IGraphTransaction beginTransaction():");
			e.printStackTrace();
		}
	}

	private void addToContents(Object id1, Object id2) {

		// Long id, String uri
		List<Object> list = new LinkedList<Object>();

		if (selectedColumnNames.contains("id"))
			list.add(id1);
		if (selectedColumnNames.contains("nsUri"))
			list.add(id2);

		console.println(list.toString());

		contents.add(list);

	}

}
