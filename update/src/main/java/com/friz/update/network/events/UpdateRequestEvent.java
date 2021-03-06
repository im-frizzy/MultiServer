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


import com.friz.network.event.Event;

/**
 * Created by Kyle Fricilone on 9/20/2015.
 */
public final class UpdateRequestEvent implements Event {

	/**
	 * The handshake type of the client
	 */
	private final int type;

	/**
	 * The version of the client
	 */
	private final int version;
	
	/**
	 * The sub version of the client
	 */
	private final int subVersion;
	
	/**
	 * The validation key from the web parameter.
	 */
	private final String validationKey;

	/**
	 * The language id of the client
	 */
	private final int langId;

	/**
	 * Creates a new UpdateValidationMessage.
	 * @param version The version of the client.
	 */
	public UpdateRequestEvent(int type, int version, int subVersion, String validationKey, int lang) {
		this.type = type;
		this.version = version;
		this.subVersion = subVersion;
		this.validationKey = validationKey;
		this.langId = lang;
	}

	/**
	 * Gets the type.
	 * @return the type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Gets the sub version.
	 * @return the subVersion.
	 */
	public int getSubVersion() {
		return subVersion;
	}

	/**
	 * Gets the validation key.
	 * @return the validationKey.
	 */
	public String getValidationKey() {
		return validationKey;
	}

	/**
	 * Gets the version of the client.
	 * @return The version.
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Gets the language id.
	 * @return the langId.
	 */
	public int getLangId() {
		return langId;
	}
}
