package net.farrucho.openblocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.farrucho.openblocks.block.OpenBlocksModBlocks;
import net.farrucho.openblocks.block.custom.ElevatorBlockEntityRenderer;

public class OpenBlocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OpenBlocks.LOGGER.info("OPENBLOCKS CLIENT INITIALIZER RUNNING");
        BlockEntityRendererRegistry.register(
                OpenBlocksModBlocks.ELEVATOR_BLOCK_ENTITY,
                ElevatorBlockEntityRenderer::new
        );
    }
}
