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
            .defineInRange("sandpaperSpeed", 20, 1, Integer.MAX_VALUE);

    // a list of strings that are treated as resource locations for items
    private static final ModConfigSpec.ConfigValue<List<? extends String>> SANDPAPER_SOURCE_ITEMS = BUILDER
            .comment("The list of SOURCE items for sandpaper'ering \nNO DUPLICATES within this list, the order IS important" )
            .defineListAllowEmpty("items", List.of("minecraft:magma_cream"), Config::validateItemName);

    private static final ModConfigSpec.ConfigValue<List<? extends String>> SANDPAPER_RESULT_ITEMS = BUILDER
            .comment("The list of RESULT items for sandpaper'ering \nNO DUPLICATES within this list, the order IS important")
            .defineListAllowEmpty("items", List.of("minecraft:slime_ball"), Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    public  static int sandpaperSpeed;
    public static List<Item> sandpaperSources;
    public static List<Item> sandpaperResults;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        if (sandpaperSources.size() != sandpaperResults.size())
        {
            throw new RuntimeException("Fatal error, SANDPAPER_SOURCE_ITEMS & SANDPAPER_RESULT_ITEMS are of different lengths");
        }

        sandpaperSpeed = SANDPAPER_SPEED.get();

        // convert the list of strings into a set of items
        sandpaperSources = SANDPAPER_SOURCE_ITEMS.get().stream()
                .map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName)))
                .collect(Collectors.toList());

        sandpaperResults = SANDPAPER_RESULT_ITEMS.get().stream()
                .map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName)))
                .collect(Collectors.toList());
    }
}
