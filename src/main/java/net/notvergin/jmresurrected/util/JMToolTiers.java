package net.notvergin.jmresurrected.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.notvergin.jmresurrected.items.JMItems;

import java.util.List;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class JMToolTiers
{
    public static final Tier IMMORTAL = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1561, 9.0F, 3.0F, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> {
                return Ingredient.of(JMItems.IMMORTALITY_GEM.get());
            }),
            new ResourceLocation(MODID, "immortal"), List.of(Tiers.NETHERITE), List.of());
}
