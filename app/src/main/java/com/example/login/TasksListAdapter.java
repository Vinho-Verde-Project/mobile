package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TasksListAdapter extends ArrayAdapter<Tasks> {

    private Context mContext;
    int mResource;
    public TasksListAdapter(Context context, int resource, @NonNull ArrayList<Tasks> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Integer id = getItem(position).id;
        String startedAt = getItem(position).startedAt;
        String endedAt = getItem(position).endedAt;
        String status = getItem(position).status;

        Tasks task = new Tasks(id,startedAt,endedAt,status);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvStatus = convertView.findViewById(R.id.textView1);
        TextView tvStartedAt = convertView.findViewById(R.id.textView2);
        TextView tvEndedAt = convertView.findViewById(R.id.textView3);

        tvStatus.setText(status);
        tvStartedAt.setText(startedAt);
        tvEndedAt.setText(endedAt);

        return convertView;
    }
}
