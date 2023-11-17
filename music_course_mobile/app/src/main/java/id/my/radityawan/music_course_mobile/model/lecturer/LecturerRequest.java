package id.my.radityawan.music_course_mobile.model.lecturer;

import com.google.gson.annotations.SerializedName;

public class LecturerRequest {
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("course")
    public Course course;

    public LecturerRequest(String firstName, String lastName, Course course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }
}
