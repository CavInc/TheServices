package cav.theservices.data.models;

public class ServiceClientEditModel {
    private int mId;
    private String mTitle;
    private String mBody;
    private String mScreen;
    private Float mPrice;
    private String mLang;
    private String mImage;

    public ServiceClientEditModel(int id, String title, String body, String screen, Float price, String lang, String image) {
        mId = id;
        mTitle = title;
        mBody = body;
        mScreen = screen;
        mPrice = price;
        mLang = lang;
        mImage = image;
    }

    public ServiceClientEditModel(int id, String title, String body, String screen, Float price, String image) {
        mId = id;
        mTitle = title;
        mBody = body;
        mScreen = screen;
        mPrice = price;
        mImage = image;
    }

    public ServiceClientEditModel(int id, String title, String screen, String lang) {
        mId = id;
        mTitle = title;
        mScreen = screen;
        mLang = lang;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public String getScreen() {
        return mScreen;
    }

    public Float getPrice() {
        return mPrice;
    }

    public String getLang() {
        return mLang;
    }

    public String getImage() {
        return mImage;
    }
}