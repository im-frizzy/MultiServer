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

package com.friz.audio.network.events;

import com.friz.network.event.Event;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Created by Kyle Fricilone on 9/18/2015.
 */
public class AudioRequestEvent implements Event {

    private final FullHttpRequest request;

    public AudioRequestEvent(FullHttpRequest r) {
        this.request = r;
    }

    public FullHttpRequest getRequest() {
        return request;
    }
}
