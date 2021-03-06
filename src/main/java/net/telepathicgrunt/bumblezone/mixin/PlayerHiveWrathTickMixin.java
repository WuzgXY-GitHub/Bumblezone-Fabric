package net.telepathicgrunt.bumblezone.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.telepathicgrunt.bumblezone.Bumblezone;
import net.telepathicgrunt.bumblezone.effects.BzEffects;
import net.telepathicgrunt.bumblezone.effects.WrathOfTheHiveEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerHiveWrathTickMixin {
    // Handles where wrath of the hive can be on,
    // change player fog color when effect is active,
    // and prevent player from falling into void.
    @Inject(method = "tick",
            at = @At(value = "TAIL"))
    private void onEntityTick(CallbackInfo ci) {

        //Bumblezone.LOGGER.log(Level.INFO, "started");
        //grabs the capability attached to player for dimension hopping
        PlayerEntity playerEntity = ((PlayerEntity) (Object) this);

        //removes the wrath of the hive if it is disallowed outside dimension
        if (!(playerEntity.getEntityWorld().getRegistryKey().getValue() == Bumblezone.MOD_FULL_ID  || Bumblezone.BZ_CONFIG.allowWrathOfTheHiveOutsideBumblezone) &&
                playerEntity.hasStatusEffect(BzEffects.WRATH_OF_THE_HIVE)) {
            playerEntity.removeStatusEffect(BzEffects.WRATH_OF_THE_HIVE);
        }

        //Makes the fog redder when this effect is active
        if (playerEntity.hasStatusEffect(BzEffects.WRATH_OF_THE_HIVE)) {
            WrathOfTheHiveEffect.ACTIVE_WRATH = true;
        } else {
            WrathOfTheHiveEffect.ACTIVE_WRATH = false;
        }
    }

}