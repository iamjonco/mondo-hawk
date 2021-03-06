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
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.hawk.service.api.utils;

import org.apache.activemq.artemis.api.core.ActiveMQBuffer;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Thrift transport that reads or writes from an {@link ActiveMQBuffer}.
 */
public final class ActiveMQBufferTransport extends TTransport {
	private final ActiveMQBuffer amqBuffer;
	
	public ActiveMQBufferTransport(ActiveMQBuffer amqBuffer) {
		this.amqBuffer = amqBuffer;
	}

	@Override
	public void close() {
		// nothing to do
	}

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public void open() throws TTransportException {
		// nothing to do
	}

	@Override
	public int read(byte[] arg0, int arg1, int arg2) throws TTransportException {
		final int readable = amqBuffer.readableBytes();
		amqBuffer.readBytes(arg0, arg1, arg2);
		return readable;
	}

	@Override
	public void write(byte[] arg0, int arg1, int arg2) throws TTransportException {
		amqBuffer.writeBytes(arg0, arg1, arg2);
	}
}