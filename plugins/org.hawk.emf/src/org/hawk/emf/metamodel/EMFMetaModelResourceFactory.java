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
package org.hawk.emf.metamodel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.hawk.core.IMetaModelResourceFactory;
import org.hawk.core.model.IHawkMetaModelResource;
import org.hawk.core.model.IHawkPackage;
import org.hawk.emf.EMFPackage;
import org.hawk.emf.EMFWrapperFactory;
import org.hawk.emf.model.util.RegisterMeta;

public class EMFMetaModelResourceFactory implements IMetaModelResourceFactory {

	/**
	 * Property that can be set to a comma-separated list of extensions (e.g.
	 * ".profile.xmi") that should be supported in addition to the default
	 * ones (".ecore"). Composite extensions are allowed (e.g.
	 * ".rail.way").
	 */
	public static final String PROPERTY_EXTRA_EXTENSIONS = "org.hawk.emf.metamodel.extraExtensions";

	private final Set<String> metamodelExtensions;
	private final ResourceSet resourceSet;

	public EMFMetaModelResourceFactory() {
		metamodelExtensions = new HashSet<String>();
		metamodelExtensions.add(".ecore");
		final Object xcoreFactory = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().get("xcore");
		if (xcoreFactory != null) {
			metamodelExtensions.add(".xcore");
		}

		final String sExtraExtensions = System.getProperty(PROPERTY_EXTRA_EXTENSIONS);
		if (sExtraExtensions != null) {
			String[] extraExtensions = sExtraExtensions.split(",");
			for (String extraExtension : extraExtensions) {
				metamodelExtensions.add(extraExtension);
			}
		}

		if (EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) == null) {
			EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		}

		resourceSet = new ResourceSetImpl();
		
		final Map<String, Object> extensionMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionMap.put("ecore", new EcoreResourceFactoryImpl());
		if (xcoreFactory != null) {
			extensionMap.put("xcore", xcoreFactory);
		}
		extensionMap.put("*", new XMIResourceFactoryImpl());
	}

	@Override
	public String getHumanReadableName() {
		return "EMF Metamodel Resource Factory";
	}

	@Override
	public void shutdown() {
		// nothing to do
	}

	@Override
	public IHawkMetaModelResource parse(File f) throws Exception {
		EMFMetaModelResource ret;

		Resource r = resourceSet.createResource(URI.createFileURI(f.getAbsolutePath()));
		r.load(null);
		RegisterMeta.registerPackages(r);

		ret = new EMFMetaModelResource(r, new EMFWrapperFactory(), this);
		return ret;
	}

	@Override
	public Set<String> getMetaModelExtensions() {
		return metamodelExtensions;
	}

	@Override
	public String dumpPackageToString(IHawkPackage pkg) throws Exception {
		final EMFPackage ePackage = (EMFPackage) pkg;
		final EMFMetaModelResource eResource = (EMFMetaModelResource) ePackage.getResource();
		final Resource oldResource = eResource.getResource();

		// Separate the EPackage to be saved to its own resource
		final Resource newResource = resourceSet
				.createResource(URI.createURI(IMetaModelResourceFactory.DUMPED_PKG_PREFIX + ePackage.getNsURI()));
		final EObject eob = ePackage.getEObject();

		/*
		 * TODO: copying EPackages doesn't change packages as it should, but not copying
		 * produces problems with "frozen" resources. Perhaps should try to fall back on
		 * copying only with frozen resources, or even patch manually the XMI in those
		 * cases...
		 */
		newResource.getContents().add(eob);

		/*
		 * Separate the other EPackages that may reside in the old resource in
		 * the same way, so they all refer to each other using
		 * resource_from_epackage_...
		 */
		final List<EObject> otherContents = new ArrayList<>(oldResource.getContents());
		final List<Resource> auxResources = new ArrayList<>();
		for (EObject otherContent : otherContents) {
			if (otherContent instanceof EPackage) {
				final EPackage otherEPackage = (EPackage) otherContent;
				final Resource auxResource = resourceSet
						.createResource(URI.createURI(IMetaModelResourceFactory.DUMPED_PKG_PREFIX + otherEPackage.getNsURI()));
				auxResources.add(auxResource);
				auxResource.getContents().add(otherEPackage);
			}
		}

		final ByteArrayOutputStream bOS = new ByteArrayOutputStream();
		try {
			newResource.save(bOS, null);
			final String contents = new String(bOS.toByteArray());
			return contents;
		} finally {
			/*
			 * Move back all EPackages into the original resource, to avoid inconsistencies
			 * across restarts.
			 */
			oldResource.getContents().add(eob);
			oldResource.getContents().addAll(otherContents);

			/*
			 * Unload and remove all the auxiliary resources we've created
			 * during the dumping.
			 */
			newResource.unload();
			resourceSet.getResources().remove(newResource);
			for (Resource auxResource : auxResources) {
				auxResource.unload();
				resourceSet.getResources().remove(auxResource);
			}
		}
	}

	@Override
	public IHawkMetaModelResource parseFromString(String name, String contents) throws Exception {
		if (name != null && contents != null) {
			Resource r = resourceSet.createResource(URI.createURI(name));
			InputStream input = new ByteArrayInputStream(contents.getBytes("UTF-8"));
			r.load(input, null);

			RegisterMeta.registerPackages(r);

			return new EMFMetaModelResource(r, new EMFWrapperFactory(), this);
		} else
			return null;
	}

	@Override
	public boolean canParse(File f) {
		String[] split = f.getPath().split("\\.");
		String extension = split[split.length - 1];

		return getMetaModelExtensions().contains(extension);
	}

	@Override
	public Set<IHawkMetaModelResource> getStaticMetamodels() {
		return Collections.emptySet();
	}
}
