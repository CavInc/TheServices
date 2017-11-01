package cav.theservices.data.models;

/**
 *  Модель для языковых ресурсов
 */
public class LangDataModel {
    private int mLang;
    private String nTitle;
    private String mBody;

    public LangDataModel(int lang, String nTitle, String body) {
        mLang = lang;
        this.nTitle = nTitle;
        mBody = body;
    }

    public int getLang() {
        return mLang;
    }

    public String getnTitle() {
        return nTitle;
    }

    public String getBody() {
        return mBody;
    }
}