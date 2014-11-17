package neresources.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import neresources.api.entry.IOreEntry;
import neresources.api.distributions.DistributionBase;
import neresources.api.utils.KeyGen;
import neresources.utils.ColorHelper;
import net.minecraft.block.BlockOre;
import net.minecraft.item.ItemStack;

public class OreEntry implements IOreEntry
{

    private ItemStack[] matchStacks;
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
        matchStacks = new ItemStack[1+drops.length];
        matchStacks[0] = ore;
        int i=1;
        for (ItemStack drop:drops)
            if (drop != null) matchStacks[i++] = drop;
        this.distribution = distribution;
        this.colour = colour;
    }

    @Override
    public ItemStack[] getOreMatches() {
        return matchStacks;
    }

    @Override
    public DistributionBase getDistribution(ItemStack itemStack) {
        return distribution;
    }

    @Override
    public ItemStack getOre(ItemStack itemStack) {
        return matchStacks[0];
    }

    @Override
    public int getColour(ItemStack itemStack) {
        return colour;
    }

    @Override
    public boolean silkTouch(ItemStack itemStack)
    {
        return matchStacks.length > 1 && BlockOre.getBlockFromItem(itemStack.getItem()) instanceof BlockOre;
    }

    @Override
    public String getKey()
    {
        return KeyGen.getKey(matchStacks[0]);
    }

}
