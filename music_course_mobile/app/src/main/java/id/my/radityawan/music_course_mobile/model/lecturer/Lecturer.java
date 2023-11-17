package id.my.radityawan.music_course_mobile.model.lecturer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Lecturer implements Parcelable {
    @SerializedName("id")
    public Integer id;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("course")
    public Course course;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(this.course.name());
    }

    protected Lecturer(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.course = Course.valueOf(in.readString());
    }


    public static final Parcelable.Creator<Lecturer> CREATOR = new Parcelable.Creator<Lecturer>() {
        @Override
        public Lecturer createFromParcel(Parcel source) {
            return new Lecturer(source);
        }

        @Override
        public Lecturer[] newArray(int size) {
            return new Lecturer[size];
        }

    };
}
