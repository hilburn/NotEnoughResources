package neiresources.api.distributions;

public class DistributionSquare extends DistributionBase
{
    public DistributionSquare(int minY, int maxY, double chance)
    {
        super(DistributionHelpers.getSquareDistribution(Math.max(minY,0),Math.min(maxY,255),chance));
        this.bestHeight = (minY+maxY)/2;
    }

    public DistributionSquare(int min0, int minY, int maxY, int max0, double chance)
    {
        super(DistributionHelpers.getRoundedSquareDistribution(min0,minY,maxY,max0,chance));
        this.bestHeight = (minY+maxY)/2;
    }
}
