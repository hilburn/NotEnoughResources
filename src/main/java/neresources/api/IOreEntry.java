package neresources.api;

import neresources.api.distributions.DistributionBase;
import net.minecraft.item.ItemStack;

public interface IOreEntry {

    /**
     * @return the distribution of the ore over the Y axis
     */
    public DistributionBase getDistribution();

    /**
     * @return the ItemStack of the ore.
     */
    public ItemStack getOre();

    /**
     * @return any possible non-ore drops - eg. Diamonds from Diamond Ore
     * @return null is fine
     */
    public ItemStack[] getDrops();

    /**
     * @return colour to render this ore on the graph
     * @return null is fine
     */
    public int getColour();


    /**
     * @return true for if the ore needs to be silk touched.
     */
    public boolean silkTouch();

    public String getModName();
}
