package com.notvergin.jmr.mobeffects.potions;

import com.notvergin.jmr.mobeffects.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class ModPotions
{
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, MODID);

    public static final RegistryObject<Potion> IMMORTAL_POTION = POTIONS.register("immortal_potion",
            () -> new Potion(new MobEffectInstance(ModMobEffects.IMMORTAL_EFFECT.get(), 600)));

    public static void register(IEventBus bus)
    {
        POTIONS.register(bus);
    }
}
