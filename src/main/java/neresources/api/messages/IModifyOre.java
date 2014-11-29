package neresources.api.messages;

import net.minecraft.item.ItemStack;

public interface IModifyOre
{

    public ItemStack ore();

    public ItemStack[] removeDrops();

    public ItemStack[] addDrops();
}
