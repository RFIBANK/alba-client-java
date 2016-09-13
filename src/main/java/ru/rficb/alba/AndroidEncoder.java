package ru.rficb.alba;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AndroidEncoder extends Base64Encoder {
    private Method encodeToStringMethod;
    private int defaultFlags;
    private boolean isLoaded = false;

    @Override
    public String encode(byte[] bytes) throws AlbaFatalError {

        if(!isLoaded) {
            Class base64Class;
            try {
                base64Class = Class.forName("android.util.Base64");
            } catch (ClassNotFoundException e) {
                throw new AlbaFatalError("Can't load class android.util.Base64");
            }

            try {
                encodeToStringMethod = base64Class.getDeclaredMethod("encodeToString", byte[].class, int.class);
            } catch (NoSuchMethodException e) {
                throw new AlbaFatalError("Class android.util.Base64 haven't method encodeToString 5");
            }

            try {
                defaultFlags = base64Class.getDeclaredField("DEFAULT").getInt(null);
            } catch (IllegalAccessException e) {
                throw new AlbaFatalError("Can't gain access to android.util.Base64.DEFAULT");
            } catch (NoSuchFieldException e) {
                throw new AlbaFatalError("The field android.util.Base64.DEFAULT doesn't exists");
            }
            isLoaded = true;
        }

        try {
            return (String)encodeToStringMethod.invoke(null, bytes, defaultFlags);
        } catch (IllegalAccessException e) {
            throw new AlbaFatalError("Can't gain access to android.util.Base64.encodeToString");
        } catch (InvocationTargetException e) {
            throw new AlbaFatalError("Can't invoke android.util.Base64.encodeToString");
        }
    }

    @Override
    public String getType()
    {
        return "AndroidEncoder";
    }

    public static boolean isPresent() {
        try {
            Class.forName("android.util.Base64");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
