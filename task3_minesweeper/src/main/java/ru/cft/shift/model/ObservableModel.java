package ru.cft.shift.model;

/**
 * @author Dmitrii Taranenko
 */
public interface ObservableModel {
    void addListener(GameModelObserver listener);

    void removeListener(GameModelObserver listener);

    void notifyListeners(GameModelEvent event);
}
