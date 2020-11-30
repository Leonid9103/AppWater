package ru.appwater.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.appwater.R;

public class ListAdapter extends RecyclerView.Adapter<ru.appwater.common.ListAdapter.ListViewHolder> {
    private List<WaterIntake> waterLog;

    public ListAdapter(List<WaterIntake> list) {
        this.waterLog = list;
    }

    @NonNull
    @Override
    public ru.appwater.common.ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ru.appwater.common.ListAdapter.ListViewHolder ListViewHolder = new ru.appwater.common.ListAdapter.ListViewHolder(view);
        return ListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.volume.setText(waterLog.get(position).getVolume());
        holder.date.setText(waterLog.get(position).getDate());
        holder.time.setText(waterLog.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return waterLog.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView volume;
        TextView date;
        TextView time;

        public ListViewHolder(View itemView) {
            super(itemView);

            volume = itemView.findViewById(R.id.volume);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
