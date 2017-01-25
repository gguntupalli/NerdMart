package com.bignerdranch.android.nerdmart;

import com.bignerdranch.android.nerdmart.inject.NerdMartGraph;
import com.bignerdranch.android.nerdmart.inject.TestNerdMartComponent;

/**
 * Created by gguntupalli on 25/01/17.
 */

public class TestNerdMartApplication extends NerdMartApplication {
    @Override
    protected void setUpDagger() {
        NerdMartGraph graph = TestNerdMartComponent.Initializer.init(this);
        setComponent(graph);
    }
    public void setComponent(NerdMartGraph graph) {
        mComponent = graph;
    }
}
