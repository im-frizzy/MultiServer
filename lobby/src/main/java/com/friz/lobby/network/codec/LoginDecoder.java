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

package com.friz.lobby.network.codec;

import com.friz.lobby.network.events.LoginRequestEvent;
import com.friz.network.Constants;
import com.friz.network.module.Module;
import com.friz.lobby.network.modules.ClientTypeModule;
import com.friz.lobby.network.modules.ClientVersionModule;
import com.friz.network.utility.BufferUtils;
import com.friz.network.utility.XTEA;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle Fricilone on 9/22/2015.
 */
public class LoginDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        if (!buf.isReadable())
            return;

        int type = buf.readUnsignedByte();
        int size = buf.readUnsignedShort();

        if (!buf.isReadable(size))
            return;

        int major = buf.readInt();
        int minor = buf.readInt();

        int rsaSize = buf.readUnsignedShort();
        byte[] rsa = new byte[rsaSize];
        buf.readBytes(rsa);

        ByteBuf rsaBuf = Unpooled.wrappedBuffer(new BigInteger(rsa).modPow(Constants.LOGIN_EXPONENT, Constants.LOGIN_MODULUS).toByteArray());
        int rsaMagic = rsaBuf.readUnsignedByte();

        int[] key = new int[4];
        for (int i = 0; i < key.length; i++)
            key[i] = rsaBuf.readInt();

        int block = rsaBuf.readUnsignedByte();

        if (block == 1 || block == 3) {
            int code = rsaBuf.readUnsignedMedium();
            rsaBuf.readerIndex(rsaBuf.readerIndex() + 1);
            System.out.println(new GoogleAuthenticator().authorize("OE2ZSYF6T7N2R5CG", code));
        } else if (block == 0) {
            int trusted = rsaBuf.readInt();
        } else if (block == 2) {
            rsaBuf.readerIndex(rsaBuf.readerIndex() + 4);
        }

        String password = BufferUtils.getString(rsaBuf);

        long serverKey = rsaBuf.readLong();
        long clientKey = rsaBuf.readLong();

        byte[] xtea = new byte[buf.readableBytes()];
        buf.readBytes(xtea);
        ByteBuf xteaBuf = Unpooled.wrappedBuffer(new XTEA(xtea).decrypt(key).toByteArray());

        String username = "";
        boolean asString = xteaBuf.readBoolean();
        if (asString)
            username = BufferUtils.getString(xteaBuf);
        else
            username = BufferUtils.getBase37(xteaBuf);

        int game = xteaBuf.readUnsignedByte();
        int lang = xteaBuf.readUnsignedByte();
        int display = xteaBuf.readUnsignedByte();
        int width = xteaBuf.readUnsignedShort();
        int height = xteaBuf.readUnsignedShort();

        int multisample = xteaBuf.readByte();

        byte[] uid = new byte[24];
        for (int i = 0; i < uid.length; i++)
            uid[i] = xteaBuf.readByte();

        String token = BufferUtils.getString(xteaBuf);

        int prefSize = xteaBuf.readUnsignedByte();
        int prefVersion = xteaBuf.readUnsignedByte();
        int aPref = xteaBuf.readUnsignedByte();
        int antiAliasing = xteaBuf.readUnsignedByte();
        int aPref1 = xteaBuf.readUnsignedByte();
        int bloom = xteaBuf.readUnsignedByte();
        int brightness = xteaBuf.readUnsignedByte();
        int buildArea = xteaBuf.readUnsignedByte();
        int aPref2 = xteaBuf.readUnsignedByte();
        int flickeringEffects = xteaBuf.readUnsignedByte();
        int fog = xteaBuf.readUnsignedByte();
        int groundBlending = xteaBuf.readUnsignedByte();
        int groundDecoration = xteaBuf.readUnsignedByte();
        int idleAnimations = xteaBuf.readUnsignedByte();
        int lighting = xteaBuf.readUnsignedByte();
        int sceneryShadows = xteaBuf.readUnsignedByte();
        int aPref3 = xteaBuf.readUnsignedByte();
        int nullPref = xteaBuf.readUnsignedByte();
        int orthoMode = xteaBuf.readUnsignedByte();
        int particles = xteaBuf.readUnsignedByte();
        int removeRoofs = xteaBuf.readUnsignedByte();
        int maxScreenSize = xteaBuf.readUnsignedByte();
        int skyboxes = xteaBuf.readUnsignedByte();
        int mobShadows = xteaBuf.readUnsignedByte();
        int textures = xteaBuf.readUnsignedByte();
        int desiredToolkit = xteaBuf.readUnsignedByte();
        int nullPref1 = xteaBuf.readUnsignedByte();
        int water = xteaBuf.readUnsignedByte();
        int screenSize = xteaBuf.readUnsignedByte();
        int customCursors = xteaBuf.readUnsignedByte();
        int graphics = xteaBuf.readUnsignedByte();
        int cpu = xteaBuf.readUnsignedByte();
        int aPref4 = xteaBuf.readUnsignedByte();
        int safeMode = xteaBuf.readUnsignedByte();
        int aPref5 = xteaBuf.readUnsignedByte();
        int aPref6 = xteaBuf.readUnsignedByte();
        int aPref7 = xteaBuf.readUnsignedByte();
        int soundEffectsVolume = xteaBuf.readUnsignedByte();
        int areaSoundsVolume = xteaBuf.readUnsignedByte();
        int voiceOverVolume = xteaBuf.readUnsignedByte();
        int musicVolume = xteaBuf.readUnsignedByte();
        int themeMusicVolume = xteaBuf.readUnsignedByte();
        int steroSound = xteaBuf.readUnsignedByte();

        int infoVersion = xteaBuf.readUnsignedByte();
        int osType = xteaBuf.readUnsignedByte();
        boolean arch64 = xteaBuf.readBoolean();
        int versionType = xteaBuf.readUnsignedByte();
        int vendorType = xteaBuf.readUnsignedByte();
        int jMajor = xteaBuf.readUnsignedByte();
        int jMinor = xteaBuf.readUnsignedByte();
        int jPatch = xteaBuf.readUnsignedByte();
        boolean falseBool = xteaBuf.readBoolean();
        int heapSize = xteaBuf.readUnsignedShort();
        int pocessorCount = xteaBuf.readUnsignedByte();
        int cpuPhyscialMemory = xteaBuf.readUnsignedMedium();
        int cpuClock = xteaBuf.readUnsignedShort();
        String gpuName = BufferUtils.getJagString(xteaBuf);
        String aString = BufferUtils.getJagString(xteaBuf);
        String dxVersion = BufferUtils.getJagString(xteaBuf);
        String aString1 = BufferUtils.getJagString(xteaBuf);
        int gpuDriverMonth = xteaBuf.readUnsignedByte();
        int gpuDriverYear = xteaBuf.readUnsignedShort();
        String cpuType = BufferUtils.getJagString(xteaBuf);
        String cpuName = BufferUtils.getJagString(xteaBuf);
        int cpuThreads = xteaBuf.readUnsignedByte();
        int anInt = xteaBuf.readUnsignedByte();
        int anInt1 = xteaBuf.readInt();
        int anInt2 = xteaBuf.readInt();
        int anInt3 = xteaBuf.readInt();
        int anInt4 = xteaBuf.readInt();
        String aString2 = BufferUtils.getJagString(xteaBuf);

        int anInt5 = xteaBuf.readInt();
        String aString3 = BufferUtils.getString(xteaBuf);
        int affiliate = xteaBuf.readInt();
        int anInt6 = xteaBuf.readInt();
        String aString4 = BufferUtils.getString(xteaBuf);
        int anInt7 = xteaBuf.readUnsignedByte();

        int[] checksums = new int[(xteaBuf.readableBytes() / 4) + 1];
        for (int i = 0; i < checksums.length; i++) {
            if (i == 32)
                checksums[i] = -1;
            else
                checksums[i] = xteaBuf.readInt();
        }

        final List<Module> modules = new ArrayList<>();
        modules.add(new ClientVersionModule(major, minor));
        modules.add(new ClientTypeModule(game, lang, display, width, height));
        out.add(new LoginRequestEvent(modules));
    }
}
