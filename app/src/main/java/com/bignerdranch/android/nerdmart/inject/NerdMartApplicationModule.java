package com.bignerdranch.android.nerdmart.inject;

import android.content.Context;

import com.bignerdranch.android.nerdmart.model.DataStore;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmartservice.service.NerdMartService;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;

import javax.inject.Singleton;

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
    @Singleton
    DataStore provideDataStore() {
        return new DataStore();
    }

    @Provides
    NerdMartServiceInterface provideNerdMartInterfaceService() {
        return new NerdMartService();
    }

    @Provides
    @Singleton
    NerdMartServiceManager provideNerdMartServiceManager(NerdMartServiceInterface serviceInterface, DataStore dataStore) {
        return new NerdMartServiceManager(serviceInterface, dataStore);
    }
}
