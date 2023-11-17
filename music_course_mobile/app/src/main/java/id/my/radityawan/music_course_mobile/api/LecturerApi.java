package id.my.radityawan.music_course_mobile.api;

import id.my.radityawan.music_course_mobile.model.lecturer.LecturerDeleteResponse;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerRequest;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerResponse;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturersResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LecturerApi {

    @GET("lecturer/")
    Single<LecturersResponse> getLecturers();

    @POST("lecturer/")
    Single<LecturerResponse> createLecturer(@Body LecturerRequest request);

    @GET("lecturer/{lecturer}/")
    Single<LecturerResponse> getLecturerById(@Path("lecturer") Integer lecturerId);

    @PUT("lecturer/{lecturer}/")
    Single<LecturerResponse> updateLecturer(@Body LecturerRequest request, @Path("lecturer") Integer lecturerId);

    @DELETE("lecturer/{lecturer}/")
    Single<LecturerDeleteResponse> deleteLecturer(@Path("lecturer") Integer lecturerId);
}
