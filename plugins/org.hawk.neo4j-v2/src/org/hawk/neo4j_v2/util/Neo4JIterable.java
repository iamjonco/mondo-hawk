/*******************************************************************************
 * Copyright (c) 2011-2015 The University of York.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 3.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-3.0
 *
 * Contributors:
 *     Konstantinos Barmpis - initial API and implementation
 ******************************************************************************/
package org.hawk.neo4j_v2.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.Callable;

import org.hawk.core.graph.IGraphDatabase;
import org.hawk.core.graph.IGraphIterable;
import org.neo4j.graphdb.index.IndexHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Neo4JIterable<T> implements IGraphIterable<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(Neo4JIterable.class);

	Callable<Iterable<?>> query;
	IGraphDatabase graph;

	public Neo4JIterable(Callable<Iterable<?>> query, IGraphDatabase graph) {
		this.query = query;
		this.graph = graph;
	}

	public Neo4JIterable(Iterable<?> query, IGraphDatabase graph) {
		this(() -> query, graph);
	}

	@Override
	public Iterator<T> iterator() {
		try {
			return new Neo4JIterator<T>(query.call().iterator(), graph);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return Collections.emptyIterator();
		}
	}

	@Override
	public int size() {
		try {
			Iterable<?> iterable = query.call();
			if (iterable instanceof IndexHits<?>) {
				return ((IndexHits<?>) iterable).size();
			} else {
				return count(iterable);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return 0;
		}
	}

	private int count(Iterable<?> iterable) {
		int ret = 0;
		for (Object e : iterable) {
			ret++;
		}

		return ret;
	}

	@Override
	public T getSingle() {
		return iterator().next();
	}

}
