package com.bignerdranch.android.nerdmart.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.bignerdranch.android.nerdmart.BR;
import com.bignerdranch.android.nerdmart.R;
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;
import com.bignerdranch.android.nerdmartservice.service.payload.User;

/**
 * Created by gguntupalli on 25/01/17.
 */

public class NerdMartViewModel extends BaseObservable{

    private Context mContext;
    private Cart mCart;
    private User mUser;
    public NerdMartViewModel(Context context, Cart cart, User user) {
        mContext = context;
        mCart = cart;
        mUser = user;
    }
    public String formatCartItemsDisplay() {
        int numItems = 0;
        if (mCart != null && mCart.getProducts() != null) {
            numItems = mCart.getProducts().size();
        }
        return mContext.getResources().getQuantityString(R.plurals.cart,
                numItems, numItems);
    }
    public String getUserGreeting() {
        return mContext.getString(R.string.user_greeting, mUser.getName());
    }
    @Bindable
    public String getCartDisplay() {
        return formatCartItemsDisplay();
    }

    public void updateCartStatus(Cart cart) {
        mCart = cart;
        notifyPropertyChanged(BR.cartDisplay);
    }
}
