package net.notvergin.jmresurrected.mobeffects.potions;

import net.notvergin.jmresurrected.mobeffects.JMMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class JMPotionRegistry
{
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, MODID);

    public static final RegistryObject<Potion> IMMORTAL_POTION = POTIONS.register("immortal_potion",
            () -> new Potion(new MobEffectInstance(JMMobEffects.IMMORTAL_EFFECT.get(), 600)));

    public static void register(IEventBus bus)
    {
        POTIONS.register(bus);
    }
}
