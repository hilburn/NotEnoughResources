package neresources.api.messages;

import neresources.api.utils.DropItem;
import net.minecraft.item.ItemStack;

public interface IModifyMob
{
    public Class applyToClass();

    public boolean isExactMatch();

    public boolean witherSkeleton();

    public DropItem[] addItems();

    public ItemStack[] removeItems();
}
