package neiresources.utils;

import java.lang.reflect.Field;

public class ReflectionHelper
{

    public static Integer getInt(Class clazz, String name, Object instance)
    {
        Integer result = null;
        try {
            Field getField = clazz.getDeclaredField(name);
            getField.setAccessible(true);
            result = getField.getInt(instance);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object getObject(Class clazz, String name, Object instance)
    {
        try {
            Field getField = clazz.getDeclaredField(name);
            getField.setAccessible(true);
            return getField.get(instance);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
