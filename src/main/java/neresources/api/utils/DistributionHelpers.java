package neresources.api.utils;

public class DistributionHelpers
{
    public static final float PI = 3.14159265359F;

    /**
     * @param midY the top, middle of the triangle
     * @param range length of the sides
     * @param maxChance chance at the top
     * @return an array of 256 doubles in triangular distribution
     */
    public static double[] getTriangularDistribution(int midY, int range, double maxChance)
    {
        double[] triangle = new double[range * 2 + 1];
        double modChance = maxChance / (range+1);
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

    /**
     * @param minY first occurrence
     * @param maxY last occurrence
     * @param chance the chance
     * @return an array of 256 doubles in square distribution
     */
    public static double[] getSquareDistribution(int minY, int maxY, double chance)
    {
        double[] result = new double[256];
        for (int i = minY; i <= maxY; i++)
            result[i] = chance;
        return result;
    }

    /**
     * @param min0 start of the ramp
     * @param minY end of the ramp up
     * @param maxY start of the ramp down
     * @param max0 end of ramp down
     * @param chance the chance at the top
     * @return an array of 256 doubles in square distribution
     */
    public static double[] getRoundedSquareDistribution(int min0, int minY, int maxY, int max0, double chance)
    {
        double[] result = new double[256];
        addDistribution(result, getRampDistribution(min0, minY, chance), min0);
        addDistribution(result, getSquareDistribution(minY, maxY, chance));
        addDistribution(result, getRampDistribution(max0, maxY, chance), maxY);
        return result;
    }

    public static double[] getUnderwaterDistribution(double chance)
    {
        double[] result = getTriangularDistribution(47,8,chance/7);
        addDistribution(result,getRampDistribution(57,62,chance),57);
        result[62] = chance;
        addDistribution(result,getTriangularDistribution(55,4,chance/3));
        return result;
    }

    /**
     * @param minY first occurrence
     * @param maxY last occurrence
     * @param maxChance chance at the top of the ramp
     * @return an array of doubles with length |maxY - minY| in ramp distribution
     */
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

    /**
     * @param base base distribution
     * @param add the to add distribution
     * @return the sum of both distributions
     */
    public static double[] addDistribution(double[] base, double[] add)
    {
        return addDistribution(base, add, 0);
    }

    /**
     * @param base base distribution
     * @param add the to add distribution
     * @param offset the first element from the base array to start adding to
     * @return the sum of both distributions
     */
    public static double[] addDistribution(double[] base, double[] add, int offset)
    {
        int addCount = 0;
        for (int i = offset; i < Math.min(base.length, add.length + offset); i++)
            base[i] += add[addCount++];
        return base;
    }

    /**
     * @param array
     * @return a reversed version of the given array
     */
    public static double[] reverse(double[] array)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[array.length - 1 - i] = array[i];
        }
        return result;
    }

    /**
     * @param distribution the target array
     * @param mid the middle of the array
     * @param oldMid the old middle
     * @param difference the difference
     * @return the mean level of the distribution
     */
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

    /**
     * @param array the to divide array
     * @param num the denominator
     * @return the divided array
     */
    public static double[] divideArray(double[] array, double num)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
            result[i]=array[i]/num;
        return result;
    }

    /**
     * @param array the to multiply array
     * @param num the multiplier
     * @return the divided array
     */
    public static double[] multiplyArray(double[] array, double num)
    {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
            result[i]=array[i]*num;
        return result;
    }

    public static double[] maxJoinArray(double[] array1, double[] array2)
    {
        double[] result = new double[array1.length];
        if (array1.length != array2.length) return result;
        for (int i = 0; i < array1.length; i++)
            result[i]=Math.max(array1[i],array2[i]);
        return result;
    }

    public static double sum(double[] distribution) {
        double result = 0;
        for (double val:distribution)
            result+=val;
        return result;
    }
}
