package com.example.myshelf;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Entity(tableName = "book_table")
public class Book {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("title")
    public String title;


    @SerializedName("first_publish_year")
    public int publishYear;

    @SerializedName("cover_i")
    public int coverId;

    public String myNotes;
}