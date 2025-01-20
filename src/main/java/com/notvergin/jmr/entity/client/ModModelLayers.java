package com.notvergin.jmr.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class ModModelLayers
{
    public static final ModelLayerLocation JOHN_LAYER = new ModelLayerLocation(
            new ResourceLocation(MODID, "john"), "main");
}
