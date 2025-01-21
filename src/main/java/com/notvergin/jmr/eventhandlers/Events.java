package com.notvergin.jmr.eventhandlers;

import com.notvergin.jmr.customitems.weapons.ImmortalBlade;
import com.notvergin.jmr.entity.mobs.JohnEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events
{
    // Class ready for events
//    @SubscribeEvent
//    public static void onEntityHit(LivingHurtEvent event)
//    {
//        System.out.println("Works");
//        Entity entity = event.getEntity();
//        Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.LAVA, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
//    }

    @SubscribeEvent
    public static void onVillagerSpawn(EntityJoinLevelEvent event)
    {
        if(event.getEntity() instanceof Villager villager)
        {
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, JohnEntity.class, 16.0f, 1.0f, 1.3f));
        }
    }

}
