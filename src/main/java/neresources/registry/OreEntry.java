package neresources.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import neresources.api.distributions.DistributionBase;
import net.minecraft.item.ItemStack;

public class OreEntry
{
    private double[] spawnChances;
    private String modName;
    private ItemStack ore;
    private int bestY = -1;

    public OreEntry(ItemStack ore, double[] spawnChances)
    {
        this.ore = ore;
        this.modName = GameRegistry.findUniqueIdentifierFor(ore.getItem()).modId;
        this.spawnChances = spawnChances;
    }

    public OreEntry(ItemStack ore, DistributionBase distribution)
    {
        this.ore = ore;
        this.modName = GameRegistry.findUniqueIdentifierFor(ore.getItem()).modId;
        this.spawnChances = distribution.getDistribution();
        this.bestY = distribution.getBestHeight();
    }

    public double getChance(int y)
    {
        if (y < 0 || y >= spawnChances.length)
            return 0;
        return spawnChances[y];
    }

    public String getModName()
    {
        return modName;
    }

    public ItemStack getOre()
    {
        return ore;
    }

    public int getBestY()
    {
        return bestY;
    }
}
