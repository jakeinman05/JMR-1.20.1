package com.notvergin.jmr.registries;

import com.notvergin.jmr.customitems.item.ImmortalGem;
import com.notvergin.jmr.customitems.item.ImmortalShard;
import com.notvergin.jmr.customitems.weapons.ImmortalBlade;
import com.notvergin.jmr.entity.ModEntities;
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
            () -> new ImmortalShard(new Item.Properties()));
    public static final RegistryObject<Item> IMMORTALITY_GEM = ITEMS.register("immortality_gem",
            () -> new ImmortalGem(new Item.Properties().rarity(Rarity.RARE)));

    // Tools
    public static final RegistryObject<Item> IMMORTALITY_SWORD = ITEMS.register("immortality_sword",
            () -> new ImmortalBlade(Tiers.DIAMOND, 3, -2.4F, new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    //Spawn Eggs
    public static final RegistryObject<Item> JOHN_EGG = ITEMS.register("john_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JOHN, 0xc9bebd, 0x058632, new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
