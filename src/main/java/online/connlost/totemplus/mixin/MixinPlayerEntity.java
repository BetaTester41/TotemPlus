package online.connlost.totemplus.mixin;

import online.connlost.totemplus.util.IPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends Entity implements IPlayerEntity {

    public MixinPlayerEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    private ArrayList<BlockPos> lastOnGroundPos = new ArrayList<>();


    @Override
    public ArrayList<BlockPos> getLastBlockPos(){
        return lastOnGroundPos;
    }

    @Override
    public void setLastBlockPos(BlockPos pos) {
        if (lastOnGroundPos.size()>0){
            if (lastOnGroundPos.get(lastOnGroundPos.size()-1).equals(pos)){
                return;
            }
        }
        lastOnGroundPos.add(pos);

        if(lastOnGroundPos.size()>100){
            lastOnGroundPos.remove(0);
        }
    }
}
