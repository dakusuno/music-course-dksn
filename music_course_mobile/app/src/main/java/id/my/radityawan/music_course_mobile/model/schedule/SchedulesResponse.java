package id.my.radityawan.music_course_mobile.model.schedule;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchedulesResponse {
    @SerializedName("data")
    public List<Schedule> data;
}
