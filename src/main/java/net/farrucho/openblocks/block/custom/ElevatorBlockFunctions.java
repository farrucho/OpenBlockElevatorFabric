package net.farrucho.openblocks.block.custom;

import net.farrucho.openblocks.block.OpenBlocksModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElevatorBlockFunctions {
    public static boolean goUp(BlockPos bp, World world, PlayerEntity player){
        Block CURRENT_ELEVATOR_TYPE_BLOCK = world.getBlockState(new BlockPos(bp.getX(),bp.getY(),bp.getZ())).getBlock();


        //////////player.sendMessage(Text.of(CURRENT_ELEVATOR_TYPE_BLOCK.toString()),true);

        for(int y = bp.getY() + 2; y <= bp.getY() + 21; ++y){//offset 25 em relacao a cabeca do player
            BlockPos currentBlockPos = new BlockPos(bp.getX(),y,bp.getZ());

            if(world.getBlockState(currentBlockPos).isOf(CURRENT_ELEVATOR_TYPE_BLOCK) &&
                    world.getBlockState(new BlockPos(bp.getX(),y+1,bp.getZ())).isOf(Blocks.AIR) &&
                        world.getBlockState(new BlockPos(bp.getX(),y+2,bp.getZ())).isOf(Blocks.AIR)){
                //player.setPos(bp.getX(), y+1, bp.getZ());
                player.teleport(bp.getX() + 0.5, y+1, bp.getZ() + 0.5);
                return true;
            }
        }
        return false;
    }

    //METER RANGE DE 20 BLOCOS!!! //meter som
    public static boolean goDown(BlockPos bp, World world, PlayerEntity player){
        Block CURRENT_ELEVATOR_TYPE_BLOCK = world.getBlockState(new BlockPos(bp.getX(),bp.getY(),bp.getZ())).getBlock();
        //////////player.sendMessage(Text.of(CURRENT_ELEVATOR_TYPE_BLOCK.toString()),true);


        for(int y = bp.getY() - 2; y >= bp.getY() - 21; --y){
            BlockPos currentBlockPos = new BlockPos(bp.getX(),y,bp.getZ());

            //player.sendMessage(Text.of( "y = " + y + world.getBlockState(currentBlockPos).isOf(OpenBlocksModBlocks.ELEVATOR_BLOCK)),true);
            if(world.getBlockState(currentBlockPos).isOf(CURRENT_ELEVATOR_TYPE_BLOCK) &&
                    world.getBlockState(new BlockPos(bp.getX(),y+1,bp.getZ())).isOf(Blocks.AIR) &&
                    world.getBlockState(new BlockPos(bp.getX(),y+2,bp.getZ())).isOf(Blocks.AIR)){
                player.teleport(bp.getX() + 0.5, y+1, bp.getZ() + 0.5);
                player.setSneaking(false);
                //player.sendMessage(Text.of("Tp para baixo feito"),true);
                return true;
            }
        }
        return false;
    }


}
