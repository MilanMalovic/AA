package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Beer implements Parcelable {


    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("image_url")
    private String avatar;
    @SerializedName("brewers_tips")
    private String tips;

    public Beer(String name, String description, String avatar, String tips) {
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.tips = tips;
    }

    protected Beer(Parcel in) {
        name = in.readString();
        description = in.readString();
        avatar = in.readString();
        tips = in.readString();
    }

    public static final Creator<Beer> CREATOR = new Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getTips() {
        return tips;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(avatar);
        dest.writeString(tips);
    }
}
