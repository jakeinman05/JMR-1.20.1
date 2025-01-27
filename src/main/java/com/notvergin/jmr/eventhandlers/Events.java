package com.notvergin.jmr.eventhandlers;

import com.notvergin.jmr.entity.mobs.JohnEntity;
import com.notvergin.jmr.mobeffects.ModMobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events
{
    @SubscribeEvent
    public static void onVillagerSpawn(EntityJoinLevelEvent event)
    {
        if(event.getEntity() instanceof Villager villager)
        {
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, JohnEntity.class, 16.0f, 1.0f, 1.3f));
        }
    }

    @SubscribeEvent
    public static void onImmortalEffect(LivingHurtEvent event)
    {
        Entity mob = event.getEntity();
        if(mob instanceof LivingEntity entity)
        {
            if(entity.hasEffect(ModMobEffects.IMMORTAL_EFFECT.get()))
            {
                event.setCanceled(true);

                Iterable<ItemStack> armorItems = entity.getArmorSlots();
                for(ItemStack armorItem : armorItems)
                {
                    if(!armorItem.isEmpty() && armorItem.isDamageableItem())
                    {
                        EquipmentSlot slot = Mob.getEquipmentSlotForItem(armorItem);
                        if(slot != null && slot.getType() == EquipmentSlot.Type.ARMOR)
                        {
                            armorItem.hurtAndBreak((int)event.getAmount(), entity,
                                    (e) -> e.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, slot.getIndex())));
                        }

                    }
                }
            }
        }
    }

    // LivingEntityDamageEvent
    //  go through entity.getArmorSlots and check for unbreakable tag then cancel the event
    // LivingEntityUseItemEvent and do same thing for mainHandItem
}
