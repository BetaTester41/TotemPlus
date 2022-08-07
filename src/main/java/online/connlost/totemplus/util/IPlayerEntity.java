package online.connlost.totemplus.util;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public interface IPlayerEntity {
    public ArrayList<BlockPos> getLastBlockPos();
    public void setLastBlockPos(BlockPos pos);
}
