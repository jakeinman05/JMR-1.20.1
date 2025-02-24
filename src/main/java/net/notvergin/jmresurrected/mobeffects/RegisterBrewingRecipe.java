package net.notvergin.jmresurrected.mobeffects;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.notvergin.jmresurrected.JohnModResurrected;
import net.notvergin.jmresurrected.items.JMItems;
import net.notvergin.jmresurrected.mobeffects.potions.JMPotionRegistry;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterBrewingRecipe
{
    public static void register(FMLCommonSetupEvent event)
    {
        ItemStack input = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRENGTH);
        ItemStack output = PotionUtils.setPotion(new ItemStack(Items.POTION), JMPotionRegistry.IMMORTAL_POTION.get());

        event.enqueueWork(()->{
            BrewingRecipeRegistry.addRecipe(Ingredient.of(input), Ingredient.of(JMItems.IMMORTAL_HUSK.get()), output);
        });
    }
}
