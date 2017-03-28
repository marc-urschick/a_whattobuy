package com.wgmc.whattobuy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by proxie on 27.03.17.
 */

public class DefaultService extends Observable implements Observer {
    private List<Observer> observers;

    protected DefaultService() {
        observers = new ArrayList<>();
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(Object arg) {
        for (Observer o : observers) {
            o.update(this, arg);
        }
    }

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public synchronized void deleteObservers() {
        observers.clear();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
