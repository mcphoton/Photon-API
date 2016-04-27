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
import java.util.UUID;
import org.mcphoton.network.Packet;
import org.mcphoton.network.ProtocolHelper;
import org.mcphoton.network.ProtocolOutputStream;

/**
 *
 * @author Maaattt
 * @author TheElectronWill
 */
public class SpawnObjectPacket implements Packet {

	public int entityID;
	public UUID objectUUID;
	public byte type;
	public double x, y, z;
	public float pitch, yaw;
	public int data;
	public short xVelocity, yVelocity, zVelocity;

	@Override
	public int getId() {
		return 0x00;
	}

	@Override
	public boolean isServerBound() {
		return false;
	}

	@Override
	public void writeTo(ProtocolOutputStream out) {
		out.writeVarInt(entityID);
		out.writeLong(objectUUID.getMostSignificantBits());
		out.writeLong(objectUUID.getLeastSignificantBits());
		out.writeByte(type);
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeByte(ProtocolHelper.toRotationStep(pitch));
		out.writeByte(ProtocolHelper.toRotationStep(yaw));
		out.writeInt(data);
		out.writeShort(xVelocity);
		out.writeShort(yVelocity);
		out.writeShort(zVelocity);
	}

	@Override
	public Packet readFrom(ByteBuffer buff) {
		entityID = ProtocolHelper.readVarInt(buff);
		long MSB = buff.getLong();
		long LSB = buff.getLong();
		objectUUID = new UUID(MSB, LSB);
		type = buff.get();
		x = buff.getDouble();
		y = buff.getDouble();
		z = buff.getDouble();
		pitch = ProtocolHelper.toDegrees(buff.get());
		yaw = ProtocolHelper.toDegrees(buff.get());
		data = buff.getInt();
		xVelocity = buff.getShort();
		yVelocity = buff.getShort();
		zVelocity = buff.getShort();
		return this;
	}

	@Override
	public String toString() {
		return "SpawnObjectPacket{" + "entityID=" + entityID + ", objectUUID=" + objectUUID + ", type=" + type + ", x=" + x + ", y=" + y + ", z=" + z + ", pitch=" + pitch + ", yaw=" + yaw + ", data=" + data + ", xVelocity=" + xVelocity + ", yVelocity=" + yVelocity + ", zVelocity=" + zVelocity + '}';
	}
}
