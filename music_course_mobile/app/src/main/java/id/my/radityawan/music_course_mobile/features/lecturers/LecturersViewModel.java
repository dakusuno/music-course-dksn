package id.my.radityawan.music_course_mobile.features.lecturers;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.my.radityawan.music_course_mobile.api.APIClient;
import id.my.radityawan.music_course_mobile.api.LecturerApi;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturersResponse;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LecturersViewModel extends ViewModel {

    public LecturersViewModel() {
        disposable = new CompositeDisposable();
        createLecturer();
    }

    private CompositeDisposable disposable;

    private final MutableLiveData<List<Lecturer>> lecturers = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    LiveData<List<Lecturer>> getLecturers() {
        if (lecturers == null) {
            return new MutableLiveData<List<Lecturer>>();
        }
        return lecturers;

    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    private void createLecturer() {
        loading.setValue(true);

        LecturerApi lecturerApi = APIClient.getClient().create(LecturerApi.class);

        disposable.add(lecturerApi.getLecturers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<LecturersResponse>() {
                    @Override
                    public void onSuccess(LecturersResponse value) {
                        repoLoadError.setValue(false);
                        lecturers.setValue(value.data);
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