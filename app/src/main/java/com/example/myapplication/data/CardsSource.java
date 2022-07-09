package com.example.myapplication.data;

public interface CardsSource {

    NoteData getCardData(int position);

    int size();

    void deleteCardData(int position);

    void updateCardData(int position, NoteData noteData);

    void addCardData(NoteData noteData);

    void clearCardData();
}
