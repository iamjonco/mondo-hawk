package org.hawk.uml.model;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.hawk.core.IFileImporter;
import org.hawk.core.IModelResourceFactory;
import org.hawk.core.model.IHawkModelResource;
import org.hawk.emf.model.EMFModelResource;

@SuppressWarnings("restriction")
public class UMLModelResourceFactory implements IModelResourceFactory {

	@Override
	public String getType() {
		return getClass().getCanonicalName();
	}

	@Override
	public String getHumanReadableName() {
		return "UML Model Resource Factory";
	}

	@Override
	public IHawkModelResource parse(IFileImporter importer, File changedFile) throws Exception {
		ResourceSet rset = new ResourceSetImpl();
		rset.getResourceFactoryRegistry().getExtensionToFactoryMap()
			.put("uml", new UMLResourceFactoryImpl());
		Resource r = rset.createResource(URI.createFileURI(changedFile.getPath()));
		r.load(null);

		return new EMFModelResource(r, this);
	}

	@Override
	public void shutdown() {
		// nothing to do for now
	}

	@Override
	public boolean canParse(File f) {
		return f.getName().toLowerCase().endsWith(".uml");
	}

	@Override
	public Collection<String> getModelExtensions() {
		return Collections.singleton(".uml");
	}

}
