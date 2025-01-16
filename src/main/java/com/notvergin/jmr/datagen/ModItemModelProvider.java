package com.notvergin.jmr.datagen;

import com.notvergin.jmr.registries.ModItems;
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

        buildHandheldItem(ModItems.IMMORTALITY_SWORD);
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
