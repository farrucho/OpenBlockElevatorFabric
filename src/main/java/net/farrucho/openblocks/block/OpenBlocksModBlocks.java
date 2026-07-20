package net.farrucho.openblocks.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.farrucho.openblocks.OpenBlocks;
import net.farrucho.openblocks.block.custom.ElevatorBlock;
import net.farrucho.openblocks.block.custom.ElevatorBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;

import java.util.function.Function;

public class OpenBlocksModBlocks {

    public static final Block ELEVATOR_BLOCK = registerBlock(
            "elevator_block",
            ElevatorBlock::new,
            AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)
                    .strength(0.8f)
    );


    public static final BlockEntityType<ElevatorBlockEntity> ELEVATOR_BLOCK_ENTITY =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(OpenBlocks.MOD_ID, "elevator_block_entity"),
                    FabricBlockEntityTypeBuilder.create(
                            ElevatorBlockEntity::new,
                            ELEVATOR_BLOCK
                    ).build()
            );


    private static Block registerBlock(
            String name,
            Function<AbstractBlock.Settings, Block> factory,
            AbstractBlock.Settings settings
    ) {
        Identifier id = Identifier.of(OpenBlocks.MOD_ID, name);

        RegistryKey<Block> key =
                RegistryKey.of(RegistryKeys.BLOCK, id);

        Block block = factory.apply(
                settings.registryKey(key)
        );

        registerBlockItem(name, block);

        return Registry.register(
                Registries.BLOCK,
                key,
                block
        );
    }


    private static Item registerBlockItem(String name, Block block) {
        Identifier id = Identifier.of(OpenBlocks.MOD_ID, name);

        RegistryKey<Item> key =
                RegistryKey.of(RegistryKeys.ITEM, id);

        Item item = new BlockItem(
                block,
                new Item.Settings()
                        .registryKey(key)
        );

        Registry.register(
                Registries.ITEM,
                key,
                item
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE)
                .register(entries -> {
                    entries.add(item);
                });

        return item;
    }


    public static void registerModBlock() {
        OpenBlocks.LOGGER.debug(
                "Registando blocos para " + OpenBlocks.MOD_ID
        );
    }
}