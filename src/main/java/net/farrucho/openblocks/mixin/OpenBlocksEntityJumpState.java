package net.farrucho.openblocks.mixin;

import net.farrucho.openblocks.block.OpenBlocksModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.farrucho.openblocks.block.custom.ElevatorBlockFunctions.goUp;

@Mixin(PlayerEntity.class)
public class OpenBlocksEntityJumpState {
    @Inject(at = @At("HEAD"), method = "jump()V")
    private void injected(CallbackInfo info) {
        PlayerEntity p = (PlayerEntity)(Object)this;
        //World world = p.getWorld();
        World world = p.getEntityWorld();
        if(!p.world.isClient()){
            //p.sendMessage(Text.literal("player jumped"));
            Position posUnder = new Position() {
                @Override
                public double getX() {
                    return p.getX();
                }

                @Override
                public double getY() {
                    return p.getY() - 1;
                }

                @Override
                public double getZ() {
                    return p.getZ();
                }
            };

            BlockPos blockpos = new BlockPos(posUnder);
            //Block block = world.getBlockState(blockpos).getBlock();
            BlockState blockState = world.getBlockState(blockpos);
            if(blockState.isOf(OpenBlocksModBlocks.ELEVATOR_BLOCK) && !p.isInSneakingPose()){
                //p.sendMessage(Text.literal("Elevador para cima"));
                goUp(blockpos, world, p);
            }
        }
    }
}