package neresources.utils;

public enum DeObfMappings
{
    numberOfBlocks("numberOfBlocks", "field_76541_b"),
    itemWeight("itemWeight", "field_76292_a");

    private String deObfname;
    private String obfname;

    private DeObfMappings(String deObfname, String obfName)
    {
        this.deObfname = deObfname;
        this.obfname = obfName;
    }

    public String getFieldName()
    {
        return ReflectionHelper.isObf ? obfname : deObfname;
    }
}
