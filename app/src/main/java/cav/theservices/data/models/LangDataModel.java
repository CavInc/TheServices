package cav.theservices.data.models;

/**
 *  Модель для языковых ресурсов
 */
public class LangDataModel {
    private int mLang;
    private String mTitle;
    private String mBody;

    public LangDataModel(int lang, String nTitle, String body) {
        mLang = lang;
        this.mTitle = nTitle;
        mBody = body;
    }

    public int getLang() {
        return mLang;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
        /* Удостоверимся, что ссылки имеют тот же самый тип */
        if(!(getClass() == obj.getClass())) {
            return false;
        } else {
            LangDataModel tmp = (LangDataModel) obj;
            if (tmp.getLang() == this.mLang) {
                return true;
            }
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + mLang+mTitle.hashCode();
        return result;
    }


}