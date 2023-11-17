package id.my.radityawan.music_course_mobile.events;

import id.my.radityawan.music_course_mobile.model.schedule.Schedule;

public class ScheduleUpdatedEvent {
    public ScheduleUpdatedEvent(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule schedule;
}
