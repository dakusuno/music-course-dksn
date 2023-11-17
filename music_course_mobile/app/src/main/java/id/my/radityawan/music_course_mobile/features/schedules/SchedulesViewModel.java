package id.my.radityawan.music_course_mobile.features.schedules;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.my.radityawan.music_course_mobile.api.APIClient;
import id.my.radityawan.music_course_mobile.api.ScheduleApi;
import id.my.radityawan.music_course_mobile.model.schedule.Schedule;
import id.my.radityawan.music_course_mobile.model.schedule.SchedulesResponse;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SchedulesViewModel extends ViewModel {

    public SchedulesViewModel() {
        disposable = new CompositeDisposable();
        createLecturer();
    }

    private CompositeDisposable disposable;

    private final MutableLiveData<List<Schedule>> schedules = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    LiveData<List<Schedule>> getSchedules() {
        if (schedules == null) {
            return new MutableLiveData<List<Schedule>>();
        }
        return schedules;

    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    private void createLecturer() {
        loading.setValue(true);

        ScheduleApi lecturerApi = APIClient.getClient().create(ScheduleApi.class);

        disposable.add(lecturerApi.getSchedules().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<SchedulesResponse>() {
                    @Override
                    public void onSuccess(SchedulesResponse value) {
                        repoLoadError.setValue(false);
                        schedules.setValue(value.data);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
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