package id.my.radityawan.music_course_mobile.features.lecturersadd;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import id.my.radityawan.music_course_mobile.R;
import id.my.radityawan.music_course_mobile.databinding.FragmentLecturersAddBinding;
import id.my.radityawan.music_course_mobile.databinding.FragmentLecturersBinding;
import id.my.radityawan.music_course_mobile.features.lecturers.LecturersFragment;
import id.my.radityawan.music_course_mobile.features.lecturers.LecturersViewModel;
import id.my.radityawan.music_course_mobile.features.lecturers.MyLecturersRecyclerViewAdapter;
import id.my.radityawan.music_course_mobile.model.lecturer.Course;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerRequest;

public class LecturersAddFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private LecturersAddViewModel mViewModel;

    private FragmentLecturersAddBinding binding;

    private Course course;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLecturersAddBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(LecturersAddViewModel.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                binding.getRoot().getContext(),
                R.array.courses,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(this);

        binding.confirm.setOnClickListener(view -> {
            String firstName = Objects.requireNonNull(binding.firstName.getEditText()).getText().toString();

            String lastName = Objects.requireNonNull(binding.lastName.getEditText()).getText().toString();

            LecturerRequest request = new LecturerRequest(firstName, lastName, course);

            mViewModel.fetchRepos(request);
        });

        mViewModel.getLecturer().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if (value) {
                    NavHostFragment.findNavController(LecturersAddFragment.this).popBackStack();
                    if(Boolean.FALSE.equals(mViewModel.getError().getValue())){
                        Snackbar.make(binding.getRoot(), "Data Berhasil Disimpan", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        course = Course.values()[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}