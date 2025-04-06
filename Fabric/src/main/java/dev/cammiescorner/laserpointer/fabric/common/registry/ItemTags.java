package dev.cammiescorner.laserpointer.fabric.common.registry;

import dev.cammiescorner.laserpointer.LaserPointer;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTags {
	public static final TagKey<Item> LASER_POINTERS = TagKey.create(Registries.ITEM, LaserPointer.id("laser_pointers"));
}
