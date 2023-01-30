package net.farrucho.openblocks.block.custom;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
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
    //public static final BooleanProperty CLICKED_GRASS_BLOCK = BooleanProperty.of("clicked");
    public static final IntProperty BLOCKNUMPROPERTY = IntProperty.of("blocknum",0,70);

    public ElevatorBlock(AbstractBlock.Settings settings) {
        super(settings);
        //this.setDefaultState((BlockState)this.getDefaultState().with(CLICKED_GRASS_BLOCK, false));
        this.setDefaultState((BlockState)this.getDefaultState().with(BLOCKNUMPROPERTY, 0));
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        //builder.add(CLICKED_GRASS_BLOCK);
        builder.add(new Property[]{BLOCKNUMPROPERTY});
    }



    @Override
    public void onSteppedOn(World world, BlockPos blockpos, BlockState state, Entity entity) {
        if(entity.isInSneakingPose() && entity.isPlayer() && !world.isClient()){
            PlayerEntity p = (PlayerEntity) entity;
            //p.sendMessage(Text.literal("Elevador para baixo"));
            //p.sendMessage(Text.of("Elevador para baixo"),true);
            goDown(blockpos, world, p);
        }
    }




    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            Item itemUsed = player.getMainHandStack().getItem();


            BlockItem bi = (BlockItem) itemUsed;
            boolean iscube = bi.getBlock().getDefaultState().isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);

            if(iscube){
                if(bi.getTranslationKey().equals("block.openblocks.elevator_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 0),Block.NOTIFY_ALL);
                }
                else if (bi.getTranslationKey().equals("block.minecraft.stone")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 1),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.granite")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 2),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.diorite")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 3),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.andesite")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 4),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.deepslate")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 5),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.dirt")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 6),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.cobblestone")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 7),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.oak_planks")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 8),Block.NOTIFY_ALL);
                }
                else if(bi.getTranslationKey().equals("block.minecraft.spruce_planks")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 9),Block.NOTIFY_ALL);
                }
                else if(bi.getTranslationKey().equals("block.minecraft.birch_planks")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 10),Block.NOTIFY_ALL);
                }
                else if(bi.getTranslationKey().equals("block.minecraft.jungle_planks")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 11),Block.NOTIFY_ALL);
                }
                else if(bi.getTranslationKey().equals("block.minecraft.acacia_planks")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 12),Block.NOTIFY_ALL);
                }
                else if(bi.getTranslationKey().equals("block.minecraft.dark_oak_planks")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 13),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.sand")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 14),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.red_sand")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 15),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.gravel")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 16),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.iron_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 17),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.copper_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 18),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.gold_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 19),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.diamond_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 20),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.netherite_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 21),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.sandstone")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 22),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.glowstone")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 23),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.oak_log")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 24),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.spruce_log")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 25),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.birch_log")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 26),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.jungle_log")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 27),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.acacia_log")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 28),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.dark_oak_log")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 29),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.ice")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 30),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.bricks")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 31),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.bookshelf")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 32),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.white_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 33),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.orange_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 34),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.magenta_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 35),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.light_blue_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 36),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.yellow_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 37),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.lime_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 38),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.pink_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 39),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.gray_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 40),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.light_gray_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 41),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.cyan_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 42),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.purple_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 43),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.blue_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 44),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.brown_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 45),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.green_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 46),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.red_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 47),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.black_wool")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 48),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.smooth_quartz")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 49),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.stone_bricks")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 50),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.magma_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 51),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.hay_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 52),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.terracotta")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 53),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.emerald_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 54),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.netherrack")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 55),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.redstone_lamp")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 56),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.tnt")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 57),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.slime_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 58),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.sticky_piston")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 59),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.piston")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 60),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.dropper")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 61),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.observer")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 62),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.target")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 63),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.lapis_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 64),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.crafting_table")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 65),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.melon")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 66),Block.NOTIFY_ALL);
                }else if(bi.getTranslationKey().equals("block.minecraft.grass_block")){
                    world.setBlockState(pos,state.with(BLOCKNUMPROPERTY, 67),Block.NOTIFY_ALL);
                }





                ////////player.sendMessage(Text.of(bi.getTranslationKey()),true);

                //mudar textura
                //implementar primeiro mudar apenas as cores com tintas e depois logo se ve como camuflar


                //world.setBlockState(pos,);

                //player.sendMessage(Text.of("mudar textura do bloco"),true);
            }



            //player.sendMessage(Text.literal(String.valueOf(b.getHardness())));
            //player.sendMessage(Text.literal(itemUsed.getTranslationKey()));
            //if(itemUsed != null && itemUsed.getTranslationKey().substring(0, 5).equals("block") && b.getHardness() < 0.5){
                //player.sendMessage(Text.literal("mudar textura"));
            //}
        }
        return ActionResult.SUCCESS;
    }

}
