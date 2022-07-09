package com.example.myapplication.observe;

import com.example.myapplication.data.NoteData;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;   // Все обозреватели

    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notifySingle(NoteData noteData) {
        for (Observer observer : observers) {
            observer.updateCardData(noteData);
            unsubscribe(observer);
        }
    }

}
