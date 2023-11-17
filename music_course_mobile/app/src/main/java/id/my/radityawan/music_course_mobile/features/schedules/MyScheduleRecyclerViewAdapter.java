package id.my.radityawan.music_course_mobile.features.schedules;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import id.my.radityawan.music_course_mobile.databinding.FragmentSchedulesItemBinding;
import id.my.radityawan.music_course_mobile.features.lecturers.LecturersFragmentDirections;
import id.my.radityawan.music_course_mobile.features.schedules.SchedulesFragmentDirections;
import id.my.radityawan.music_course_mobile.model.schedule.Schedule;

import java.util.List;
import java.util.Objects;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyScheduleRecyclerViewAdapter extends RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder> {

    private final List<Schedule> mValues;

    public MyScheduleRecyclerViewAdapter(List<Schedule> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentSchedulesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String dateFormat = "dd MMM yyyy, hh:mm:ss";
        String formatStartSchedule = DateFormat.format(dateFormat, mValues.get(position).startSchedule).toString();
        String formatEndSchedule = DateFormat.format(dateFormat, mValues.get(position).endSchedule).toString();

        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).lecturer.firstName);
        holder.mStartDate.setText(formatStartSchedule);
        holder.mEndDate.setText(formatEndSchedule);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public final TextView mStartDate;
        public final TextView mEndDate;
        public Schedule mItem;

        public ViewHolder(FragmentSchedulesItemBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
            mStartDate = binding.startDate;
            mEndDate = binding.endDate;

            binding.getRoot().setOnClickListener(view -> {
                id.my.radityawan.music_course_mobile.features.schedules.SchedulesFragmentDirections.ActionSchedulesFragmentToScheduleDetailFragment action =
                        SchedulesFragmentDirections.actionSchedulesFragmentToScheduleDetailFragment(mItem);

                NavController controller = Navigation.findNavController(view);
                controller.navigate(action);
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}