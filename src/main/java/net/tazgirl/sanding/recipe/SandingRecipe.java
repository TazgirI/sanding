package net.tazgirl.sanding.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record SandingRecipe(Ingredient inputItem, ItemStack output) implements Recipe<SandingRecipeInput>
{
    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list
                ;
    }

    @Override
    public boolean matches(SandingRecipeInput sandingRecipeInput, Level level)
    {
        if (level.isClientSide())
        {
            return false;
        }
        return inputItem.test((sandingRecipeInput.getItem(0)));
    }

    @Override
    public ItemStack assemble(SandingRecipeInput sandingRecipeInput, HolderLookup.Provider provider)
    {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider)
    {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.SANDING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.SANDING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<SandingRecipe>
    {
        public static final MapCodec<SandingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(SandingRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(SandingRecipe::output)).apply(inst, SandingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SandingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, SandingRecipe::inputItem,
                        ItemStack.STREAM_CODEC, SandingRecipe::output,
                        SandingRecipe::new);



        @Override
        public MapCodec<SandingRecipe> codec()
        {
            return null;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SandingRecipe> streamCodec()
        {
            return null;
        }
    }
}
