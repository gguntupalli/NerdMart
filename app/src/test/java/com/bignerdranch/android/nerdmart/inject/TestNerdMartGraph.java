package com.bignerdranch.android.nerdmart.inject;

import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManagerTest;

/**
 * Created by gguntupalli on 25/01/17.
 */


public interface TestNerdMartGraph extends NerdMartGraph {
    void inject(NerdMartServiceManagerTest nerdMartServiceManagerTest);
}
