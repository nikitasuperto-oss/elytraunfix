package com.example.mixin;

import net.minecraft.world.entity.LivingEntity; // Обновленный путь к LivingEntity в новых версиях
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LavaElytraMixin {

    @Inject(method = "canStartFallFlying", at = @At("HEAD"), cancellable = true)
    private void allowLavaFly(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.isInLava()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isInLava", at = @At("HEAD"), cancellable = true)
    private void ignoreLavaCheck(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.isFallFlying()) {
            cir.setReturnValue(false);
        }
    }
}