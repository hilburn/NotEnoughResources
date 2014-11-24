package neresources.registry;

import neresources.api.entry.IModifyOre;
import net.minecraft.item.ItemStack;

public class AddOreDrop implements IModifyOre
{

    ItemStack ore;
    ItemStack[] drops;

    public AddOreDrop(ItemStack ore, ItemStack... drops)
    {
        this.ore = ore;
        this.drops = drops;
    }

    @Override
    public ItemStack ore()
    {
        return ore;
    }

    @Override
    public ItemStack[] removeDrops()
    {
        return null;
    }

    @Override
    public ItemStack[] addDrops()
    {
        return drops;
    }
}
