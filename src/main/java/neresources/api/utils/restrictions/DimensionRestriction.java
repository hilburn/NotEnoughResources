package neresources.api.utils.restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DimensionRestriction
{
    public static final DimensionRestriction OVERWORLD = new DimensionRestriction(0);
    public static final DimensionRestriction NETHER = new DimensionRestriction(-1);
    public static final DimensionRestriction END = new DimensionRestriction(1);
    public static final DimensionRestriction NONE = new DimensionRestriction();

    private int min;
    private int max;
    private Type type;

    public DimensionRestriction(int dim)
    {
        this(dim,dim);
    }

    public DimensionRestriction(Type type, int dim)
    {
        this(type, dim, dim);
    }

    public DimensionRestriction(int minDim, int maxDim)
    {
        this(Type.WHITELIST, minDim, maxDim);
    }

    public DimensionRestriction(Type type, int minDim, int maxDim)
    {
        this.type = type;
        this.min = minDim;
        this.max = maxDim;
    }

    public DimensionRestriction()
    {
        this.type = Type.NONE;
    }

    public List<Integer> getValidDimensions(List<Integer> dimensions)
    {
        if (type == Type.NONE) return dimensions;
        List<Integer> result = new ArrayList<Integer>();
        for (int dimension:dimensions)
        {
            if (dimension>=min == (type == Type.WHITELIST) == dimension<=max) result.add(dimension);
        }
        return result;
    }
}
