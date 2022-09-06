package com.cdgodoy.buttoncount;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.cdgodoy.buttoncount.util.Constants;
import com.cdgodoy.buttoncount.util.ScreenSaverHelper;
import com.cdgodoy.buttoncount.util.SharedPrefUtil;

public class ScreenSaverDialog extends DialogFragment {

    interface OnDialogDismissListener{
        void onDismiss();
    }

    private ConstraintLayout background;

    private OnDialogDismissListener listener;

    private SharedPrefUtil sharedPrefUtil;

    public ScreenSaverDialog(OnDialogDismissListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setCancelable(true);
        sharedPrefUtil = new SharedPrefUtil(getContext());
        View v = inflater.inflate(R.layout.layout_screen_saver, container, false);
        background = v.findViewById(R.id.background);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int position = sharedPrefUtil.getInt(Constants.KEY_IMAGE_POSITION);
        ScreenSaverModel image = ScreenSaverHelper.getImage(position);

        Drawable drawable = ContextCompat.getDrawable(getContext(), image.getImage());
        background.setBackground(drawable);
        background.setOnClickListener(v -> {
            listener.onDismiss();
            dismiss();
        });
    }

    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }
}
