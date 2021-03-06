/*******************************************************************************
 * Copyright (c) 2015-2017 The University of York, Aston University.
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
package org.hawk.backend.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.hawk.backend.tests.factories.IGraphDatabaseFactory;
import org.hawk.core.graph.IGraphDatabase;
import org.hawk.core.graph.IGraphTransaction;
import org.hawk.core.util.DefaultConsole;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for the high-level capabilities for starting, stopping, deleting a
 * graph and switching between transactional and batch modes.
 */
@RunWith(Parameterized.class)
public class DatabaseManagementTest {
	private IGraphDatabaseFactory dbFactory;

	@Rule
	public RedirectSystemErrorRule errRule = new RedirectSystemErrorRule();

	@Rule
	public LogbackOnlyErrorsRule logRule = new LogbackOnlyErrorsRule();

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Parameters(name="Parameters are {0}")
	public static Iterable<Object[]> params() {
		return BackendTestSuite.caseParams();
	}

	public DatabaseManagementTest(IGraphDatabaseFactory dbFactory) {
		this.dbFactory = dbFactory;
	}

	@Test
	public void testStartShutdown() throws Exception {
		IGraphDatabase db = dbFactory.create();
		db.run(folder.getRoot(), new DefaultConsole());
		assertNotNull(db.getFileIndex());
		assertNotNull(db.getMetamodelIndex());
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(2, db.getNodeIndexNames().size());
			tx.success();
		}
		db.shutdown();
	}

	@Test
	public void testStartDelete() throws Exception {
		IGraphDatabase db = dbFactory.create();
		final DefaultConsole console = new DefaultConsole();
		db.run(folder.getRoot(), console);
		assertTrue(folder.getRoot().exists());
		db.delete();
		assertFalse(folder.getRoot().exists());

		// Try to reuse the same directory
		folder.getRoot().mkdirs();
		db.run(folder.getRoot(), console);
		db.shutdown();
	}

	@Test
	public void testStartTransaction() throws Exception {
		IGraphDatabase db = dbFactory.create();
		db.run(folder.getRoot(), new DefaultConsole());

		assertEquals(IGraphDatabase.Mode.TX_MODE, db.currentMode());
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(IGraphDatabase.Mode.TX_MODE, db.currentMode());
		}
		db.enterBatchMode();
		assertEquals(IGraphDatabase.Mode.NO_TX_MODE, db.currentMode());
		db.exitBatchMode();
		assertEquals(IGraphDatabase.Mode.TX_MODE, db.currentMode());

		db.shutdown();
	}
}
