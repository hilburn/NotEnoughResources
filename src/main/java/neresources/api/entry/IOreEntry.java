package neresources.api.entry;

import neresources.api.distributions.DistributionBase;
import net.minecraft.item.ItemStack;

public interface IOreEntry
{
    /**
     * @return an array of all possible drops from this IOreEntry
     */
    public ItemStack[] getOreMatches();

    /**
     * @return the distribution of the ore over the Y axis
     */
    public DistributionBase getDistribution(ItemStack itemStack);

    /**
     * @return the ItemStack of the ore to be rendered in NEI - this is to allow you to define eg (Lapis ore block displays for Lapis Lazuli).
     */
    public ItemStack getOre(ItemStack itemStack);

    /**
     * @return colour to render this ore on the graph
     * @return null is fine
     */
    public int getColour(ItemStack itemStack);

    /**
     * @return true for if the ore needs to be silk touched to get a certain output eg. (Diamond Ore Blocks from Diamond Ore Blocks)
     */
    public boolean needSilkTouch(ItemStack itemStack);
}
