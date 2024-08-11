package com.example.happyhomes.NhanVien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.happyhomes.Model.Employee;
import com.example.happyhomes.R;
import com.example.happyhomes.databinding.ActivityNhanVienBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NhanVienActivity extends AppCompatActivity {

    ActivityNhanVienBinding binding;
    boolean checkIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNhanVienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();

    }



    private void addEvents() {

        // Nhận dữ liệu Employee từ Intent
        Intent intent = getIntent();
        Employee employee = (Employee) intent.getSerializableExtra("Employee");

        //Hien thi thong tin
        if(employee != null){
            binding.txtusername.setText(employee.getEmName());
        }





        //hien thi dialog
        binding.layotProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });

        // Check-in
        if (binding.txtstatus.getText().toString().equals("Tạm nghỉ")) {
            checkIn = false;
        } else if (binding.txtstatus.getText().toString().equals("Đang làm việc")) {
            checkIn = true;
        }


        binding.btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkIn && binding.btnCheckIn.getText().toString().equals("Sẵn sàng làm việc")) {
                    showReadyToWorkDialog();

                } else if (checkIn && binding.btnCheckIn.getText().toString().equals("Tạm nghỉ")) {
                    showConfirmDialog();
                }
            }
        });


    }

    //tạm nghỉ
    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NhanVienActivity.this);
        builder.setTitle("Xác nhận nghỉ");
        builder.setIcon(R.drawable.notifications);
        builder.setMessage("Bạn có chắc chắn muốn nghỉ?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                binding.txtstatus.setText("Tạm nghỉ");
                binding.btnCheckIn.setText("Sẵn sàng làm việc");
                checkIn = false; // Cập nhật trạng thái checkIn
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //sẵn sàng làm việc
    private void showReadyToWorkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NhanVienActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_ready_to_work, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView tvReadyDate = dialogView.findViewById(R.id.tvReadyDate);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnCheckIn = dialogView.findViewById(R.id.btnCheckIn);

        // Lấy thời gian hiện tại
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        tvReadyDate.setText(currentDate);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi bấm nút Check-in
                dialog.dismiss();
                binding.txtstatus.setText("Đang làm việc");
                binding.btnCheckIn.setText("Tạm nghỉ");


                checkIn = true;
            }
        });

    }

    private void showBottomSheet() {

        // Nhận dữ liệu Employee từ Intent
        Employee employee = (Employee) getIntent().getSerializableExtra("Employee");

        Dialog dialog = new Dialog(NhanVienActivity.this);
        dialog.setContentView(R.layout.bottom_dialog);

        TextView txtEmployeeName = dialog.findViewById(R.id.txtusername);
        if(employee != null){
            txtEmployeeName.setText(employee.getEmName());
        }

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.START | Gravity.TOP; // Hiển thị ở bên trái và phía trên
        params.width = WindowManager.LayoutParams.WRAP_CONTENT; // Chiều rộng tự động theo nội dung
        params.height = WindowManager.LayoutParams.MATCH_PARENT; // Chiều cao đầy đủ
        params.x = 0; // Đặt vị trí X của dialog
        params.y = 0; // Đặt vị trí Y của dialog

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().getAttributes().windowAnimations = R.style.BottonSheetAnimation;
        dialog.show();

    }
}