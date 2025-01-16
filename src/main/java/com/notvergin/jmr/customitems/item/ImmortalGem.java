package com.notvergin.jmr.customitems.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ImmortalGem extends Item
{
    public ImmortalGem(Properties itemProps) { super(itemProps); }

    public boolean isFoil(ItemStack pStack) { return true; }

}
