package dev.cammiescorner.laserpointer.common.registry;

import dev.cammiescorner.laserpointer.LaserPointer;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class EntityTags {
	public static final TagKey<EntityType<?>> THIN_ENTITIES = TagKey.of(Registry.ENTITY_TYPE_KEY, LaserPointer.id("thin_entities"));
}
