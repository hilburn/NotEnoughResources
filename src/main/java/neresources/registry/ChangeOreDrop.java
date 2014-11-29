package neresources.registry;

import neresources.api.messages.IModifyOre;
import net.minecraft.item.ItemStack;

public class ChangeOreDrop implements IModifyOre
{
    private ItemStack ore;
    private ItemStack[] addDrops = new ItemStack[0];
    private ItemStack[] removeDrops = new ItemStack[0];


    public ChangeOreDrop(ItemStack ore, ItemStack... drops)
    {
        this(ore, true, drops);
    }

    public ChangeOreDrop(ItemStack ore, boolean add, ItemStack... drops)
    {
        this.ore = ore;
        if (add)
            this.addDrops = drops;
        else
            this.removeDrops = drops;
    }

    public ChangeOreDrop(ItemStack ore, ItemStack[] removeDrops, ItemStack[] addDrops)
    {
        this.ore = ore;
        this.removeDrops = removeDrops;
        this.addDrops = addDrops;
    }

    @Override
    public ItemStack ore()
    {
        return ore;
    }

    @Override
    public ItemStack[] removeDrops()
    {
        return removeDrops;
    }

    @Override
    public ItemStack[] addDrops()
    {
        return addDrops;
    }
}
