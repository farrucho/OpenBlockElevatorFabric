package net.farrucho.openblocks.block.custom;

import net.farrucho.openblocks.block.OpenBlocksModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

//import javax.annotation.Nullable;
import org.jetbrains.annotations.Nullable;

/**
 * Holds the (optional) BlockState this elevator block is currently camouflaged as.
 * Because this stores a real BlockState instead of an index into a hardcoded list,
 * it works for ANY block in the game (or any other installed mod's blocks) with
 * zero extra registration.
 */
public class ElevatorBlockEntity extends BlockEntity {

    private static final String CAMOUFLAGE_KEY = "CamouflageState";
    private static final String HAS_CAMOUFLAGE_KEY = "HasCamouflage";

    @Nullable
    private BlockState camouflageState = null;

    public ElevatorBlockEntity(BlockPos pos, BlockState state) {
        super(OpenBlocksModBlocks.ELEVATOR_BLOCK_ENTITY, pos, state);
    }

    @Nullable
    public BlockState getCamouflageState() {
        return camouflageState;
    }

    /**
     * @param camouflageState the block appearance to copy, or null to clear the camouflage.
     */
    public void setCamouflageState(@Nullable BlockState camouflageState) {
        this.camouflageState = camouflageState;
        sync();
    }

    /** Marks the block entity dirty for saving and pushes an update to nearby clients. */
    private void sync() {
        markDirty();
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        // IMPORTANT: always write something here, even when camouflageState is null. If this
        // method (combined with super.writeNbt, which writes nothing by default) produces a
        // fully empty NbtCompound, BlockEntityUpdateS2CPacket collapses that to "no data" and
        // the client never actually receives/applies the update - which is why clearing the
        // camo used to get stuck showing the last camo texture forever. This boolean guarantees
        // the compound always has at least one key, so "clear" updates actually reach the client.
        nbt.putBoolean(HAS_CAMOUFLAGE_KEY, camouflageState != null);
        if (camouflageState != null) {
            nbt.put(CAMOUFLAGE_KEY, NbtHelper.fromBlockState(camouflageState));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);

        BlockState previous = camouflageState;

        if (nbt.contains(CAMOUFLAGE_KEY, NbtElement.COMPOUND_TYPE)) {
            BlockState state = NbtHelper.toBlockState(
                    registries.getWrapperOrThrow(Registries.BLOCK.getKey()),
                    nbt.getCompound(CAMOUFLAGE_KEY)
            );

            camouflageState = state.isAir() ? null : state;
        } else {
            camouflageState = null;
        }

        if (!java.util.Objects.equals(previous, camouflageState)) {
            String side = (world != null && world.isClient) ? "CLIENT" : "SERVER/unknown";
        }
    }

    // --- Client sync boilerplate: sends the full NBT (including our camo state) whenever
    // the block entity is updated, and whenever a chunk is sent to a client. ---

    @Nullable
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}