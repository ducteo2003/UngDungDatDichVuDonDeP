package com.example.happyhomes.NhanVien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.happyhomes.R;
import com.example.happyhomes.databinding.ActivityNhanVienBinding;

public class NhanVienActivity extends AppCompatActivity {

    ActivityNhanVienBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNhanVienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();

    }



    private void addEvents() {

        //hien thi dialog
        binding.layotProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });


    }

    private void showBottomSheet() {
        Dialog dialog = new Dialog(NhanVienActivity.this);
        dialog.setContentView(R.layout.bottom_dialog);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.START | Gravity.TOP; // Hiển thị ở bên trái và phía trên
        params.width = WindowManager.LayoutParams.WRAP_CONTENT; // Chiều rộng tự động theo nội dung
        params.height = WindowManager.LayoutParams.MATCH_PARENT; // Chiều cao đầy đủ
        params.x = 0; // Đặt vị trí X của dialog
        params.y = 0; // Đặt vị trí Y của dialog

        dialog.getWindow().setAttributes(params);
        dialog.show();

    }
}