package net.tazgirl.sanding.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazgirl.sanding.Sanding;
import net.tazgirl.sanding.item.custom.SandpaperItem;

public class ModItems
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Sanding.MODID);

    public static final DeferredItem<Item> SANDPAPER = ITEMS.register("sandpaper", ()-> new SandpaperItem(new Item.Properties()));
}
