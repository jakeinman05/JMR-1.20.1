package net.notvergin.jmresurrected.entity.models;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class ModModelLayers
{
    public static final ModelLayerLocation JOHN_LAYER = new ModelLayerLocation(
            new ResourceLocation(MODID, "john"), "main");
    public static final ModelLayerLocation BABY_JOHN_LAYER = new ModelLayerLocation(
            new ResourceLocation(MODID, "babyjohn"), "main");
}
