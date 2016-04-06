package ru.rficb.alba;


public class AlbaFatalError  extends Exception {
    private static final long serialVersionUID = -3979667104002126209L;
    AlbaErrorCode code;

    public AlbaErrorCode getCode() {
        return code;
    }

    public void setCode(AlbaErrorCode code) {
        this.code = code;
    }

    public AlbaFatalError(String s) {
        super(s);
        setCode(AlbaErrorCode.COMMON);
    }

    public AlbaFatalError(String s, AlbaErrorCode code) {
        super(s);
        this.code = code;
    }
}
