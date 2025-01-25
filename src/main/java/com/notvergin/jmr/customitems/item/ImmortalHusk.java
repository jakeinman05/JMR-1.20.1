package com.notvergin.jmr.customitems.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ImmortalHusk extends Item
{
    public ImmortalHusk(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public boolean isFoil(ItemStack pStack)
    {
        return true;
    }
}
