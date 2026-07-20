package net.farrucho.openblocks.mixin;

import net.farrucho.openblocks.block.OpenBlocksModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
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
@Mixin(LivingEntity.class)
public class OpenBlocksEntityJumpState {
    @Inject(method = "jump()V", at = @At("HEAD"))
    private void injected(CallbackInfo info) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!(entity instanceof PlayerEntity p)) {
            return;
        }
        World world = p.getEntityWorld();
        if (!world.isClient()) {
            BlockPos blockpos = p.getBlockPos().down();
            BlockState blockState = world.getBlockState(blockpos);

            if (blockState.isOf(OpenBlocksModBlocks.ELEVATOR_BLOCK)
                    && !p.isInSneakingPose()) {
                goUp(blockpos, world, p);
            }
        }
    }
}