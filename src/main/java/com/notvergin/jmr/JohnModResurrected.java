package com.notvergin.jmr;

import com.notvergin.jmr.entity.client.JohnModel;
import com.notvergin.jmr.entity.client.ModModelLayers;
import com.notvergin.jmr.mobeffects.RegisterBrewingRecipe;
import com.notvergin.jmr.mobeffects.potions.ModPotions;
import com.notvergin.jmr.entity.ModEntities;
import com.notvergin.jmr.entity.client.RenderJohn;
import com.notvergin.jmr.mobeffects.ModMobEffects;
import com.notvergin.jmr.customitems.ModItems;
import com.notvergin.jmr.sound.ModSounds;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(JohnModResurrected.MODID)
public class JohnModResurrected
{
    public static final String MODID = "jmresurrected";

    public JohnModResurrected(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModSounds.register(modEventBus);

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
        }
        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.IMMORTALITY_SWORD);
            event.accept(ModItems.UIMMORTALITY_SWORD);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.JOHN.get(), RenderJohn::new);
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.JOHN_LAYER, JohnModel::createBodyLayer);
        }
    }
}
