package net.notvergin.jmresurrected;

import net.notvergin.jmresurrected.mobeffects.RegisterBrewingRecipe;
import net.notvergin.jmresurrected.mobeffects.potions.JMPotionRegistry;
import net.notvergin.jmresurrected.entity.registryhandlers.JMEntites;
import net.notvergin.jmresurrected.mobeffects.JMMobEffects;
import net.notvergin.jmresurrected.customitems.JMModItems;
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

@Mod(JohnModResurrected.MODID)
public class JohnModResurrected
{
    public static final String MODID = "jmresurrected";

    public JohnModResurrected() {}

    public JohnModResurrected(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        JMEntites.register(modEventBus);
        JMModItems.register(modEventBus);
        JMMobEffects.register(modEventBus);
        JMPotionRegistry.register(modEventBus);
        JMSounds.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        RegisterBrewingRecipe.registerCustomBrewingRecipes();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(JMModItems.JOHN_TEAR);
            event.accept(JMModItems.IMMORTALITY_GEM);
            event.accept(JMModItems.IMMORTALITY_SHARD);
            event.accept(JMModItems.IMMORTAL_HUSK);
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(JMModItems.REFINED_IMMORTAL_GEM);
        }
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(JMModItems.JOHN_EGG);
            event.accept(JMModItems.BABY_JOHN_EGG);
        }
        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(JMModItems.IMMORTALITY_SWORD);
            event.accept(JMModItems.UIMMORTALITY_SWORD);
        }
    }

    @SubscribeEvent
    public void onServerSetup(final FMLCommonSetupEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {}
}
