package com.notvergin.jmr.registries;

import com.notvergin.jmr.customitems.item.ImmortalGem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class ModItems
{
    // Creates a deferred register for new items, based off forge registries
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Items
    public static final RegistryObject<Item> JOHN_TEAR = ITEMS.register("john_tear",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IMMORTALITY_GEM = ITEMS.register("immortality_gem",
            () -> new ImmortalGem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> IMMORTALITY_SWORD = ITEMS.register("immortality_sword",
            () -> new SwordItem(Tiers.DIAMOND, 3, -2.4F, new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
