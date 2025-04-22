package net.tazgirl.sanding.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.tazgirl.sanding.Config;

import java.util.Map;

public class SandpaperItem extends Item
{
    public SandpaperItem(Properties properties)
    {
        super(properties);
    }

    private static final Map<Item, Item> RECIPE_MAP =
            Map.of
                    (
                            null, null
                    );

    @SubscribeEvent
    static void commonSetup (FMLCommonSetupEvent event)
    {
        for (int i = 0; i < Config.sandpaperSources.size(); i++)
        {
            RECIPE_MAP.put(Config.sandpaperSources.get(i), Config.sandpaperResults.get(i));
        }
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

}
