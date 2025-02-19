package net.notvergin.jmresurrected.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class JMToolTiers
{
    // how to make a tier
    public static final Tier IMMORTAL = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1561, 9.0F, 3.0F, 10,
                    null, null),
            new ResourceLocation(MODID, "immortal"), List.of(Tiers.NETHERITE), List.of());
}
