package net.notvergin.jmresurrected.items;

import net.notvergin.jmresurrected.items.item.ImmortalGem;
import net.notvergin.jmresurrected.items.item.ImmortalHusk;
import net.notvergin.jmresurrected.items.item.RefImmortalityGem;
import net.notvergin.jmresurrected.items.weapons.ImmortalBlade;
import net.notvergin.jmresurrected.entity.JMEntites;
import net.notvergin.jmresurrected.util.JMToolTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class JMItems
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
            () -> new ImmortalBlade(Tiers.DIAMOND, 2, -2.4F, new Item.Properties().rarity(Rarity.RARE).fireResistant().defaultDurability(1561)));
    public static final RegistryObject<Item> UIMMORTALITY_SWORD = ITEMS.register("uimmortality_sword",
            () -> new ImmortalBlade(JMToolTiers.IMMORTAL, 7, -2.4F, new Item.Properties().rarity(Rarity.EPIC).fireResistant().defaultDurability(1561)));

    //Spawn Eggs
    public static final RegistryObject<Item> JOHN_EGG = ITEMS.register("john_spawn_egg",
            () -> new ForgeSpawnEggItem(JMEntites.JOHN, 16777203, 9240576, new Item.Properties()));
    public static final RegistryObject<Item> BABY_JOHN_EGG = ITEMS.register("babyjohn_spawn_egg",
            () -> new ForgeSpawnEggItem(JMEntites.BABYJOHN, 16777203, 9240576, new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
