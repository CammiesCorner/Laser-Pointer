package dev.cammiescorner.laserpointer.fabric.entrypoints;

import dev.cammiescorner.laserpointer.fabric.common.registry.ItemTags;
import dev.cammiescorner.laserpointer.fabric.common.registry.ModItems;
import dev.cammiescorner.laserpointer.fabric.common.registry.ModParticles;
import dev.upcraft.sparkweave.api.annotation.CalledByReflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;

@CalledByReflection
public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        ModItems.register();
        ModParticles.register();

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if(player.getItemInHand(hand).is(ItemTags.LASER_POINTERS) && !player.isShiftKeyDown()) {
                player.getItemInHand(hand).use(world, player, hand);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(player.getItemInHand(hand).is(ItemTags.LASER_POINTERS) && !player.isShiftKeyDown()) {
                player.getItemInHand(hand).use(world, player, hand);
                return InteractionResult.CONSUME;
            }

            return InteractionResult.PASS;
        });
    }
}
