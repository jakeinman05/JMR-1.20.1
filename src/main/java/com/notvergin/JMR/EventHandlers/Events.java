package com.notvergin.JMR.EventHandlers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
