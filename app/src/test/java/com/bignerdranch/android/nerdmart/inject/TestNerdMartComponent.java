package com.bignerdranch.android.nerdmart.inject;

import com.bignerdranch.android.nerdmart.NerdMartApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by gguntupalli on 25/01/17.
 */

@Singleton
@Component(modules = {
        NerdMartApplicationModule.class,
        NerdMartCommonModule.class,
        NerdMartServiceModule.class
})
public interface TestNerdMartComponent extends TestNerdMartGraph {
    final class Initializer {
        private Initializer() {
            throw new AssertionError("No instances.");
        }

        public static NerdMartGraph init(NerdMartApplication app) {
            return DaggerTestNerdMartComponent.builder()
                    .nerdMartApplicationModule(new NerdMartApplicationModule(app))
                    .build();
        }
    }
}