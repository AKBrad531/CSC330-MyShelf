package com.example.myshelf;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookResponse {
    @SerializedName("docs")
    public List<Book> bookList;
}
