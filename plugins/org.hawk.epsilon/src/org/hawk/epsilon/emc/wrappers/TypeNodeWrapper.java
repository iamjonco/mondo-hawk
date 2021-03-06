/*******************************************************************************
 * Copyright (c) 2011-2016 The University of York.
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
package org.hawk.epsilon.emc.wrappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hawk.core.graph.IGraphNode;
import org.hawk.core.graph.IGraphTypeNodeReference;
import org.hawk.core.graph.timeaware.ITimeAwareGraphNode;
import org.hawk.core.query.IQueryEngine;
import org.hawk.epsilon.emc.EOLQueryEngine;
import org.hawk.graph.ModelElementNode;
import org.hawk.graph.Slot;
import org.hawk.graph.TypeNode;

public class TypeNodeWrapper implements IGraphTypeNodeReference {

	private final TypeNode typeNode;
	private final EOLQueryEngine model;

	public TypeNodeWrapper(TypeNode node, EOLQueryEngine model) {
		this.typeNode = node;
		this.model = model;
	}

	@Override
	public String getId() {
		return typeNode.getNode().getId().toString();
	}

	@Override
	public IGraphNode getNode() {
		return typeNode.getNode();
	}

	@Override
	public IQueryEngine getContainerModel() {
		return model;
	}

	@Override
	public String getTypeName() {
		return "_hawkTypeNode";
	}

	public String getName() {
		return typeNode.getTypeName();
	}

	public Collection<Object> getAll() {
		return model.getAllOf(typeNode.getNode(), ModelElementNode.EDGE_LABEL_OFKIND);
	}

	public List<Slot> getAttributes() {
		List<Slot> attributes = new ArrayList<>();
		for (Slot s : typeNode.getSlots().values()) {
			if (s.isAttribute()) {
				attributes.add(s);
			}
		}
		return attributes;
	}

	public List<Slot> getReferences() {
		List<Slot> attributes = new ArrayList<>();
		for (Slot s : typeNode.getSlots().values()) {
			if (s.isReference()) {
				attributes.add(s);
			}
		}
		return attributes;
	}

	public Collection<Slot> getFeatures() {
		return typeNode.getSlots().values();
	}

	public MetamodelNodeWrapper getMetamodel() {
		return new MetamodelNodeWrapper(typeNode.getMetamodel(), model);
	}

	@Override
	public String toString() {
		return String.format("TNW|id:%s|type:%s", getId(), getTypeName());
	}

	/* TIME AWARE OPERATIONS - FOR USE WITHIN EOL */

	public TypeNodeWrapper getnext() throws Exception {
		if (typeNode.getNode() instanceof ITimeAwareGraphNode) {
			final ITimeAwareGraphNode taNode = (ITimeAwareGraphNode)typeNode.getNode();
			final ITimeAwareGraphNode next = taNode.getNext();
			if (next == null) {
				return null;
			}

			return new TypeNodeWrapper(new TypeNode(next), model);
		}
		throw new UnsupportedOperationException("Node of type " + typeNode.getClass().getName() + " is not time aware");
	}

	public TypeNodeWrapper getprevious() throws Exception {
		if (typeNode.getNode() instanceof ITimeAwareGraphNode) {
			final ITimeAwareGraphNode taNode = (ITimeAwareGraphNode)typeNode.getNode();
			final ITimeAwareGraphNode prev = taNode.getPrevious();
			if (prev == null) {
				return null;
			}
			return new TypeNodeWrapper(new TypeNode(prev), model);
		}
		throw new UnsupportedOperationException("Node of type " + typeNode.getClass().getName() + " is not time aware");
	}

	public TypeNodeWrapper getprev() throws Exception {
		return getprevious();
	}

	public long gettime() throws Exception {
		if (typeNode.getNode() instanceof ITimeAwareGraphNode) {
			return ((ITimeAwareGraphNode)typeNode.getNode()).getTime();
		}
		throw new UnsupportedOperationException("Node of type " + typeNode.getClass().getName() + " is not time aware");
	}

}