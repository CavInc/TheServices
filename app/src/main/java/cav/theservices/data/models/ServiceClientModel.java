package cav.theservices.data.models;

/*
  модель для карточки клиента.
  заполняется выбранным языком
 */
public class ServiceClientModel {
    private int mId;
    private String mTitle;
    private String mBody;
    private String mScreen;
    private Float mPrice;

    public ServiceClientModel(int id, String title, String body, String screen, Float price) {
        mId = id;
        mTitle = title;
        mBody = body;
        mScreen = screen;
        mPrice = price;
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
}