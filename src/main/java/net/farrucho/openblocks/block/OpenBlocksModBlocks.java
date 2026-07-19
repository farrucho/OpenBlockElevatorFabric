package net.farrucho.openblocks.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.farrucho.openblocks.OpenBlocks;
import net.farrucho.openblocks.block.custom.ElevatorBlock;
import net.farrucho.openblocks.block.custom.ElevatorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
/*import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;*/
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OpenBlocksModBlocks {

    public static final Block ELEVATOR_BLOCK = registerBlock("elevator_block", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL).hardness(0.8f).sounds(BlockSoundGroup.WOOL)), ItemGroup.REDSTONE);
    //1.19.3 itemgroups
    //1.19.2 itemgroup

    public static final BlockEntityType<ElevatorBlockEntity> ELEVATOR_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(OpenBlocks.MOD_ID, "elevator_block_entity"),
            FabricBlockEntityTypeBuilder.create(ElevatorBlockEntity::new, ELEVATOR_BLOCK).build()
    );

    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        //return Registry.register(Registries.BLOCK, new Identifier(OpenBlocks.MOD_ID, name), block); // 1.19.3
        return Registry.register(Registry.BLOCK, new Identifier(OpenBlocks.MOD_ID, name), block); // 1.19.2
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        /*return Registry.register(Registries.ITEM, new Identifier(OpenBlocks.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));*/
        return Registry.register(Registry.ITEM, new Identifier(OpenBlocks.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(ItemGroup.REDSTONE)));
    }


    public static void registerModBlock() {
        OpenBlocks.LOGGER.debug("Registando blocos para " + OpenBlocks.MOD_ID);
    }
}