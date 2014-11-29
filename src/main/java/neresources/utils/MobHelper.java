package neresources.utils;

import neresources.api.messages.IMobEntry;
import neresources.api.utils.DropItem;
import net.minecraft.entity.EntityHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

public class MobHelper
{
    public static boolean dropsItem(IMobEntry entry, ItemStack item)
    {
        for (DropItem dropItem : entry.getDrops())
            if (dropItem.item.isItemEqual(item)) return true;
        return false;
    }

    public static String getEntityName(IMobEntry entry)
    {
        return EntityHelper.getEntityName(entry.getEntity());
    }

    public static int getExpDrop(IMobEntry entry)
    {
        if (entry.getEntity() instanceof EntityLiving)
            return EntityHelper.getExperience((EntityLiving) entry.getEntity());
        return 0;
    }
}
