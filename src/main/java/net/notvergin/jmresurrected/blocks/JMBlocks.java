package net.notvergin.jmresurrected.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.notvergin.jmresurrected.items.JMItems;

import java.util.function.Supplier;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class JMBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Block> PERENNIAL_BLOCK = registerRareBlockWithItem("perennial_block", () -> new PerennialBlock());
    public static final RegistryObject<Block> IMMORTAL_GEM_BLOCK = registerBlockWithItem("immortal_gem_block", () -> new ImmortalGemBlock());
    public static final RegistryObject<Block> IMMORTAL_SHARD_BLOCK = registerBlockWithItem("immortal_shard_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.PINK).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(5.0F, 20.0F)));

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
        RegistryObject<T> blockRegistry = BLOCKS.register(name, block);
        registerBlockItem(name, blockRegistry);
        return blockRegistry;
    }

    private static <T extends Block> RegistryObject<T> registerRareBlockWithItem(String name, Supplier<T> block) {
        RegistryObject<T> blockRegistry = BLOCKS.register(name, block);
        registerRareBlockItem(name, blockRegistry);
        return blockRegistry;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String id, RegistryObject<T> block) {
        return JMItems.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<Item> registerRareBlockItem(String id, RegistryObject<T> block) {
        return JMItems.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties().rarity(Rarity.EPIC)));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
