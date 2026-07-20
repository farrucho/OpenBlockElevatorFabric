package net.farrucho.openblocks.block.custom;

import net.farrucho.openblocks.block.OpenBlocksModBlocks;
import net.farrucho.openblocks.config.ModConfigs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElevatorBlockFunctions {
    static int RANGE = ModConfigs.ELEVATOR_BLOCK_RANGE;

    public static boolean goUp(BlockPos bp, World world, PlayerEntity player){
        Block CURRENT_ELEVATOR_TYPE_BLOCK = world.getBlockState(new BlockPos(bp.getX(),bp.getY(),bp.getZ())).getBlock();


        //////////player.sendMessage(Text.of(CURRENT_ELEVATOR_TYPE_BLOCK.toString()),true);

        for(int y = bp.getY() + 2; y <= bp.getY() + RANGE + 1; ++y){//offset 25 em relacao a cabeca do player
            BlockPos currentBlockPos = new BlockPos(bp.getX(),y,bp.getZ());

            if(world.getBlockState(currentBlockPos).isOf(CURRENT_ELEVATOR_TYPE_BLOCK) &&
                    world.getBlockState(new BlockPos(bp.getX(),y+1,bp.getZ())).isOf(Blocks.AIR) &&
                    world.getBlockState(new BlockPos(bp.getX(),y+2,bp.getZ())).isOf(Blocks.AIR)){
                BlockPos destination = new BlockPos(bp.getX(), y+1, bp.getZ());
                playTeleportEffects(world, bp, destination);
                //player.setPos(bp.getX(), y+1, bp.getZ());
                player.teleport(
                        bp.getX() + 0.5,
                        y + 1,
                        bp.getZ() + 0.5,
                        false
                );
                return true;
            }
        }
        return false;
    }

    public static boolean goDown(BlockPos bp, World world, PlayerEntity player){
        Block CURRENT_ELEVATOR_TYPE_BLOCK = world.getBlockState(new BlockPos(bp.getX(),bp.getY(),bp.getZ())).getBlock();
        //////////player.sendMessage(Text.of(CURRENT_ELEVATOR_TYPE_BLOCK.toString()),true);


        for(int y = bp.getY() - 2; y >= bp.getY() - RANGE - 1; --y){
            BlockPos currentBlockPos = new BlockPos(bp.getX(),y,bp.getZ());

            //player.sendMessage(Text.of( "y = " + y + world.getBlockState(currentBlockPos).isOf(OpenBlocksModBlocks.ELEVATOR_BLOCK)),true);
            if(world.getBlockState(currentBlockPos).isOf(CURRENT_ELEVATOR_TYPE_BLOCK) &&
                    world.getBlockState(new BlockPos(bp.getX(),y+1,bp.getZ())).isOf(Blocks.AIR) &&
                    world.getBlockState(new BlockPos(bp.getX(),y+2,bp.getZ())).isOf(Blocks.AIR)){
                BlockPos destination = new BlockPos(bp.getX(), y+1, bp.getZ());
                playTeleportEffects(world, bp, destination);
                player.teleport(
                        bp.getX() + 0.5,
                        y + 1,
                        bp.getZ() + 0.5,
                        false
                );
                player.setSneaking(false);
                //player.sendMessage(Text.of("Tp para baixo feito"),true);
                return true;
            }
        }
        return false;
    }

    /**
     * Plays a small departure/arrival cue - a piston-style whoosh at the spot the player left,
     * a softer settling thump where they land, plus a cloud of particles at both ends.
     */
    private static void playTeleportEffects(World world, BlockPos from, BlockPos to){
        world.playSound(null, from.getX() + 0.5, from.getY() + 0.5, from.getZ() + 0.5,
                SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5f, 1.4f);
        world.playSound(null, to.getX() + 0.5, to.getY() + 0.5, to.getZ() + 0.5,
                SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5f, 1.4f);

        spawnTeleportParticles(world, from);
        spawnTeleportParticles(world, to);
    }

    private static void spawnTeleportParticles(World world, BlockPos pos){
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.15;
        double z = pos.getZ() + 0.5;

        // World#addParticle is a client-only no-op; on the logical server we have to use
        // ServerWorld#spawnParticles so the packet actually gets sent to nearby players.
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.CLOUD, x, y, z, 14, 0.3, 0.1, 0.3, 0.02);
            serverWorld.spawnParticles(ParticleTypes.END_ROD, x, y + 0.2, z, 6, 0.2, 0.25, 0.2, 0.01);
        } else {
            world.addParticleClient(
                    ParticleTypes.CLOUD,
                    x, y, z,
                    0, 0.05, 0
            );
        }
    }

}