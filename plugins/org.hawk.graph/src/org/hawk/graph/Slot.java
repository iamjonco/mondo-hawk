/*******************************************************************************
 * Copyright (c) 2015-2018 The University of York, Aston University.
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
package org.hawk.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Read-only abstraction of the known metadata about a slot (an attribute or a
 * reference) in the graph populated by this updater.
 */
public class Slot {
	private final TypeNode typeNode;
	private final String propertyName, propertyType;
	private final boolean isAttribute, isReference, isMixed, isDerived, isIndexed;
	private final boolean isMany, isOrdered, isUnique;

	// Only valid if this is derived
	private final String derivationLanguage, derivationLogic;

	public Slot(TypeNode typeNode, String propertyName) {
		this.typeNode = typeNode;
		this.propertyName = propertyName;

		final String[] propertyMetadata = (String[]) typeNode.getNode().getProperty(propertyName);
		this.isAttribute = "a".equals(propertyMetadata[0]);
		this.isReference = "r".equals(propertyMetadata[0]);
		this.isMixed = "m".equals(propertyMetadata[0]);
		this.isDerived = "d".equals(propertyMetadata[0]);
		this.isMany = "t".equals(propertyMetadata[1]);
		this.isOrdered = "t".equals(propertyMetadata[2]);
		this.isUnique = "t".equals(propertyMetadata[3]);
		this.isIndexed = isAttribute && "t".equals(propertyMetadata[5]);
		this.propertyType = propertyMetadata[4];

		if (isDerived) {
			this.derivationLanguage = propertyMetadata[5];
			this.derivationLogic = propertyMetadata[6];
		} else {
			this.derivationLanguage = null;
			this.derivationLogic = null;
		}
	}

	/**
	 * Returns the collection container that should be used for the value of
	 * this slot.
	 */
	protected Collection<Object> getCollection() {
		if (isOrdered && isUnique) {
			return new LinkedHashSet<Object>(); // ordered set
		} else if (isOrdered) {
			return new ArrayList<Object>(); // sequence
		} else if (isUnique) {
			return new HashSet<Object>(); // set
		} else {
			return new ArrayList<Object>(); // bag
		}
	}

	public TypeNode getTypeNode() {
		return typeNode;
	}

	public String getName() {
		return propertyName;
	}

	public boolean isAttribute() {
		return isAttribute;
	}

	public boolean isReference() {
		return isReference;
	}

	public boolean isMixed() {
		return isMixed;
	}

	public boolean isDerived() {
		return isDerived;
	}

	public boolean isMany() {
		return isMany;
	}

	public boolean isOrdered() {
		return isOrdered;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public boolean isIndexed() {
		return isIndexed;
	}

	public String getType() {
		return propertyType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((propertyName == null) ? 0 : propertyName.hashCode());
		result = prime * result
				+ ((typeNode == null) ? 0 : typeNode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Slot other = (Slot) obj;
		if (propertyName == null) {
			if (other.propertyName != null)
				return false;
		} else if (!propertyName.equals(other.propertyName))
			return false;
		if (typeNode == null) {
			if (other.typeNode != null)
				return false;
		} else if (!typeNode.equals(other.typeNode))
			return false;
		return true;
	}

	public String getDerivationLanguage() {
		return derivationLanguage;
	}

	public String getDerivationLogic() {
		return derivationLogic;
	}

	@Override
	public String toString() {
		return "Slot [typeNode=" + typeNode + ", propertyName=" + propertyName + ", propertyType=" + propertyType
				+ ", isAttribute=" + isAttribute + ", isReference=" + isReference + ", isMixed=" + isMixed
				+ ", isDerived=" + isDerived + ", isIndexed=" + isIndexed + ", isMany=" + isMany + ", isOrdered="
				+ isOrdered + ", isUnique=" + isUnique + ", derivationLanguage=" + derivationLanguage
				+ ", derivationLogic=" + derivationLogic + "]";
	}
}