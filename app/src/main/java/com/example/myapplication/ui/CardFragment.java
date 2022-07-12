package com.example.myapplication.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.NoteData;
import com.example.myapplication.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class CardFragment extends Fragment {

    private static final String ARG_CARD_DATA = "Param_CardData";

    private NoteData noteData;      // Данные по карточке
    private Publisher publisher;    // Паблишер, с его помощью обмениваемся данными

    private TextInputEditText title;
    private TextInputEditText subtitle;
    private DatePicker datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        initView(view);
        // если cardData пустая, то это добавление
        if (noteData != null) {
            populateView();
        }
        return view;
    }

    // Здесь соберём данные из views


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    @Override
    public void onStop() {
        super.onStop();
        noteData = collectCardData();
    }
    // Здесь передадим данные в паблишер

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(noteData);
    }
    private NoteData collectCardData(){
        String title = this.title.getText().toString();
        String subtitle = this.subtitle.getText().toString();
        Date date = getDateFromDatePicker();
        boolean isImportant;
        if (noteData != null){
            isImportant = noteData.isImportant();
        } else {
            isImportant = false;
        }
        return new NoteData(title, subtitle, isImportant, date);
    }

    // Получение даты из DatePicker

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }
    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        subtitle = view.findViewById(R.id.inputSubtitle);
        datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView(){
        title.setText(noteData.getTitle());
        subtitle.setText(noteData.getSubtitle());
        initDatePicker(noteData.getDate());
    }

    // Установка даты в DatePicker

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    public static CardFragment newInstance() {
        return new CardFragment();
    }

    public static CardFragment newInstance(NoteData noteData) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, noteData);
        fragment.setArguments(args);
        return fragment;
    }
}
