package neiresources.api.distributions;

public class DistributionHelpers
{
    public static double[] getNormalDistribution(int midY, int range)
    {
        return null;
    }

    public static double[] getTriangularDistribution(int midY, int range, double maxChance)
    {
        double[] triangle = new double[range*2+1];
        double modChance = maxChance/range;
        for (int i=0;i<=range;i++)
            for (int j=0;j<=range;j++)
                triangle[i+j]+=modChance;
        double[] result = new double[256];
        for (int i=0;i<triangle.length;i++)
        {
            int mapToPos = i+midY-range;
            if (mapToPos<0) continue;
            if (mapToPos==result.length) break;
            result[mapToPos]=triangle[i];
        }
        return result;
    }

}
