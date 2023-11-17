package id.my.radityawan.music_course_mobile.features.schedules;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import id.my.radityawan.music_course_mobile.R;
import id.my.radityawan.music_course_mobile.databinding.FragmentSchedulesBinding;
import id.my.radityawan.music_course_mobile.events.LecturerCreatedEvent;
import id.my.radityawan.music_course_mobile.events.LecturerDeletedEvent;
import id.my.radityawan.music_course_mobile.events.LecturerUpdatedEvent;
import id.my.radityawan.music_course_mobile.events.ScheduleCreatedEvent;
import id.my.radityawan.music_course_mobile.events.ScheduleDeletedEvent;
import id.my.radityawan.music_course_mobile.events.ScheduleUpdatedEvent;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;
import id.my.radityawan.music_course_mobile.model.schedule.Schedule;

/**
 * A fragment representing a list of Items.
 */
public class SchedulesFragment extends Fragment {
    private FragmentSchedulesBinding binding;

    private SchedulesViewModel schedulesViewModel;

    @SuppressWarnings("unused")
    public static SchedulesFragment newInstance(int columnCount) {
        SchedulesFragment fragment = new SchedulesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSchedulesBinding.inflate(inflater, container, false);

        schedulesViewModel = new ViewModelProvider(this).get(SchedulesViewModel.class);

        Context context = binding.getRoot().getContext();

        RecyclerView recyclerView = binding.list;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        schedulesViewModel.getSchedules().observe(getViewLifecycleOwner(), new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                recyclerView.setAdapter(new MyScheduleRecyclerViewAdapter(schedules));
            }

        });
        // Set the adapter

        binding.fab.setOnClickListener(view -> {
            NavHostFragment.findNavController(SchedulesFragment.this)
                    .navigate(R.id.action_schedulesFragment_to_scheduleAddFragment);
        });

        return binding.getRoot();
    }

    @Subscribe
    public void scheduleAdded(ScheduleCreatedEvent scheduleCreated) {
        List<Schedule> latestData = schedulesViewModel.getSchedules().getValue();

        assert latestData != null;
        latestData.add(scheduleCreated.schedule);

        schedulesViewModel.updateListData(latestData);
    }

    @Subscribe
    public void scheduleUpdated(ScheduleUpdatedEvent scheduleUpdatedEvent) {
        List<Schedule> latestData = schedulesViewModel.getSchedules().getValue();

        assert latestData != null;
        List<Schedule> updatedData = latestData.stream().map(e -> Objects.equals(e.id, scheduleUpdatedEvent.schedule.id) ? scheduleUpdatedEvent.schedule : e).collect(Collectors.toList());

        schedulesViewModel.updateListData(updatedData);
    }

    @Subscribe
    public void scheduleDeleted(ScheduleDeletedEvent scheduleDeletedEvent) {
        List<Schedule> latestData = schedulesViewModel.getSchedules().getValue();

        assert latestData != null;

        List<Schedule> updatedData = latestData.stream().filter(e -> !Objects.equals(e.id, scheduleDeletedEvent.schedule.id)).collect(Collectors.toList());

        schedulesViewModel.updateListData(updatedData);
    }

    @Subscribe
    public void lecturerUpdated(LecturerUpdatedEvent lecturerUpdatedEvent) {
        List<Schedule> latestData = schedulesViewModel.getSchedules().getValue();

        assert latestData != null;
        List<Schedule> updatedData = latestData.stream().map(e -> Objects.equals(e.lecturer.id, lecturerUpdatedEvent.lecturer.id) ? e.copyLecturer(lecturerUpdatedEvent.lecturer) : e).collect(Collectors.toList());

        schedulesViewModel.updateListData(updatedData);
    }

    @Subscribe
    public void lecturerDeleted(LecturerDeletedEvent lecturerUpdatedEvent) {
        List<Schedule> latestData = schedulesViewModel.getSchedules().getValue();

        assert latestData != null;

        List<Schedule> updatedData = latestData.stream().filter(e -> !Objects.equals(e.lecturer.id, lecturerUpdatedEvent.lecturer.id)).collect(Collectors.toList());

        schedulesViewModel.updateListData(updatedData);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}