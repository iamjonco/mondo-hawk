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
 *     Konstantinos Barmpis - initial API and implementation
 ******************************************************************************/
package org.hawk.manifest;

import org.hawk.core.model.IHawkAttribute;
import org.hawk.core.model.IHawkClassifier;
import org.hawk.core.model.IHawkReference;
import org.hawk.core.model.IHawkStructuralFeature;
import org.hawk.manifest.model.ManifestModelResource;

public class ManifestPackageInstanceObject extends ManifestObject {

	private String version;
	private ManifestPackageObject ePackage;
	private int position;

	public ManifestPackageInstanceObject(String version,
			ManifestModelResource manifestModelResource,
			ManifestPackageObject ePackage, int iExport) {

		this.version = version;
		this.res = manifestModelResource;
		this.ePackage = ePackage;
		this.position = iExport;
	}

	@Override
	public String getUri() {
		return res.getUri() + "#" + getUriFragment();
	}

	@Override
	public boolean URIIsRelative() {
		return false;
	}

	@Override
	public String getUriFragment() {
		return "exports/" + position;
	}

	@Override
	public boolean isFragmentUnique() {
		return false;
	}

	@Override
	public IHawkClassifier getType() {
		return res.getType(ManifestPackageInstance.CLASSNAME);
	}

	@Override
	public boolean isSet(IHawkStructuralFeature hsf) {
		String name = hsf.getName();
		switch (name) {
		case "version":
			return version != null;
		case "provides":
			return ePackage != null;
		default:
			return false;
		}
	}

	@Override
	public Object get(IHawkAttribute attr) {
		String name = attr.getName();
		switch (name) {
		case "version":
			return version;
		default:
			return null;
		}
	}

	@Override
	public Object get(IHawkReference ref, boolean b) {
		String name = ref.getName();
		switch (name) {
		case "provides":
			return ePackage;
		default:
			return null;
		}
	}

}
