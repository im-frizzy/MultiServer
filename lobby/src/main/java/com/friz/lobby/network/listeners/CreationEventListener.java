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

package com.friz.lobby.network.listeners;

import com.friz.lobby.network.LobbySessionContext;
import com.friz.lobby.network.events.CreationRequestEvent;
import com.friz.lobby.network.events.CreationResponseEvent;
import com.friz.lobby.network.events.SocialInitRequestEvent;
import com.friz.lobby.network.events.SocialInitResponseEvent;
import com.friz.network.event.EventListener;

/**
 * Created by Kyle Fricilone on 9/18/2015.
 */
public class CreationEventListener implements EventListener<CreationRequestEvent, LobbySessionContext> {

    @Override
    public void onEvent(CreationRequestEvent event, LobbySessionContext context) {
        context.write(new CreationResponseEvent(2));
    }
}
