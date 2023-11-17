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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import id.my.radityawan.music_course_mobile.R;
import id.my.radityawan.music_course_mobile.databinding.FragmentLecturersAddBinding;
import id.my.radityawan.music_course_mobile.databinding.FragmentScheduleAddBinding;
import id.my.radityawan.music_course_mobile.features.lecturers.LecturersFragment;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;
import id.my.radityawan.music_course_mobile.model.schedule.Schedule;

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

        binding.lecturerID.setOnClickListener(view -> {
            Log.d("aaaaaa", "vbbbbbbbbbb");
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
                            startDate = new Date(year, monthOfYear, dayOfMonth);

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
                            endDate = new Date(year, monthOfYear, dayOfMonth);

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



        return binding.getRoot();
    }
}