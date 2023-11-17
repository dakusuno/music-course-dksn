package id.my.radityawan.music_course_mobile.model.schedule;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;

public class Schedule implements Parcelable {
    @SerializedName("id")
    public Integer id;
    @SerializedName("start_schedule")
    public Date startSchedule;
    @SerializedName("end_schedule")
    public Date endSchedule;
    @SerializedName("lecturer")
    public Lecturer lecturer;

    public Schedule(Integer id, Date startSchedule, Date endSchedule, Lecturer lecturer) {
        this.id = id;
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
        this.lecturer = lecturer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeSerializable(startSchedule);
        parcel.writeSerializable(endSchedule);
        parcel.writeParcelable(this.lecturer, 1);
    }

    protected Schedule(Parcel in) {
        this.id = in.readInt();
        this.startSchedule = (Date) in.readSerializable();
        this.endSchedule = (Date) in.readSerializable();
        this.lecturer = in.readParcelable(Lecturer.class.getClassLoader());
    }


    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }

    };

    public Schedule copyLecturer(Lecturer lecturer) {
        return new Schedule(this.id, this.startSchedule, this.endSchedule, lecturer);
    }
}
