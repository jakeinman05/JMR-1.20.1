package net.notvergin.jmresurrected;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.notvergin.jmresurrected.blocks.JMBlocks;
import net.notvergin.jmresurrected.blocks.blockentities.JMBlockEntities;
import net.notvergin.jmresurrected.mobeffects.RegisterBrewingRecipe;
import net.notvergin.jmresurrected.mobeffects.potions.JMPotionRegistry;
import net.notvergin.jmresurrected.entity.JMEntites;
import net.notvergin.jmresurrected.mobeffects.JMMobEffects;
import net.notvergin.jmresurrected.items.JMItems;
import net.notvergin.jmresurrected.sound.JMSounds;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(JohnModResurrected.MODID)
public class JohnModResurrected
{
    public static final String MODID = "jmresurrected";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> JM_CREATIVE_TAB = TAB_REGISTER.register("jm_creative_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.literal("John Mod Resurrected"))
            .icon(() -> JMItems.IMMORTALITY_GEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(JMItems.IMMORTALITY_SHARD.get());
                output.accept(JMItems.IMMORTALITY_GEM.get());
                output.accept(JMItems.JOHN_TEAR.get());
                output.accept(JMItems.IMMORTAL_HUSK.get());
                output.accept(JMItems.REFINED_IMMORTAL_GEM.get());
                output.accept(JMBlocks.IMMORTAL_GEM_BLOCK.get());
                output.accept(JMBlocks.PERENNIAL_BLOCK.get());
                output.accept(JMItems.IMMORTALITY_SWORD.get());
                output.accept(JMItems.UIMMORTALITY_SWORD.get());
                output.accept(JMItems.BABY_JOHN_EGG.get());
                output.accept(JMItems.JOHN_EGG.get());
            }).build());

    public JohnModResurrected() {}

    public JohnModResurrected(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        JMEntites.register(modEventBus);
        JMBlocks.register(modEventBus);
        JMBlockEntities.register(modEventBus);
        JMItems.register(modEventBus);
        JMMobEffects.register(modEventBus);
        JMPotionRegistry.register(modEventBus);
        JMSounds.register(modEventBus);

        TAB_REGISTER.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        RegisterBrewingRecipe.register(event);
    }

    @SubscribeEvent
    public void onServerSetup(final FMLCommonSetupEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {}
}
