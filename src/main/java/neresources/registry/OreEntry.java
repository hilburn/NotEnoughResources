package neresources.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import neresources.api.IOreEntry;
import neresources.api.distributions.DistributionBase;
import neresources.utils.ColorHelper;
import net.minecraft.item.ItemStack;

public class OreEntry implements IOreEntry
{
    private String modName;
    private ItemStack ore;
    private ItemStack[] drops = null;
    private DistributionBase distribution;

    public OreEntry(ItemStack ore, DistributionBase distribution)
    {
        this.ore = ore;
        this.modName = GameRegistry.findUniqueIdentifierFor(ore.getItem()).modId;
        this.distribution = distribution;
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, ItemStack... drops)
    {
        this(ore,distribution);
        this.drops = drops;
    }

    public String getModName()
    {
        return modName;
    }

    @Override
    public DistributionBase getDistribution()
    {
        return distribution;
    }

    public ItemStack getOre()
    {
        return ore;
    }

    public ItemStack[] getDrops()
    {
        return drops;
    }

    @Override
    public int getColour()
    {
        return ColorHelper.GREEN;
    }

    @Override
    public boolean silkTouch()
    {
        return false;
    }
}
