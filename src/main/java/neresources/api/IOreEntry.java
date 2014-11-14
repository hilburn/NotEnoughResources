package neresources.api;

import neresources.api.distributions.DistributionBase;
import net.minecraft.item.ItemStack;

public interface IOreEntry extends IBaseEntry{


    /**
     * @return an array of all ItemStacks to match against.
     */
    public ItemStack[] getOreMatches();

    /**
     * @return the distribution of the ore over the Y axis
     */
    public DistributionBase getDistribution(ItemStack itemStack);

    /**
     * @return the ItemStack of the ore to be rendered in NEI.
     */
    public ItemStack getOre(ItemStack itemStack);

    /**
     * @return colour to render this ore on the graph
     * @return null is fine
     */
    public int getColour(ItemStack itemStack);


    /**
     * @return true for if the ore needs to be silk touched.
     */
    public boolean silkTouch(ItemStack itemStack);
}
