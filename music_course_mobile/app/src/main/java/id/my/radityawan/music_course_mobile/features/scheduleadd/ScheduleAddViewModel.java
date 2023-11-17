package id.my.radityawan.music_course_mobile.features.scheduleadd;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.my.radityawan.music_course_mobile.api.APIClient;
import id.my.radityawan.music_course_mobile.api.LecturerApi;
import id.my.radityawan.music_course_mobile.api.ScheduleApi;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerRequest;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerResponse;
import id.my.radityawan.music_course_mobile.model.schedule.Schedule;
import id.my.radityawan.music_course_mobile.model.schedule.ScheduleRequest;
import id.my.radityawan.music_course_mobile.model.schedule.ScheduleResponse;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ScheduleAddViewModel extends ViewModel {
    public ScheduleAddViewModel() {
        disposable = new CompositeDisposable();
    }

    private CompositeDisposable disposable;

    private final MutableLiveData<Schedule> lecturer = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    LiveData<Schedule> getLecturer() {
        return lecturer;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    void fetchRepos(ScheduleRequest request) {
        loading.setValue(true);

        ScheduleApi scheduleApi = APIClient.getClient().create(ScheduleApi.class);

        disposable.add(scheduleApi.createSchedule(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<ScheduleResponse>() {
                    @Override
                    public void onSuccess(ScheduleResponse value) {
                        Log.d("success", "aaaaaaaaaaa");
                        repoLoadError.setValue(false);
                        lecturer.setValue(value.data);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error cyin", e.toString());
                        repoLoadError.setValue(false);
                        loading.setValue(false);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}