package net.ciaphas.testingmod.item.custom;

import net.ciaphas.testingmod.block.ModBlocks;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
    if(!pContext.getLevel().isClientSide()) {
        BlockPos positionClicked = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        boolean foundblock = false;

        for(int i = 0; i <= positionClicked.getY() + 64; i++) {
            BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i));

            if (isValuableBlock(state)) {
                outputValuableCoordinates(positionClicked.below(i), player, state.getBlock());
                foundblock = true;

                break;
            }
        }

        if (!foundblock) {
            player.sendSystemMessage(Component.literal("No valuables Found."));
        }
    }
        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    private void outputValuableCoordinates(BlockPos blockpos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found " + I18n.get(block.getDescriptionId()) + "at " +
                "(" + blockpos.getX() + ", " + blockpos.getY() + "," + blockpos.getZ() + ")"));
    }

    private boolean isValuableBlock(BlockState state) {
        return state.is(Blocks.IRON_ORE) || state.is(Blocks.DIAMOND_ORE); // || state.is(ModBlocks.ADAMANTIUM_ORE)
    }
}
