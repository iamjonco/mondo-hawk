/*******************************************************************************
 * Copyright (c) 2011-2014 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Konstantinos Barmpis - initial API and implementation
 ******************************************************************************/
package org.hawk.manifest;

import java.util.HashSet;
import java.util.Set;

import org.hawk.core.model.IHawkAttribute;
import org.hawk.core.model.IHawkClass;
import org.hawk.core.model.IHawkReference;
import org.hawk.core.model.IHawkStructuralFeature;
import org.hawk.manifest.utils.Utils;

public class ManifestImport extends ManifestClass implements IHawkClass {

	final static String CLASSNAME = "ManifestImport";

	private IHawkAttribute minVersion;
	private IHawkAttribute maxVersion;
	private IHawkAttribute isMinVersionInclusive;
	private IHawkAttribute isMaxVersionInclusive;
	private IHawkAttribute optionalResolution;

	private HashSet<IHawkReference> references;

	public ManifestImport(ManifestMetamodel p) {
		ep = p;
		minVersion = new ManifestAttribute("minVersion");
		maxVersion = new ManifestAttribute("maxVersion");
		isMinVersionInclusive = new ManifestAttribute("isMinVersionInclusive");
		isMaxVersionInclusive = new ManifestAttribute("isMaxVersionInclusive");
		optionalResolution = new ManifestAttribute("optionalResolution");
		references = new HashSet<>();
		references.add(new ManifestReference("package", false, new ManifestPackage(p)));
	}

	@Override
	public String getInstanceType() {
		return ep.getNsURI() + "#" + CLASSNAME + "Object";
	}

	@Override
	public String getUri() {
		return ep.getNsURI() + "#" + CLASSNAME;
	}

	@Override
	public String getUriFragment() {
		return CLASSNAME;
	}

	@Override
	public String getName() {
		return CLASSNAME;
	}

	@Override
	public String getPackageNSURI() {
		return ep.getNsURI();
	}

	@Override
	public Set<IHawkAttribute> getAllAttributes() {
		Set<IHawkAttribute> ret = new HashSet<>();
		ret.add(minVersion);
		ret.add(maxVersion);
		ret.add(isMinVersionInclusive);
		ret.add(isMaxVersionInclusive);
		ret.add(optionalResolution);
		return ret;
	}

	@Override
	public Set<IHawkClass> getAllSuperTypes() {
		return new HashSet<>();
	}

	@Override
	public Set<IHawkReference> getAllReferences() {
		return references;
	}

	@Override
	public IHawkStructuralFeature getStructuralFeature(String name) {
		if (name.equals("minVersion"))
			return minVersion;
		if (name.equals("maxVersion"))
			return maxVersion;
		if (name.equals("isMinVersionInclusive"))
			return isMinVersionInclusive;
		if (name.equals("isMaxVersionInclusive"))
			return isMaxVersionInclusive;
		if (name.equals("optionalResolution"))
			return optionalResolution;
		if (name.equals("package"))
			return new Utils().getReference("package", references);
		return null;
	}

}
