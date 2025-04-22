package net.tazgirl.sanding.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.tazgirl.sanding.Config;
import net.tazgirl.sanding.Sanding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = Sanding.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SandpaperItem extends Item
{
    public SandpaperItem(Properties properties)
    {
        super(properties);
    }

    private static final Map<Item, Item> RECIPE_MAP = new Map<Item, Item>()
    {
        @Override
        public int size()
        {
            return 0;
        }

        @Override
        public boolean isEmpty()
        {
            return false;
        }

        @Override
        public boolean containsKey(Object key)
        {
            return false;
        }

        @Override
        public boolean containsValue(Object value)
        {
            return false;
        }

        @Override
        public Item get(Object key)
        {
            return null;
        }

        @Nullable
        @Override
        public Item put(Item key, Item value)
        {
            return null;
        }

        @Override
        public Item remove(Object key)
        {
            return null;
        }

        @Override
        public void putAll(@NotNull Map<? extends Item, ? extends Item> m)
        {

        }

        @Override
        public void clear()
        {

        }

        @NotNull
        @Override
        public Set<Item> keySet()
        {
            return Set.of();
        }

        @NotNull
        @Override
        public Collection<Item> values()
        {
            return List.of();
        }

        @NotNull
        @Override
        public Set<Entry<Item, Item>> entrySet()
        {
            return Set.of();
        }
    };

    @SubscribeEvent
    static void commonSetup(FMLCommonSetupEvent event)
    {
        System.out.println("commonSetup ran");

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
