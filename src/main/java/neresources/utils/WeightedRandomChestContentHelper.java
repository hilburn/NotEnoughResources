package neresources.utils;

import neresources.api.entry.IDungeonEntry;
import net.minecraft.util.WeightedRandomChestContent;

import java.util.Arrays;

public class WeightedRandomChestContentHelper
{
    /**
     * Sorts WeightedRandomChestContents based on dropChance
     * Uses merge sorting algorithm
     *
     * @param contents
     * @return
     */
    public static WeightedRandomChestContent[] sort(WeightedRandomChestContent[] contents, IDungeonEntry entry)
    {
        if (contents.length <= 1) return contents;

        int split = contents.length / 2;
        WeightedRandomChestContent[] left = subArray(contents, 0, split);
        WeightedRandomChestContent[] right = subArray(contents, split, contents.length);

        left = sort(left, entry);
        right = sort(right, entry);

        return merge(left, right, entry);
    }

    private static WeightedRandomChestContent[] merge(WeightedRandomChestContent[] left, WeightedRandomChestContent[] right, IDungeonEntry entry)
    {
        int length = left.length + right.length;
        WeightedRandomChestContent[] merged = new WeightedRandomChestContent[length];

        int i = 0;
        int li = 0;
        int ri = 0;
        while (i < length)
        {
            if ((li < left.length) && (ri < right.length))
            {
                if (DungeonHelper.getChance(entry, left[li]) >= DungeonHelper.getChance(entry, right[ri]))
                {
                    merged[i] = left[li];
                    i++;
                    li++;
                } else
                {
                    merged[i] = right[ri];
                    i++;
                    ri++;
                }
            } else
            {
                if (li >= left.length)
                {
                    while (ri < right.length)
                    {
                        merged[i] = right[ri];
                        i++;
                        ri++;
                    }
                }
                if (ri >= right.length)
                {
                    while (li < left.length)
                    {
                        merged[i] = left[li];
                        i++;
                        li++;
                    }
                }
            }
        }

        return merged;
    }

    private static WeightedRandomChestContent[] subArray(WeightedRandomChestContent[] array, int begin, int end)
    {
        return Arrays.copyOfRange(array, begin, end);
    }
}
