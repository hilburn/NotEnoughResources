package neiresources.utils;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MapKeys
{

    public static String[] getKeys(ItemStack itemStack)
    {
        if (itemStack!=null && itemStack.getItem()!=null) {
            int[] oreDictIds = OreDictionary.getOreIDs(itemStack);
            String[] oreDictNames = new String[oreDictIds.length];
            for (int i = 0; i < oreDictIds.length; i++)
                oreDictNames[i] = OreDictionary.getOreName(oreDictIds[i]);
            if (oreDictNames.length == 0)
                oreDictNames = new String[]{getKey(itemStack)};
            return oreDictNames;
        }
        return null;
    }

    private static String getKey(ItemStack itemStack)
    {
        return GameRegistry.findUniqueIdentifierFor(itemStack.getItem()).toString()+"@"+itemStack.getItemDamage();
    }
}
