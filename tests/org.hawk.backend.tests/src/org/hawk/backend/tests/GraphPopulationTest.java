/*******************************************************************************
 * Copyright (c) 2015-2017 The University of York, Aston University.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.hawk.backend.tests;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hawk.backend.tests.factories.IGraphDatabaseFactory;
import org.hawk.core.graph.IGraphEdge;
import org.hawk.core.graph.IGraphIterable;
import org.hawk.core.graph.IGraphNode;
import org.hawk.core.graph.IGraphTransaction;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for populating the graph with nodes and vertices and navigating it.
 */
public class GraphPopulationTest extends TemporaryDatabaseTest {

	@Parameters(name = "Parameters are {0}")
	public static Iterable<Object[]> params() {
		return BackendTestSuite.caseParams();
	}

	public GraphPopulationTest(IGraphDatabaseFactory dbFactory) {
		super(dbFactory);
	}

	@Test
	public void oneNode() throws Exception {
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(0, db.allNodes("eobject").size());
			db.createNode(new HashMap<String, Object>(), "eobject");
			assertEquals(1, db.allNodes("eobject").size());
			tx.success();
		}
	}

	@Test
	public void oneNodeProperty() throws Exception {
		final String yValue = "hello";
		IGraphNode n;
		IGraphIterable<IGraphNode> eobs;

		try (IGraphTransaction tx = db.beginTransaction()) {
			eobs = db.allNodes("eobject");
			assertEquals(0, eobs.size());

			Map<String, Object> map = new HashMap<>();
			map.put("x", 1.34);
			map.put("y", yValue);
			n = db.createNode(map, "eobject");

			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertTrue((double) eobs.getSingle().getProperty("x") > 1.3);
			assertEquals(1, eobs.size());
			tx.success();
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			n.setProperty("x", 2.57);
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertTrue((double) eobs.getSingle().getProperty("x") > 2.5);
			tx.success();
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			n.setProperty("x", null);
			assertThat(n.getPropertyKeys(), not(contains("x")));
			tx.success();
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			n.removeProperty("y");
			tx.failure();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(yValue, eobs.getSingle().getProperty("y"));
			tx.failure();
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			n.removeProperty("y");
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertNull(eobs.getSingle().getProperty("y"));
			tx.failure();
		}
	}

	@Test
	public void oneNodeBatch() throws Exception {
		db.enterBatchMode();

		final String idValue = "http://foo.bar";

		// OSchemaShared#checkFieldNameIfValid refers to these invalid
		// characters
		char[] invalidChars = ":,; %=".toCharArray();
		final Map<String, Object> props = new HashMap<String, Object>();
		props.put("id", idValue);
		for (char invalidChar : invalidChars) {
			props.put("my" + invalidChar + "value", invalidChar + "");
		}

		IGraphNode n = db.createNode(props, "metamodel");
		assertEquals(props.keySet(), n.getPropertyKeys());
		for (Map.Entry<String, Object> entry : props.entrySet()) {
			assertEquals(entry.getValue(), n.getProperty(entry.getKey()));
		}
		db.exitBatchMode();

		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(1, db.allNodes("metamodel").size());
			tx.success();
		}
	}

	@Test
	public void oneNodeRollback() throws Exception {
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(0, db.allNodes("metamodel").size());
			db.createNode(new HashMap<String, Object>(), "metamodel");
			assertEquals(1, db.allNodes("metamodel").size());
			tx.failure();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(0, db.allNodes("metamodel").size());
			tx.success();
		}
	}

	@Test
	public void oneNodeRemove() throws Exception {
		IGraphNode n;
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(0, db.allNodes("eobject").size());
			n = db.createNode(null, "eobject");
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(1, db.allNodes("eobject").size());
			n.delete();
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(0, db.allNodes("eobject").size());
			tx.success();
		}
	}

	@Test
	public void oneNodeRemoveRollback() throws Exception {
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(0, db.allNodes("eobject").size());
			tx.success();
		}

		IGraphNode n;
		try (IGraphTransaction tx = db.beginTransaction()) {
			n = db.createNode(null, "eobject");
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(1, db.allNodes("eobject").size());
			tx.success();
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			n.delete();
			tx.failure();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(1, db.allNodes("eobject").size());
			tx.success();
		}
	}

	@Test
	public void twoNodesBatch() throws Exception {
		db.enterBatchMode();
		IGraphNode x1 = db.createNode(Collections.singletonMap("x", 1), "eobject");
		IGraphNode x10 = db.createNode(Collections.singletonMap("x", 10), "eobject");
		IGraphEdge e = db.createRelationship(x1, x10, "dep", Collections.singletonMap("y", "abc"));
		db.exitBatchMode();

		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(1, e.getStartNode().getProperty("x"));
			assertEquals(10, e.getEndNode().getProperty("x"));
			assertEquals("abc", e.getProperty("y"));
			assertEquals("dep", e.getType());
			assertEquals(1, size(x1.getOutgoingWithType("dep")));
			assertEquals(1, size(x10.getIncomingWithType("dep")));
			tx.success();
		}
	}

	@Test
	public void twoNodesBatchRemoveRel() throws Exception {
		db.enterBatchMode();
		IGraphNode x1 = db.createNode(Collections.singletonMap("x", 1), "eobject");
		IGraphNode x10 = db.createNode(Collections.singletonMap("x", 10), "eobject");
		IGraphEdge e = db.createRelationship(x1, x10, "dep", Collections.singletonMap("y", "abc"));
		db.exitBatchMode();

		try {
			db.enterBatchMode();
			e.delete();
			db.exitBatchMode();
		} catch (IllegalStateException ex) {
			Assume.assumeTrue("Backend does not allow deletion during batch mode", false);
		}

		assertEquals(0, size(x1.getOutgoingWithType("dep")));
		assertEquals(0, size(x10.getIncomingWithType("dep")));
	}

	@Test
	public void threeNodesRemoveMiddle() throws Exception {
		IGraphNode left, middle, right;
		try (IGraphTransaction tx = db.beginTransaction()) {
			left = db.createNode(null, "eobject");
			middle = db.createNode(null, "eobject");
			right = db.createNode(null, "eobject");
			db.createRelationship(left, middle, "x", null);
			db.createRelationship(middle, right, "x", null);
			tx.success();
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(0, size(left.getIncoming()));
			assertEquals(1, size(left.getOutgoing()));
			assertEquals(1, size(middle.getIncoming()));
			assertEquals(1, size(middle.getOutgoing()));
			assertEquals(1, size(right.getIncoming()));
			assertEquals(0, size(right.getOutgoing()));
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			middle.delete();
			tx.success();
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(0, size(left.getIncoming()));
			assertEquals(0, size(left.getOutgoing()));
			assertEquals(0, size(right.getIncoming()));
			assertEquals(0, size(right.getOutgoing()));
		}
	}

	@Test
	public void escapeInvalidClassCharacters() throws Exception {
		char[] invalidClassChars = ":,; %@=.".toCharArray();
		for (char invalidChar : invalidClassChars) {
			try (IGraphTransaction tx = db.beginTransaction()) {
				final String nodeType = "my" + invalidChar + "object";
				db.createNode(null, nodeType);
				assertEquals(1, db.allNodes(nodeType).size());
				tx.success();
			}
		}
	}

	@Test
	public void escapeInvalidCharactersEdges() throws Exception {
		IGraphNode n1, n2;
		try (IGraphTransaction tx = db.beginTransaction()) {
			n1 = db.createNode(null, "eobject");
			n2 = db.createNode(null, "eobject");
			tx.success();
		}

		char[] invalidClassChars = ":,; %@=.".toCharArray();
		for (char invalidChar : invalidClassChars) {
			final String type = "link" + invalidChar + "type";

			try (IGraphTransaction tx = db.beginTransaction()) {
				db.createRelationship(n1, n2, type, Collections.<String, Object>singletonMap("test", "t"));

				final Iterable<IGraphEdge> allOut = n1.getOutgoingWithType(type);
				assertEquals("There should be one outgoing " + type + " edge for n1", 1, size(allOut));
				final Iterable<IGraphEdge> allIn = n2.getIncomingWithType(type);
				assertEquals("There should be one incoming " + type + " edge for n2", 1, size(allIn));
				tx.success();
			}
		}
	}

	@Test
	public void escapeInvalidFieldCharacters() throws Exception {
		IGraphNode n1;
		try (IGraphTransaction tx = db.beginTransaction()) {
			n1 = db.createNode(null, "eobject");
			tx.success();
		}

		char[] invalidClassChars = ":,; %@=.".toCharArray();
		for (char invalidChar : invalidClassChars) {
			try (IGraphTransaction tx = db.beginTransaction()) {
				final String propName = "x" + invalidChar + "a";
				n1.setProperty(propName, 1);
				assertEquals("Property " + propName + " should have been set", 1, n1.getProperty(propName));
				assertTrue("Property " + propName + " should be one of the keys",
						n1.getPropertyKeys().contains(propName));
				n1.removeProperty(propName);
				assertFalse("Property " + propName + " should not be one of the keys",
						n1.getPropertyKeys().contains(propName));
				tx.success();
			}
		}
	}

	private <T> int size(Iterable<T> it) {
		Iterator<T> iterator = it.iterator();
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}
}