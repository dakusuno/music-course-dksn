package id.my.radityawan.music_course_mobile.model.schedule;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import id.my.radityawan.music_course_mobile.model.lecturer.Course;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;

public class ScheduleRequest  {
    @SerializedName("start_schedule")
    public Date startSchedule;
    @SerializedName("end_schedule")
    public Date endSchedule;
    @SerializedName("lecturer")
    public int lecturerId;

    public ScheduleRequest(Date startSchedule, Date endSchedule, int lecturerId) {
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
        this.lecturerId = lecturerId;
    }
}