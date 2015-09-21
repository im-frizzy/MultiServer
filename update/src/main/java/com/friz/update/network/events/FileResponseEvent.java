/*
 * MultiServer - Multiple Server Communication Application
 * Copyright (C) 2015 Kyle Fricilone
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.friz.update.network.events;

import io.netty.buffer.ByteBuf;


/**
 * Created by Kyle Fricilone on 9/20/2015.
 */
public final class FileResponseEvent {

	/**
	 * The priority of the file.
	 */
	private final boolean priority;
	/**
	 * The index and file id.
	 */
	private final int type, file;
	
	/**
	 * The data of the file.
	 */
	private final ByteBuf container;

	public FileResponseEvent(boolean priority, int type, int file, ByteBuf container) {
		this.priority = priority;
		this.type = type;
		this.file = file;
		this.container = container;
	}

	public ByteBuf getContainer() {
		return container;
	}

	public int getFile() {
		return file;
	}

	public int getType() {
		return type;
	}

	public boolean isPriority() {
		return priority;
	}

}
