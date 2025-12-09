package com.example.myshelf;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM book_table")
    List<Book> getAllBooks();
}