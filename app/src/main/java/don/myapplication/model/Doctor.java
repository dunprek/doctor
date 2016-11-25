package don.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doctor {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("speciality")
    @Expose
    private String speciality;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("photo")
    @Expose
    private String photo;


    public Doctor(Integer id, String name, String speciality
            , String area, String currency, Integer rate, String photo) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.area = area;
        this.currency = currency;
        this.rate = rate;
        this.photo = photo;

    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The speciality
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * @param speciality The speciality
     */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    /**
     * @return The area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return The rate
     */
    public Integer getRate() {
        return rate;
    }

    /**
     * @param rate The rate
     */
    public void setRate(Integer rate) {
        this.rate = rate;
    }

    /**
     * @return The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}




