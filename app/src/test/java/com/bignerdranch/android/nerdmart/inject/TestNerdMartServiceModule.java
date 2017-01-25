package com.bignerdranch.android.nerdmart.inject;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by gguntupalli on 25/01/17.
 */

public class TestNerdMartServiceModule extends NerdMartServiceModule {
    @Override
    protected Scheduler provideScheduler() {
        return Schedulers.immediate();
    }
}
