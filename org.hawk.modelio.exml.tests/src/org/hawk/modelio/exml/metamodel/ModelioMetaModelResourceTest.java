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
package org.hawk.modelio.exml.metamodel;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.hawk.core.model.IHawkObject;
import org.hawk.core.model.IHawkPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link ModelioMetaModelResource} class.
 */
public class ModelioMetaModelResourceTest {

	private ModelioMetaModelResource r;

	@Before
	public void setup() {
		r = new ModelioMetaModelResource(new ModelioMetaModelResourceFactory());
	}

	@Test
	public void countPackages() {
		List<IHawkPackage> rootPackages = new ArrayList<>();
		for (IHawkObject o : r.getAllContents()) {
			if (o instanceof IHawkPackage && ((IHawkPackage)o).isRoot()) {
				rootPackages.add((IHawkPackage)o);
			}
		}
		assertEquals("There should be 1 IHawkPackage per root MPackage + 1 meta package", 6, rootPackages.size());
	}
}
