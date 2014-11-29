package neresources.api.messages;

import neresources.api.messages.utils.MessageHelper;
import neresources.api.messages.utils.MessageKeys;
import neresources.api.utils.DropItem;
import neresources.api.utils.Priority;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModifyMobMessage extends ModifyMessage
{
    private String className;
    private DropItem[] addDrops = new DropItem[0];
    private ItemStack[] removeDrops = new ItemStack[0];
    private boolean strict;
    private boolean witherSkeleton;
    
    public ModifyMobMessage(Object clazz, DropItem... addDrops)
    {
        this(clazz,false,false,addDrops);
    }
    
    public ModifyMobMessage(Object clazz, boolean strict, DropItem... addDrops)
    {
        this(clazz, strict,false,addDrops);
    }

    public ModifyMobMessage(Object clazz, boolean strict, boolean witherSkeleton, DropItem... addDrops)
    {
        this(clazz, strict,witherSkeleton,Priority.FIRST,addDrops);
    }

    public ModifyMobMessage(Object clazz, Priority priority,  DropItem... addDrops)
    {
        this(clazz,false,false,priority,addDrops);
    }

    public ModifyMobMessage(Object clazz, boolean strict, Priority priority,  DropItem... addDrops)
    {
        this(clazz, strict,false,priority,addDrops);
    }
    
    public ModifyMobMessage(Object clazz, boolean strict, boolean witherSkeleton, Priority priority, DropItem... addDrops)
    {
        this(clazz, strict, witherSkeleton, addDrops, new ItemStack[0], priority);
    }

    public ModifyMobMessage(Object clazz, ItemStack... removeDrops)
    {
        this(clazz,false,false,removeDrops);
    }

    public ModifyMobMessage(Object clazz, boolean strict, ItemStack... removeDrops)
    {
        this(clazz, strict,false,removeDrops);
    }

    public ModifyMobMessage(Object clazz, boolean strict, boolean witherSkeleton, ItemStack... removeDrops)
    {
        this(clazz, strict,witherSkeleton,Priority.FIRST,removeDrops);
    }

    public ModifyMobMessage(Object clazz, Priority priority,  ItemStack... removeDrops)
    {
        this(clazz,false,false,priority,removeDrops);
    }

    public ModifyMobMessage(Object clazz, boolean strict, Priority priority,  ItemStack... removeDrops)
    {
        this(clazz, strict,false,priority,removeDrops);
    }

    public ModifyMobMessage(Object clazz, boolean strict, boolean witherSkeleton, Priority priority, ItemStack... removeDrops)
    {
        this(clazz, strict, witherSkeleton, new DropItem[0], removeDrops, priority);
    }

    public ModifyMobMessage(Object clazz, DropItem[] addDrops, ItemStack[] removeDrops)
    {
        this(clazz,addDrops,removeDrops,Priority.FIRST);
    }

    public ModifyMobMessage(Object clazz, boolean strict, DropItem[] addDrops, ItemStack[] removeDrops)
    {
        this(clazz, strict,addDrops,removeDrops,Priority.FIRST);
    }

    public ModifyMobMessage(Object clazz, boolean strict, boolean witherSkeleton, DropItem[] addDrops, ItemStack[] removeDrops)
    {
        this(clazz, strict,witherSkeleton,addDrops,removeDrops,Priority.FIRST);
    }

    public ModifyMobMessage(Object clazz, DropItem[] addDrops, ItemStack[] removeDrops, Priority priority)
    {
        this(clazz,false,addDrops,removeDrops,priority);
    }

    public ModifyMobMessage(Object clazz, boolean strict, DropItem[] addDrops, ItemStack[] removeDrops, Priority priority)
    {
        this(clazz, strict,false,addDrops,removeDrops,priority);
    }

    public ModifyMobMessage(Object clazz, boolean strict, boolean witherSkeleton, DropItem[] addDrops, ItemStack[] removeDrops, Priority priority)
    {
        this(clazz, strict, witherSkeleton, addDrops, removeDrops, priority, priority);
    }

    public ModifyMobMessage(Object clazz, boolean strict, boolean witherSkeleton, DropItem[] addDrops, ItemStack[] removeDrops, Priority addPriority, Priority removePriority)
    {
        super(addPriority,removePriority);
        initialize(clazz, strict,witherSkeleton,addDrops,removeDrops);
    }

    public ModifyMobMessage(NBTTagCompound tagCompound)
    {
        super(tagCompound);
        initialize(tagCompound.getString(MessageKeys.className),tagCompound.getBoolean(MessageKeys.strict),tagCompound.getBoolean(MessageKeys.wither),MessageHelper.getDropItems(tagCompound,MessageKeys.addDrops),MessageHelper.getItemStacks(tagCompound,MessageKeys.removeDrops));
    }

    private void initialize(Object clazz, boolean strict, boolean witherSkeleton, DropItem[] addDrops, ItemStack[] removeDrops)
    {
        this.className = MessageHelper.getClass(clazz);
        this.strict = strict;
        this.witherSkeleton = witherSkeleton;
        this.addDrops  = addDrops;
        this.removeDrops = removeDrops;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setString(MessageKeys.className, this.className);
        tagCompound.setBoolean(MessageKeys.strict, this.strict);
        tagCompound.setBoolean(MessageKeys.wither, this.witherSkeleton);
        tagCompound.setTag(MessageKeys.addDrops, MessageHelper.getDropItemList(addDrops));
        tagCompound.setTag(MessageKeys.removeDrops, MessageHelper.getItemStackList(removeDrops));
        return tagCompound;
    }

    @Override
    public boolean isValid()
    {
        return !className.equals("") && (addDrops.length>0 || removeDrops.length>0);
    }
}