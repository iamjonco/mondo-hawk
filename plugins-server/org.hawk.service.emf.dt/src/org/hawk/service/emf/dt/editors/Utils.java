/*******************************************************************************
 * Copyright (c) 2015-2016 University of York.
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
 *    Antonio Garcia-Dominguez - initial API and implementation
 *******************************************************************************/
package org.hawk.service.emf.dt.editors;

import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * Common utility methods.
 */
class Utils {

	private Utils() {}

	public static String concat(final String[] elems, final String separator) {
		final StringBuffer sbuf = new StringBuffer();
		boolean bFirst = true;
		for (String filePattern : elems) {
			if (bFirst) {
				bFirst = false;
			} else {
				sbuf.append(separator);
			}
			sbuf.append(filePattern);
		}
		return sbuf.toString();
	}

	public static TableWrapLayout createTableWrapLayout(int nColumns) {
		final TableWrapLayout cContentsLayout = new TableWrapLayout();
	    cContentsLayout.numColumns = nColumns;
	    cContentsLayout.horizontalSpacing = 5;
	    cContentsLayout.verticalSpacing = 3;
		return cContentsLayout;
	}
}
