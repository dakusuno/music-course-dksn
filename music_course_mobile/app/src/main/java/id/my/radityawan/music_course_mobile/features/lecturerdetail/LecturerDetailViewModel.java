package id.my.radityawan.music_course_mobile.features.lecturerdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.my.radityawan.music_course_mobile.api.APIClient;
import id.my.radityawan.music_course_mobile.api.LecturerApi;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerDeleteResponse;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerRequest;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerResponse;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LecturerDetailViewModel extends ViewModel {

    public LecturerDetailViewModel() {
        disposable = new CompositeDisposable();
    }

    private CompositeDisposable disposable;

    private final MutableLiveData<Lecturer> lecturerUpdate = new MutableLiveData<>();

    private final MutableLiveData<Boolean> lecturerDelete = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();

    LiveData<Lecturer> getLecturerUpdate() {
        return lecturerUpdate;
    }

    LiveData<Boolean> getLecturerDelete() {
        return lecturerDelete;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    void setLecturerUpdate(LecturerRequest request, Integer id) {
        loading.setValue(true);

        LecturerApi lecturerApi = APIClient.getClient().create(LecturerApi.class);

        disposable.add(lecturerApi.updateLecturer(request, id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<LecturerResponse>() {
                    @Override
                    public void onSuccess(LecturerResponse value) {
                        repoLoadError.setValue(false);
                        lecturerUpdate.setValue(value.data);
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

        LecturerApi lecturerApi = APIClient.getClient().create(LecturerApi.class);

        disposable.add(lecturerApi.deleteLecturer(id).subscribeOn(Schedulers.io())
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