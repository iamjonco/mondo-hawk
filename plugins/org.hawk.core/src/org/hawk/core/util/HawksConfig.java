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
package org.hawk.core.util;

import java.util.Collection;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("hawksConfig")
public class HawksConfig {

	@XStreamAlias("hawks")
	protected Collection<HawkConfig> hawks;

	public Collection<HawkConfig> getConfigs() {
		return hawks;
	}

	public void setLocs(Collection<HawkConfig> locs) {
		this.hawks = locs;
	}

	public HawksConfig(Collection<HawkConfig> locations) {
		this.hawks = locations;
	}

	public void addLocs(HawkConfig config) {
		this.hawks.add(config);
	}

	public boolean removeLoc(HawkConfig config) {
		return this.hawks.remove(config);
	}

}
