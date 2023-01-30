package net.farrucho.openblocks;

import net.fabricmc.api.ClientModInitializer;

public class OpenBlocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        /*ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.add(OpenBlocksModBlocks.ELEVATOR_BLOCK.asItem());
        });*/
    }
}
