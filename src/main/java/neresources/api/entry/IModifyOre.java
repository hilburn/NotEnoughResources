package neresources.api.entry;

import net.minecraft.item.ItemStack;

public interface IModifyOre
{

    public ItemStack ore();

    public ItemStack[] removeDrops();

    public ItemStack[] addDrops();
}
