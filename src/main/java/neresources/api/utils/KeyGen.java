package neresources.api.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KeyGen {
    public static String getKey(ItemStack itemStack)
    {
        return Item.getIdFromItem(itemStack.getItem())+":"+itemStack.getItemDamage();
    }
}
