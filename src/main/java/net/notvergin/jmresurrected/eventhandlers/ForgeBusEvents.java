package net.notvergin.jmresurrected.eventhandlers;

import net.notvergin.jmresurrected.customitems.ModItems;
import net.notvergin.jmresurrected.customitems.weapons.ImmortalBlade;
import net.minecraft.client.Minecraft;
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

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEvents
{
    @SubscribeEvent
    public static void onRefGemUse(PlayerInteractEvent.RightClickItem event)
    {
        if(!event.getEntity().level().isClientSide)
        {
            Player player = event.getEntity();

            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();

            if(offHand.getItem() == ModItems.REFINED_IMMORTAL_GEM.get() && !mainHand.isEmpty())
            {
                if(mainHand.getItem() == ModItems.IMMORTALITY_SWORD.get())
                {
                    doHandEffects(player);
                    mainHand.shrink(1);
                    player.setItemInHand(InteractionHand.MAIN_HAND, ModItems.UIMMORTALITY_SWORD.get().getDefaultInstance());
                    event.setCanceled(true);
                    return;
                }
                // if item already unbreakable
                if(mainHand.getOrCreateTag().contains("Unbreakable") || mainHand.getItem() instanceof ImmortalBlade)
                {
                    player.swing(InteractionHand.OFF_HAND);
                    player.sendSystemMessage(Component.literal("Item is already unbreakable."));
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
                    event.setCanceled(true);
                }
                else {
                    player.swing(InteractionHand.OFF_HAND);
                    player.sendSystemMessage(Component.literal("Item cannot be used with this."));
                    event.setCanceled(true);
                }
            }
        }
    }

    private static void doHandEffects(Player player)
    {
        Minecraft.getInstance().particleEngine.createTrackingEmitter(player, ParticleTypes.DRAGON_BREATH);
        player.swing(InteractionHand.MAIN_HAND);
        player.swing(InteractionHand.OFF_HAND);
        player.level().playLocalSound(
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ANVIL_BREAK,
                SoundSource.PLAYERS,
                1.0f,
                1.0f,
                true);
        player.getOffhandItem().shrink(1);
    }
}
