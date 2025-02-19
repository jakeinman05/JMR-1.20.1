package net.notvergin.jmresurrected.blocks.blockentities;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.notvergin.jmresurrected.blocks.JMBlocks;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class JMBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<PerennialBlockEntity>> PERENNIAL = BLOCK_ENTITIES.register("perennial", () -> BlockEntityType.Builder.of(PerennialBlockEntity::new, JMBlocks.PERENNIAL_BLOCK.get()).build(null));

    public static void register(IEventBus bus){
        BLOCK_ENTITIES.register(bus);
    }
}
