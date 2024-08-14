//package com.example.happyhomes.NhanVien;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.happyhomes.DatabaseHelper;
//import com.example.happyhomes.LoginActivity;
//import com.example.happyhomes.Model.Check;
//import com.example.happyhomes.Model.Employee;
//import com.example.happyhomes.R;
//import com.example.happyhomes.databinding.ActivityNhanVienBinding;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class NhanVienActivity extends AppCompatActivity {
//
//    ActivityNhanVienBinding binding;
//    boolean checkIn;
//    Employee employee;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        binding = ActivityNhanVienBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Nhận dữ liệu Employee từ Intent
//        Intent intent = getIntent();
//        employee = (Employee) intent.getSerializableExtra("Employee");
//
//        // Kiểm tra nếu đối tượng employee là null
//        if (employee == null) {
//            Log.e("NhanVienActivity", "Employee object is null!");
//            finish();  // Dừng Activity nếu không có dữ liệu employee
//            return;
//        }
//
//        // Hiển thị thông tin Employee
//        binding.txtusername.setText(employee.getEmName());
//
//        // Khôi phục trạng thái từ SharedPreferences
//        restoreCheckStatusFromPreferences();
//
//        addEvents();
//
//        displayAllChecks();
//    }
//
//    private void addEvents() {
//        // Hiển thị dialog khi nhấn vào layoutProfile
//        binding.layotProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showBottomSheet();
//            }
//        });
//
//        // Xử lý sự kiện Check-in/Check-out
//        binding.btnCheckIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!checkIn && binding.btnCheckIn.getText().toString().equals("Sẵn sàng làm việc")) {
//                    showReadyToWorkDialog();
//                } else if (checkIn && binding.btnCheckIn.getText().toString().equals("Tạm nghỉ")) {
//                    showConfirmDialog();
//                }
//            }
//        });
//    }
//
//    // Hiển thị dialog xác nhận tạm nghỉ
//    private void showConfirmDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(NhanVienActivity.this);
//        builder.setTitle("Xác nhận nghỉ");
//        builder.setIcon(R.drawable.notifications);
//        builder.setMessage("Bạn có chắc chắn muốn nghỉ?");
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                binding.txtstatus.setText("Tạm nghỉ");
//                binding.btnCheckIn.setText("Sẵn sàng làm việc");
//                checkIn = false; // Cập nhật trạng thái checkIn
//
//                // Lưu thông tin check-out vào bảng CHECK
//                saveCheckInOutData(0); // 0 là mã cho check-out
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//    }
//
//    // Hiển thị dialog sẵn sàng làm việc
//    private void showReadyToWorkDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(NhanVienActivity.this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_ready_to_work, null);
//        builder.setView(dialogView);
//
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//
//        TextView tvReadyDate = dialogView.findViewById(R.id.tvReadyDate);
//        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
//        Button btnCheckIn = dialogView.findViewById(R.id.btnCheckIn);
//
//        // Lấy thời gian hiện tại
//        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
//        tvReadyDate.setText(currentDate);
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        btnCheckIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Xử lý khi bấm nút Check-in
//                dialog.dismiss();
//                binding.txtstatus.setText("Đang làm việc");
//                binding.btnCheckIn.setText("Tạm nghỉ");
//
//                checkIn = true;
//
//                // Lưu thông tin check-in vào bảng CHECK
//                saveCheckInOutData(1); // 1 là mã cho check-in
//            }
//        });
//    }
//
//    // Phương thức lưu dữ liệu Check-in/Check-out
//    private void saveCheckInOutData(int checkType) {
//        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
//        Check check = new Check(employee.getEmId(), null, checkType, currentDate);
//
//        Log.d("NhanVienActivity", "Saving Check - Employee ID: " + employee.getEmId() +
//                ", Check Type: " + checkType +
//                ", Time: " + currentDate);
//
//        DatabaseHelper dbHelper = new DatabaseHelper(NhanVienActivity.this);
//        dbHelper.addCheck(check);
//
//        // Lưu trạng thái Check-in/Check-out vào SharedPreferences
//        saveCheckStatusToPreferences(checkType);
//    }
//
//    // Lưu trạng thái vào SharedPreferences
//    private void saveCheckStatusToPreferences(int checkType) {
//        SharedPreferences sharedPreferences = getSharedPreferences("CheckStatusPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("CheckType", checkType); // Lưu trạng thái check-in hoặc check-out
//        editor.apply();
//    }
//
//    // Khôi phục trạng thái từ SharedPreferences
//    private void restoreCheckStatusFromPreferences() {
//        SharedPreferences sharedPreferences = getSharedPreferences("CheckStatusPrefs", MODE_PRIVATE);
//        int checkType = sharedPreferences.getInt("CheckType", -1);
//
//        if (checkType == 1) { // 1 là Check-in
//            checkIn = true;
//            binding.txtstatus.setText("Đang làm việc");
//            binding.btnCheckIn.setText("Tạm nghỉ");
//        } else if (checkType == 0) { // 0 là Check-out
//            checkIn = false;
//            binding.txtstatus.setText("Tạm nghỉ");
//            binding.btnCheckIn.setText("Sẵn sàng làm việc");
//        }
//    }
//
//    // Hiển thị bottom sheet
//    private void showBottomSheet() {
//        Dialog dialog = new Dialog(NhanVienActivity.this);
//        dialog.setContentView(R.layout.bottom_dialog);
//
//        TextView txtEmployeeName = dialog.findViewById(R.id.txtusername);
//        if (employee != null) {
//            txtEmployeeName.setText(employee.getEmName());
//        }
//
//        //ho tro
//        LinearLayout linearHoTro = dialog.findViewById(R.id.layotHoTro);
//        linearHoTro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent( NhanVienActivity.this, HoTroActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //dang xat
//        LinearLayout linearDangXat = dialog.findViewById(R.id.layotĐangXuat);
//        linearDangXat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(NhanVienActivity.this, LoginActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//        //Ho So
//        LinearLayout linearHoSo = dialog.findViewById(R.id.layoutHoSo);
//        linearHoSo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(NhanVienActivity.this, HoSoNVActivity.class);
//                intent.putExtra("EMPLOYEE_ID", employee.getEmId());
//                startActivity(intent);
//            }
//        });
//
//
//
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.gravity = Gravity.START | Gravity.TOP;
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.MATCH_PARENT;
//        params.x = 0;
//        params.y = 0;
//
//        dialog.getWindow().setAttributes(params);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.BottonSheetAnimation;
//        dialog.show();
//    }
//
//    // Phương thức hiển thị tất cả các bản ghi trong bảng CHECK
//    private void displayAllChecks() {
//        DatabaseHelper dbHelper = new DatabaseHelper(NhanVienActivity.this);
//        List<Check> checks = dbHelper.getAllChecks();
//
//        for (Check check : checks) {
//            Log.d("NhanVienActivity", "Check ID: " + check.getCheckId() +
//                    ", Employee ID: " + check.getEmId() +
//                    ", Check Type: " + check.getCheckType() +
//                    ", Check Time: " + check.getCheckTime());
//        }
//    }
//}
