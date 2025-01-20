package com.notvergin.jmr.customitems.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ImmortalShard extends Item
{

    public ImmortalShard(Properties pProperties) {
        super(pProperties);
    }

    public boolean isFoil(ItemStack pStack) {
        return false;
    }
}
