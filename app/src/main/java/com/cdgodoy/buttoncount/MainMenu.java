package com.cdgodoy.buttoncount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdgodoy.buttoncount.util.Constants;
import com.cdgodoy.buttoncount.util.ScreenSaverHelper;
import com.cdgodoy.buttoncount.util.SharedPrefUtil;

public class MainMenu extends AppCompatActivity {

    private LinearLayoutCompat llOne, llTwo, llThree;
    private ImageView ivOne, ivTwo, ivThree;
    private TextView tvSelected;
    private Button btnStart;

    private Context mContext;
    private SharedPrefUtil sharedPrefUtil;

    private int selectedImagePosition = -1;

    private String selectedLabel = "Selected: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mContext = this;
        sharedPrefUtil = new SharedPrefUtil(mContext);

        llOne = findViewById(R.id.ll_one);
        llTwo = findViewById(R.id.ll_two);
        llThree = findViewById(R.id.ll_three);
        ivOne = findViewById(R.id.iv_one);
        ivTwo = findViewById(R.id.iv_two);
        ivThree = findViewById(R.id.iv_three);
        tvSelected = findViewById(R.id.tv_selected);
        btnStart = findViewById(R.id.btn_start);

        ScreenSaverModel imageOne = ScreenSaverHelper.getImage(0);
        ScreenSaverModel imageTwo = ScreenSaverHelper.getImage(1);
        ScreenSaverModel imageThree = ScreenSaverHelper.getImage(2);

        ivOne.setBackground(ContextCompat.getDrawable(mContext, imageOne.getImage()));
        ivTwo.setBackground(ContextCompat.getDrawable(mContext, imageTwo.getImage()));
        ivThree.setBackground(ContextCompat.getDrawable(mContext, imageThree.getImage()));

        int savedPosition = sharedPrefUtil.getInt(Constants.KEY_IMAGE_POSITION);
        if (savedPosition != -1) {
            selectedImagePosition = savedPosition;

            ScreenSaverModel currentSelection = ScreenSaverHelper.getImage(selectedImagePosition);
            String name = currentSelection.getName();
            tvSelected.setText(String.format("Current selection: %s", name));

            if (name.equals(imageOne.getName())) {
                showBackground(llOne);
                removeBackground(llTwo);
                removeBackground(llThree);
            } else if (name.equals(imageTwo.getName())) {
                showBackground(llTwo);
                removeBackground(llThree);
                removeBackground(llOne);
            } else if (name.equals(imageThree.getName())) {
                showBackground(llThree);
                removeBackground(llTwo);
                removeBackground(llOne);
            }
        }

        ivOne.setOnClickListener(v -> {
            selectedImagePosition = 0;
            tvSelected.setText(String.format("%s%s", selectedLabel, imageOne.getName()));
            showBackground(llOne);
            removeBackground(llTwo);
            removeBackground(llThree);
        });

        ivTwo.setOnClickListener(v -> {
            selectedImagePosition = 1;
            tvSelected.setText(String.format("%s%s", selectedLabel, imageTwo.getName()));
            showBackground(llTwo);
            removeBackground(llThree);
            removeBackground(llOne);
        });

        ivThree.setOnClickListener(v -> {
            selectedImagePosition = 2;
            tvSelected.setText(String.format("%s%s", selectedLabel, imageThree.getName()));
            showBackground(llThree);
            removeBackground(llTwo);
            removeBackground(llOne);
        });

        btnStart.setOnClickListener(v -> {
            sharedPrefUtil.setInt(Constants.KEY_IMAGE_POSITION, selectedImagePosition);
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        });
    }

    private void showBackground(LinearLayoutCompat linearLayout) {
        linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.outline_border));
    }

    private void removeBackground(LinearLayoutCompat linearLayout) {
        linearLayout.setBackground(null);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}