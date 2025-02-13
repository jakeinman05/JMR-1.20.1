package net.notvergin.jmresurrected;

import net.notvergin.jmresurrected.mobeffects.RegisterBrewingRecipe;
import net.notvergin.jmresurrected.mobeffects.potions.ModPotions;
import net.notvergin.jmresurrected.entity.registryhandlers.ModEntities;
import net.notvergin.jmresurrected.mobeffects.ModMobEffects;
import net.notvergin.jmresurrected.customitems.ModItems;
import net.notvergin.jmresurrected.sound.ModSounds;
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

        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModSounds.register(modEventBus);

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
            event.accept(ModItems.JOHN_TEAR);
            event.accept(ModItems.IMMORTALITY_GEM);
            event.accept(ModItems.IMMORTALITY_SHARD);
            event.accept(ModItems.IMMORTAL_HUSK);
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.REFINED_IMMORTAL_GEM);
        }
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.JOHN_EGG);
            event.accept(ModItems.BABY_JOHN_EGG);
        }
        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.IMMORTALITY_SWORD);
            event.accept(ModItems.UIMMORTALITY_SWORD);
        }
    }

    @SubscribeEvent
    public void onServerSetup(final FMLCommonSetupEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {}
}
