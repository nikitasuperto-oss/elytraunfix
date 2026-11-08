package com.example.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LavaElytraMixin {

    // Разрешаем активировать элитры, даже если игрок в лаве
    @Inject(method = "isInLava", at = @At("HEAD"), cancellable = true, require = 0)
    private void allowLavaFlyStart(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        // Если игрок пытается взлететь (нажимает пробел в падении/прыжке в лаве) или уже летит
        if (entity.isFallFlying()) {
            cir.setReturnValue(false); // Игнорируем лаву во время полета
        }
    }
}