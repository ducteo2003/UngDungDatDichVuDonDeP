package com.example.happyhomes.NhanVien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happyhomes.DatabaseHelper;
import com.example.happyhomes.Model.Schedule;
import com.example.happyhomes.R;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter {

    private Context context;
    private List<Schedule> scheduleList;
    private LayoutInflater inflater;
    private long employeeId;

    public ScheduleAdapter(Context context, List<Schedule> scheduleList, long employeeId) {
        this.context = context;
        this.scheduleList = scheduleList;
        this.employeeId = employeeId;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return scheduleList.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_schedule, parent, false);
        }

        TextView txtStartTime = convertView.findViewById(R.id.txtStartTime);
        TextView txtLocation = convertView.findViewById(R.id.txtLocation);
        TextView txtStatus = convertView.findViewById(R.id.txtStatus);
        Button btnDki = convertView.findViewById(R.id.btnDki);

        Schedule schedule = scheduleList.get(position);

        txtStartTime.setText(schedule.getStartTimeString());
        txtLocation.setText(schedule.getLocation());
        txtStatus.setText(schedule.getStatus());

        if ("Đã xác nhận".equals(schedule.getStatus()) || "Hoàn tất".equals(schedule.getStatus())) {
            btnDki.setVisibility(View.GONE);
        } else {
            btnDki.setVisibility(View.VISIBLE);
        }

        btnDki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                boolean success = dbHelper.registerSchedule(employeeId, schedule.getScheduleId());

                if (success) {
                    ((ScheduleActivity) context).refreshData();
                } else {
                    Toast.makeText(context, "Ca làm việc đã được người khác đăng kí!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    public void updateData(List<Schedule> newScheduleList) {
        this.scheduleList = newScheduleList;
        notifyDataSetChanged();
    }
}
