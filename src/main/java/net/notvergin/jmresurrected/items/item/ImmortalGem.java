package net.notvergin.jmresurrected.items.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ImmortalGem extends Item
{
    public ImmortalGem(Properties itemProps) { super(itemProps); }

    public boolean isFoil(ItemStack pStack) { return true; }

}
