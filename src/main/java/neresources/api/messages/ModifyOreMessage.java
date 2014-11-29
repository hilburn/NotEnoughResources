package neresources.api.messages;

import neresources.api.messages.utils.MessageHelper;
import neresources.api.messages.utils.MessageKeys;
import neresources.api.utils.Priority;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModifyOreMessage extends ModifyMessage
{
    private ItemStack ore;
    private ItemStack[] addDrops = new ItemStack[0];
    private ItemStack[] removeDrops = new ItemStack[0];

    public ModifyOreMessage(ItemStack ore, ItemStack... drops)
    {
        this(ore, true, drops);
    }

    public ModifyOreMessage(ItemStack ore, boolean add, ItemStack... drops)
    {
        this(ore, add, Priority.SECOND, drops);
    }

    public ModifyOreMessage(ItemStack ore, boolean add, Priority priority, ItemStack... drops)
    {
        super(priority,add);
        this.ore = ore;
        if (add)
            this.addDrops = drops;
        else
            this.removeDrops = drops;
    }

    public ModifyOreMessage(ItemStack ore, ItemStack[] removeDrops, ItemStack[] addDrops)
    {
        this(ore, removeDrops, addDrops, Priority.SECOND, Priority.FIRST);
    }

    public ModifyOreMessage(ItemStack ore, ItemStack[] removeDrops, ItemStack[] addDrops, Priority removePriority, Priority addPriority)
    {
        super(addPriority,removePriority);
        this.ore = ore;
        this.removeDrops = removeDrops;
        this.addDrops = addDrops;
    }

    public ModifyOreMessage(NBTTagCompound tagCompound)
    {
        super(tagCompound);
        this.ore = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag(MessageKeys.ore));
        this.addDrops = MessageHelper.getItemStacks(tagCompound, MessageKeys.addDrops);
        this.removeDrops = MessageHelper.getItemStacks(tagCompound, MessageKeys.removeDrops);
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

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setTag(MessageKeys.ore, ore.writeToNBT(new NBTTagCompound()));
        tagCompound.setTag(MessageKeys.addDrops, MessageHelper.getItemStackList(addDrops));
        tagCompound.setTag(MessageKeys.removeDrops, MessageHelper.getItemStackList(removeDrops));
        return tagCompound;
    }

    @Override
    public boolean isValid()
    {
        return ore!=null && !(addDrops.length==0 && removeDrops.length==0);
    }
}
