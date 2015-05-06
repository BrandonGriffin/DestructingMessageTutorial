package com.example.brandon.myapplication;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Brandon on 4/15/2015.
 */
public class DestructingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "EoDtrPT0DoWklRnCSMlFgAVoh84TZ3voI3lKvYgm", "vmMZ5flL9MsKNcFwum01BnMaeTYn86A5PoV2m3zc");
    }
}
