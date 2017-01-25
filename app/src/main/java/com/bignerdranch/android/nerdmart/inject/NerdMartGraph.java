package com.bignerdranch.android.nerdmart.inject;

import com.bignerdranch.android.nerdmart.NerdMartAbstractActivity;
import com.bignerdranch.android.nerdmart.NerdMartAbstractFragment;

/**
 * Created by gguntupalli on 24/01/17.
 */

public interface NerdMartGraph {
    void inject(NerdMartAbstractFragment fragment);
    void inject(NerdMartAbstractActivity activity);
}
