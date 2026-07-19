package net.farrucho.openblocks.block.custom;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.farrucho.openblocks.config.ModConfigs;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EmptyBlockView;
import net.minecraft.world.World;

import static net.farrucho.openblocks.block.custom.ElevatorBlockFunctions.goDown;

public class ElevatorBlock extends Block {
    public static final IntProperty BLOCKNUMPROPERTY = IntProperty.of("blocknum", 0, 10000);


    public ElevatorBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)this.getDefaultState().with(BLOCKNUMPROPERTY, 0));
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{BLOCKNUMPROPERTY});
    }


    @Override
    public void onSteppedOn(World world, BlockPos blockpos, BlockState state, Entity entity) {
        if(entity.isInSneakingPose() && entity.isPlayer() && !world.isClient()){
            PlayerEntity p = (PlayerEntity) entity;
            goDown(blockpos, world, p);
        }
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            Item itemUsed = player.getMainHandStack().getItem();

            if (itemUsed instanceof BlockItem) {
                BlockItem bi = (BlockItem) itemUsed;
                Block blockPlaced = bi.getBlock();
                
                // Store the block ID for camouflage
                int blockId = Block.getRawIdFromState(blockPlaced.getDefaultState());
                world.setBlockState(pos, state.with(BLOCKNUMPROPERTY, blockId), Block.NOTIFY_ALL);
                
                //player.sendMessage(Text.of("Camouflage set to: " + blockPlaced.getTranslationKey()), true);
            }
        }
        return ActionResult.SUCCESS;
    }
}
