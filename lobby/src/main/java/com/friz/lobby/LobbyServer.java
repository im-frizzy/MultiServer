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
package com.friz.lobby;

import com.friz.cache.Cache;
import com.friz.lobby.network.LobbyChannelHandler;
import com.friz.lobby.network.codec.LobbyInitDecoder;
import com.friz.lobby.network.codec.LobbyInitEncoder;
import com.friz.lobby.network.events.CreationRequestEvent;
import com.friz.lobby.network.events.LobbyInitRequestEvent;
import com.friz.lobby.network.events.LoginRequestEvent;
import com.friz.lobby.network.events.SocialInitRequestEvent;
import com.friz.lobby.network.events.listeners.CreationEventListener;
import com.friz.lobby.network.events.listeners.LobbyInitEventListener;
import com.friz.lobby.network.events.listeners.LoginRequestEventListener;
import com.friz.lobby.network.events.listeners.SocialInitEventListener;
import com.friz.network.NetworkServer;
import com.friz.network.SessionContext;
import com.friz.network.event.EventHub;
import com.friz.network.module.ModuleHub;
import com.friz.lobby.network.modules.ClientTypeModule;
import com.friz.lobby.network.modules.listeners.ClientTypeModuleListener;
import com.friz.lobby.network.modules.ClientVersionModule;
import com.friz.lobby.network.modules.listeners.ClientVersionModuleListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Kyle Fricilone on 9/22/2015.
 */
public class LobbyServer extends NetworkServer {

    private final Cache cache;

    private final EventHub eventHub = new EventHub();
    private final ModuleHub moduleHub = new ModuleHub();
    private final AttributeKey<SessionContext> attr = AttributeKey.valueOf("lobby-attribute-key");

    private final ConcurrentMap<Integer, Channel> channels = new ConcurrentHashMap<>();

    public LobbyServer(Cache c) {
        this.cache = c;
    }

    @Override
    public void initialize() {
        group = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        bootstrap = new ServerBootstrap();

        LobbyServer s = this;

        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                //.handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(LobbyInitEncoder.class.getName(), new LobbyInitEncoder());
                        p.addLast(LobbyInitDecoder.class.getName(), new LobbyInitDecoder());
                        p.addLast(LobbyChannelHandler.class.getName(), new LobbyChannelHandler(s));
                    }

                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY, true);

        eventHub.listen(LobbyInitRequestEvent.class, new LobbyInitEventListener());
        eventHub.listen(SocialInitRequestEvent.class, new SocialInitEventListener());
        eventHub.listen(CreationRequestEvent.class, new CreationEventListener());
        eventHub.listen(LoginRequestEvent.class, new LoginRequestEventListener());

        moduleHub.listen(ClientVersionModule.class, new ClientVersionModuleListener());
        moduleHub.listen(ClientTypeModule.class, new ClientTypeModuleListener());
    }

    @Override
    public void bind() {
        try {
            future = bootstrap.bind(new InetSocketAddress("0.0.0.0", 40000)).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public final Cache getCache() {
        return cache;
    }

    public final EventHub getEventHub() {
        return eventHub;
    }

    public final ModuleHub getModuleHub() { return moduleHub; }

    public final AttributeKey<SessionContext> getAttr() {
        return attr;
    }

    public final ConcurrentMap<Integer, Channel> getChannels() { return channels; }

    public final int getHashForChannel(Channel c) {
        if (channels.containsValue(c)) {
            for (Map.Entry<Integer, Channel> entry : channels.entrySet()) {
                if (entry.getValue().equals(c))
                    return entry.getKey();
            }
        }
        return -1;
    }
}
