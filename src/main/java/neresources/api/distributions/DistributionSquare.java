package neresources.api.distributions;

import neresources.api.utils.DistributionHelpers;

public class DistributionSquare extends DistributionBase
{
    public DistributionSquare(int minY, int maxY, double chance)
    {
        super(DistributionHelpers.getSquareDistribution(Math.max(minY, 0), Math.min(maxY, 255), chance));
        this.bestHeight = (minY + maxY) / 2;
    }

    public DistributionSquare(int min0, int minY, int maxY, int max0, double chance)
    {
        super(DistributionHelpers.getRoundedSquareDistribution(min0, minY, maxY, max0, chance));
        this.bestHeight = DistributionHelpers.calculateMeanLevel(this.getDistribution(),(minY+maxY)/2,0,100D);
    }
}
