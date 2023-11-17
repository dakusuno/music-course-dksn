package id.my.radityawan.music_course_mobile.features.lecturerdetail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import id.my.radityawan.music_course_mobile.R;
import id.my.radityawan.music_course_mobile.databinding.FragmentLecturerDetailBinding;
import id.my.radityawan.music_course_mobile.databinding.FragmentLecturersAddBinding;
import id.my.radityawan.music_course_mobile.features.lecturersadd.LecturersAddFragment;
import id.my.radityawan.music_course_mobile.features.lecturersadd.LecturersAddViewModel;
import id.my.radityawan.music_course_mobile.model.lecturer.Course;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;
import id.my.radityawan.music_course_mobile.model.lecturer.LecturerRequest;

public class LecturerDetailFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private LecturerDetailViewModel mViewModel;

    private FragmentLecturerDetailBinding binding;

    private Lecturer lecturer;

    private Course course;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLecturerDetailBinding.inflate(inflater, container, false);

        assert getArguments() != null;
        lecturer = LecturerDetailFragmentArgs.fromBundle(getArguments()).getLecturer();

        mViewModel = new ViewModelProvider(this).get(LecturerDetailViewModel.class);

        Objects.requireNonNull(binding.firstName.getEditText()).setText(lecturer.firstName);

        Objects.requireNonNull(binding.lastName.getEditText()).setText(lecturer.lastName);

        setupSpinner();

        binding.update.setOnClickListener(view -> {
            String firstName = Objects.requireNonNull(binding.firstName.getEditText()).getText().toString();

            String lastName = Objects.requireNonNull(binding.lastName.getEditText()).getText().toString();

            LecturerRequest request = new LecturerRequest(firstName, lastName, course);

            mViewModel.setLecturerUpdate(request, lecturer.id);
        });

        binding.delete.setOnClickListener(view -> {
            mViewModel.setLecturerDelete(lecturer.id);
        });

        mViewModel.getLecturerUpdate().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if (value) {
                    NavHostFragment.findNavController(LecturerDetailFragment.this).popBackStack();
                    if(Boolean.FALSE.equals(mViewModel.getError().getValue())){
                        Snackbar.make(binding.getRoot(), "Data Berhasil Disimpan", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else{
                        Snackbar.make(binding.getRoot(), "Terjadi Kesalahan", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });

        mViewModel.getLecturerDelete().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if (value) {
                    NavHostFragment.findNavController(LecturerDetailFragment.this).popBackStack();
                    if(Boolean.FALSE.equals(mViewModel.getError().getValue())){
                        Snackbar.make(binding.getRoot(), "Data Berhasil Dihapus", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else{
                        Snackbar.make(binding.getRoot(), "Terjadi Kesalahan", Snackbar.LENGTH_LONG)
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

    public void setupSpinner() {
        course = lecturer.course;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                binding.getRoot().getContext(),
                R.array.courses,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int courseIndex = lecturer.course.ordinal();

        binding.spinner.setAdapter(adapter);

        binding.spinner.setSelection(courseIndex);
    }
}