package com.notvergin.jmr.customitems.weapons;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ImmortalBlade extends SwordItem
{
    public ImmortalBlade(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
    @Override
    public boolean isFoil(ItemStack stack) { return true; }
}
