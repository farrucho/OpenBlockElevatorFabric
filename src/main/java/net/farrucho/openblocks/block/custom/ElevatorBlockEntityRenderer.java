package net.farrucho.openblocks.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.MovingBlockRenderState;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ElevatorBlockEntityRenderer
        implements BlockEntityRenderer<ElevatorBlockEntity, ElevatorBlockEntityRenderState> {

    public ElevatorBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public ElevatorBlockEntityRenderState createRenderState() {
        return new ElevatorBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(
            ElevatorBlockEntity entity,
            ElevatorBlockEntityRenderState state,
            float tickProgress,
            Vec3d cameraPos,
            ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay
    ) {
        BlockEntityRenderState.updateBlockEntityRenderState(
                entity,
                state,
                crumblingOverlay
        );

        World world = entity.getWorld();
        if (world == null) {
            return;
        }

        BlockState camouflage = entity.getCamouflageState();

        // If there is no camouflage, flag it as false and return.
        // The chunk renderer will natively display the default elevator block.
        if (camouflage == null) {
            state.hasCamouflage = false;
            return;
        }

        state.hasCamouflage = true;
        MovingBlockRenderState moving = new MovingBlockRenderState();

        moving.blockState = camouflage;
        moving.world = world;
        moving.entityBlockPos = entity.getPos();
        moving.fallingBlockPos = entity.getPos();
        moving.biome = world.getBiome(entity.getPos());

        state.movingBlock = moving;
    }

    @Override
    public void render(
            ElevatorBlockEntityRenderState state,
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            CameraRenderState cameraState
    ) {
        if (state.hasCamouflage) {
            matrices.push();

            // Shrunk from 0.002f to 0.0002f.
            // This micro-offset is enough to stop Z-fighting but keeps
            // the camouflage tucked safely inside the vanilla crumble effect.
            float offset = 0.0002f;
            matrices.translate(-offset, -offset, -offset);
            matrices.scale(1f + (offset * 2), 1f + (offset * 2), 1f + (offset * 2));

            queue.submitMovingBlock(
                    matrices,
                    state.movingBlock
            );

            matrices.pop();
        }
    }
}