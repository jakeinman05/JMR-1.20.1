package net.notvergin.jmresurrected.entity.registryhandlers;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.notvergin.jmresurrected.entity.mobs.BabyJohnEntity;
import net.notvergin.jmresurrected.entity.mobs.JohnEntity;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JMEntityRegistry
{
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(JMEntites.JOHN.get(), JohnEntity.createAttributes().build());
        event.put(JMEntites.BABYJOHN.get(), BabyJohnEntity.createAttributes());
    }

    @SubscribeEvent
    public static void registerEntitySpawns(SpawnPlacementRegisterEvent event)
    {
        event.register(JMEntites.JOHN.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                JohnEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(JMEntites.BABYJOHN.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BabyJohnEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
