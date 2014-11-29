package neresources.api.messages;

import neresources.api.utils.DropItem;
import neresources.api.utils.Priority;
import net.minecraft.item.ItemStack;

public class ModifyMobMessage extends ModifyMessage
{
    private String className;
    private DropItem[] addDrops = new DropItem[0];
    private ItemStack[] removeDrops = new ItemStack[0];
    private boolean exactMatch;
    private boolean witherSkeleton;
    
    public ModifyMobMessage(Object clazz, DropItem... addDrops)
    {
        this(clazz,false,false,addDrops);
    }
    
    public ModifyMobMessage(Object clazz, boolean exactMatch, DropItem... addDrops)
    {
        this(clazz,exactMatch,false,addDrops);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, boolean witherSkeleton, DropItem... addDrops)
    {
        this(clazz,exactMatch,witherSkeleton,Priority.FIRST,addDrops);
    }

    public ModifyMobMessage(Object clazz, Priority priority,  DropItem... addDrops)
    {
        this(clazz,false,false,priority,addDrops);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, Priority priority,  DropItem... addDrops)
    {
        this(clazz,exactMatch,false,priority,addDrops);
    }
    
    public ModifyMobMessage(Object clazz, boolean exactMatch, boolean witherSkeleton, Priority priority, DropItem... addDrops)
    {
        this(clazz,exactMatch,witherSkeleton,addDrops,new ItemStack[0],priority);
    }

    public ModifyMobMessage(Object clazz, ItemStack... removeDrops)
    {
        this(clazz,false,false,removeDrops);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, ItemStack... removeDrops)
    {
        this(clazz,exactMatch,false,removeDrops);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, boolean witherSkeleton, ItemStack... removeDrops)
    {
        this(clazz,exactMatch,witherSkeleton,Priority.FIRST,removeDrops);
    }

    public ModifyMobMessage(Object clazz, Priority priority,  ItemStack... removeDrops)
    {
        this(clazz,false,false,priority,removeDrops);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, Priority priority,  ItemStack... removeDrops)
    {
        this(clazz,exactMatch,false,priority,removeDrops);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, boolean witherSkeleton, Priority priority, ItemStack... removeDrops)
    {
        this(clazz,exactMatch,witherSkeleton,new DropItem[0],removeDrops,priority);
    }

    public ModifyMobMessage(Object clazz, DropItem[] addDrops, ItemStack[] removeDrops)
    {
        this(clazz,addDrops,removeDrops,Priority.FIRST);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, DropItem[] addDrops, ItemStack[] removeDrops)
    {
        this(clazz,exactMatch,addDrops,removeDrops,Priority.FIRST);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, boolean witherSkeleton, DropItem[] addDrops, ItemStack[] removeDrops)
    {
        this(clazz,exactMatch,witherSkeleton,addDrops,removeDrops,Priority.FIRST);
    }

    public ModifyMobMessage(Object clazz, DropItem[] addDrops, ItemStack[] removeDrops, Priority priority)
    {
        this(clazz,false,addDrops,removeDrops,priority);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, DropItem[] addDrops, ItemStack[] removeDrops, Priority priority)
    {
        this(clazz,exactMatch,false,addDrops,removeDrops,priority);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, boolean witherSkeleton, DropItem[] addDrops, ItemStack[] removeDrops, Priority priority)
    {
        this(clazz, exactMatch, witherSkeleton, addDrops, removeDrops, priority, priority);
    }

    public ModifyMobMessage(Object clazz, boolean exactMatch, boolean witherSkeleton, DropItem[] addDrops, ItemStack[] removeDrops, Priority addPriority, Priority removePriority)
    {
        super(addPriority,removePriority);
        initialize(clazz,exactMatch,witherSkeleton,addDrops,removeDrops);
    }

    private void initialize(Object clazz, boolean exactMatch, boolean witherSkeleton, DropItem[] addDrops, ItemStack[] removeDrops)
    {
        this.className = getClass(clazz);
        this.exactMatch = exactMatch;
        this.witherSkeleton = witherSkeleton;
        this.addDrops  = addDrops;
        this.removeDrops = removeDrops;
    }

    private static String getClass(Object clazz)
    {
        if (clazz instanceof String)
            return (String) clazz;
        if (clazz instanceof Class)
            return ((Class)clazz).getName();
        return "";
    }

    @Override
    public boolean isValid()
    {
        return !className.equals("") && (addDrops.length>0 || removeDrops.length>0);
    }
}