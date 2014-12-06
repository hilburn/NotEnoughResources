package neresources.api.utils.restrictions;

import java.util.*;

public class DimensionRegistry
{
    private static Map<BlockRestriction,Set<Integer>> registry = new HashMap<BlockRestriction, Set<Integer>>();
    private static Map<Integer, String> altDimensions = new TreeMap<Integer, String>();

    static
    {
        registerDimension(BlockRestriction.NETHER, -1, "Nether");
        registerDimension(BlockRestriction.STONE, 0, "Overworld");
        registerDimension(BlockRestriction.END, 1, "End");
    }

    public static void registerDimension(BlockRestriction block, int dim, String name)
    {
        Set<Integer> saved = registry.get(block);
        if (saved == null)
            saved = new TreeSet<Integer>();
        saved.add(dim);
        altDimensions.put(dim, name);
        registry.put(block,saved);
    }

    public static void registerDimension(BlockRestriction block, Integer... dims)
    {
        registerDimension(block,Arrays.asList(dims));
    }

    public static void registerDimension(BlockRestriction block, List<Integer> dims)
    {
        Set<Integer> saved = registry.get(block);
        if (saved == null)
            saved = new TreeSet<Integer>();
        saved.addAll(dims);
        for (Integer dim: dims)
            if (dim != null) altDimensions.put(dim, String.valueOf(dim));
        registry.put(block,saved);
    }

    public static Set<Integer> getDimensions(BlockRestriction block)
    {
        if (registry.containsKey(block)) return registry.get(block);
        return null;
    }

    public static Set<Integer> getAltDimensions()
    {
        return altDimensions.keySet();
    }

    public static String getDimensionName(int dim)
    {
        return altDimensions.containsKey(dim) ? altDimensions.get(dim) : null;
    }
}
