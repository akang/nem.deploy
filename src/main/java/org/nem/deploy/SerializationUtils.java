package org.nem.deploy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.nem.core.serialization.Deserializer;

public class SerializationUtils
{
    public static Constructor<?> getConstructor(Class<?> clazz)
    {
        try
        {
            return clazz.getConstructor(new Class[] { Deserializer.class }); } catch (NoSuchMethodException e) {
        }
        return null;
    }

    public static Object createInstance(Class<?> aClass, Deserializer deserializer)
    {
        try
        {
            Constructor constructor = getConstructor(aClass);
            if (null == constructor) {
                throw new UnsupportedOperationException("could not find compatible constructor");
            }

            try {
                return constructor.newInstance(new Object[] { deserializer });
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (java.lang.IllegalAccessException e) {
            if ((e.getCause() instanceof RuntimeException)) {
                throw ((RuntimeException)e.getCause());
            }
        }
        throw new UnsupportedOperationException("could not instantiate object");
    }
}