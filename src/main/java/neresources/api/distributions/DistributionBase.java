package neresources.api.distributions;

import neresources.api.utils.DistributionHelpers;
import neresources.api.messages.utils.MessageKeys;
import net.minecraft.nbt.NBTTagCompound;

public abstract class DistributionBase
{
    private float[] distribution;
    protected int bestHeight;

    public DistributionBase(float[] distribution)
    {
        this.distribution = distribution;
    }

    public float[] getDistribution()
    {
        return distribution;
    }

    public int getBestHeight()
    {
        return bestHeight;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound result)
    {
        result.setIntArray(MessageKeys.distribution, DistributionHelpers.getIntArray(distribution));
        result.setInteger(MessageKeys.bestHeight, bestHeight);
        return result;
    }
}
