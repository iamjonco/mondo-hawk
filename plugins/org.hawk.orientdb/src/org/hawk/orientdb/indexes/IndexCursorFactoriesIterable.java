/*******************************************************************************
 * Copyright (c) 2015 The University of York.
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
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.hawk.orientdb.indexes;

import java.util.Iterator;

import org.hawk.core.graph.IGraphIterable;
import org.hawk.orientdb.OrientDatabase;

final class IndexCursorFactoriesIterable<T> implements IGraphIterable<T> {
	private final Iterable<OIndexCursorFactory> iterFactories;
	private final OrientDatabase graph;
	private final Class<T> klass;

	IndexCursorFactoriesIterable(Iterable<OIndexCursorFactory> iterFactories, OrientDatabase graph, Class<T> klass) {
		this.iterFactories = iterFactories;
		this.graph = graph;
		this.klass = klass;
	}

	@Override
	public Iterator<T> iterator() {
		final Iterator<OIndexCursorFactory> itFactory = iterFactories.iterator(); 
		return new Iterator<T>() {
			Iterator<T> currentIterator = null;

			@Override
			public boolean hasNext() {
				while (currentIterator == null || !currentIterator.hasNext()) {
					if (itFactory.hasNext()) {
						currentIterator = new IndexCursorFactoryNodeIterable<>(itFactory.next(), graph, klass).iterator();
					} else {
						return false;
					}
				}
				return currentIterator != null && currentIterator.hasNext();
			}

			@Override
			public T next() {
				return currentIterator.next();
			}

			@Override
			public void remove() {
				currentIterator.remove();
			}
			
		};
	}

	@Override
	public int size() {
		int count = 0;
		for (OIndexCursorFactory factory : iterFactories) {
			count += new IndexCursorFactoryNodeIterable<>(factory, graph, klass).size();
		}
		return count;
	}

	@Override
	public T getSingle() {
		return iterator().next();
	}
}