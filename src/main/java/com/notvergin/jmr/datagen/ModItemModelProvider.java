package com.notvergin.jmr.datagen;

import com.notvergin.jmr.customitems.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class ModItemModelProvider extends ItemModelProvider
{
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        buildItem(ModItems.IMMORTALITY_GEM);
        buildItem(ModItems.JOHN_TEAR);
        buildItem(ModItems.IMMORTALITY_SHARD);
        buildItem(ModItems.IMMORTAL_HUSK);
        buildItem(ModItems.REFINED_IMMORTAL_GEM);

        buildHandheldItem(ModItems.IMMORTALITY_SWORD);
        buildHandheldItem(ModItems.UIMMORTALITY_SWORD);

        withExistingParent(ModItems.JOHN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder buildItem(RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder buildHandheldItem(RegistryObject<Item> item)
    {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(MODID, "item/" + item.getId().getPath()));
    }

}
