package neresources.api.utils.conditionals;

public class ExtendedConditionals extends Conditional
{
    Conditional conditional;
    String value;

    public ExtendedConditionals(Conditional conditional, String value)
    {
        this.conditional=conditional;
        this.value=value;
    }

    @Override
    public String toString()
    {
        return String.format(conditional.toString(), value);
    }
}
