package dev.cammiescorner.laserpointer.mixin;

import dev.cammiescorner.laserpointer.common.items.LaserPointerItem;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@Shadow @Final private ItemModels models;

	@Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
	private void malum$getHeldItemModel(ItemStack stack, World world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> info) {
		if(stack.getItem() instanceof LaserPointerItem) {
			BakedModel bakedModel = models.getModelManager().getModel(new ModelIdentifier("minecraft", "trident_in_hand", "inventory"));
			ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld) world : null;
			BakedModel bakedModel2 = bakedModel.getOverrides().apply(bakedModel, stack, clientWorld, entity, seed);
			info.setReturnValue(bakedModel2 == null ? models.getModelManager().getMissingModel() : bakedModel2);
		}
	}
}
