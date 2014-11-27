package neresources.api.utils;

public class DistributionHelpers
{
    public static final float PI = 3.14159265359F;

    /**
     * @param midY      the top, middle of the triangle
     * @param range     length of the sides
     * @param maxChance chance at the top
     * @return an array of 256 floats in triangular distribution
     */
    public static float[] getTriangularDistribution(int midY, int range, float maxChance)
    {
        float[] triangle = new float[range * 2 + 1];
        float modChance = maxChance / (range + 1);
        for (int i = 0; i <= range; i++)
            for (int j = 0; j <= range; j++)
                triangle[i + j] += modChance;
        float[] result = new float[256];
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
     * @param minY   first occurrence
     * @param maxY   last occurrence
     * @param chance the chance
     * @return an array of 256 floats in square distribution
     */
    public static float[] getSquareDistribution(int minY, int maxY, float chance)
    {
        float[] result = new float[256];
        for (int i = minY; i <= maxY; i++)
            result[i] = chance;
        return result;
    }

    /**
     * @param min0   start of the ramp
     * @param minY   end of the ramp up
     * @param maxY   start of the ramp down
     * @param max0   end of ramp down
     * @param chance the chance at the top
     * @return an array of 256 floats in square distribution
     */
    public static float[] getRoundedSquareDistribution(int min0, int minY, int maxY, int max0, float chance)
    {
        float[] result = new float[256];
        addDistribution(result, getRampDistribution(min0, minY, chance), min0);
        addDistribution(result, getSquareDistribution(minY, maxY, chance));
        addDistribution(result, getRampDistribution(max0, maxY, chance), maxY);
        return result;
    }

    public static float[] getUnderwaterDistribution(float chance)
    {
        float[] result = getTriangularDistribution(47, 8, chance / 7);
        addDistribution(result, getRampDistribution(57, 62, chance), 57);
        result[62] = chance;
        addDistribution(result, getTriangularDistribution(55, 4, chance / 3));
        return result;
    }

    /**
     * @param minY      first occurrence
     * @param maxY      last occurrence
     * @param maxChance chance at the top of the ramp
     * @return an array of floats with length |maxY - minY| in ramp distribution
     */
    public static float[] getRampDistribution(int minY, int maxY, float maxChance)
    {
        if (minY == maxY) return new float[0];
        if (minY > maxY) return reverse(getRampDistribution(maxY, minY, maxChance));

        int range = maxY - minY;
        float[] result = new float[range + 1];
        for (int i = 0; i < range; i++)
        {
            result[i] = (maxChance * (float) i) / range;
        }
        return result;
    }

    public static float[] getOverworldSurfaceDistribution(float maxChance)
    {
        return getTriangularDistribution(69,5,maxChance);
    }

    /**
     * @param base base distribution
     * @param add  the to add distribution
     * @return the sum of both distributions
     */
    public static float[] addDistribution(float[] base, float[] add)
    {
        return addDistribution(base, add, 0);
    }

    /**
     * @param base   base distribution
     * @param add    the to add distribution
     * @param offset the first element from the base array to start adding to
     * @return the sum of both distributions
     */
    public static float[] addDistribution(float[] base, float[] add, int offset)
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
    public static float[] reverse(float[] array)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            result[array.length - 1 - i] = array[i];
        }
        return result;
    }

    /**
     * @param distribution the target array
     * @param mid          the middle of the array
     * @param oldMid       the old middle
     * @param difference   the difference
     * @return the mean level of the distribution
     */
    public static int calculateMeanLevel(float[] distribution, int mid, int oldMid, float difference)
    {
        float totalUp = 0;
        float totalDown = 0;
        for (int i = 0; i < distribution.length; i++)
        {
            if (i < mid) totalDown += distribution[i];
            else totalUp += distribution[i];
        }
        float newDifference = totalUp - totalDown;
        if (Math.abs(difference + newDifference) <= Math.abs(newDifference))
        {
            if (Math.abs(newDifference) < Math.abs(difference))
                return mid;
            return oldMid;
        }
        if (newDifference > 0) return calculateMeanLevel(distribution, mid + 1, mid, newDifference);
        return calculateMeanLevel(distribution, mid - 1, mid, newDifference);
    }

    /**
     * @param array the to divide array
     * @param num   the denominator
     * @return the divided array
     */
    public static float[] divideArray(float[] array, float num)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
            result[i] = array[i] / num;
        return result;
    }

    /**
     * @param array the to multiply array
     * @param num   the multiplier
     * @return the divided array
     */
    public static float[] multiplyArray(float[] array, float num)
    {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++)
            result[i] = array[i] * num;
        return result;
    }

    public static float[] maxJoinArray(float[] array1, float[] array2)
    {
        float[] result = new float[array1.length];
        if (array1.length != array2.length) return result;
        for (int i = 0; i < array1.length; i++)
            result[i] = Math.max(array1[i], array2[i]);
        return result;
    }

    public static float sum(float[] distribution)
    {
        float result = 0;
        for (float val : distribution)
            result += val;
        return result;
    }
}
