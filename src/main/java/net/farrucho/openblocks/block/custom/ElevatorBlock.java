package net.farrucho.openblocks.block.custom;

import net.farrucho.openblocks.OpenBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.EmptyBlockView;
import net.minecraft.world.World;

import static net.farrucho.openblocks.block.custom.ElevatorBlockFunctions.goDown;

public class ElevatorBlock extends Block implements BlockEntityProvider {

    public ElevatorBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ElevatorBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // All visuals are drawn by ElevatorBlockEntityRenderer (same technique vanilla uses
        // for the moving piston block entity) - MODEL would ALSO bake/draw this block's own
        // model into the chunk mesh every frame, on top of whatever the BER draws, causing
        // z-fighting/flicker between the two overlapping full-cube geometries. INVISIBLE means
        // this block contributes zero geometry of its own; the BER is 100% responsible for visuals.
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public void onSteppedOn(World world, BlockPos blockpos, BlockState state, Entity entity) {
        if (entity.isInSneakingPose() && entity.isPlayer() && !world.isClient()) {
            PlayerEntity p = (PlayerEntity) entity;
            goDown(blockpos, world, p);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            // Let the server handle the actual state change; still "succeed" client-side
            // so the swing animation plays and the interaction isn't passed further down.
            return ActionResult.SUCCESS;
        }

        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof ElevatorBlockEntity)) {
            //player.sendMessage(Text.literal("[Elevator DEBUG] No ElevatorBlockEntity at this position!"), true);
            //OpenBlocks.LOGGER.warn("[Elevator DEBUG] getBlockEntity returned {} at {}", be, pos);
            return ActionResult.PASS;
        }
        ElevatorBlockEntity elevatorEntity = (ElevatorBlockEntity) be;

        ItemStack heldStack = player.getStackInHand(hand);
        Item itemUsed = heldStack.getItem();

        if (!(itemUsed instanceof BlockItem)) {
            // Not holding a block - nothing to camouflage with, let other interactions proceed.
            //player.sendMessage(Text.literal("[Elevator DEBUG] Not holding a BlockItem (holding: " + itemUsed + ")"), true);
            return ActionResult.PASS;
        }
        BlockItem heldBlockItem = (BlockItem) itemUsed;
        Block clickedBlock = heldBlockItem.getBlock();

        // Right-clicking with the Elevator Block itself resets to the default look.
        if (clickedBlock == this) {
            elevatorEntity.setCamouflageState(null);
            //player.sendMessage(Text.literal("[Elevator DEBUG] Camouflage cleared (server-side)."), true);
            return ActionResult.SUCCESS;
        }

        BlockState camouflageState = clickedBlock.getDefaultState();

        // Only full cube blocks make sense as camouflage (stairs/slabs/etc. would look broken).
        boolean isFullCube = camouflageState.isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);
        if (!isFullCube) {
            player.sendMessage(Text.translatable("message.openblocks.elevator_needs_full_cube"), true);
            //OpenBlocks.LOGGER.info("[Elevator DEBUG] Rejected {} - not a full cube", Registry.BLOCK.getId(clickedBlock));
            return ActionResult.FAIL;
        }

        elevatorEntity.setCamouflageState(camouflageState);
        //player.sendMessage(Text.literal("Camouflage SET to " + Registry.BLOCK.getId(clickedBlock)), true);
        //OpenBlocks.LOGGER.info("[Elevator DEBUG] setCamouflageState called with {} at {}", camouflageState, pos);
        return ActionResult.SUCCESS;
    }
}