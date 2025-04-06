package dev.cammiescorner.laserpointer.fabric.common.registry;

import dev.cammiescorner.laserpointer.LaserPointer;
import dev.cammiescorner.laserpointer.fabric.common.items.LaserPointerItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.util.LinkedHashMap;

public class ModItems {
	//-----Item Map-----//
	public static final LinkedHashMap<Item, ResourceLocation> ITEMS = new LinkedHashMap<>();

	//-----Items-----//
	public static final Item RED_LASER_POINTER = create("red_laser_pointer", new LaserPointerItem(DyeColor.RED));
	public static final Item GREEN_LASER_POINTER = create("green_laser_pointer", new LaserPointerItem(DyeColor.GREEN));
	public static final Item BLUE_LASER_POINTER = create("blue_laser_pointer", new LaserPointerItem(DyeColor.BLUE));
	public static final Item YELLOW_LASER_POINTER = create("yellow_laser_pointer", new LaserPointerItem(DyeColor.YELLOW));
	public static final Item PURPLE_LASER_POINTER = create("purple_laser_pointer", new LaserPointerItem(DyeColor.PURPLE));

	//-----Registry-----//
	public static void register() {
//		FabricItemGroupBuilder.create(LaserPointer.id("general")).icon(() -> new ItemStack(ModItems.RED_LASER_POINTER)).appendItems(entries -> {
//			entries.add(new ItemStack(RED_LASER_POINTER));
//			entries.add(new ItemStack(GREEN_LASER_POINTER));
//			entries.add(new ItemStack(BLUE_LASER_POINTER));
//			entries.add(new ItemStack(YELLOW_LASER_POINTER));
//			entries.add(new ItemStack(PURPLE_LASER_POINTER));
//		}).build();

		ITEMS.keySet().forEach(item -> Registry.register(BuiltInRegistries.ITEM, ITEMS.get(item), item));
	}

	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, LaserPointer.id(name));
		return item;
	}
}
