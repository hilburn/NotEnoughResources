package neresources.compatibility;

import cpw.mods.fml.common.Loader;
import neresources.api.NEResourcesAPI;
import neresources.api.entry.*;
import neresources.registry.*;
import neresources.utils.ModList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Compatibility
{
    public static final float DENSE_ORES_MULTIPLIER = 1F+2F*(1000F/20480F);
    public static void init()
    {
        for (ModList mod : ModList.values())
        {
            mod.initialise();
        }

        for (Object entry : NEResourcesAPI.getEntryRegistry())
        {
            if (entry instanceof IMobEntry) MobRegistry.getInstance().registerMob((IMobEntry) entry);
            else if (entry instanceof IDungeonEntry) DungeonRegistry.getInstance().registerDungeonEntry((IDungeonEntry) entry);
            else if (entry instanceof IOreEntry) OreRegistry.getInstance().registerOre(((IOreEntry) entry));
            else if (entry instanceof IModifyOre) OreRegistry.getInstance().addDrops((IModifyOre) entry);
            else if (entry instanceof ISeedEntry) GrassSeedRegistry.getInstance().add(((ISeedEntry) entry));
        }

        if (Loader.isModLoaded(ModList.denseores.toString()))
        {
            for (String oreDictEntry: OreDictionary.getOreNames())
            {
                if (oreDictEntry.startsWith("denseore"))
                {
                    ItemStack denseOre = OreDictionary.getOres(oreDictEntry).get(0);
                    ItemStack ore = OreDictionary.getOres(oreDictEntry.replace("dense","")).get(0);
                    OreRegistry.getInstance().addDrops(new AddOreDrop(ore,denseOre));
                }
            }
        }

        //remove drops
        for (Object entry : NEResourcesAPI.getEntryRegistry())
        {
            if (entry instanceof IModifyOre) OreRegistry.getInstance().removeDrops((IModifyOre) entry);
        }

        for (Object entry : NEResourcesAPI.getEntryRegistry())
        {
            if (entry instanceof IModifyOre) OreRegistry.getInstance().addDrops((IModifyOre) entry);
        }


    }
}
