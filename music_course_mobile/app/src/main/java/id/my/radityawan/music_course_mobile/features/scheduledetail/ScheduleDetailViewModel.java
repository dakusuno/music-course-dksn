package id.my.radityawan.music_course_mobile.features.scheduledetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.my.radityawan.music_course_mobile.api.APIClient;
import id.my.radityawan.music_course_mobile.api.ScheduleApi;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerDeleteResponse;
import id.my.radityawan.music_course_mobile.model.schedule.Schedule;
import id.my.radityawan.music_course_mobile.model.schedule.ScheduleRequest;
import id.my.radityawan.music_course_mobile.model.schedule.ScheduleResponse;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ScheduleDetailViewModel extends ViewModel {

    public ScheduleDetailViewModel() {
        disposable = new CompositeDisposable();
    }

    private CompositeDisposable disposable;

    private final MutableLiveData<Schedule> scheduleUpdate = new MutableLiveData<>();

    private final MutableLiveData<Boolean> lecturerDelete = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();

    LiveData<Schedule> getScheduleUpdate() {
        return scheduleUpdate;
    }

    LiveData<Boolean> getScheduleDelete() {
        return lecturerDelete;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    void setScheduleUpdate(ScheduleRequest request, Integer id) {
        loading.setValue(true);

        ScheduleApi scheduleApi = APIClient.getClient().create(ScheduleApi.class);

        disposable.add(scheduleApi.updateSchedule(request, id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<ScheduleResponse>() {
                    @Override
                    public void onSuccess(ScheduleResponse value) {
                        repoLoadError.setValue(false);
                        scheduleUpdate.setValue(value.data);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(false);
                        loading.setValue(false);
                    }
                }));
    }

    void setLecturerDelete(Integer id) {
        loading.setValue(true);

        ScheduleApi scheduleApi = APIClient.getClient().create(ScheduleApi.class);

        disposable.add(scheduleApi.deleteSchedule(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<LecturerDeleteResponse>() {
                    @Override
                    public void onSuccess(LecturerDeleteResponse value) {
                        repoLoadError.setValue(false);
                        lecturerDelete.setValue(true);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
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