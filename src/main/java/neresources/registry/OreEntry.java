package neresources.registry;

import neresources.api.distributions.DistributionBase;
import neresources.utils.ColorHelper;
import net.minecraft.block.BlockOre;
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
        this(ore, distribution, ColorHelper.BLACK, new ItemStack[0]);
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, int colour)
    {
        this(ore, distribution, colour, new ItemStack[0]);
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, ItemStack... drops)
    {
        this(ore, distribution, ColorHelper.BLACK, drops);
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, int colour, ItemStack... drops)
    {
        matchStacks.add(ore);
        int i=1;
        for (ItemStack drop:drops)
            if (drop != null) matchStacks.add(drop);
        this.distribution = distribution;
        this.colour = colour;
    }

    public ItemStack[] getOreMatches() {
        return matchStacks.toArray(new ItemStack[matchStacks.size()]);
    }

    public DistributionBase getDistribution() {
        return distribution;
    }

    public ItemStack getOre() {
        return matchStacks.get(0);
    }

    public int getColour() {
        return colour;
    }

    public boolean silkTouch(ItemStack itemStack)
    {
        return matchStacks.size() > 1 && BlockOre.getBlockFromItem(itemStack.getItem()) instanceof BlockOre;
    }
}
