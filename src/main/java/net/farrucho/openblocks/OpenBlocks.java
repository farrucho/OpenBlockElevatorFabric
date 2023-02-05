package net.farrucho.openblocks;

import net.fabricmc.api.ModInitializer;
//import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.farrucho.openblocks.block.OpenBlocksModBlocks;
//import net.minecraft.item.ItemGroups;
import net.farrucho.openblocks.config.ModConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenBlocks implements ModInitializer {
	public static final String MOD_ID = "openblocks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModConfigs.registerConfigs();
		//

		OpenBlocksModBlocks.registerModBlock();
		/*ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
			content.add(OpenBlocksModBlocks.ELEVATOR_BLOCK.asItem());
		});*///1.19.3
	}
}
