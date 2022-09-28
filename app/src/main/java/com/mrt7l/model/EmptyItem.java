package com.mrt7l.model;

import java.util.List;

import com.mrt7l.utils.menu.AbstractItem;

public class EmptyItem extends AbstractItem {

    /*constructor*/

    public EmptyItem(List<SeatModel> aSeatModelList) {
        super(aSeatModelList);
    }

    /*getter*/
    @Override
    public int getType() {
        return TYPE_EMPTY;
    }

}
