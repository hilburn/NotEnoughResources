package neresources.api;

import java.util.LinkedHashSet;
import java.util.Set;

public class NEResourcesAPI
{
    private static Set<Object> entryRegistry = new LinkedHashSet<Object>();

    /**
     * Adds {@link java.lang.Object} to the registry - if it does not implement an API interface nothing will happen.
     *
     * @param entry the to add entry
     * @return true if succeeded
     */
    public static boolean registerEntry(Object entry)
    {
        try
        {
            entryRegistry.add(entry);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * @return get the registry
     */
    public static Set<Object> getEntryRegistry()
    {
        return entryRegistry;
    }
}
