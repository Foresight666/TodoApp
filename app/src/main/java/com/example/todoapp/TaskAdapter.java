package com.example.todoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    List<Task> list;
    DatabaseHelper db;
    Context context;

    public TaskAdapter(List<Task> list, DatabaseHelper db, Context context) {
        this.list = list;
        this.db = db;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTask;
        CheckBox cbDone;
        ImageButton btnDelete;

        public ViewHolder(View v) {
            super(v);
            tvTask = v.findViewById(R.id.tvTask);
            cbDone = v.findViewById(R.id.cbDone);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int position) {
        Task t = list.get(position);

        h.tvTask.setText(t.name);
        h.cbDone.setChecked(t.isDone == 1);

        h.cbDone.setOnClickListener(v ->
                db.updateTaskStatus(t.id, h.cbDone.isChecked() ? 1 : 0));

        h.btnDelete.setOnClickListener(v -> new AlertDialog.Builder(context)
                .setTitle("Hapus Task")
                .setMessage("Yakin mau hapus?")
                .setPositiveButton("Ya", (d, w) -> {
                    db.deleteTask(t.id);
                    list.remove(position);
                    notifyDataSetChanged();
                })
                .setNegativeButton("Batal", null)
                .show());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
