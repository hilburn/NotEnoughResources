package neresources.api.distributions;

public class DistributionHelpers
{
    public static double[] getNormalDistribution(int midY, int range)
    {
        return null;
    }

    public static double[] getTriangularDistribution(int midY, int range, double maxChance)
    {
        double[] triangle = new double[range * 2 + 1];
        double modChance = maxChance / range;
        for (int i = 0; i <= range; i++)
            for (int j = 0; j <= range; j++)
                triangle[i + j] += modChance;
        double[] result = new double[256];
        for (int i = 0; i < triangle.length; i++)
        {
            int mapToPos = i + midY - range;
            if (mapToPos < 0) continue;
            if (mapToPos == result.length) break;
            result[mapToPos] = triangle[i];
        }
        return result;
    }

    public static double[] getSquareDistribution(int minY, int maxY, double chance)
    {
        double[] result = new double[256];
        for (int i = minY; i <= maxY; i++)
            result[i] = chance;
        return result;
    }

    public static double[] getRoundedSquareDistribution(int min0, int minY, int maxY, int max0, double chance)
    {
        double[] result = new double[256];
        addDistribution(result, getRampDistribution(min0, minY, chance), min0);
        addDistribution(result, getSquareDistribution(minY, maxY, chance));
        addDistribution(result, getRampDistribution(max0, maxY, chance), maxY);
        return result;
    }

    public static double[] getRampDistribution(int minY, int maxY, double maxChance)
    {
        if (minY == maxY) return new double[0];
        if (minY > maxY) return reverse(getRampDistribution(maxY, minY, maxChance));

        int range = maxY - minY;
        double[] result = new double[range + 1];
        for (int i = 0; i < range; i++)
        {
            result[i] = (maxChance * (double) i) / range;
        }
        return result;
    }

    public static double[] addDistribution(double[] base, double[] add)
    {
        return addDistribution(base, add, 0);
    }

    public static double[] addDistribution(double[] base, double[] add, int offset)
    {
        int addCount = 0;
        for (int i = offset; i < Math.min(base.length, add.length + offset); i++)
            base[i] += add[addCount++];
        return base;
    }

    public static double[] reverse(double[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[array.length - 1 - i] = array[i];
        }
        return result;
    }

    public static int calculateMeanLevel(double[] distribution, int mid, int oldMid, double difference)
    {
        double totalUp = 0;
        double totalDown = 0;
        for (int i = 0; i < distribution.length; i++){
            if (i<mid) totalDown+=distribution[i];
            else totalUp+=distribution[i];
        }
        double newDifference = totalUp-totalDown;
        if (Math.abs(difference+newDifference)<=Math.abs(newDifference)) {
            if (Math.abs(newDifference) < Math.abs(difference))
                return mid;
            return oldMid;
        }
        if (newDifference>0) return calculateMeanLevel(distribution, mid+1,mid, newDifference);
        return calculateMeanLevel(distribution,mid-1,mid,newDifference);
    }

}
