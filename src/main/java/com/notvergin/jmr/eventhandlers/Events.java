package com.notvergin.jmr.eventhandlers;

import com.notvergin.jmr.customitems.weapons.ImmortalBlade;
import net.minecraft.world.entity.player.Player;
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
}
