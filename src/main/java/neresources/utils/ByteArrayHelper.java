package neresources.utils;

import neresources.entries.DungeonEntry;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;

public class ByteArrayHelper
{
    public static byte[] toByteArray(Object o)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            return baos.toByteArray();
        } catch (IOException e)
        {
            LogHelper.warn("IOException during send to client");
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromBytesArray(byte[] bytes)
    {
        try
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream oos = new ObjectInputStream(bais);
            return  (T)oos.readObject();
        } catch (IOException e)
        {
            LogHelper.warn("IOException during receive from server");
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            LogHelper.warn("ClassNotFoundException during receive from server");
            e.printStackTrace();
        }
        return null;
    }
}
