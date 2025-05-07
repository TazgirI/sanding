package net.tazgirl.sanding.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.tazgirl.sanding.Config;
import net.tazgirl.sanding.Sanding;
import net.tazgirl.sanding.recipe.ModRecipes;
import net.tazgirl.sanding.recipe.SandingRecipe;
import net.tazgirl.sanding.recipe.SandingRecipeInput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@EventBusSubscriber(modid = Sanding.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SandpaperItem extends Item
{
    public SandpaperItem(Properties properties)
    {
        super(properties);
    }

    @SubscribeEvent
    static void commonSetup(FMLCommonSetupEvent event)
    {

    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.BRUSH;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity)
    {
        return Config.sandpaperSpeed;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand)
    {
        InteractionResultHolder<ItemStack> irh = super.use(world, player, hand);
        player.startUsingItem(hand);
        return irh;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity)
    {
        ItemStack offHandItem = livingEntity.getItemInHand(InteractionHand.OFF_HAND);
        ItemStack mainHandItem = livingEntity.getMainHandItem();



        if (ValidRecipe(level, offHandItem))
        {
            mainHandItem.hurtAndBreak(1, (ServerLevel) level, null, item -> BreakMainHand(livingEntity, level));
        }



        stack.setDamageValue(stack.getDamageValue() - 1);
        return(stack);
    }



    private boolean ValidRecipe(Level level, ItemStack input)
    {
        Optional<RecipeHolder<SandingRecipe>> recipe = GetCurrentRecipe(level, input);



        return recipe.isPresent();
    }

    private Optional<RecipeHolder<SandingRecipe>> GetCurrentRecipe(Level level, ItemStack input)
    {
        return level.getRecipeManager().getRecipeFor(ModRecipes.SANDING_TYPE.get(), new SandingRecipeInput(input), level);
    }

    public void BreakMainHand(LivingEntity livingEntity, Level level)
    {
        ItemStack stack = livingEntity.getMainHandItem();
        if (!livingEntity.isSilent()) {
            level.playLocalSound(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), stack.getBreakingSound(), SoundSource.PLAYERS, 0.8F, 0.8F + level.random.nextFloat() * 0.4F, false);
        }

        livingEntity.getMainHandItem().shrink(1);

    }

}
