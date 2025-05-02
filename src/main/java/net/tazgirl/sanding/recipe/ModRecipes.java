package net.tazgirl.sanding.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazgirl.sanding.Sanding;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Sanding.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Sanding.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SandingRecipe>> SANDING_SERIALIZER = SERIALIZERS.register("sanding", SandingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<SandingRecipe>> SANDING_TYPE = TYPES.register("sanding", () -> new RecipeType<SandingRecipe>()
    {
        @Override
        public String toString()
        {
            return "sanding";
        }
    });

    public static void register(IEventBus eventBus)
    {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }

}
