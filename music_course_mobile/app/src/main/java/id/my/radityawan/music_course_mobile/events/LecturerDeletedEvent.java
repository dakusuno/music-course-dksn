package id.my.radityawan.music_course_mobile.events;

import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;

public class LecturerDeletedEvent {
    public LecturerDeletedEvent(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Lecturer lecturer;
}