package com.example.myapplication.data;

import android.content.res.Resources;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardsSourceImpl implements CardsSource {
    private List<NoteData> dataSource;
    private Resources resources;    // ресурсы приложения

    public CardsSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public CardsSourceImpl init() {
        // строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.titles);
        // строки описаний из ресурсов
        String[] descriptions = resources.getStringArray(R.array.subtitles);
        // заполнение источника данных
        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new NoteData(titles[i], descriptions[i], false, Calendar.getInstance().getTime()));
        }
        return this;
    }

    // Механизм вытаскивания идентификаторов картинок
    // https://stackoverflow.com/questions/5347107/creating-integer-array-of-resource-ids

    public NoteData getCardData(int position) {
        return dataSource.get(position);
    }

    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, NoteData noteData) {
        dataSource.set(position, noteData);
    }

    @Override
    public void addCardData(NoteData noteData) {
        dataSource.add(noteData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }
}
