package net.notvergin.jmresurrected.entity;

import net.notvergin.jmresurrected.entity.mobs.JohnEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<JohnEntity>> JOHN =
            ENTITY_TYPES.register("john", () -> EntityType.Builder.of(JohnEntity::new, MobCategory.MONSTER)
                    .sized(0.8F, 4.0F)
                    .setTrackingRange(10)
                    .build("john"));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}
