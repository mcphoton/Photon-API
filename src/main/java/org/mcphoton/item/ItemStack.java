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
package org.mcphoton.item;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.mcphoton.Photon;
import org.mcphoton.network.ProtocolOutputStream;
import org.mcphoton.network.ProtocolWriteable;

/**
 * A stack of items.
 *
 * @author TheElectronWill
 */
public class ItemStack implements ProtocolWriteable {

	protected ItemType type;
	protected int maxSize;
	protected int size, damage;

	public ItemStack(ItemType type, int maxSize, int size, int damage) {
		this.type = type;
		this.maxSize = maxSize;
		this.size = size;
		this.damage = damage;
	}

	/**
	 * @return true if the stack is empty.
	 */
	boolean isEmpty() {
		return size == 0;
	}

	/**
	 * @return true if the stack is full, ie if its size is equal to its max size.
	 */
	boolean isFull() {
		return size == maxSize;
	}

	/**
	 * @return the stack's size.
	 */
	int getSize() {
		return size;
	}

	/**
	 * Adds the given value to the current size value.
	 *
	 * @param delta the value to add
	 */
	void addSize(int delta) {
		size += delta;
	}

	/**
	 * Sets the stack's size.
	 */
	void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the max stack's size.
	 */
	int getMaxSize() {
		return maxSize;
	}

	/**
	 * @return the item's type.
	 */
	ItemType getType() {
		return type;
	}

	/**
	 * @return the stack's damage.
	 */
	int getDamage() {
		return damage;
	}

	/**
	 * Adds the given value to the current damage value.
	 *
	 * @param delta the value to add
	 */
	void addDamage(int delta) {
		this.damage += delta;
	}

	/**
	 * Sets the stack's damage.
	 */
	void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public void writeTo(ProtocolOutputStream out) throws IOException {
		if (size == 0) {//empty
			out.writeShort(-1);
		} else {
			out.writeShort(type.getId());
			out.writeByte(size);
			out.writeShort(damage);
			out.writeByte(0);//TODO write NBT data like enchantments
		}
	}

	public static ItemStack readFrom(ByteBuffer buff) throws IOException {
		int typeId = buff.getShort();
		if (typeId == -1) {
			return new ItemStack(Photon.getItemRegistry().getRegistered(0), 64, 0, 0);
		} else {
			int size = buff.get();
			int damage = buff.getShort();
			//TODO read NBT data like enchantments
			return new ItemStack(Photon.getItemRegistry().getRegistered(size), 64, size, damage);
		}
	}

	@Override
	public String toString() {
		return "ItemStack{" + "type=" + type + ", maxSize=" + maxSize + ", size=" + size + ", damage=" + damage + '}';
	}

}
