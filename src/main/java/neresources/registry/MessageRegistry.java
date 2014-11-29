package neresources.registry;

import neresources.api.messages.Message;
import neresources.api.messages.ModifyMessage;
import neresources.api.messages.RemoveMessage;

import java.util.LinkedHashSet;
import java.util.Set;

public class MessageRegistry
{
    public static Set<Message> registerMessages = new LinkedHashSet<Message>();
    public static Set<ModifyMessage> modifyMessages = new LinkedHashSet<ModifyMessage>();
    public static Set<RemoveMessage> removeMessages = new LinkedHashSet<RemoveMessage>();
}
