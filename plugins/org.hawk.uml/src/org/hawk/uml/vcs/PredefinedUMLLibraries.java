/*******************************************************************************
 * Copyright (c) 2017 Aston University.
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
package org.hawk.uml.vcs;

import java.io.File;
import java.util.Arrays;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.hawk.core.IModelIndexer;
import org.hawk.core.IVcsManager;

/**
 * Exposes all the predefined UML libraries, so they may be indexed by Hawk.
 */
public class PredefinedUMLLibraries extends PathmapResourceCollection implements IVcsManager {

	public PredefinedUMLLibraries() {
		super(UMLResource.LIBRARIES_PATHMAP);
	}

	@Override
	public void init(String vcsloc, IModelIndexer hawk) throws Exception {
		for (String uri : Arrays.asList(
				UMLResource.ECORE_PRIMITIVE_TYPES_LIBRARY_URI,
				UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI,
				UMLResource.JAVA_PRIMITIVE_TYPES_LIBRARY_URI,
				UMLResource.XML_PRIMITIVE_TYPES_LIBRARY_URI)) {
			rs.createResource(URI.createURI(uri)).load(null);
		}
	}

	@Override
	public String getCurrentRevision() throws Exception {
		return getRootNsURI(UMLResource.ECORE_PRIMITIVE_TYPES_LIBRARY_URI);
	}

	@Override
	public String getHumanReadableName() {
		return "UML Predefined Libraries";
	}

	@Override
	public String getRepositoryPath(String rawPath) {
		// The basename of the model should be enough
		return new File(rawPath).getName();
	}

}
