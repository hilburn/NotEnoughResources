package neresources.api.utils.restrictions;

import neresources.api.messages.utils.MessageKeys;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class DimensionRestriction
{
    public static final DimensionRestriction OVERWORLD = new DimensionRestriction(0);
    public static final DimensionRestriction NETHER = new DimensionRestriction(-1);
    public static final DimensionRestriction END = new DimensionRestriction(1);
    public static final DimensionRestriction NONE = new DimensionRestriction();

    private int min;
    private int max;
    private Type type;

    public DimensionRestriction()
    {
        this.type = Type.NONE;
    }

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
        this.min = Math.min(minDim,maxDim);
        this.max = Math.max(maxDim,minDim);
    }

    public DimensionRestriction(NBTTagCompound tagCompound)
    {
        this.type = Type.values()[tagCompound.getByte(MessageKeys.type)];
        int minDim = tagCompound.getInteger(MessageKeys.min);
        int maxDim = tagCompound.getInteger(MessageKeys.max);
        this.min = Math.min(minDim,maxDim);
        this.max = Math.max(maxDim,minDim);
    }

    public String getValidDimensions(BlockRestriction blockRestriction)
    {
        Set<Integer> dimensions = DimensionRegistry.getDimensions(blockRestriction);
        if (dimensions!=null) return getDimensionString(dimensions);
        return getAltDimensionString(DimensionRegistry.getAltDimensions());
    }

    private Set<Integer> getValidDimensions(Set<Integer> dimensions)
    {
        if (type == Type.NONE) return dimensions;
        Set<Integer> result = new TreeSet<Integer>();
        for (int dimension:dimensions)
        {
            if (dimension>=min == (type == Type.WHITELIST) == dimension<=max) result.add(dimension);
        }
        return result;
    }

    private String getDimensionString(Set<Integer> dimensions)
    {
        return getString(getValidDimensions(dimensions));
    }

    private String getString(Set<Integer> set)
    {
        String result = "";
        int lastParsed = Integer.MIN_VALUE;
        Iterator<Integer> itr = set.iterator();
        while (itr.hasNext())
        {
            int dimension = itr.next();
            if (dimension==lastParsed+1)
            {
                if (!result.endsWith("-")) result+="-";
                if (!itr.hasNext()) result+=dimension;
            }
            else
            {
                if (result.endsWith("-")) result+=lastParsed;
                result += (result.length()>0?",":"") + dimension;
            }
            lastParsed = dimension;
        }
        return result;
    }

    private String getAltDimensionString(Set<Integer> dimensions)
    {

        Set<Integer> validDimensions = new TreeSet<Integer>();
        int dimMin = Integer.MAX_VALUE;
        int dimMax = Integer.MIN_VALUE;
        Iterator<Integer> itr = dimensions.iterator();
        while (itr.hasNext())
        {
            int dim  = itr.next();
            if (dim<dimMin) dimMin = dim;
            if (dim>dimMax) dimMax = dim;
        }
        for (int i = Math.min(min,dimMin)-1;i<=Math.max(max,dimMax)+1;i++)
            if (!dimensions.contains(i)) validDimensions.add(i);
        String result = getString(getValidDimensions(type!=Type.NONE?validDimensions:dimensions));
        if (result.isEmpty()) return "No Valid Spawn Dimensions";
        switch (type)
        {
            default:
                return result;
            case NONE:
                return "Not "+result;
            case BLACKLIST:
                return "<="+result+"=<";
        }
    }

    public NBTTagCompound writeToNBT()
    {
        return writeToNBT(new NBTTagCompound());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setInteger(MessageKeys.max,max);
        tagCompound.setInteger(MessageKeys.min,min);
        tagCompound.setByte(MessageKeys.type, (byte) type.ordinal());
        return tagCompound;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof DimensionRestriction)
        {
            DimensionRestriction other = (DimensionRestriction) obj;
            return other.min==min && other.max==max && other.type == type;
        }
        return false;
    }
}
