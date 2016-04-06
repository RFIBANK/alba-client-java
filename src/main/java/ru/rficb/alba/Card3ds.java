package ru.rficb.alba;

/**
 * Параметры для 3DS запроса
 */
public class Card3ds {
    private String acsUrl;
    private String md;
    private String termUrl;
    private String paReq;

    public String getAcsUrl() {
        return acsUrl;
    }

    public void setAcsUrl(String acUrl) {
        this.acsUrl = acUrl;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getTermUrl() {
        return termUrl;
    }

    public void setTermUrl(String termUrl) {
        this.termUrl = termUrl;
    }

    public String getPaReq() {
        return paReq;
    }

    public void setPaReq(String paReq) {
        this.paReq = paReq;
    }

    public Card3ds(String acsUrl, String md, String paReq) {
        this.acsUrl = acsUrl;
        this.md = md;
        this.paReq = paReq;
    }
}
