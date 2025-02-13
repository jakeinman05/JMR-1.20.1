package net.notvergin.jmresurrected.eventhandlers;

import net.notvergin.jmresurrected.entity.mobs.BabyJohnEntity;
import net.notvergin.jmresurrected.entity.mobs.JohnEntity;
import net.notvergin.jmresurrected.mobeffects.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonEvents
{
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

        if(mob instanceof JohnEntity john)
        {
            // john can become immortal 1/10 chance
            if(john.getRandom().nextInt(10) == 0)
            {
                event.setCanceled(true);
                // add sound to signify as well?
                MobEffectInstance johnImmortal = new MobEffectInstance(ModMobEffects.IMMORTAL_EFFECT.get(), 100);
                john.addEffect(johnImmortal);
            }
        }
    }

    @SubscribeEvent
    public static void villagerRun(EntityJoinLevelEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof Villager villager)
        {
            villager.goalSelector.addGoal(3, new AvoidEntityGoal<>(villager, JohnEntity.class, 16, 1.0f, 1.0f));
            villager.goalSelector.addGoal(3, new AvoidEntityGoal<>(villager, BabyJohnEntity.class, 16, 1.0f, 1.0f));
        }
    }

}
