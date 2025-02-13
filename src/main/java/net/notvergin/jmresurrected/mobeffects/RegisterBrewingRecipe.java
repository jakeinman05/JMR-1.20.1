package net.notvergin.jmresurrected.mobeffects;

import net.notvergin.jmresurrected.customitems.JMModItems;
import net.notvergin.jmresurrected.mobeffects.potions.JMPotionRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.common.Mod;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterBrewingRecipe
{
    public static void registerCustomBrewingRecipes()
    {
        IBrewingRecipe Immortal_Recipe = new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                return PotionUtils.getPotion(input) == Potions.STRENGTH;
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.getItem() == JMModItems.IMMORTAL_HUSK.get();
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                return PotionUtils.setPotion(new ItemStack(Items.POTION), JMPotionRegistry.IMMORTAL_POTION.get());
            }
        };
        BrewingRecipeRegistry.addRecipe(Immortal_Recipe);
    }
}
