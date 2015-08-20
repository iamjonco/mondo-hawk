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
package org.hawk.epsilon.emc;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.Model;

public abstract class AbstractEpsilonModel extends Model {

	protected HashSet<String> cachedTypes = new HashSet<String>();
	protected String backendURI = null;
	protected StringProperties config = null;

	// TODO try re-enable the use of a cache
	// protected boolean enableCache = true;

	// protected ModelParser parser;

	// protected ModelIndexer hawkContainer;

	/**
	 * Returns all of the contents of the database in the form of lightweight
	 * NoSQLWrapper objects - implemented for each backend
	 */
	@Override
	abstract public Collection<?> allContents();

	/**
	 * Creates a node and inserts it into the database
	 */
	@Override
	abstract public Object createInstance(String metaClassName)
			throws EolModelElementTypeNotFoundException,
			EolNotInstantiableModelElementTypeException;

	/**
	 * Deletes the element from the database
	 */
	@Override
	abstract public void deleteElement(Object arg0) throws EolRuntimeException;

	abstract public Collection<Object> getAllOf(String arg0,
			final String typeorkind)
			throws EolModelElementTypeNotFoundException;

	@Override
	public Collection<Object> getAllOfKind(String arg0)
			throws EolModelElementTypeNotFoundException {

		Collection<Object> ofType = (Collection<Object>) getAllOf(arg0,
				"typeOf");
		Collection<Object> ofKind = (Collection<Object>) getAllOf(arg0,
				"kindOf");

		// if(!ofType.type.equals(ofKind.type))System.err.println("allofkind called but the type and the kind wrappers are different! "+ofType.type+" : "+ofKind.type);

		ofKind.addAll(ofType);

		return ofKind;

	}

	@Override
	public Collection<Object> getAllOfType(String arg0)
			throws EolModelElementTypeNotFoundException {

		return getAllOf(arg0, "typeOf");

	}

	@Override
	abstract public Object getElementById(String arg0);

	@Override
	abstract public String getElementId(Object arg0);

	@Override
	public Object getEnumerationValue(String arg0, String arg1)
			throws EolEnumerationValueNotFoundException {
		throw new UnsupportedOperationException();
	}

	// public Object getEnumerationValue(String enumeration, String label)
	// throws EolEnumerationValueNotFoundException {
	//
	// for (Object pkg : EPackage.Registry.INSTANCE.keySet()) {
	//
	// if (pkg instanceof EPackage /*|| pkg instanceof EPackage.Descriptor*/) {
	// EPackage ePackage = null;
	//
	// //if (pkg instanceof EPackage) {
	// ePackage = (EPackage) pkg;
	// //}
	// //else {
	// // ePackage = ((EPackage.Descriptor) pkg).getEPackage();
	// //}
	//
	// for (EClassifier classifier : ePackage.getEClassifiers()) {
	// //for (EClassifier classifier : ePackage.getEClassifiers()) {
	// if (classifier instanceof EEnum &&
	// (((EEnum) classifier).getName().equals(enumeration) ||
	// enumeration.contains("/"+classifier.getName()))){
	// EEnum eEnum = (EEnum) classifier;
	// EEnumLiteral literal = eEnum.getEEnumLiteral(label);
	//
	// if (literal != null) return literal.getInstance();
	// }
	// }
	// }
	// }
	//
	// throw new
	// EolEnumerationValueNotFoundException(enumeration,label,this.getName());
	// }

	@Override
	abstract public String getTypeNameOf(Object arg0);

	@Override
	abstract public Object getTypeOf(Object arg0);

	@Override
	abstract public boolean hasType(String arg0);

	@Override
	public boolean isInstantiable(String arg0) {

		System.err
				.println("isInstantiable called on a hawk model, this is not supported, returning false");
		return false;
		// HawkClass o = null;
		//
		// try {
		//
		// if (arg0.indexOf("/") > -1) {
		//
		// o = parser
		// .getEPackageRegistryInstance()
		// .getPackage(arg0.substring(0, arg0.indexOf("/")))
		// .getEClassifier(arg0.substring(arg0.indexOf("/") + 1));
		//
		// } else {
		//
		// for (String pack : parser
		// .getEPackageRegistryInstance().keySet()) {
		//
		// if (!pack.contains("/XMLType") && !pack.contains("/Ecore")) {
		//
		// o = parser
		// .getEPackageRegistryInstance()
		// .getPackage(pack)
		// .getEClassifier(
		// arg0.substring(arg0.indexOf("/") + 1));
		//
		// if (o != null)
		// break;
		//
		// }
		//
		// }
		//
		// }
		//
		// return o == null ? false : !(o.isAbstract() || o.isInterface());
		//
		// } catch (Exception e) {
		// System.err
		// .println("ERROR IN isInstantiable(String arg0), returning FALSE");
		// e.printStackTrace();
		// }
		//
		// return false;
	}

	@Override
	abstract public boolean isModelElement(Object arg0);

	@Override
	abstract public void load() throws EolModelLoadingException;

	/**
	 * The full path of the database using '/' as separators (if the database
	 * folder does not exist a new one will be created at that location)
	 */
	public final static String databaseLocation = "DATABASE_LOCATION";
	public final static String enableCaching = "ENABLE_CACHING";
	public final static String dumpModelConfig = "DUMP_MODEL_CONFIG_ON_EXIT";
	public final static String dumpDatabaseConfig = "DUMP_FULL_DATABASE_CONFIG_ON_EXIT";

	@Override
	public void load(StringProperties properties, String basePath)
			throws EolModelLoadingException {

		super.load(properties, basePath);

		setDatabaseConfig(properties);

		load();

	}

	@Override
	public void dispose() {
		String dump1 = (String) config.get(dumpDatabaseConfig);
		String dump2 = (String) config.get(dumpModelConfig);
		if (dump1 != null && dump1.equalsIgnoreCase("true") || dump2 != null
				&& dump2.equalsIgnoreCase("true"))
			System.out.println("\n--dumping configuration--");
		if (dump1 != null && dump1.equalsIgnoreCase("true"))
			dumpDatabaseConfig();
		if (dump2 != null && dump2.equalsIgnoreCase("true"))
			dumpModelConfig();
		if (dump1 != null && dump1.equalsIgnoreCase("true") || dump2 != null
				&& dump2.equalsIgnoreCase("true"))
			System.out.println("----------\n");
		super.dispose();
		// System.err.println(types);
	}

	@Override
	abstract public boolean owns(Object arg0);

	@Override
	public void setElementId(Object arg0, String arg1) {
		System.err
				.println("This impelementation of IModel does not allow for ElementId to be changed after creation, hence this method does nothing");
	}

	@Override
	public boolean store() {
		// current implementation stores on create so this method is
		// deprecated - maybe change to store in memory and only flush on method
		// call
		// throw new UnsupportedOperationException();
		return true;
	}

	@Override
	public boolean store(String arg0) {
		// current implementation stores on create so this method is
		// deprecated - maybe change to store in memory and only flush on method
		// call
		// throw new UnsupportedOperationException();
		return true;
	}

	abstract public boolean isOf(Object instance, String metaClass,
			final String typeorkind)
			throws EolModelElementTypeNotFoundException;

	@Override
	public boolean isOfKind(Object instance, String metaClass)
			throws EolModelElementTypeNotFoundException {

		return isOf(instance, metaClass, "kindOf")
				|| isOf(instance, metaClass, "typeOf");

	}

	@Override
	public boolean isOfType(Object instance, String metaClass)
			throws EolModelElementTypeNotFoundException {

		return isOf(instance, metaClass, "typeOf");

	}

	public void setDatabaseConfig(StringProperties configuration) {
		this.config = configuration;
	}

	public StringProperties getDatabaseConfig() {
		if (config == null) {
			System.err
					.println("warning: null properties used for loading, defaulting to model named: \"Model\"");
			config = getDefaultDatabaseConfig();
		}
		return config;
	}

	abstract protected StringProperties getDefaultDatabaseConfig();

	@Override
	public abstract boolean knowsAboutProperty(Object instance, String property);

	public void setBackendURI(String uri) {
		backendURI = uri;
	}

	public String getBackendURI() {
		return backendURI;
	}

	@Override
	abstract public IPropertyGetter getPropertyGetter();

	@Override
	abstract public IPropertySetter getPropertySetter();

	abstract public Object getBackend();

	public void dumpModelConfig() {

		for (Object c : config.keySet())
			System.out.println(c + " = " + config.get(c));

	}

	abstract public void dumpDatabaseConfig();

}
