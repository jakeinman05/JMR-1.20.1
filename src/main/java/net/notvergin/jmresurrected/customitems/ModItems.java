package net.notvergin.jmresurrected.customitems;

import net.notvergin.jmresurrected.customitems.item.ImmortalGem;
import net.notvergin.jmresurrected.customitems.item.ImmortalHusk;
import net.notvergin.jmresurrected.customitems.item.RefImmortalityGem;
import net.notvergin.jmresurrected.customitems.weapons.ImmortalBlade;
import net.notvergin.jmresurrected.entity.ModEntities;
import net.notvergin.jmresurrected.registries.ModToolTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

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
    public static final RegistryObject<Item> REFINED_IMMORTAL_GEM = ITEMS.register("ref_immortal_gem",
            () -> new RefImmortalityGem(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    // Tools
    public static final RegistryObject<Item> IMMORTALITY_SWORD = ITEMS.register("immortality_sword",
            () -> new ImmortalBlade(Tiers.NETHERITE, 3, -2.4F, new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> UIMMORTALITY_SWORD = ITEMS.register("uimmortality_sword",
            () -> new ImmortalBlade(ModToolTiers.IMMORTAL, 10, -2.4F, new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    //Spawn Eggs
    public static final RegistryObject<Item> JOHN_EGG = ITEMS.register("john_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JOHN, 16777203, 9240576, new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
