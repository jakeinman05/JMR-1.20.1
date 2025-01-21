package com.notvergin.jmr.eventhandlers;

import com.notvergin.jmr.entity.ModEntities;
import com.notvergin.jmr.entity.mobs.JohnEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.notvergin.jmr.JohnModResurrected.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents
{
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.JOHN.get(), JohnEntity.createAttributes().build());
    }
}
