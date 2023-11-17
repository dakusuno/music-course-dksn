package id.my.radityawan.music_course_mobile.model.lecturer;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LecturersResponse {
    @SerializedName("data")
    public List<Lecturer> data = new ArrayList<>();
}
