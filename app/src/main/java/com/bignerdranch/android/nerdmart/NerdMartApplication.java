package com.bignerdranch.android.nerdmart;

import android.app.Application;
import android.support.annotation.NonNull;

import com.bignerdranch.android.nerdmart.inject.Injector;
import com.bignerdranch.android.nerdmart.inject.NerdMartComponent;
import com.bignerdranch.android.nerdmart.inject.NerdMartGraph;

import timber.log.Timber;

/**
 * Created by gguntupalli on 24/01/17.
 */

public class NerdMartApplication extends Application {
    protected NerdMartGraph mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        setUpDagger();
    }

    protected void setUpDagger() {
        mComponent = NerdMartComponent.Initializer.init(this);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if(Injector.matchesService(name)) {
            return mComponent;
        }

        return super.getSystemService(name);
    }
}
