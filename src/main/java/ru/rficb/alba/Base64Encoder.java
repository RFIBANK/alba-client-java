package ru.rficb.alba;

public abstract class Base64Encoder {

    private static Base64Encoder instance;

    public static synchronized Base64Encoder getInstance()
    {
        if (instance == null)
        {
            instance = createEncoderInstance();
        }
        return instance;
    }

    private static Base64Encoder createEncoderInstance()
    {
        if (AndroidEncoder.isPresent())
        {
            return new AndroidEncoder();
        }
        else
        {
            return new DatatypeConverterEncoder();
        }
    }

    public static String type()
    {
        return getInstance().getType();
    }

    public abstract String encode(byte[] bytes) throws AlbaFatalError;

    public abstract String getType();
}
