package neresources.api.distributions;

import neresources.api.utils.DistributionHelpers;

public class DistributionCustom extends DistributionBase
{

    public DistributionCustom(float[] distribution)
    {
        super(distribution);
        this.bestHeight = DistributionHelpers.calculateMeanLevel(this.getDistribution(), distribution.length / 2, 0, 100F);
    }

    public DistributionCustom(float[] distribution, int bestHeight)
    {
        super(distribution);
        this.bestHeight = bestHeight;
    }

}
