package net.farrucho.openblocks.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.farrucho.openblocks.OpenBlocks;
import net.farrucho.openblocks.block.OpenBlocksModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.renderer.v1.render.RenderLayerHelper;

/**
 * Renders the elevator block as whatever block it's camouflaged as. Because this asks
 * Minecraft's own block model renderer to draw the target BlockState directly (with real world
 * context), it automatically gets the correct texture, tint (e.g. grass/leaves color), and
 * lighting/ambient occlusion for ANY block - no per-block model/texture setup required on our end.
 */
@Environment(EnvType.CLIENT)
public class ElevatorBlockEntityRenderer implements BlockEntityRenderer<ElevatorBlockEntity> {

    private BlockState lastLoggedState = null;

    public ElevatorBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        // Fires once when Fabric wires up the renderer
        //OpenBlocks.LOGGER.info("[Elevator DEBUG] ElevatorBlockEntityRenderer constructed - registration is working.");
    }

    @Override
    public void render(
            ElevatorBlockEntity entity,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            Vec3d cameraPos
    ) {
        World world = entity.getWorld();
        if (world == null) {
            return;
        }

        BlockState renderState = entity.getCamouflageState();

        if (renderState == null) {
            // No camouflage applied yet - show the elevator block's own plain look.
            renderState = OpenBlocksModBlocks.ELEVATOR_BLOCK.getDefaultState();
        }

        BlockPos pos = entity.getPos();

        if (!java.util.Objects.equals(renderState, lastLoggedState)) {
            //OpenBlocks.LOGGER.info("[Elevator DEBUG] render() at {} now drawing state = {}", pos, renderState);
            lastLoggedState = renderState;
        }

        try {
            matrices.push();

            MinecraftClient client = MinecraftClient.getInstance();
            BlockRenderManager blockRenderManager = client.getBlockRenderManager();
            BlockStateModel model = blockRenderManager.getModel(renderState);

            // 1.21.6 FIX: Passing `vertexConsumers::getBuffer` satisfies the new BlockVertexConsumerProvider interface
            blockRenderManager.getModelRenderer().render(
                    world,
                    model,
                    renderState,
                    pos,
                    matrices,
                    RenderLayerHelper.movingDelegate(vertexConsumers),
                    true,
                    renderState.getRenderingSeed(pos),
                    overlay
            );

            matrices.pop();
        } catch (Throwable t) {
            //OpenBlocks.LOGGER.error("[Elevator DEBUG] render threw an exception at " + pos, t);
        }
    }
}