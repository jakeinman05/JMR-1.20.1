package net.notvergin.jmresurrected.datagen;

import net.notvergin.jmresurrected.customitems.JMModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class JMItemModelProvider extends ItemModelProvider
{
    public JMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        buildItem(JMModItems.IMMORTALITY_GEM);
        buildItem(JMModItems.JOHN_TEAR);
        buildItem(JMModItems.IMMORTALITY_SHARD);
        buildItem(JMModItems.IMMORTAL_HUSK);
        buildItem(JMModItems.REFINED_IMMORTAL_GEM);

        buildHandheldItem(JMModItems.IMMORTALITY_SWORD);
        buildHandheldItem(JMModItems.UIMMORTALITY_SWORD);

        withExistingParent(JMModItems.JOHN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(JMModItems.BABY_JOHN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
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
