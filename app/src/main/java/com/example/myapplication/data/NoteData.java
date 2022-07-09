package com.example.myapplication.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteData implements Parcelable {
    private final String title;       // заголовок
    private final String subtitle; // описание
    private final boolean isImportant;       // флажок
    private final Date date;          // дата

    public NoteData(String title, String subtitle, boolean isImportant, Date date) {
        this.title = title;
        this.subtitle = subtitle;
        this.isImportant = isImportant;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public Date getDate() {
        return date;
    }

    protected NoteData(Parcel in) {
        title = in.readString();
        subtitle = in.readString();
        isImportant = in.readByte() != 0;
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeByte((byte) (isImportant ? 1 : 0));
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };
}
