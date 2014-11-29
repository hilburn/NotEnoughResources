package neresources.compatibility;

import neresources.api.NEResourcesAPI;
import neresources.api.messages.*;
import neresources.registry.*;
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

        for (Object entry : NEResourcesAPI.getEntryRegistry())
        {
            if (entry instanceof MobEntry) MobRegistry.getInstance().registerMob((MobEntry) entry);
            else if (entry instanceof IDungeonEntry)DungeonRegistry.getInstance().registerDungeonEntry((IDungeonEntry) entry);
            else if (entry instanceof ISeedEntry) GrassSeedRegistry.getInstance().add(((ISeedEntry) entry));
            else if (entry instanceof OreEntry) OreRegistry.getInstance().register((OreEntry) entry);
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

        //remove drops
        for (Object entry : NEResourcesAPI.getEntryRegistry())
        {
            if (entry instanceof ChangeOreDrop) OreRegistry.getInstance().addDrops((ChangeOreDrop) entry);
            if (entry instanceof ChangeMobDrop) MobRegistry.getInstance().removeMobDrops((ChangeMobDrop) entry);
        }

        for (Object entry : NEResourcesAPI.getEntryRegistry())
        {
            if (entry instanceof ChangeOreDrop) OreRegistry.getInstance().removeDrops((ChangeOreDrop) entry);
            if (entry instanceof ChangeMobDrop) MobRegistry.getInstance().addMobDrops((ChangeMobDrop) entry);
        }


    }
}
