package online.connlost.totemplus.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class TotemFeatures {
    public static void reviveFromVoid(Entity player) {
        player.setVelocity(0, 0, 0);
        player.refreshPositionAfterTeleport(findSafePos(player));
        if (player instanceof LivingEntity) {
            ((LivingEntity) player).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100, 1));
            ((LivingEntity) player).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 1));
        }
    }

    public static Vec3d findSafePos(Entity player) {
        ArrayList<BlockPos> blockPoses = ((IPlayerEntity) player).getLastBlockPos();
        for (int i = blockPoses.size() - 1; i >= 0; i--) {
            if (player.world.getBlockState(blockPoses.get(i).down()).isAir()) continue;
            return new Vec3d(blockPoses.get(i).getX(), blockPoses.get(i).getY(), blockPoses.get(i).getZ());
        }

        // fallback
        if (!player.world.getBlockState(new BlockPos(0, 64, 0)).isAir()) {
            for (int i = 65; i < 256; i++) {
                if (!player.world.getBlockState(new BlockPos(0, i, 0)).isAir()) continue;
                return new Vec3d(0, i, 0);
            }
        }
        if (player.world.getBlockState(new BlockPos(0, 63, 0)).isAir()) {
            for (int i = 62; i > 0; i--) {
                if (player.world.getBlockState(new BlockPos(0, i, 0)).isAir()) continue;
                return new Vec3d(0, i + 1, 0);
            }
        }
        return new Vec3d(0, 70, 0);
    }
}
