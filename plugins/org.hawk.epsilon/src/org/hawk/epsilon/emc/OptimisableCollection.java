/*******************************************************************************
 * Copyright (c) 2011-2015 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Konstantinos Barmpis - initial API and implementation
 ******************************************************************************/
package org.hawk.epsilon.emc;

import java.util.HashSet;

import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.IAbstractOperationContributor;
import org.eclipse.epsilon.eol.models.IModel;

public class OptimisableCollection extends HashSet<Object> implements
		IAbstractOperationContributor {

	protected EOLQueryEngine model;
	protected GraphNodeWrapper type = null;

	protected OptimisableCollectionSelectOperation indexedAttributeListSelectOperation = new OptimisableCollectionSelectOperation();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OptimisableCollection(IModel m, GraphNodeWrapper t) {

		model = (EOLQueryEngine) m;

		if (type == null)
			type = t;

	}

	@Override
	public AbstractOperation getAbstractOperation(String name) {
		if ("select".equals(name)) {
			return indexedAttributeListSelectOperation;
		} else
			return null;
	}

	public IModel getModel() {
		return model;
	}

}
