package online.connlost.totemplus.mixin;

import online.connlost.totemplus.util.TotemFeatures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.world.World;
import net.minecraft.registry.tag.TagKey;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    
    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Redirect(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean tryUseTotemBypass(DamageSource instance, TagKey<DamageType> tag) {
        return (! instance.isOf(DamageTypes.OUT_OF_WORLD)) && instance.isIn(tag);
    }


    @Inject(method = "tryUseTotem", at=@At(value = "NEW", target = "net/minecraft/entity/effect/StatusEffectInstance", ordinal = 0))
    private void savePlayerFromVoid(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        if (source.isOf(DamageTypes.OUT_OF_WORLD)){
            TotemFeatures.reviveFromVoid(this);
        }
    }

}

