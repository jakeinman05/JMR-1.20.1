package net.notvergin.jmresurrected.eventhandlers;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.notvergin.jmresurrected.blocks.ImmortalGemBlock;
import net.notvergin.jmresurrected.blocks.JMBlocks;
import net.notvergin.jmresurrected.blocks.PerennialBlock;
import net.notvergin.jmresurrected.items.JMItems;
import net.notvergin.jmresurrected.items.weapons.ImmortalBlade;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;
import static net.notvergin.jmresurrected.blocks.PerennialBlock.ACTIVE;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class JMForgeBusEvents
{
    @SubscribeEvent
    public static void onRefGemUse(PlayerInteractEvent.RightClickItem event)
    {
        Player player = event.getEntity();

        if(!player.level().isClientSide)
        {
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();

            if(offHand.getItem() == JMItems.REFINED_IMMORTAL_GEM.get() && !mainHand.isEmpty())
            {
                if(mainHand.getItem() == JMItems.IMMORTALITY_SWORD.get())
                {
                    doHandEffects(player);
                    mainHand.shrink(1);
                    player.setItemInHand(InteractionHand.MAIN_HAND, JMItems.UIMMORTALITY_SWORD.get().getDefaultInstance());
                    event.setCancellationResult(InteractionResult.CONSUME);
                    event.setCanceled(true);
                    return;
                }
                // if item already unbreakable
                if(mainHand.getOrCreateTag().contains("Unbreakable") || mainHand.getItem() instanceof ImmortalBlade)
                {
                    player.swing(InteractionHand.OFF_HAND);
                    player.sendSystemMessage(Component.literal("Item is already unbreakable."));
                    event.setCancellationResult(InteractionResult.CONSUME);
                    event.setCanceled(true);
                    return;
                }
                if(mainHand.getItem() instanceof TieredItem || mainHand.getItem() instanceof Equipable || mainHand.getItem() instanceof ProjectileWeaponItem
                        || mainHand.getItem() instanceof TridentItem)
                {
                    CompoundTag tag = mainHand.getOrCreateTag();
                    tag.putBoolean("Unbreakable", true);
                    mainHand.setTag(tag);
                    doHandEffects(player);
                }
                else {
                    player.swing(InteractionHand.OFF_HAND);
                    player.sendSystemMessage(Component.literal("Item cannot be used with this."));
                }
                event.setCancellationResult(InteractionResult.CONSUME);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void PlayerRightClickGemBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = player.level();
        BlockState state = level.getBlockState(event.getPos());
        BlockPos pos = event.getPos();

        if(!level.isClientSide)
        {
            Item handItem = player.getMainHandItem().getItem();
            Block clickedBlock = state.getBlock();

            if(clickedBlock instanceof ImmortalGemBlock gemBlock && !player.isShiftKeyDown()) {
                if(handItem == JMItems.REFINED_IMMORTAL_GEM.get()) {
                    player.swing(InteractionHand.MAIN_HAND);

                    if(!player.isCreative()) {
                        player.getMainHandItem().shrink(1);
                    }

                    gemBlock.destroy(level, event.getPos(), state);

                    ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 32, 0.2D, 0.3D, 0.2D, 0.1D );
                    //level.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 0.3F, 0.3F);

                    BlockState perennial = JMBlocks.PERENNIAL_BLOCK.get().defaultBlockState();
                    level.setBlockAndUpdate(pos, perennial);

                    event.setCancellationResult(InteractionResult.CONSUME);
                }
            }
        }
    }

    private static void doHandEffects(Player player)
    {
        if(!player.level().isClientSide) {
            player.swing(InteractionHand.MAIN_HAND);
            player.swing(InteractionHand.OFF_HAND);
            player.getOffhandItem().shrink(1);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_DESTROY, SoundSource.PLAYERS, 1.0F, 1.0F);

            ((ServerLevel) player.level()).sendParticles(ParticleTypes.DRAGON_BREATH, player.getX(), player.getY() + 1, player.getZ(), 16, 0.3, 0.3, 0.3, 0.2);
        }
    }
}
