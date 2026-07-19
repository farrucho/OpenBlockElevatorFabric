package net.farrucho.openblocks.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.farrucho.openblocks.OpenBlocks;
import net.farrucho.openblocks.block.OpenBlocksModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Renders the elevator block as whatever block it's camouflaged as. Because this asks
 * Minecraft's own BlockRenderManager to draw the target BlockState directly, it automatically
 * gets the correct texture, tint (e.g. grass/leaves color), and lighting/ambient occlusion for
 * ANY block - no per-block model/texture setup required on our end.
 */
@Environment(EnvType.CLIENT)
public class ElevatorBlockEntityRenderer implements BlockEntityRenderer<ElevatorBlockEntity> {

    private final Random random = Random.create();
    private BlockState lastLoggedState = null;
    private boolean loggedConstruction = false;

    public ElevatorBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        // Fires once when Fabric wires up the renderer - if this line never appears in the log,
        // BlockEntityRendererRegistry.register() never ran / never matched this BlockEntityType.
        OpenBlocks.LOGGER.info("[Elevator DEBUG] ElevatorBlockEntityRenderer constructed - registration is working.");
    }

    @Override
    public void render(ElevatorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        if (world == null) {
            return;
        }

        BlockState renderState = entity.getCamouflageState();

        if (renderState == null) {
            // No camouflage applied yet - show the elevator block's own plain look. This is
            // safe to draw here (and won't cause the earlier flicker) because ElevatorBlock's
            // getRenderType() returns INVISIBLE, so the chunk mesh contributes zero geometry
            // of its own - this BER call is now the ONLY thing drawing the block, camo or not.
            renderState = OpenBlocksModBlocks.ELEVATOR_BLOCK.getDefaultState();
        }

        if (!java.util.Objects.equals(renderState, lastLoggedState)) {
            OpenBlocks.LOGGER.info("[Elevator DEBUG] render() at {} now drawing state = {}", entity.getPos(), renderState);
            lastLoggedState = renderState;
        }

        try {
            matrices.push();

            MinecraftClient client = MinecraftClient.getInstance();
            net.minecraft.client.render.block.BlockRenderManager blockRenderManager = client.getBlockRenderManager();
            net.minecraft.client.render.model.BakedModel model = blockRenderManager.getModel(renderState);

            // NOTE: we deliberately do NOT use blockRenderManager.renderBlock(...) here. That
            // convenience method checks renderState.getRenderType() internally and silently draws
            // nothing unless it equals BlockRenderType.MODEL. Since ElevatorBlock.getRenderType()
            // always returns INVISIBLE (that's what stops the chunk mesh double-drawing our
            // camouflage), the elevator's OWN default state would also get vetoed by that same
            // check when there's no camo, rendering as fully transparent. Calling the lower-level
            // BlockModelRenderer directly (the same one the chunk mesher itself calls) skips that
            // gate entirely, so it draws regardless of what getRenderType() reports.
            blockRenderManager.getModelRenderer().render(
                    world,
                    model,
                    renderState,
                    entity.getPos(),
                    matrices,
                    vertexConsumers.getBuffer(RenderLayers.getBlockLayer(renderState)),
                    true,
                    random,
                    renderState.getRenderingSeed(entity.getPos()),
                    overlay
            );

            matrices.pop();
        } catch (Throwable t) {
            // Minecraft normally swallows exceptions thrown inside a BlockEntityRenderer without
            // crashing, which makes rendering bugs like this invisible unless we log them ourselves.
            OpenBlocks.LOGGER.error("[Elevator DEBUG] renderBlock threw an exception at " + entity.getPos(), t);
        }
    }
}