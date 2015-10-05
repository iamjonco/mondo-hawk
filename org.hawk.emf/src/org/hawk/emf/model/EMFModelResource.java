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
package org.hawk.emf.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.hawk.core.IModelResourceFactory;
import org.hawk.core.model.IHawkModelResource;
import org.hawk.core.model.IHawkObject;
import org.hawk.emf.EMFObject;

public class EMFModelResource implements IHawkModelResource {

	Resource res;
	private IModelResourceFactory parser;
	Set<IHawkObject> allContents = null;

	@Override
	public void unload() {
		res.unload();
		res.getResourceSet().getResources().remove(res);

		res = null;
		allContents = null;
	}

	// @Override
	// public Resource getEMFResource() {
	// return res;
	//
	// }

	// @Override
	// public ResourceSet getEMFResourceSet() {
	// return set;
	//
	// }

	public EMFModelResource(Resource r, IModelResourceFactory p) {

		// System.err.println(r);

		parser = p;
		res = r;

	}

	@Override
	public Iterator<IHawkObject> getAllContents() {

		return getAllContentsSet().iterator();

	}

	@Override
	public Set<IHawkObject> getAllContentsSet() {

		if (allContents == null) {

			allContents = new HashSet<>();

			TreeIterator<EObject> it = EcoreUtil.getAllContents(res, false);

			while (it.hasNext()) {
				EObject next = it.next();
				if (!next.eIsProxy()) {
					// ensure the element is from the same resource -- even if
					// emf sais its not a proxy!
					String resourceURIString = res.getURI().toString();
					String elementURIString = EcoreUtil.getURI(next).toString();
					String elementResourceURIString = elementURIString
							.indexOf("#") == -1 ? elementURIString
							: elementURIString.substring(0,
									elementURIString.lastIndexOf("#"));

					if (elementResourceURIString.equals(resourceURIString))
						allContents.add(new EMFObject(next));
				} else {
					// ignore it as it will resolve later - FIXED!
					// System.err
					// .println("PROXY FOUND (emfmodelresource - getAllContents) !!!");
				}
			}
		}
		return allContents;

	}

	@Override
	public String getType() {
		return parser.getType();
	}

	@Override
	public int getSignature(IHawkObject o) {
		return o.hashCode();
	}

}