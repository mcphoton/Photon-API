/*
 * Copyright (c) 2016 MCPhoton <http://mcphoton.org> and contributors.
 *
 * This file is part of the Photon API <https://github.com/mcphoton/Photon-API>.
 *
 * The Photon API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Photon API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mcphoton.network.play.clientbound;

import java.nio.ByteBuffer;
import org.mcphoton.network.Packet;
import org.mcphoton.network.ProtocolHelper;
import org.mcphoton.network.ProtocolOutputStream;

/**
 *
 * @author TheElectronWill
 */
public class PlayerPositionAndLookPacket implements Packet {

	public double x, y, z;
	public float yaw, pitch;
	public byte flags;
	public int teleportId;

	@Override
	public int getId() {
		return 0x2E;
	}

	@Override
	public boolean isServerBound() {
		return false;
	}

	@Override
	public void writeTo(ProtocolOutputStream out) {
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeFloat(yaw);
		out.writeFloat(pitch);
		out.writeByte(flags);
		out.writeVarInt(teleportId);
	}

	@Override
	public Packet readFrom(ByteBuffer buff) {
		x = buff.getDouble();
		y = buff.getDouble();
		z = buff.getDouble();
		yaw = buff.getFloat();
		pitch = buff.getFloat();
		flags = buff.get();
		teleportId = ProtocolHelper.readVarInt(buff);
		return this;
	}

	@Override
	public String toString() {
		return "PlayerPositionAndLookPacket{" + "x=" + x + ", y=" + y + ", z=" + z + ", yaw=" + yaw + ", pitch=" + pitch + ", flags=" + flags + ", teleportId=" + teleportId + '}';
	}

}
