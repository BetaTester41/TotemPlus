package online.connlost.totemplus.mixin;

import online.connlost.totemplus.util.IDamageSource;
import online.connlost.totemplus.util.TotemFeatures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    private boolean manipulated = false;

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryUseTotem", at=@At(value = "HEAD"))
    private void setOutOfWorldFalse(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        Entity e = this;
        PlayerEntity player = null;
        if (e instanceof PlayerEntity){
            player = (PlayerEntity)e;
        }
        if (source.isOutOfWorld() && this.getPos().y<-60 && player != null && !player.isCreative()){
            ((IDamageSource)source).setOutOfWorld(false);
            this.manipulated = true;
        }
    }

    @Inject(method = "tryUseTotem", at=@At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/damage/DamageSource;isOutOfWorld()Z"))
    private void resetOutOfWorld(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        if (manipulated){
            manipulated = false;
            ((IDamageSource)source).setOutOfWorld(true);
        }
    }


    @Inject(method = "tryUseTotem", at=@At(value = "NEW", target = "net/minecraft/entity/effect/StatusEffectInstance", ordinal = 0))
    private void savePlayerFromVoid(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        if (source.isOutOfWorld()){
            TotemFeatures.reviveFromVoid(this);
        }
    }

}

