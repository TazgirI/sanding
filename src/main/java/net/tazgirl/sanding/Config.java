package net.tazgirl.sanding;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Sanding.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue SANDPAPER_SPEED = BUILDER
            .comment("Number of ticks sandpaper takes to use")
            .defineInRange("sandpaperSpeed", 30, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue SANDPAPER_DURABILITY = BUILDER
            .comment("Number of ticks sandpaper takes to use")
            .defineInRange("sandpaperDurability", 20, 1, Integer.MAX_VALUE);




    static final ModConfigSpec SPEC = BUILDER.build();

    public static int sandpaperSpeed;

    public static int sandpaperDurability;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        sandpaperSpeed = SANDPAPER_SPEED.get();

        sandpaperDurability = SANDPAPER_DURABILITY.get();

    }
}
