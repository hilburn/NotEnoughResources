package neresources.registry;

import neresources.api.distributions.DistributionBase;
import neresources.utils.ColorHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OreEntry
{

    private List<ItemStack> matchStacks = new ArrayList<ItemStack>();
    private DistributionBase distribution;
    private int colour;

    public OreEntry(ItemStack ore, DistributionBase distribution)
    {
        this(ore, distribution, ColorHelper.BLACK);
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, int colour)
    {
        matchStacks.add(ore);
        this.distribution = distribution;
        this.colour = colour;
    }

    public ItemStack[] getOreMatches() {
        return matchStacks.toArray(new ItemStack[matchStacks.size()]);
    }

    public DistributionBase getDistribution() {
        return distribution;
    }

    public int getColour() {
        return colour;
    }
}
