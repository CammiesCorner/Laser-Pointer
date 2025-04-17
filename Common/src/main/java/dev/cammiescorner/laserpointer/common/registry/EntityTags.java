package dev.cammiescorner.laserpointer.common.registry;

import dev.cammiescorner.laserpointer.LaserPointer;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EntityTags {
	public static final TagKey<EntityType<?>> THIN_ENTITIES = TagKey.create(Registries.ENTITY_TYPE, LaserPointer.id("thin_entities"));
}
