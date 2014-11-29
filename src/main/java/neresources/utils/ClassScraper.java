package neresources.utils;

import java.util.*;

public class ClassScraper
{

    public static Class[] getSuperInterfaces(Class[] childInterfaces)
    {

        List allInterfaces = new ArrayList();

        for (int i = 0; i < childInterfaces.length; i++)
        {
            allInterfaces.add(childInterfaces[i]);
            allInterfaces.addAll(
                    Arrays.asList(
                            getSuperInterfaces(childInterfaces[i].getInterfaces())));
        }

        return (Class[]) allInterfaces.toArray(new Class[allInterfaces.size()]);
    }

    public static Set getGeneralizations(Class classObject)
    {
        Set generalizations = new HashSet();

        generalizations.add(classObject);

        Class superClass = classObject.getSuperclass();
        if (superClass != null)
        {
            generalizations.addAll(getGeneralizations(superClass));
        }

        Class[] superInterfaces = classObject.getInterfaces();
        for (int i = 0; i < superInterfaces.length; i++)
        {
            Class superInterface = superInterfaces[i];
            generalizations.addAll(getGeneralizations(superInterface));
        }

        return generalizations;
    }

}
