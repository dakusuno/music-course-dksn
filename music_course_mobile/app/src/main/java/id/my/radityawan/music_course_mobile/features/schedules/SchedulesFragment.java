package id.my.radityawan.music_course_mobile.features.schedules;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.my.radityawan.music_course_mobile.R;
import id.my.radityawan.music_course_mobile.databinding.FragmentSchedulesBinding;
import id.my.radityawan.music_course_mobile.model.schedule.Schedule;

/**
 * A fragment representing a list of Items.
 */
public class SchedulesFragment extends Fragment {
    private FragmentSchedulesBinding binding;

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

        SchedulesViewModel lecturersViewModel = new ViewModelProvider(this).get(SchedulesViewModel.class);

        Context context = binding.getRoot().getContext();

        RecyclerView recyclerView = binding.list;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        lecturersViewModel.getSchedules().observe(getViewLifecycleOwner(), new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                recyclerView.setAdapter(new MyScheduleRecyclerViewAdapter(schedules));
            }

        });
        // Set the adapter

        binding.fab.setOnClickListener(view -> {
            NavHostFragment.findNavController(SchedulesFragment.this)
                    .navigate(R.id.action_schedulesFragment_to_scheduleAddFragment  );
        });

        return binding.getRoot();
    }
}