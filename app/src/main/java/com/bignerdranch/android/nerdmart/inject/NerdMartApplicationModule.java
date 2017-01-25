package com.bignerdranch.android.nerdmart.inject;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gguntupalli on 24/01/17.
 */
@Module
public class NerdMartApplicationModule {
    private Context mApplicationContext;

    public NerdMartApplicationModule(Context context) {
        mApplicationContext = context.getApplicationContext();
    }

    @Provides
    public Context provideContext() {
        return mApplicationContext;
    }

}
