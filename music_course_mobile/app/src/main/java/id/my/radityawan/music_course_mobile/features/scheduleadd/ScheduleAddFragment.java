package id.my.radityawan.music_course_mobile.features.scheduleadd;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import id.my.radityawan.music_course_mobile.R;
import id.my.radityawan.music_course_mobile.databinding.FragmentLecturersAddBinding;
import id.my.radityawan.music_course_mobile.databinding.FragmentScheduleAddBinding;
import id.my.radityawan.music_course_mobile.features.lecturers.LecturersFragment;
import id.my.radityawan.music_course_mobile.features.lecturersadd.LecturersAddFragment;
import id.my.radityawan.music_course_mobile.features.lecturersadd.LecturersAddViewModel;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerRequest;
import id.my.radityawan.music_course_mobile.model.schedule.Schedule;
import id.my.radityawan.music_course_mobile.model.schedule.ScheduleRequest;

public class ScheduleAddFragment extends Fragment {

    private ScheduleAddViewModel mViewModel;

    private Lecturer lecturerData;

    private FragmentScheduleAddBinding binding;

    private Date startDate;

    private Date endDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentScheduleAddBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(ScheduleAddViewModel.class);

        binding.lecturerID.setOnClickListener(view -> {
            NavHostFragment.findNavController(ScheduleAddFragment.this)
                    .navigate(R.id.action_scheduleAddFragment_to_lecturersFragment);
        });

        MutableLiveData<Lecturer> liveData = Objects.requireNonNull(NavHostFragment.findNavController(ScheduleAddFragment.this).getCurrentBackStackEntry())
                .getSavedStateHandle()
                .getLiveData("key");

        binding.startDate.setOnClickListener(view -> {
            Date startDateSelected = startDate == null ? new Date() : startDate;

            Calendar startCalendar = Calendar.getInstance();

            startCalendar.setTime(startDateSelected);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            startDate = new Date(year-1900, monthOfYear, dayOfMonth);

                            String dateFormat = "dd MMM yyyy, hh:mm:ss";

                            String formatStartSchedule = DateFormat.format(dateFormat, startDate).toString();

                            binding.startDate.setText(formatStartSchedule);
                        }
                    }, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DATE));
            datePickerDialog.show();
        });

        binding.endDate.setOnClickListener(view -> {
            Date endDateSelected = endDate == null ? new Date() : endDate;

            Calendar endCalendar = Calendar.getInstance();

            endCalendar.setTime(endDateSelected);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            endDate = new Date(year-1900, monthOfYear, dayOfMonth);

                            String dateFormat = "dd MMM yyyy, hh:mm:ss";

                            String formatStartSchedule = DateFormat.format(dateFormat, endDate).toString();

                            binding.endDate.setText(formatStartSchedule);
                        }
                    }, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DATE));
            datePickerDialog.show();
        });
        liveData.observe(getViewLifecycleOwner(), new Observer<Lecturer>() {
            @Override
            public void onChanged(Lecturer lecturer) {
                if (lecturer != null) {
                    lecturerData = lecturer;
                    binding.lecturerID.setText(lecturer.firstName);
                }
            }
        });

        binding.confirm.setOnClickListener(view -> {
            if(startDate == null || endDate == null || lecturerData == null){
                Snackbar.make(binding.getRoot(), "Data Belum Diisi", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                ScheduleRequest request = new ScheduleRequest(startDate, endDate, lecturerData.id);

                mViewModel.fetchRepos(request);
            }
        });

        mViewModel.getLecturer().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if (value) {
                    NavHostFragment.findNavController(ScheduleAddFragment.this).popBackStack();
                    if (Boolean.FALSE.equals(mViewModel.getError().getValue())) {
                        Snackbar.make(binding.getRoot(), "Data Berhasil Disimpan", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });


        return binding.getRoot();
    }
}