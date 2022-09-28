package com.mrt7l.model;

import java.util.Observable;

public class ReservationListModel extends Observable {

    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        setChanged();
    }
}
