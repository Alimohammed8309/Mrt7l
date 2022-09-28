package com.mrt7l.utils.menu;

public class CenterItem extends AbstractItem {
    /*constructor*/
    public CenterItem(String valueOf) {
        super(valueOf);
    }

    /*getter*/
    @Override
    public int getType() {
        return TYPE_CENTER;
    }

}
