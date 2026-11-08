package com.example.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LavaElytraMixin {

    // Перехватываем метод проверки движения/падения, чтобы игра не запрещала полет из-за лавы
    @Inject(method = "updateFallFlying", at = @At("HEAD"), cancellable = true, require = 0)
    private void forceLavaFlight(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        // Если игрок в лаве и нажал кнопку полета (или пытается активировать элитры)
        if (entity.isInLava() && entity.getFallFlyingTicks() > 0) {
            cir.setReturnValue(true);
        }
    }

    // Дополнительно отключаем трение/замедление лавы для элитр
    @Inject(method = "isInLava", at = @At("HEAD"), cancellable = true, require = 0)
    private void ignoreLavaCheck(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.isFallFlying()) {
            cir.setReturnValue(false);
        }
    }
}