/*******************************************************************************
 * Copyright (c) 2015 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.hawk.orientdb.indexes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.hawk.core.graph.IGraphIterable;
import org.hawk.core.graph.IGraphNode;
import org.hawk.core.graph.IGraphNodeIndex;
import org.hawk.orientdb.OrientDatabase;
import org.hawk.orientdb.OrientIndexStore;
import org.hawk.orientdb.OrientNode;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.index.OIndex;
import com.orientechnologies.orient.core.index.OIndexCursor;
import com.orientechnologies.orient.core.index.OIndexFactory;
import com.orientechnologies.orient.core.index.OIndexManager;
import com.orientechnologies.orient.core.index.OIndexManagerProxy;
import com.orientechnologies.orient.core.index.OIndexes;
import com.orientechnologies.orient.core.index.OSimpleKeyIndexDefinition;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.impls.orient.OrientExtendedVertex;

/**
 * Logical index for nodes, which combines exact indexes (based on an SBTree)
 * with Lucene indexes. Both are needed, since Lucene indexes do not support
 * iterating over all values. Vertex indexes are not useful either, as they
 * would not easily support iterating over all values indexed with a certain
 * field.
 *
 * Additionally, OrientDB has only one level of naming for indexes. To overcome
 * this, adding a key to a field F for the first time will create a new index of
 * each type named <code>name SEPARATOR_EXACT F</code> and
 * <code>name SEPARATOR_LUCENE F</code>, respectively.
 *
 * Node index names and node index field names are kept in a singleton vertex
 * type, maintained by the {@link OrientIndexStore} class.
 */
public class OrientNodeIndex implements IGraphNodeIndex {

	private static final class EmptyIGraphIterable implements IGraphIterable<IGraphNode> {
		@Override
		public Iterator<IGraphNode> iterator() {
			return Collections.emptyListIterator();
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public IGraphNode getSingle() {
			return null;
		}
	}

	private final class StarKeyValueOIndexCursorFactoryIterable implements Iterable<OIndexCursorFactory> {
		private final Object valueExpr;
		private final Set<String> valueIdxNames;

		private StarKeyValueOIndexCursorFactoryIterable(Object valueExpr, Set<String> valueIdxNames) {
			this.valueExpr = valueExpr;
			this.valueIdxNames = valueIdxNames;
		}

		@Override
		public Iterator<OIndexCursorFactory> iterator() {
			final Iterator<String> itIdxNames = valueIdxNames.iterator();
			return new Iterator<OIndexCursorFactory>() {
				@Override
				public boolean hasNext() {
					return itIdxNames.hasNext();
				}

				@Override
				public OIndexCursorFactory next() {
					return new SingleKeyValueQueryOIndexCursorFactory(valueExpr, itIdxNames.next());
				}

				@Override
				public void remove() {
					itIdxNames.remove();
				}
			};
		}
	}

	private final class SingleKeyValueQueryOIndexCursorFactory implements OIndexCursorFactory {
		private final Object valueExpr;
		private final String key;

		private SingleKeyValueQueryOIndexCursorFactory(Object valueExpr, String key) {
			this.valueExpr = valueExpr;
			this.key = key;
		}

		@Override
		public Iterator<OIdentifiable> query() {
			final boolean requiresLucene = requiresLuceneIndex(valueExpr);
			final OIndex<?> index = getIndexManager().getIndex(requiresLucene ? getLuceneIndexName(key) : getExactIndexName(key));
			if (index == null) {
				return Collections.emptyListIterator();
			}

			if ("*".equals(valueExpr)) {
				return index.cursor();
			} else if (requiresLucene) {
				final Object escaped = QueryParser.escape(valueExpr.toString()).replace("\\*", "*");
				return index.iterateEntries(Collections.singleton(escaped), false);
			} else {
				return index.iterateEntries(Collections.singleton(valueExpr), false);
			}
		}
	}

	static boolean requiresLuceneIndex(Object valueExpr) {
		return valueExpr instanceof String && !"*".equals(valueExpr) && valueExpr.toString().contains("*");
	}

	private static final String SEPARATOR_EXACT = "_@exact@_";
	private static final String SEPARATOR_LUCENE = "_@lucene@_";

	private String name;
	private OrientDatabase graph;

	public OrientNodeIndex(String name, OrientDatabase graph) {
		this.name = name;
		this.graph = graph;

		final OrientIndexStore idxStore = graph.getIndexStore();
		idxStore.addNodeIndex(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IGraphIterable<IGraphNode> query(final String key, Object valueExpr) {
		valueExpr = normalizeValue(valueExpr);
		if ("*".equals(key)) {
			final Set<String> valueIdxNames = graph.getIndexStore().getNodeFieldIndexNames(name);
			final Iterable<OIndexCursorFactory> iterFactories = new StarKeyValueOIndexCursorFactoryIterable(valueExpr, valueIdxNames);
			return new IndexCursorFactoriesIterable(iterFactories, graph);
		} else {
			final SingleKeyValueQueryOIndexCursorFactory factory = new SingleKeyValueQueryOIndexCursorFactory(valueExpr, key);
			return new IndexCursorFactoryIterable(factory, graph);
		}
	}

	@Override
	public IGraphIterable<IGraphNode> query(final String key, final int from, final int to, final boolean fromInclusive, final boolean toInclusive) {
		final OIndex<?> exactIndex = getIndexManager().getIndex(getExactIndexName(key));
		if (exactIndex == null) {
			return new EmptyIGraphIterable();
		}
		return new IndexCursorFactoryIterable(new OIndexCursorFactory() {
			@Override
			public Iterator<OIdentifiable> query() {
				return exactIndex.iterateEntriesBetween(from, fromInclusive, to, toInclusive, false);
			}
		}, graph);
	}

	@Override
	public IGraphIterable<IGraphNode> query(final String key, final double from, final double to, final boolean fromInclusive, final boolean toInclusive) {
		final OIndex<?> exactIndex = getIndexManager().getIndex(getExactIndexName(key));
		if (exactIndex == null) {
			return new EmptyIGraphIterable();
		}
		return new IndexCursorFactoryIterable(new OIndexCursorFactory() {
			@Override
			public Iterator<OIdentifiable> query() {
				return exactIndex.iterateEntriesBetween(from, fromInclusive, to, toInclusive, false);
			}
		}, graph);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IGraphIterable<IGraphNode> get(final String key, Object valueExpr) {
		valueExpr = normalizeValue(valueExpr);
		final OIndex<?> idx = getOrCreateFieldIndex(key, valueExpr.getClass(), requiresLuceneIndex(valueExpr));
		final Collection<OIdentifiable> resultSet = (Collection<OIdentifiable>) idx.get(valueExpr);
		return new IGraphIterable<IGraphNode>() {
			@Override
			public Iterator<IGraphNode> iterator() {
				if (resultSet == null || resultSet.isEmpty()) {
					return Collections.emptyListIterator();
				} else {
					return Collections.singleton(getSingle()).iterator();
				}
			}

			@Override
			public int size() {
				return resultSet.size();
			}

			@Override
			public IGraphNode getSingle() {
				final Iterator<OIdentifiable> iterator = resultSet.iterator();
				if (iterator.hasNext()) {
					return new OrientNode(graph.getVertex(iterator.next().getIdentity()), graph);
				}
				return null;
			}

		};
	}

	@Override
	public void add(IGraphNode n, Map<String, Object> derived) {
		final OrientNode orientNode = (OrientNode) n;
		final OrientExtendedVertex eVertex = (OrientExtendedVertex) orientNode.getVertex();

		for (Entry<String, Object> entry : derived.entrySet()) {
			final String field = entry.getKey();
			final Object valueExpr = normalizeValue(entry.getValue());
			final Class<?> valueClass = valueExpr.getClass();
			final OIndex<?> exactIndex = getOrCreateFieldIndex(field, valueClass, false);

			exactIndex.put(valueExpr, eVertex.getRecord());
			if (valueExpr instanceof String) {
				final OIndex<?> luceneIndex = getOrCreateFieldIndex(field, valueClass, true);
				luceneIndex.put(valueExpr, eVertex.getRecord());
			}
		}
	}

	/**
	 * Normalizes a value expression so it'll always be either an Integer, a
	 * Double or a String.
	 */
	private static Object normalizeValue(Object valueExpr) {
		if (valueExpr instanceof Byte || valueExpr instanceof Short || valueExpr instanceof Long) {
			valueExpr = ((Number)valueExpr).intValue();
		} else if (valueExpr instanceof Float) {
			valueExpr = ((Float)valueExpr).doubleValue();
		} else if (valueExpr instanceof String || valueExpr instanceof Integer || valueExpr instanceof Double) {
			return valueExpr;
		}
		return valueExpr.toString();
	}

	@Override
	public void add(IGraphNode n, String s, Object derived) {
		add(n, Collections.singletonMap(s, derived));
	}

	@Override
	public void remove(IGraphNode n) {
		final OrientNode oNode = (OrientNode)n;
		final OrientIndexStore store = graph.getIndexStore();
		for (String fieldName : store.getNodeFieldIndexNames(name)) {
			remove(fieldName, oNode);
		}
	}

	@Override
	public void remove(String field, Object value, IGraphNode n) {
		final OrientNode oNode = (OrientNode)n;

		if (field == null && value == null) {
			remove(n);
		} else if (field == null) {
			final OrientIndexStore store = graph.getIndexStore();
			for (String fieldName : store.getNodeFieldIndexNames(name)) {
				remove(fieldName, value, n);
			}
		} else if (value == null) {
			remove(field, oNode);
		} else {
			value = normalizeValue(value);

			final OIndex<?> exactIndex = getIndexManager().getIndex(getExactIndexName(field));
			if (exactIndex != null) {
				exactIndex.remove(value, oNode.getVertex());
			}

			final OIndex<?> luceneIndex = getIndexManager().getIndex(getLuceneIndexName(field));
			if (luceneIndex != null) {
				luceneIndex.remove(value, oNode.getVertex());
			}
		}
	}

	private void remove(String field, final OrientNode n) {
		final List<Object> keysToRemove = new ArrayList<>();
		final OIndex<?> exactIndex = getIndexManager().getIndex(getExactIndexName(field));
		if (exactIndex == null) return;

		final OIndexCursor exactCursor = exactIndex.cursor();
		for (Entry<Object, OIdentifiable> entry = exactCursor.nextEntry(); entry != null; entry = exactCursor.nextEntry()) {
			if (n.getId().equals(entry.getValue().getIdentity())) {
				keysToRemove.add(entry.getKey());
			}
		}

		final OIndex<?> luceneIndex = getIndexManager().getIndex(getLuceneIndexName(field));
		if (luceneIndex != null) {
			for (Object o : keysToRemove) {
				exactIndex.remove(o, n.getVertex());
				luceneIndex.remove(o, n.getVertex());
			}
		}
	}

	@Override
	public void flush() {
		final OrientBaseGraph orientGraph = graph.getGraph();
		if (orientGraph != null) {
			orientGraph.commit();
		}
	}

	@Override
	public void delete() {
		OrientIndexStore store = graph.getIndexStore();
		for (String fieldName : store.getNodeFieldIndexNames(name)) {
			final OIndex<?> exactIndex = getIndexManager().getIndex(getExactIndexName(fieldName));
			if (exactIndex != null) {
				exactIndex.delete();
			}

			final OIndex<?> luceneIndex = getIndexManager().getIndex(getLuceneIndexName(fieldName));
			if (luceneIndex != null) {
				luceneIndex.delete();
			}
		}
		store.removeNodeIndex(name);
	}

	private OIndex<?> getOrCreateFieldIndex(final String field, final Class<?> valueClass, final boolean requiresLucene) {
		final String idxName = requiresLucene ? getLuceneIndexName(field) : getExactIndexName(field);
		final OIndexManager indexManager = getIndexManager();
		OIndex<?> idx = indexManager.getIndex(idxName);

		if (idx == null) {
			createIndexes(field, valueClass);

			// We need to fetch again the index: using the one that was just
			// created will result in multithreading exceptions from OrientDB
			idx = indexManager.getIndex(idxName);
		}
		return idx;
	}

	/**
	 * Creates the exact and Lucene indexes paired to this field within this logical index. 
	 */
	private void createIndexes(final String field, final Class<?> keyClass) {
		final OIndexManager indexManager = getIndexManager();

		// Indexes have to be created outside transactions
		final boolean wasTransactional = graph.currentMode() == OrientDatabase.TX_MODE;
		if (wasTransactional) {
			graph.enterBatchMode();
		}

		if (keyClass == String.class) {
			// Lucene index, for prefix*suffix queries with string keys
			final String luceneName = getLuceneIndexName(field);
			final OIndexFactory luceneFactory = OIndexes.getFactory("FULLTEXT", "LUCENE");
			final OSimpleKeyIndexDefinition indexDefinition = new OSimpleKeyIndexDefinition(luceneFactory.getLastVersion(), OType.STRING);
			final ODocument metadata = new ODocument().field("analyzer", "org.apache.lucene.analysis.core.WhitespaceAnalyzer");
			indexManager.createIndex(luceneName, "FULLTEXT", indexDefinition, null, null, metadata, "LUCENE");
		}

		// Exact index key type
		OType keyType = OType.STRING;
		if (keyClass == Byte.class || keyClass == Short.class || keyClass == Integer.class || keyClass == Long.class) {
			keyType = OType.INTEGER;
		} else if (keyClass == Float.class || keyClass == Double.class) {
			keyType = OType.DOUBLE;
		}

		// Exact index, for exact queries, numeric ranges and iteration
		final String exactName = getExactIndexName(field);
		final OIndexFactory exactFactory = OIndexes.getFactory("NOTUNIQUE", "SBTREE");
		final OSimpleKeyIndexDefinition exactIndexDef = new OSimpleKeyIndexDefinition(exactFactory.getLastVersion(), keyType);
		indexManager.createIndex(exactName, "NOTUNIQUE", exactIndexDef, null, null, null, "SBTREE");

		graph.getIndexStore().addNodeFieldIndex(name, field);

		if (wasTransactional) {
			graph.exitBatchMode();
		}
	}

	private String getExactIndexName(final String field) {
		return name + SEPARATOR_EXACT + field.replace(':', '!');
	}

	private String getLuceneIndexName(final String field) {
		return name + SEPARATOR_LUCENE + field.replace(':', '!');
	}

	private OIndexManager getIndexManager() {
		final ODatabaseDocumentTx rawGraph = graph.getGraph().getRawGraph();
		final OIndexManagerProxy indexManager = rawGraph.getMetadata().getIndexManager();
		return indexManager;
	}

}