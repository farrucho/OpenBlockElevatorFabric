package net.farrucho.openblocks.block.custom;

import net.farrucho.openblocks.block.OpenBlocksModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
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
    protected void writeData(WriteView view) {
        super.writeData(view);

        view.putBoolean(HAS_CAMOUFLAGE_KEY, camouflageState != null);

        if (camouflageState != null) {
            view.put(CAMOUFLAGE_KEY, BlockState.CODEC, camouflageState);
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);

        camouflageState = null;

        if (view.getBoolean(HAS_CAMOUFLAGE_KEY, false)) {
            camouflageState = view.read(CAMOUFLAGE_KEY, BlockState.CODEC).orElse(null);

            if (camouflageState != null && camouflageState.isAir()) {
                camouflageState = null;
            }
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