package id.my.radityawan.music_course_mobile.events;

import id.my.radityawan.music_course_mobile.model.schedule.Schedule;

public class ScheduleCreatedEvent {
    public ScheduleCreatedEvent(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule schedule;


}
