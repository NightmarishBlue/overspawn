package blue.nightmarish.overspawn.mixin.world.entity;

import blue.nightmarish.overspawn.config.OverspawnConfig;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(MobCategory.class)
public abstract class MobCategoryChangeCreature {
    @Shadow
    @Final
    private String name;

    // we can do this if we want to change the value often. otherwise we can use ~reflection~
    @Inject(method = "getMaxInstancesPerChunk", cancellable = true, at = @At("HEAD"))
    void onGetMaxInstancesPerChunk(CallbackInfoReturnable<Integer> cir) {
        //if (Objects.equals(this.name, "creature")) cir.setReturnValue(OverspawnConfig.CREATURE_SPAWN_CAP.get());
    }

    // remove the inherent persistence of all animals
    @Inject(method = "isPersistent", cancellable = true, at = @At("HEAD"))
    void onIsPersistent(CallbackInfoReturnable<Boolean> cir) {
        if (Objects.equals(this.name, "creature")) cir.setReturnValue(false);
    }
}
