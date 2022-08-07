package online.connlost.totemplus.mixin;

import online.connlost.totemplus.util.IPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "move", at = @At(value = "HEAD"))
    private void updateLastBlockPos(MovementType type, Vec3d movement, CallbackInfo ci){
        Object that = this;
        if (that instanceof PlayerEntity){
            PlayerEntity player = ((PlayerEntity)that);
            if (player.isOnGround()){
                ((IPlayerEntity)player).setLastBlockPos(player.getBlockPos());
            }
        }
    }
}
