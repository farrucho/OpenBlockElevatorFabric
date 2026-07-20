package net.farrucho.openblocks.block;

import net.minecraft.item.Item;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.farrucho.openblocks.OpenBlocks;
import net.farrucho.openblocks.block.custom.ElevatorBlock;
import net.farrucho.openblocks.block.custom.ElevatorBlockEntity;
import net.minecraft.block.Block;
//import net.minecraft.block.Material;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class OpenBlocksModBlocks {

    public static final Block ELEVATOR_BLOCK = registerBlock(
            "elevator_block",
            new ElevatorBlock(
                    FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)
                            .strength(0.8f)
            )
    );

    public static final BlockEntityType<ElevatorBlockEntity> ELEVATOR_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(OpenBlocks.MOD_ID, "elevator_block_entity"),
            FabricBlockEntityTypeBuilder.create(ElevatorBlockEntity::new, ELEVATOR_BLOCK).build()
    );

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);

        return Registry.register(
                Registries.BLOCK,
                new Identifier(OpenBlocks.MOD_ID, name),
                block
        );
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(
                Registries.ITEM,
                new Identifier(OpenBlocks.MOD_ID, name),
                new BlockItem(block, new  Item.Settings())
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.add(item);
        });

        return item;
    }

    public static void registerModBlock() {
        OpenBlocks.LOGGER.debug("Registando blocos para " + OpenBlocks.MOD_ID);
    }
}