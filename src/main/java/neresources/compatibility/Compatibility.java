package neresources.compatibility;

import neresources.registry.ChangeOreDrop;
import neresources.registry.MessageRegistry;
import neresources.registry.OreRegistry;
import neresources.utils.ModList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Compatibility
{
    public static final float DENSE_ORES_MULTIPLIER = 1F + 2F * (1000F / 20480F);

    public static void init()
    {
        for (ModList mod : ModList.values())
        {
            mod.initialise();
        }

        if (ModList.denseores.isLoaded())
        {
            for (String oreDictEntry : OreDictionary.getOreNames())
            {
                if (oreDictEntry.startsWith("denseore") && OreDictionary.getOres(oreDictEntry).size() > 0)
                {
                    ItemStack denseOre = OreDictionary.getOres(oreDictEntry).get(0);
                    ItemStack ore = OreDictionary.getOres(oreDictEntry.replace("dense", "")).get(0);
                    OreRegistry.getInstance().addDrops(new ChangeOreDrop(ore, denseOre));
                }
            }
        }

        MessageRegistry.processMessages();
    }
}
