package neresources.api.distributions;

import neresources.api.utils.DistributionHelpers;

public class DistributionSquare extends DistributionBase
{
    /**
     * Creates pure square distribution
     * @param minY first occurrence
     * @param maxY last occurrence
     * @param chance chance for the distribution
     */
    public DistributionSquare(int minY, int maxY, double chance)
    {
        super(DistributionHelpers.getSquareDistribution(Math.max(minY, 0), Math.min(maxY, 255), chance));
        this.bestHeight = (minY + maxY) / 2;
    }

    /**
     * Creates rounded square distribution
     * @param min0 start of the ramp
     * @param minY end of the ramp up
     * @param maxY start of the ramp down
     * @param max0 end of ramp down
     * @param chance the chance at the top
     */
    public DistributionSquare(int min0, int minY, int maxY, int max0, double chance)
    {
        super(DistributionHelpers.getRoundedSquareDistribution(min0, minY, maxY, max0, chance));
        this.bestHeight = DistributionHelpers.calculateMeanLevel(this.getDistribution(),(minY+maxY)/2,0,100D);
    }
}
