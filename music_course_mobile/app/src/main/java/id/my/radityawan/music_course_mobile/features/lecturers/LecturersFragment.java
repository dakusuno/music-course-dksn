package id.my.radityawan.music_course_mobile.features.lecturers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.List;

import id.my.radityawan.music_course_mobile.R;
import id.my.radityawan.music_course_mobile.databinding.FragmentLecturersBinding;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;

/**
 * A fragment representing a list of Items.
 */
public class LecturersFragment extends Fragment {


    private FragmentLecturersBinding binding;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LecturersFragment newInstance(int columnCount) {
        LecturersFragment fragment = new LecturersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLecturersBinding.inflate(inflater, container, false);

        LecturersViewModel lecturersViewModel = new ViewModelProvider(this).get(LecturersViewModel.class);

        Context context = binding.getRoot().getContext();

        RecyclerView recyclerView = binding.list;

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));

        lecturersViewModel.getLecturers().observe(getViewLifecycleOwner(),new Observer<List<Lecturer>>(){
            @Override
            public void onChanged(List<Lecturer> lecturers) {
                    recyclerView.setAdapter(new MyLecturersRecyclerViewAdapter(lecturers));
                }

        });
        // Set the adapter

        binding.fab.setOnClickListener(view -> {
            NavHostFragment.findNavController(LecturersFragment.this)
                    .navigate(R.id.action_lecturersFragment_to_lecturersAddFragment);
        });

        return binding.getRoot();
    }
}