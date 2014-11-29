package neresources.api.messages;

import neresources.api.utils.Priority;

public class ModifyMobMessage extends ModifyMessage
{
    public ModifyMobMessage(Priority addPriority, Priority removePriority)
    {
        super(addPriority, removePriority);
    }

    @Override
    public boolean isValid()
    {
        return false;
    }
}
