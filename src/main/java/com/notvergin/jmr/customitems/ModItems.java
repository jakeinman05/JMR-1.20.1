package com.notvergin.jmr.customitems;

import com.notvergin.jmr.customitems.item.ImmortalGem;
import com.notvergin.jmr.customitems.item.ImmortalHusk;
import com.notvergin.jmr.customitems.weapons.ImmortalBlade;
import com.notvergin.jmr.entity.ModEntities;
import com.notvergin.jmr.registries.ModToolTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
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
    public static final RegistryObject<Item> IMMORTALITY_SHARD = ITEMS.register("immortality_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IMMORTALITY_GEM = ITEMS.register("immortality_gem",
            () -> new ImmortalGem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> IMMORTAL_HUSK = ITEMS.register("immortal_husk",
            () -> new ImmortalHusk(new Item.Properties().rarity(Rarity.UNCOMMON)));

    // Tools
    public static final RegistryObject<Item> IMMORTALITY_SWORD = ITEMS.register("immortality_sword",
            () -> new ImmortalBlade(Tiers.DIAMOND, 3, -2.4F, new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> UIMMORTALITY_SWORD = ITEMS.register("uimmortality_sword",
            () -> new ImmortalBlade(ModToolTiers.IMMORTAL, 3, -2.4F, new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    //Spawn Eggs
    public static final RegistryObject<Item> JOHN_EGG = ITEMS.register("john_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JOHN, 0xc9bebd, 0x058632, new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
