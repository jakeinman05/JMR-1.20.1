package net.notvergin.jmresurrected.entity.registryhandlers;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.notvergin.jmresurrected.entity.JMEntites;
import net.notvergin.jmresurrected.entity.client.RenderBabyJohn;
import net.notvergin.jmresurrected.entity.client.RenderJohn;
import net.notvergin.jmresurrected.entity.models.JMModelLayers;
import net.notvergin.jmresurrected.entity.models.entity.BabyJohnModel;
import net.notvergin.jmresurrected.entity.models.entity.JohnModel;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEntityRegistry
{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(JMEntites.JOHN.get(), RenderJohn::new);
        EntityRenderers.register(JMEntites.BABYJOHN.get(), RenderBabyJohn::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(JMModelLayers.JOHN_LAYER, JohnModel::createBodyLayer);
        event.registerLayerDefinition(JMModelLayers.BABY_JOHN_LAYER, BabyJohnModel::createBodyLayer);
    }
}
