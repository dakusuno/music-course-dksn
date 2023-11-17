package id.my.radityawan.music_course_mobile.features.lecturers;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import id.my.radityawan.music_course_mobile.R;
import id.my.radityawan.music_course_mobile.databinding.LecturerItemBinding;
import id.my.radityawan.music_course_mobile.model.lecturer.Lecturer;

import java.util.List;
import java.util.Objects;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyLecturersRecyclerViewAdapter extends RecyclerView.Adapter<MyLecturersRecyclerViewAdapter.ViewHolder> {

    private final List<Lecturer> mValues;

    public MyLecturersRecyclerViewAdapter(List<Lecturer> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LecturerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id.toString());
        holder.mContentView.setText(mValues.get(position).firstName);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Lecturer mItem;

        public ViewHolder(LecturerItemBinding binding) {

            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            binding.update.setOnClickListener(view -> {
                LecturersFragmentDirections.ActionLecturersFragmentToLecturerDetailFragment action =
                        LecturersFragmentDirections.actionLecturersFragmentToLecturerDetailFragment(mItem);

                NavController controller = Navigation.findNavController(view);
                controller.navigate(action);
            });
            binding.getRoot().setOnClickListener(view -> {
                NavController controller = Navigation.findNavController(view);

                Objects.requireNonNull(controller.getPreviousBackStackEntry()).getSavedStateHandle().set("key", mItem);
                Objects.requireNonNull(controller.getPreviousBackStackEntry()).getSavedStateHandle().set("key", mItem);

                controller.popBackStack();
            });
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}