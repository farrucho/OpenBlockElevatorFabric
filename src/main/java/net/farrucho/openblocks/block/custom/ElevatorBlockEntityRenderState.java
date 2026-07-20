package net.farrucho.openblocks.block.custom;

import net.minecraft.client.render.block.MovingBlockRenderState;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;

public class ElevatorBlockEntityRenderState extends BlockEntityRenderState {

    public boolean hasCamouflage = false;
    public MovingBlockRenderState movingBlock = new MovingBlockRenderState();

}