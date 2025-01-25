package com.notvergin.jmr.eventhandlers;

import com.notvergin.jmr.entity.ModEntities;
import com.notvergin.jmr.entity.mobs.JohnEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
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

    @SubscribeEvent
    public static void registerEntitySpawns(SpawnPlacementRegisterEvent event)
    {
        event.register(ModEntities.JOHN.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                JohnEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
        System.out.println("John Spawns Registered");
    }
}
