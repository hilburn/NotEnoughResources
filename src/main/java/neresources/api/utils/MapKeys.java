package neresources.api.utils;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MapKeys
{

    public static String[] getKeys(ItemStack itemStack)
    {
        if (itemStack != null && itemStack.getItem() != null)
        {
            int[] oreDictIds = OreDictionary.getOreIDs(itemStack);
            String[] oreDictNames = new String[oreDictIds.length];
            for (int i = 0; i < oreDictIds.length; i++)
                oreDictNames[i] = OreDictionary.getOreName(oreDictIds[i]);
            if (oreDictNames.length == 0)
                oreDictNames = new String[]{getKey(itemStack)};
            return oreDictNames;
        }
        return new String[0];
    }

    public static String getKey(ItemStack itemStack)
    {
        GameRegistry.UniqueIdentifier UUID = GameRegistry.findUniqueIdentifierFor(itemStack.getItem());
        if (UUID != null)
            return UUID.toString() + "@" + itemStack.getItemDamage();
        return itemStack.toString();
    }
}
