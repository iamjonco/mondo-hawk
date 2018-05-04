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
package runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.hawk.core.IModelIndexer;
import org.hawk.core.IVcsManager;
import org.hawk.core.IModelIndexer.ShutdownRequestType;
import org.hawk.core.graph.IGraphDatabase;
import org.hawk.core.query.IQueryEngine;
import org.hawk.core.runtime.ModelIndexerImpl;
import org.hawk.core.security.FileBasedCredentialsStore;
import org.hawk.core.util.DefaultConsole;
import org.hawk.emf.metamodel.EMFMetaModelResourceFactory;
import org.hawk.emf.model.EMFModelResourceFactory;
import org.hawk.epsilon.emc.EOLQueryEngine;
import org.hawk.graph.internal.updater.GraphMetaModelUpdater;
import org.hawk.graph.internal.updater.GraphModelUpdater;
import org.hawk.ifc.IFCModelFactory;
import org.hawk.ifc.mm.IFCMetaModelResourceFactory;
import org.hawk.localfolder.LocalFolder;
import org.hawk.neo4j_v2.Neo4JDatabase;

//import org.hawk.core.query.IQueryLanguage;

public class IFC_Titan_Runtime_example {

	private final static char[] adminpw = "admin".toCharArray();

	private static IQueryEngine q;
	private static IModelIndexer i;

	private static File parent = new File("runtime_data");

	private static File query3 = new File(
			"../org.hawk.epsilon/src/org/hawk/epsilon/query/Test_Query_IFC.eol");

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		final FileBasedCredentialsStore credStore = new FileBasedCredentialsStore(
				new File("credentials.xml"), adminpw);
		i = new ModelIndexerImpl("hawk1", parent, credStore,
				new DefaultConsole());

		// metamodel
		i.addMetaModelResourceFactory(new EMFMetaModelResourceFactory());
		i.addMetaModelResourceFactory(new IFCMetaModelResourceFactory());

		// model
		i.addModelResourceFactory(new EMFModelResourceFactory());
		// model
		i.addModelResourceFactory(new IFCModelFactory());

		IGraphDatabase db = (new Neo4JDatabase());
		// create the indexer with relevant database
		db.run(i.getParentFolder(), i.getConsole());
		i.setDB(db, true);

		// set path of vcs
		String vcsloc = "../uk.ac.york.cs.mde.hawk.ifc/samples";

		// add vcs monitors
		IVcsManager vcs = new LocalFolder();	
		vcs.setCredentials("un", "pw", credStore);
		vcs.init(vcsloc, i);
		vcs.run();
		i.addVCSManager(vcs, true);

		// metamodel updater
		i.setMetaModelUpdater(new GraphMetaModelUpdater());

		// add one or more metamodel files
		File metamodel = new File(
				"../uk.ac.york.cs.mde.hawk.ifc/models/Ecore.ecore");
		// register them
		i.registerMetamodels(metamodel);

		// add one or more metamodel files
		metamodel = new File(
				"../uk.ac.york.cs.mde.hawk.ifc/models/ifc2x3tc1.ecore");
		// register them
		i.registerMetamodels(metamodel);

		// model updater
		i.addModelUpdater(new GraphModelUpdater());

		// query language
		q = new EOLQueryEngine();
		i.addQueryEngine(q);
		//
		// initialise the server for real-time updates to changes
		i.init(1000, 512_000);

		// add console interaction if needed
		Thread t = consoleInteraction(i);
		t.start();

		// terminate hawk
		// h.shutdown();
	}

	private static Thread consoleInteraction(final IModelIndexer i2) {
		return new Thread() {
			@Override
			public void run() {
				while (true) {
					BufferedReader r = new BufferedReader(
							new InputStreamReader(System.in));
					try {
						String s = r.readLine();
						if (s.equalsIgnoreCase("quit")
								|| s.equalsIgnoreCase("exit")
								|| s.equalsIgnoreCase("e")) {
							i2.shutdown(ShutdownRequestType.ONLY_LOCAL);
							System.exit(0);
						}
						if (s.equalsIgnoreCase("query")
								|| s.equalsIgnoreCase("q")) {

							q.query(i, query3, null);

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		};
	}
}