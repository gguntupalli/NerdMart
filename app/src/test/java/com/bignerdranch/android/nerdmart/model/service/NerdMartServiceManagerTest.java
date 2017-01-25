package com.bignerdranch.android.nerdmart.model.service;

import com.bignerdranch.android.nerdmart.BuildConfig;
import com.bignerdranch.android.nerdmart.TestNerdMartApplication;
import com.bignerdranch.android.nerdmart.inject.DaggerTestNerdMartComponent;
import com.bignerdranch.android.nerdmart.inject.NerdMartApplicationModule;
import com.bignerdranch.android.nerdmart.inject.NerdMartGraph;
import com.bignerdranch.android.nerdmart.inject.NerdMartServiceModule;
import com.bignerdranch.android.nerdmart.inject.TestInjector;
import com.bignerdranch.android.nerdmart.inject.TestNerdMartServiceModule;
import com.bignerdranch.android.nerdmart.model.DataStore;
import com.bignerdranch.android.nerdmartservice.model.NerdMartDataSourceInterface;
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;
import com.bignerdranch.android.nerdmartservice.service.payload.Product;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by gguntupalli on 25/01/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23, constants = BuildConfig.class)
public class NerdMartServiceManagerTest {

    @Inject DataStore mDataStore;
    @Inject NerdMartServiceManager mNerdMartServiceManager;
    @Inject NerdMartDataSourceInterface mNerdMartDataSourceInterface;

    @Before
    public void setup() {
        TestNerdMartApplication application
                = (TestNerdMartApplication) RuntimeEnvironment.application;
        NerdMartApplicationModule applicationModule
                = new NerdMartApplicationModule(application);
        NerdMartServiceModule serviceModule = new TestNerdMartServiceModule();
        NerdMartGraph component = DaggerTestNerdMartComponent.builder()
                .nerdMartApplicationModule(applicationModule)
                .nerdMartServiceModule(serviceModule)
                .build();
        application.setComponent(component);

        TestInjector.obtain(application)
                .inject(this);
    }

    @Test
    public void testAuthenticateMethodReturnsFalseWithInvalidCredentials() {
        TestSubscriber subscriber = new TestSubscriber<>();
        mNerdMartServiceManager.authenticate("johnnydoe", "WrongPassword")
                .subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertThat(subscriber.getOnNextEvents().get(0)).isEqualTo(false);
        assertThat(mDataStore.getCachedUser()).isEqualTo(null);
    }

    @Test
    public void testAuthenticateMethodReturnsTrueWithValidCredentials() {
        TestSubscriber subscriber = new TestSubscriber<>();
        mNerdMartServiceManager.authenticate("johnnydoe", "pizza")
                .subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertThat(subscriber.getOnNextEvents().get(0)).isEqualTo(true);
        assertThat(mDataStore.getCachedUser()).isEqualTo(mNerdMartDataSourceInterface.getUser());
    }

    @Test
    public void testSignoutRemoveUserObjects() {
        TestSubscriber subscriber = new TestSubscriber();
        mNerdMartServiceManager.authenticate("johnnydoe", "pizza")
        .subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        TestSubscriber signoutSubscriber = new TestSubscriber();
        mNerdMartServiceManager.signout()
                .subscribe(signoutSubscriber);
        signoutSubscriber.awaitTerminalEvent();
        assertThat(mDataStore.getCachedUser()).isEqualTo(null);
        assertThat(mDataStore.getCachedCart()).isEqualTo(null);

    }

    @Test
    public void testGetProductsReturnsExpectedProductListings() {
        mDataStore.setCachedUser(mNerdMartDataSourceInterface.getUser());
        TestSubscriber<List> subscriber = new TestSubscriber();
        mNerdMartServiceManager.getProducts().toList()
                .subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertThat(subscriber.getOnNextEvents()
        .get(0)).containsAll(mNerdMartDataSourceInterface.getProducts());
    }

    @Test
    public void testGetCartReturnsCartAndCachesCartInDataStore() {
        mDataStore.setCachedUser(mNerdMartDataSourceInterface.getUser());
        TestSubscriber<Cart> subscriber = new TestSubscriber();
        mNerdMartServiceManager.getCart()
                .subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        Cart actual = subscriber.getOnNextEvents().get(0);
        assertThat(actual).isNotEqualTo(null);
        assertThat(mDataStore.getCachedCart()).isEqualTo(actual);
        assertThat(mDataStore.getCachedCart().getProducts()).hasSize(0);
    }


    @Test
    public void testPostProductToCartAddsProductsToUserCart() {
        mDataStore.setCachedUser(mNerdMartDataSourceInterface.getUser());
        ArrayList<Product> products = Lists.newArrayList();
        TestSubscriber<Boolean> postProductsSubscriber = new TestSubscriber<>();
        products.addAll(mNerdMartDataSourceInterface.getProducts());
        mNerdMartServiceManager.postProductToCart(products.get(0))
                .subscribe(postProductsSubscriber);
        postProductsSubscriber.awaitTerminalEvent();
        assertThat(postProductsSubscriber.getOnNextEvents().get(0)).isEqualTo(true);
        TestSubscriber<Cart> cartSubscriber = new TestSubscriber<>();
        mNerdMartServiceManager.getCart().subscribe(cartSubscriber);
        cartSubscriber.awaitTerminalEvent();
        Cart cart = cartSubscriber.getOnNextEvents().get(0);
        assertThat(cart.getProducts()).hasSize(1);
    }
}
