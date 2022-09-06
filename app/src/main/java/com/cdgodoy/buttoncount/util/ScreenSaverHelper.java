package com.cdgodoy.buttoncount.util;

import com.cdgodoy.buttoncount.R;
import com.cdgodoy.buttoncount.ScreenSaverModel;

import java.util.ArrayList;
import java.util.List;

public class ScreenSaverHelper {

    private static List<ScreenSaverModel> getImages(){
        List<ScreenSaverModel> images = new ArrayList<>();
        images.add(new ScreenSaverModel(R.drawable.batman_logo_1, "Batman"));
        images.add(new ScreenSaverModel(R.drawable.capsule_corp_1, "Capsule Corp"));
        images.add(new ScreenSaverModel(R.drawable.superman_logo_1, "Superman"));
        return images;
    }

    public static ScreenSaverModel getImage(int position){
        List<ScreenSaverModel> images = getImages();
        return images.get(position);
    }

}
