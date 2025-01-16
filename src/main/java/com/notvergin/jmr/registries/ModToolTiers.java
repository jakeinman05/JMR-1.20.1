package com.notvergin.jmr.registries;

import com.notvergin.jmr.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class ModToolTiers
{
    public static final Tier IMMORTAL = TierSortingRegistry.registerTier(
            new ForgeTier(3, 1561, 8.0F, 3.0F, 10,
                    ModTags.Blocks.NEEDS_IMMORTAL_TOOL, () -> Ingredient.of(ModItems.IMMORTALITY_GEM.get())),
            new ResourceLocation(MODID, "immortal"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
}
