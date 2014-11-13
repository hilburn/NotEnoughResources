package neiresources.api.distributions;

public class DistributionTriangular extends DistributionBase
{
    public DistributionTriangular(int midY, int range, double maxChance)
    {
        super(DistributionHelpers.getTriangularDistribution(midY,range,maxChance));
        this.bestHeight = midY;
    }
}
