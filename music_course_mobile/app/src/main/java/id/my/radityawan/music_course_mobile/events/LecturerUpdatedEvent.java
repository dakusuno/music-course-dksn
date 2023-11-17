package id.my.radityawan.music_course_mobile.events;

import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;

public class LecturerUpdatedEvent {
    public LecturerUpdatedEvent(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Lecturer lecturer;
}