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
 *     Antonio Garcia-Dominguez - use explicit HManager instances, add support for
 *                                remote locations
 ******************************************************************************/
package org.hawk.osgiserver;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.hawk.core.IConsole;
import org.hawk.core.ICredentialsStore;
import org.hawk.core.IHawk;
import org.hawk.core.IHawkFactory;
import org.hawk.core.IMetaModelResourceFactory;
import org.hawk.core.IMetaModelUpdater;
import org.hawk.core.IModelIndexer;
import org.hawk.core.IModelIndexer.ShutdownRequestType;
import org.hawk.core.IModelResourceFactory;
import org.hawk.core.IModelUpdater;
import org.hawk.core.IStateListener;
import org.hawk.core.IVcsManager;
import org.hawk.core.graph.IGraphChangeListener;
import org.hawk.core.graph.IGraphDatabase;
import org.hawk.core.query.IQueryEngine;
import org.hawk.core.util.HawkConfig;
import org.hawk.core.util.HawkProperties;
import org.hawk.core.util.HawksConfig;
import org.hawk.core.util.IndexedAttributeParameters;
import org.osgi.service.prefs.BackingStoreException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class HModel implements IStateListener {
	private HawkState status;

	public static String DEFAULT_INFO = "Sleeping...";

	private String info = DEFAULT_INFO;

	protected void setStatus(HawkState status) {
		if (this.status != status) {
			this.status = status;
			manager.stateChanged(this);
		}
	}

	public HawkState getStatus() {
		return status;
	}

	public void setInfo(String info) {
		if (!this.info.equals(info)) {
			this.info = info;
			manager.infoChanged(this);
		}
	}

	public String getInfo() {
		return info;
	}

	private static IConsole CONSOLE = new SLF4JConsole();

	public static IConsole getConsole() {
		if (CONSOLE == null)
			CONSOLE = new SLF4JConsole();
		return CONSOLE;
	}

	public static void setConsole(IConsole c) {
		CONSOLE = c;
	}

	/**
	 * Creates a new Hawk instance in a local folder, and saves its metadata
	 * into the {@link HManager}.
	 *
	 * @throws IllegalArgumentException
	 *             The minimum and maximum delays are not valid or not
	 *             consistent with each other.
	 */
	public static HModel create(IHawkFactory hawkFactory, String name, File storageFolder, String location,
			String dbType, List<String> plugins, HManager manager, ICredentialsStore credStore, int minDelay,
			int maxDelay) throws Exception {

		if (minDelay > maxDelay) {
			throw new IllegalArgumentException("minimum delay must be less than or equal to maximum delay");
		} else if (minDelay < 0) {
			throw new IllegalArgumentException("minimum delay must not be negative");
		} else if (maxDelay < 0) {
			throw new IllegalArgumentException("maximum delay must not be negative");
		}

		HModel hm = new HModel(manager, hawkFactory, name, storageFolder, location, credStore, plugins);
		if (dbType != null) {
			hm.hawk.setDbtype(dbType);
		}

		// TODO use plugins list to enable only these plugins
		IGraphDatabase db = null;
		final IConsole console = getConsole();
		try {
			// create the indexer with relevant database
			console.println("Creating Hawk indexer...");

			if (hawkFactory.instancesCreateGraph()) {
				console.println("Setting up hawk's back-end store:");
				db = manager.createGraph(hm.hawk);
				db.run(storageFolder, console);
				hm.hawk.getModelIndexer().setDB(db, true);
			}

			// hard coded metamodel updater?
			IMetaModelUpdater metaModelUpdater = manager.getMetaModelUpdater();
			console.println("Setting up hawk's metamodel updater:\n" + metaModelUpdater.getName());
			hm.hawk.getModelIndexer().setMetaModelUpdater(metaModelUpdater);
			hm.hawk.getModelIndexer().init(minDelay, maxDelay);

			manager.addHawk(hm);
			manager.saveHawkToMetadata(hm);
			console.println("Created Hawk indexer!");
			return hm;
		} catch (Exception e) {
			console.printerrln("Adding of indexer aborted, please try again.\n"
					+ "Shutting down and removing back-end (if it was created)");
			console.printerrln(e);

			try {
				if (db != null) {
					db.delete();
				}
			} catch (Exception e2) {
				throw e2;
			}

			console.printerrln("aborting finished.");
			throw e;
		}

	}

	/**
	 * Loads a previously existing Hawk instance from its {@link HawkConfig}.
	 */
	public static HModel load(HawkConfig config, HManager manager) throws Exception {

		try {
			final IHawkFactory hawkFactory = manager.createHawkFactory(config.getHawkFactory());
			final HModel hm = new HModel(manager, hawkFactory, config.getName(), new File(config.getStorageFolder()),
					config.getLocation(), manager.getCredentialsStore(), config.getEnabledPlugins());

			// hard coded metamodel updater?
			IMetaModelUpdater metaModelUpdater = manager.getMetaModelUpdater();
			hm.hawk.getModelIndexer().setMetaModelUpdater(metaModelUpdater);
			return hm;
		} catch (Throwable e) {
			getConsole().printerrln("Exception in trying to add create Indexer from folder:");
			getConsole().printerrln(e);
			getConsole().printerrln("Adding of indexer aborted, please try again");
			return null;
		}
	}

	public boolean isLocal() {
		return hawk.getModelIndexer().getGraph() != null;
	}

	public IHawk getHawk() {
		return hawk;
	}
	
	public String getDbType() {
		return this.hawk.getDbtype();
	}

	public void setDbType(String dbType) throws Exception {
		hawk.setDbtype(dbType);
		if (hawkFactory.instancesCreateGraph()) {
			IGraphDatabase db = manager.createGraph(this.hawk);
			db.run(new File(this.getHawkConfig().getStorageFolder()), getConsole());
			this.hawk.getModelIndexer().setDB(db, true);
		}
	}

	/**
	 * Either <code>null</code> (all plugins are implicitly enabled) or a collection of plugins (as reported by HManager
	 */
	private List<String> enabledPlugins;
	private final IHawk hawk;
	private final IHawkFactory hawkFactory;
	private final HManager manager;
	private final String hawkLocation;

	/**
	 * Constructor for loading existing local Hawk instances and
	 * creating/loading custom {@link IHawk} implementations.
	 * @param plugins 
	 * @param plugins 
	 */
	public HModel(HManager manager, IHawkFactory hawkFactory, String name, File storageFolder, String location, ICredentialsStore credStore, List<String> plugins) throws Exception {
		this.hawkFactory = hawkFactory;
		this.hawk = hawkFactory.create(name, storageFolder, location, credStore, getConsole(), plugins);
		this.manager = manager;
		this.hawkLocation = location;
		this.enabledPlugins = plugins;

		enablePlugins();
		hawk.getModelIndexer().addStateListener(this);
	}

	public void addPlugins(List<String> plugins) throws Exception {
		if(this.enabledPlugins == null) {
			this.enabledPlugins = plugins;
		} else {
			this.enabledPlugins.addAll(plugins);
		}
		enablePlugins();
	}
	
	private void enablePlugins() throws Exception {
		if (hawkFactory.instancesAreExtensible()) {
			final IConsole console = getConsole();
			// set up plugins
			// first get all of type (static callto HawkOSGIConfigManager)
			// check each one has the an ID that was selected
			// create VCS
			// call m.add
			console.println("adding metamodel resource factories:");
			for (IConfigurationElement mmparse : manager.getMmps()) {
				IMetaModelResourceFactory f = (IMetaModelResourceFactory) mmparse
						.createExecutableExtension(HManager.MMPARSER_CLASS_ATTRIBUTE);
				if (enabledPlugins == null || enabledPlugins.contains(f.getClass().getName())) {
					this.hawk.getModelIndexer().addMetaModelResourceFactory(f);
					console.println(f.getHumanReadableName());
				}
			}
			console.println("adding model resource factories:");
			for (IConfigurationElement mparse : manager.getMps()) {
				IModelResourceFactory f = (IModelResourceFactory) mparse.createExecutableExtension(HManager.MPARSER_CLASS_ATTRIBUTE);
				if (enabledPlugins == null || enabledPlugins.contains(f.getClass().getName())) {
					this.hawk.getModelIndexer().addModelResourceFactory(f);
					console.println(f.getHumanReadableName());
				}
			}
			console.println("adding query engines:");
			for (IConfigurationElement ql : manager.getLanguages()) {
				IQueryEngine q = (IQueryEngine) ql.createExecutableExtension(HManager.QUERYLANG_CLASS_ATTRIBUTE);
				// So far we only have one choice (EOL) and it doesn't make sense to disable it - see HManager#getAvailablePlugins()
//				if (enabledPlugins == null || enabledPlugins.contains(q.getClass().getName())) {
					this.hawk.getModelIndexer().addQueryEngine(q);
					console.println(q.getType());
//				}
			}
			console.println("adding model updaters:");
			for (IConfigurationElement updater : manager.getUps()) {
				IModelUpdater u = (IModelUpdater) updater.createExecutableExtension(HManager.MUPDATER_CLASS_ATTRIBUTE);
				// So far we only have one choice (graph updater) and it doesn't make sense to disable it - see HManager#getAvailablePlugins()
//				if (enabledPlugins == null || enabledPlugins.contains(u.getClass().getName())) {
					this.hawk.getModelIndexer().addModelUpdater(u);
					console.println(u.getName());
//				}
			}
			console.println("adding graph change listeners:");
			for (IConfigurationElement listener : manager.getGraphChangeListeners()) {
				IGraphChangeListener l = (IGraphChangeListener) listener.createExecutableExtension(HManager.GCHANGEL_CLASS_ATTRIBUTE);
				if (enabledPlugins == null || enabledPlugins.contains(l.getClass().getName())) {
					l.setModelIndexer(this.hawk.getModelIndexer());
					this.hawk.getModelIndexer().addGraphChangeListener(l);
					console.println(l.getName());
				}
			}
		}
	}
	
	public void addDerivedAttribute(String metamodeluri, String typename, String attributename, String attributetype,
			Boolean isMany, Boolean isOrdered, Boolean isUnique, String derivationlanguage, String derivationlogic)
					throws Exception {
		hawk.getModelIndexer().addDerivedAttribute(metamodeluri, typename, attributename, attributetype, isMany,
				isOrdered, isUnique, derivationlanguage, derivationlogic);
	}

	private void loadVCS(String loc, String type, boolean isFrozen) throws Exception {
		final IModelIndexer indexer = hawk.getModelIndexer();
		final IVcsManager mo = manager.createVCSManager(type);
		mo.init(loc, indexer);
		if (!this.getLocations().contains(mo.getLocation())) {
			mo.run();
			mo.setFrozen(isFrozen);
			indexer.addVCSManager(mo, false);
		}
	}

	public void addIndexedAttribute(String metamodeluri, String typename, String attributename) throws Exception {
		hawk.getModelIndexer().addIndexedAttribute(metamodeluri, typename, attributename);
	}

	public void addVCS(String loc, String type, String user, String pass, boolean isFrozen) {
		try {
			IVcsManager mo = manager.createVCSManager(type);
			mo.init(loc, hawk.getModelIndexer());

			if (!this.getLocations().contains(mo.getLocation())) {
				mo.setCredentials(user, pass, hawk.getModelIndexer().getCredentialsStore());
				mo.run();
				mo.setFrozen(isFrozen);
				hawk.getModelIndexer().addVCSManager(mo, true);
			}
		} catch (Exception e) {
			getConsole().printerrln(e);
		}
	}

	/**
	 * Registers a new graph change listener into the model indexer, if it
	 * wasn't already registered. Otherwise, it does nothing.
	 */
	public boolean addGraphChangeListener(IGraphChangeListener changeListener) {
		return hawk.getModelIndexer().addGraphChangeListener(changeListener);
	}

	/**
	 * Removes a new graph change listener from the model indexer, if it was
	 * already registered. Otherwise, it does nothing.
	 */
	public boolean removeGraphChangeListener(IGraphChangeListener changeListener) {
		return hawk.getModelIndexer().removeGraphChangeListener(changeListener);
	}

	/**
	 * Performs a query and returns its result. The result must be a Double, a
	 * String, an Integer, a ModelElement, the null reference or an Iterable of
	 * these things.
	 * 
	 * @throws NoSuchElementException
	 *             Unknown query language.
	 */
	public Object query(File query, String ql, Map<String, Object> context) throws Exception {
		IQueryEngine q = hawk.getModelIndexer().getKnownQueryLanguages().get(ql);
		if (q == null) {
			throw new NoSuchElementException();
		}
		return q.query(hawk.getModelIndexer(), query, context);
	}

	/**
	 * Performs a query and returns its result. For the result types, see
	 * {@link #contextFullQuery(File, String, Map)}.
	 * 
	 * @throws NoSuchElementException
	 *             Unknown query language.
	 */
	public Object query(String query, String ql, Map<String, Object> context) throws Exception {
		IQueryEngine q = hawk.getModelIndexer().getKnownQueryLanguages().get(ql);
		if (q == null) {
			throw new NoSuchElementException();
		}
		return q.query(hawk.getModelIndexer(), query, context);
	}

	public void delete() throws BackingStoreException {
		removeHawkFromMetadata(getHawkConfig());

		File f = hawk.getModelIndexer().getParentFolder();
		if (this.isRunning()) {
			try {
				hawk.getModelIndexer().shutdown(ShutdownRequestType.ONLY_LOCAL);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (f.exists()) {
			getConsole().println("Hawk instance removed from ui but persistence remains at: " + f);
		}
	}

	/**
	 * Returns a {@link HawkConfig} from which this instance can be reloaded.
	 */
	public HawkConfig getHawkConfig() {
		return new HawkConfig(getName(), getFolder(), hawkLocation, hawkFactory.getClass().getName(), enabledPlugins);
	}

	public boolean exists() {
		return hawk != null && hawk.exists();
	}

	public List<String> getEnabledPlugins() {
		return enabledPlugins;
	}

	public Collection<IMetaModelResourceFactory> getMetamodelParsers() {
		List<IMetaModelResourceFactory> parsers = new ArrayList<>();
		for (String type : hawk.getModelIndexer().getKnownMetaModelParserTypes()) {
			IMetaModelResourceFactory parser = hawk.getModelIndexer().getMetaModelParser(type);
			parsers.add(parser);
		}
		return parsers;
	}

	public Collection<IndexedAttributeParameters> getDerivedAttributes() {
		return hawk.getModelIndexer().getDerivedAttributes();
	}

	public String getFolder() {
		return hawk.getModelIndexer().getParentFolder().toString();
	}

	public IGraphDatabase getGraph() {
		return hawk.getModelIndexer().getGraph();
	}

	public Collection<IndexedAttributeParameters> getIndexedAttributes() {
		return hawk.getModelIndexer().getIndexedAttributes();
	}
	

	public Collection<String> getIndexes() {
		return hawk.getModelIndexer().getIndexes();
	}

	public Set<String> getKnownQueryLanguages() {
		return hawk.getModelIndexer().getKnownQueryLanguages().keySet();
	}

	public Collection<String> getLocations() {
		List<String> locations = new ArrayList<String>();
		for (IVcsManager o : getRunningVCSManagers()) {
			locations.add(o.getLocation());
		}
		return locations;
	}

	public Collection<IVcsManager> getRunningVCSManagers() {
		return hawk.getModelIndexer().getRunningVCSManagers();
	}

	public String getName() {
		return hawk.getModelIndexer().getName();
	}

	public ArrayList<String> getRegisteredMetamodels() {
		return new ArrayList<String>(hawk.getModelIndexer().getKnownMMUris());
	}

	public List<IVcsManager> getVCSInstances() {
		return manager.getVCSInstances();
	}

	public boolean isRunning() {
		return hawk.getModelIndexer().isRunning();
	}

	public HManager getManager() {
		return manager;
	}

	public boolean registerMeta(File... f) {
		try {
			hawk.getModelIndexer().registerMetamodels(f);
		} catch (Exception e) {
			getConsole().printerrln(e);
			return false;
		}
		return true;
	}

	public void removeHawkFromMetadata(HawkConfig config) throws BackingStoreException {
		IEclipsePreferences preferences = HManager.getPreferences();

		String xml = preferences.get("config", null);

		if (xml != null) {
			XStream stream = new XStream(new DomDriver());
			stream.processAnnotations(HawksConfig.class);
			stream.processAnnotations(HawkConfig.class);
			stream.setClassLoader(HawksConfig.class.getClassLoader());
			HawksConfig hc = (HawksConfig) stream.fromXML(xml);
			hc.removeLoc(config);
			xml = stream.toXML(hc);
			preferences.put("config", xml);
			preferences.flush();
		} else {
			getConsole().printerrln("removeHawkFromMetadata tried to load preferences but it could not.");
		}
	}

	public boolean start(HManager manager) {
		try {
			final HawkProperties hp = loadIndexerMetadata();

			if (hawkFactory.instancesCreateGraph()) {
				// create the indexer with relevant database
				IGraphDatabase db = manager.createGraph(hawk);
				db.run(new File(this.getFolder()), getConsole());
				hawk.getModelIndexer().setDB(db, false);
			}

			hawk.getModelIndexer().init(hp.getMinDelay(), hp.getMaxDelay());
		} catch (Exception e) {
			getConsole().printerrln(e);
		}

		boolean running = hawk.getModelIndexer().isRunning();

		return running;
	}

	public void stop(ShutdownRequestType requestType) {
		try {
			hawk.getModelIndexer().shutdown(requestType);
		} catch (Exception e) {
			getConsole().printerrln(e);
		}
	}

	public void sync() throws Exception {
		hawk.getModelIndexer().requestImmediateSync();
	}

	@Override
	public String toString() {
		String ret = "";
		try {
			ret = getName() + " [" + this.getFolder() + "] ";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public List<String> validateExpression(String derivationlanguage, String derivationlogic) {
		return hawk.getModelIndexer().validateExpression(derivationlanguage, derivationlogic);
	}

	private HawkProperties loadIndexerMetadata() throws Exception {
		XStream stream = new XStream(new DomDriver());
		stream.processAnnotations(HawkProperties.class);
		stream.setClassLoader(HawkProperties.class.getClassLoader());
		String path = hawk.getModelIndexer().getParentFolder() + File.separator + "properties.xml";

		HawkProperties hp = (HawkProperties) stream.fromXML(new File(path));
		hawk.setDbtype(hp.getDbType());
		for (String[] s : hp.getMonitoredVCS()) {
			loadVCS(s[0], s[1], s.length > 2 ? Boolean.parseBoolean(s[2]) : false);
		}

		return hp;
	}

	public boolean removeDerivedAttribute(String metamodelUri, String typeName, String attributeName) {
		return hawk.getModelIndexer().removeDerivedAttribute(metamodelUri, typeName, attributeName);
	}

	public boolean removeIndexedAttribute(String metamodelUri, String typename, String attributename) {
		return hawk.getModelIndexer().removeIndexedAttribute(metamodelUri, typename, attributename);
	}

	public void removeRepository(IVcsManager manager) throws Exception {
		try {
			hawk.getModelIndexer().removeVCS(manager);
		} catch (Exception e) {
			getConsole().printerrln(e);
		}
	}

	public IModelIndexer getIndexer() {
		return hawk.getModelIndexer();
	}

	/**
	 * Should throw an {@link IllegalArgumentException} if the configuration for
	 * the polling is not valid (base or max <= 0 or base > max).
	 */
	public void configurePolling(int base, int max) {
		hawk.getModelIndexer().setPolling(base, max);
	}

	public void removeMetamodels(String[] selectedMetamodels) {
		try {
			hawk.getModelIndexer().removeMetamodels(selectedMetamodels);
		} catch (Exception e) {
			getConsole().printerrln(e);
		}
	}

	@Override
	public void state(HawkState state) {
		setStatus(state);
	}

	@Override
	public void info(String s) {
		setInfo(s);
	}

	@Override
	public void error(String s) {
		setInfo(s);
	}

	@Override
	public void removed() {
		// nothing to do when the state listener has been removed
	}

	public boolean removeIndexedAttributes(String[] selected) {

		boolean allSuccess = true;

		for (String s : selected) {
			String[] ss = s.split("##");
			if (ss.length == 3)
				allSuccess = allSuccess && removeIndexedAttribute(ss[0], ss[1], ss[2]);
			else {
				setInfo("internal error in removeIndexedAttributes: " + Arrays.toString(ss));
				allSuccess = false;
			}
		}
		return allSuccess;

	}

	public boolean removeDerviedAttributes(String[] selected) {
		boolean allSuccess = true;

		for (String s : selected) {
			String[] ss = s.split("##");
			if (ss.length == 3)
				allSuccess = allSuccess && removeDerivedAttribute(ss[0], ss[1], ss[2]);
			else {
				setInfo("internal error in removeIndexedAttributes: " + Arrays.toString(ss));
				allSuccess = false;
			}
		}
		return allSuccess;

	}

}
