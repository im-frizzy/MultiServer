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

package com.friz.game.network;

import com.friz.game.GameServer;
import com.friz.network.event.Event;
import com.friz.network.event.EventLink;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Kyle Fricilone on 9/8/2015.
 */
public class GameChannelHandler extends SimpleChannelInboundHandler<Event> {

    private final GameServer server;

    public GameChannelHandler(GameServer s) {
        this.server = s;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("[GameServer] Channel Connected from: " + ctx.channel().remoteAddress().toString());
        ctx.channel().attr(server.getAttr()).set(new GameSessionContext(ctx.channel(), server));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Event msg) throws Exception {
        server.getHub().onLink(new EventLink(msg, ctx.channel().attr(server.getAttr()).get()));
    }
}
