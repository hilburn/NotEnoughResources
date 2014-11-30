package neresources.registry;

import neresources.api.messages.ModifyOreMessage;
import net.minecraft.item.ItemStack;

public class ChangeOreDrop
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

    public ChangeOreDrop(ModifyOreMessage message)
    {
        this.ore = message.getOre();
        this.removeDrops = message.getRemoveDrops();
        this.addDrops = message.getAddDrops();
    }

    public ItemStack ore()
    {
        return ore;
    }

    public ItemStack[] removeDrops()
    {
        return removeDrops;
    }

    public ItemStack[] addDrops()
    {
        return addDrops;
    }
}
