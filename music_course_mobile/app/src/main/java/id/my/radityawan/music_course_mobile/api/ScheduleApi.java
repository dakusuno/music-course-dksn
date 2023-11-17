package id.my.radityawan.music_course_mobile.api;

import id.my.radityawan.music_course_mobile.model.lecturer.LecturerDeleteResponse;
import id.my.radityawan.music_course_mobile.model.schedule.ScheduleRequest;
import id.my.radityawan.music_course_mobile.model.schedule.ScheduleResponse;
import id.my.radityawan.music_course_mobile.model.schedule.SchedulesResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ScheduleApi {
    @GET("schedule/")
    Single<SchedulesResponse> getSchedules();

    @POST("schedule/")
    Single<ScheduleResponse> createSchedule(@Body ScheduleRequest request);

    @GET("schedule/{schedule}/")
    Single<ScheduleResponse> getScheduleById(@Path("schedule") Integer lecturerId);

    @PUT("schedule/{schedule}/")
    Single<ScheduleResponse> updateSchedule(@Body ScheduleRequest request, @Path("schedule") Integer lecturerId);

    @DELETE("schedule/{schedule}/")
    Single<LecturerDeleteResponse> deleteSchedule(@Path("schedule") Integer lecturerId);
}
