package neresources.registry;

import neresources.api.messages.IModifyMob;
import neresources.api.utils.DropItem;
import net.minecraft.item.ItemStack;

public class ChangeMobDrop implements IModifyMob
{
    private Class mobClass;
    private DropItem[] addDrops = new DropItem[0];
    private ItemStack[] removeDrops = new ItemStack[0];
    private boolean exactMatch;
    private boolean witherSkeleton;

    public ChangeMobDrop(Class clazz, DropItem... dropItems)
    {
        this(clazz, false, dropItems);
    }

    public ChangeMobDrop(Class clazz, boolean exact, DropItem... dropItems)
    {
        this(clazz, exact, false, dropItems);
    }

    public ChangeMobDrop(Class clazz, ItemStack... dropItems)
    {
        this(clazz, false, dropItems);
    }

    public ChangeMobDrop(Class clazz, boolean exact, ItemStack... dropItems)
    {
        this(clazz, exact, false, dropItems);
    }

    public ChangeMobDrop(Class clazz, boolean exact, boolean wither, DropItem... dropItems)
    {
        this(clazz, exact, wither, new ItemStack[0], dropItems == null ? new DropItem[0] : dropItems);
    }

    public ChangeMobDrop(Class clazz, boolean exact, boolean wither, ItemStack... dropItems)
    {
        this(clazz, exact, wither, dropItems == null ? new ItemStack[0] : dropItems, new DropItem[0]);
    }

    public ChangeMobDrop(Class clazz, boolean exact, boolean wither, ItemStack[] removeDrops, DropItem[] addDrops)
    {
        mobClass = clazz;
        this.removeDrops = removeDrops;
        this.addDrops = addDrops;
        exactMatch = exact;
        witherSkeleton = wither;
    }

    public void setWitherSkeleton(boolean witherSkeleton)
    {
        this.witherSkeleton = witherSkeleton;
    }

    @Override
    public Class applyToClass()
    {
        return mobClass;
    }

    @Override
    public boolean isExactMatch()
    {
        return exactMatch;
    }

    @Override
    public boolean witherSkeleton()
    {
        return witherSkeleton;
    }

    @Override
    public DropItem[] addItems()
    {
        return addDrops;
    }

    @Override
    public ItemStack[] removeItems()
    {
        return removeDrops;
    }
}
