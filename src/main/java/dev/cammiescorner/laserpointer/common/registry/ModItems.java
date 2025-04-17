package dev.cammiescorner.laserpointer.common.registry;

import dev.cammiescorner.laserpointer.LaserPointer;
import dev.cammiescorner.laserpointer.common.items.LaserPointerItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ModItems {
	//-----Item Map-----//
	public static final LinkedHashMap<Item, Identifier> ITEMS = new LinkedHashMap<>();

	//-----Items-----//
	public static final Item RED_LASER_POINTER = create("red_laser_pointer", new LaserPointerItem(DyeColor.RED));
	public static final Item GREEN_LASER_POINTER = create("green_laser_pointer", new LaserPointerItem(DyeColor.GREEN));
	public static final Item BLUE_LASER_POINTER = create("blue_laser_pointer", new LaserPointerItem(DyeColor.BLUE));
	public static final Item YELLOW_LASER_POINTER = create("yellow_laser_pointer", new LaserPointerItem(DyeColor.YELLOW));
	public static final Item PURPLE_LASER_POINTER = create("purple_laser_pointer", new LaserPointerItem(DyeColor.PURPLE));

	//-----Registry-----//
	public static void register() {
		FabricItemGroupBuilder.create(LaserPointer.id("general")).icon(() -> new ItemStack(ModItems.RED_LASER_POINTER)).appendItems(entries -> {
			entries.add(new ItemStack(RED_LASER_POINTER));
			entries.add(new ItemStack(GREEN_LASER_POINTER));
			entries.add(new ItemStack(BLUE_LASER_POINTER));
			entries.add(new ItemStack(YELLOW_LASER_POINTER));
			entries.add(new ItemStack(PURPLE_LASER_POINTER));
		}).build();

		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}

	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, LaserPointer.id(name));
		return item;
	}
}
