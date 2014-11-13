package neresources.api.distributions;

public abstract class DistributionBase
{
    private double[] distribution;
    protected int bestHeight;

    public DistributionBase(double[] distribution)
    {
        this.distribution = distribution;
    }

    public double[] getDistribution()
    {
        return distribution;
    }

    public int getBestHeight()
    {
        return bestHeight;
    }
}
