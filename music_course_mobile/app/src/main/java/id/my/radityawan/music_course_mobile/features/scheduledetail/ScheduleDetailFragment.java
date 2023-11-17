package id.my.radityawan.music_course_mobile.features.scheduledetail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import id.my.radityawan.music_course_mobile.R;
import id.my.radityawan.music_course_mobile.databinding.FragmentScheduleDetailBinding;
import id.my.radityawan.music_course_mobile.events.ScheduleCreatedEvent;
import id.my.radityawan.music_course_mobile.events.ScheduleUpdatedEvent;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;
import id.my.radityawan.music_course_mobile.model.schedule.Schedule;
import id.my.radityawan.music_course_mobile.model.schedule.ScheduleRequest;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScheduleDetailFragment extends Fragment {
    private ScheduleDetailViewModel mViewModel;

    private FragmentScheduleDetailBinding binding;

    private Schedule schedule;

    private Lecturer lecturerData;

    private Date startDate;

    private Date endDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleDetailBinding.inflate(inflater, container, false);

        assert getArguments() != null;
        schedule = id.my.radityawan.music_course_mobile.features.scheduledetail.ScheduleDetailFragmentArgs.fromBundle(getArguments()).getSchedule();

        initTexts();

        binding.lecturerID.setText(lecturerData.firstName);
        binding.lecturerID.setText(lecturerData.firstName);

        mViewModel = new ViewModelProvider(this).get(ScheduleDetailViewModel.class);

        binding.lecturerID.setOnClickListener(view -> {
            NavHostFragment.findNavController(ScheduleDetailFragment.this)
                    .navigate(R.id.action_scheduleDetailFragment_to_lecturersFragment);
        });

        MutableLiveData<Lecturer> liveData = Objects.requireNonNull(NavHostFragment.findNavController(ScheduleDetailFragment.this).getCurrentBackStackEntry())
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

        binding.update.setOnClickListener(view -> {
            if(startDate == null || endDate == null || lecturerData == null){
                Snackbar.make(binding.getRoot(), "Data Belum Diisi", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                ScheduleRequest request = new ScheduleRequest(startDate, endDate, lecturerData.id);

                mViewModel.setScheduleUpdate(request,schedule.id);
            }
        });

        mViewModel.getScheduleUpdate().observe(getViewLifecycleOwner(), new Observer<Schedule>() {
            @Override
            public void onChanged(Schedule value) {
                if (value != null) {
                    EventBus.getDefault().post(new ScheduleUpdatedEvent(value));
                    NavHostFragment.findNavController(ScheduleDetailFragment.this).popBackStack();
                    if (Boolean.FALSE.equals(mViewModel.getError().getValue())) {
                        Snackbar.make(binding.getRoot(), "Data Berhasil Disimpan", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });

        binding.delete.setOnClickListener(view -> {
            mViewModel.setLecturerDelete(schedule.id);
        });

        mViewModel.getScheduleDelete().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if (value) {
                    NavHostFragment.findNavController(ScheduleDetailFragment.this).popBackStack();
                    if (Boolean.FALSE.equals(mViewModel.getError().getValue())) {
                        Snackbar.make(binding.getRoot(), "Data Berhasil Dihapus", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });


        return binding.getRoot();
    }

    private void initTexts(){
        startDate = schedule.startSchedule;

        endDate = schedule.endSchedule;

        lecturerData = schedule.lecturer;


        String dateFormat = "dd MMM yyyy, hh:mm:ss";

        String formatStartSchedule = DateFormat.format(dateFormat, startDate).toString();

        binding.startDate.setText(formatStartSchedule);

        String formatEndSchedule = DateFormat.format(dateFormat, endDate).toString();

        binding.endDate.setText(formatEndSchedule);

        binding.lecturerID.setText(lecturerData.firstName);
    }
}