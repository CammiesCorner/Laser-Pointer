package dev.cammiescorner.laserpointer.fabric.mixin;

import dev.cammiescorner.laserpointer.common.items.LaserPointerItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@Shadow @Final private ItemModelShaper itemModelShaper;

	@Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
	private void getHeldItemModel(ItemStack stack, Level world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> info) {
		if(stack.getItem() instanceof LaserPointerItem) {
			BakedModel bakedModel = itemModelShaper.getModelManager().getModel(new ModelResourceLocation(ResourceLocation.withDefaultNamespace("trident_in_hand"), "inventory"));
			ClientLevel clientWorld = world instanceof ClientLevel ? (ClientLevel) world : null;
			BakedModel bakedModel2 = bakedModel.getOverrides().resolve(bakedModel, stack, clientWorld, entity, seed);
			info.setReturnValue(bakedModel2 == null ? itemModelShaper.getModelManager().getMissingModel() : bakedModel2);
		}
	}
}
