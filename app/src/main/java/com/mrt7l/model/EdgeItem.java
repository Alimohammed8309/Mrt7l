package com.mrt7l.model;

import com.mrt7l.utils.menu.AbstractItem;

public class EdgeItem extends AbstractItem {


    /*constructor*/
    public EdgeItem(String aValueOf) {
        super(aValueOf);
    }

    /*getter*/
    @Override
    public int getType() {
        return TYPE_EDGE;
    }

}
