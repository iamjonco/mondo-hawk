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

import static org.junit.Assert.assertArrayEquals;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for populating the graph with nodes and vertices and navigating it.
 */
public class GraphPopulationTest extends TemporaryDatabaseTest {

	@Rule
	public RedirectSystemErrorRule errRule = new RedirectSystemErrorRule();

	@Rule
	public LogbackOnlyErrorsRule logRule = new LogbackOnlyErrorsRule();

	@Parameters(name = "Parameters are {0}")
	public static Iterable<Object[]> params() {
		return BackendTestSuite.caseParams();
	}

	public GraphPopulationTest(IGraphDatabaseFactory dbFactory) {
		super(dbFactory);
	}

	@Test
	public void oneNode() throws Exception {
		IGraphNode n;
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(0, db.allNodes("eobject").size());
			n = db.createNode(new HashMap<String, Object>(), "eobject");
			assertEquals(1, db.allNodes("eobject").size());
			tx.success();
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			IGraphNode n2 = db.getNodeById(n.getId());
			assertEquals("equals() should be implemented", n, n2);
			assertEquals("hashCode() should be implemented", n.hashCode(), n2.hashCode());
			tx.success();
		}
	}

	@Test
	public void oneNodeProperty() throws Exception {
		final String sValue = "hello world";
		final String esValue = ""; // empty string
		final boolean bValue = true;
		final double dValue = 1.34;
		final float fValue = 3.3f;

		// Array types - strings and scalar numeric values
		final String[] asValue = new String[] {"a", "b"};
		final double[] adValue = new double[] {1.1, 2.3};
		final float[] afValue = new float[] {1.1f, 2.3f};
		final long[] alValue = new long[] {1L, 2L};
		final short[] ahValue = new short[] {0, 1, 2, 3};
		final int[] aiValue = new int[] {0, 1, 2, 3};
		final byte[] abValue = new byte[] {0, 1, 2, 3};

		IGraphNode n;
		IGraphIterable<? extends IGraphNode> eobs;

		final Map<String, Object> map = new HashMap<>();
		try (IGraphTransaction tx = db.beginTransaction()) {
			eobs = db.allNodes("eobject");
			assertEquals(0, eobs.size());

			map.put("d", dValue);
			map.put("f", fValue);
			map.put("s", sValue);
			map.put("es", esValue);
			map.put("b", bValue);
			map.put("as", asValue);
			map.put("ad", adValue);
			map.put("af", afValue);
			map.put("al", alValue);
			map.put("ai", aiValue);
			map.put("ah", ahValue);
			map.put("ab", abValue);
			n = db.createNode(map, "eobject");

			tx.success();
		}

		// property keys
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(map.keySet(), n.getPropertyKeys());
			tx.success();
		}

		// doubles
		try (IGraphTransaction tx = db.beginTransaction()) {
			IGraphNode node = eobs.getSingle();
			assertEquals(dValue, (double) node.getProperty("d"), Double.MIN_NORMAL);
			assertEquals(1, eobs.size());
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			n.setProperty("d", 2.57);
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(2.57, (double) eobs.getSingle().getProperty("d"), Double.MIN_NORMAL);
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			n.setProperty("d", null);
			assertFalse(n.getPropertyKeys().stream().anyMatch(key -> "d".equals(key)));
			tx.success();
		}

		// floats
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(fValue, (float) eobs.getSingle().getProperty("f"), Float.MIN_NORMAL);
			tx.success();
		}

		// Non-empty strings
		try (IGraphTransaction tx = db.beginTransaction()) {
			n.removeProperty("s");
			tx.failure();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(sValue, eobs.getSingle().getProperty("s"));
			tx.failure();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			n.removeProperty("s");
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertNull(eobs.getSingle().getProperty("s"));
			tx.failure();
		}

		// Empty strings
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(esValue, n.getProperty("es"));
			tx.success();
		}

		// Booleans
		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(bValue, eobs.getSingle().getProperty("b"));
			tx.success();
		}

		try (IGraphTransaction tx = db.beginTransaction()) {
			final Object propValue = eobs.getSingle().getProperty("as");
			assertArrayEquals(asValue, (String[]) propValue);
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			final Object propValue = eobs.getSingle().getProperty("ad");
			assertArrayEquals(adValue, (double[]) propValue, 1e-2);
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			final Object propValue = eobs.getSingle().getProperty("af");
			assertArrayEquals(afValue, (float[]) propValue, 1e-2f);
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			final Object propValue = eobs.getSingle().getProperty("al");
			assertArrayEquals(alValue, (long[]) propValue);
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			final Object propValue = eobs.getSingle().getProperty("ah");
			assertArrayEquals(ahValue, (short[]) propValue);
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			final Object propValue = eobs.getSingle().getProperty("ai");
			assertArrayEquals(aiValue, (int[]) propValue);
			tx.success();
		}
		try (IGraphTransaction tx = db.beginTransaction()) {
			final Object propValue = eobs.getSingle().getProperty("ab");
			assertArrayEquals(abValue, (byte[]) propValue);
			tx.success();
		}
	}

	@Test
	public void oneNodeBatch() throws Exception {
		db.enterBatchMode();

		final String idValue = "http://foo.bar";

		// OSchemaShared#checkFieldNameIfValid refers to these invalid characters
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
		IGraphEdge e1 = db.createRelationship(x1, x10, "dep", Collections.singletonMap("y", "abc"));
		db.createRelationship(x1, x10, "light", null);
		db.exitBatchMode();

		try (IGraphTransaction tx = db.beginTransaction()) {
			assertEquals(1, e1.getStartNode().getProperty("x"));
			assertEquals(10, e1.getEndNode().getProperty("x"));
			assertEquals("abc", e1.getProperty("y"));
			assertEquals("dep", e1.getType());

			assertEquals(1, size(x1.getOutgoingWithType("light")));
			assertEquals(1, size(x10.getIncomingWithType("light")));

			assertEquals(1, size(x1.getOutgoingWithType("dep")));
			assertEquals(1, size(x10.getIncomingWithType("dep")));
			assertEquals(0, size(x10.getIncomingWithType("other")));
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
			db.createRelationship(middle, right, "x", Collections.singletonMap("some", "thing"));
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
			tx.success();
		}
	}

	@Test
	public void escapeInvalidClassCharacters() throws Exception {
		char[] invalidClassChars = ":,; %@=.".toCharArray();
		for (char invalidChar : invalidClassChars) {
			try (IGraphTransaction tx = db.beginTransaction()) {
				final String nodeType = "my" + invalidChar + "object";
				db.createNode(null, nodeType);
				assertEquals("There should be one node of type " + nodeType,
					1, db.allNodes(nodeType).size());
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
				assertFalse("Property " + propName + " should have been removed",
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
