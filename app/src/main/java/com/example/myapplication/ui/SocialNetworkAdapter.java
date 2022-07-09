package com.example.myapplication.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.CardsSource;
import com.example.myapplication.data.NoteData;

import java.text.SimpleDateFormat;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.ViewHolderMain> {

    private final static String TAG = "SocialNetworkAdapter";
    private final CardsSource dataSource;
    private final Fragment listItemFragment;// FIXME Remove fragment from here
    private MyItemClickListener myItemClickListener;  // TODO Слушатель будет устанавливаться извне
    private int listItemPosition;

    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public SocialNetworkAdapter(CardsSource dataSource, Fragment listItemFragment) {
        this.dataSource = dataSource;
        this.listItemFragment = listItemFragment;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public ViewHolderMain onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаём новый элемент пользовательского интерфейса
        // через Inflater
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item, viewGroup, false);
        Log.d(TAG, "onCreateViewHolder");
        // Здесь можно установить всякие параметры
        return new ViewHolderMain(v);
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull ViewHolderMain viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран используя ViewHolder
        viewHolder.setData(dataSource.getCardData(i));
        Log.d(TAG, "onBindViewHolder");
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // Сеттер слушателя нажатий
    public void setOnItemClickListener(MyItemClickListener itemClickListener) {
        this.myItemClickListener = itemClickListener;
    }

    public int getListItemPosition() {
        return listItemPosition;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на
    // один пункт списка
    public class ViewHolderMain extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private CheckBox isImportant;
        private TextView date;

        public ViewHolderMain(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
            isImportant = itemView.findViewById(R.id.importance);
            date = itemView.findViewById(R.id.date);

            registerContextMenu(itemView);

            // Обработчик нажатий на картинке
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myItemClickListener != null) {
                        myItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

            // Обработчик нажатий на картинке
            title.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View v) {
                    listItemPosition = getLayoutPosition();
                    itemView.showContextMenu(10, 10);
                    return true;
                }
            });
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (listItemFragment != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        listItemPosition = getLayoutPosition();
                        return false;
                    }
                });
                listItemFragment.registerForContextMenu(itemView);
            }
        }

        @SuppressLint("SimpleDateFormat")
        public void setData(NoteData noteData) {
            title.setText(noteData.getTitle());
            subtitle.setText(noteData.getSubtitle());
            isImportant.setChecked(noteData.isImportant());
            date.setText(new SimpleDateFormat("dd-MM-yy").format(noteData.getDate()));
        }
    }
}
