package id.my.radityawan.music_course_mobile.features.lecturersadd;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.my.radityawan.music_course_mobile.api.APIClient;
import id.my.radityawan.music_course_mobile.api.LecturerApi;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerRequest;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerResponse;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturersResponse;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LecturersAddViewModel extends ViewModel {
    public LecturersAddViewModel() {
        disposable = new CompositeDisposable();
    }

    private CompositeDisposable disposable;

    private final MutableLiveData<Lecturer> lecturer = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    LiveData<Lecturer> getLecturer() {
        return lecturer;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    void fetchRepos(LecturerRequest request) {
        loading.setValue(true);

        LecturerApi lecturerApi = APIClient.getClient().create(LecturerApi.class);

        disposable.add(lecturerApi.createLecturer(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<LecturerResponse>() {
                    @Override
                    public void onSuccess(LecturerResponse value) {
                        repoLoadError.setValue(false);
                        lecturer.setValue(value.data);
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