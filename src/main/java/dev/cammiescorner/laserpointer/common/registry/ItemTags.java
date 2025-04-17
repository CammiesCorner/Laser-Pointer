package dev.cammiescorner.laserpointer.common.registry;

import dev.cammiescorner.laserpointer.LaserPointer;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class ItemTags {
	public static final TagKey<Item> LASER_POINTERS = TagKey.of(Registry.ITEM_KEY, LaserPointer.id("laser_pointers"));
}
