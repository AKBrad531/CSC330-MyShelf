package com.example.myshelf;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private BookService bookService;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "my-shelf-db").build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openlibrary.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bookService = retrofit.create(BookService.class);

        EditText searchInput = findViewById(R.id.searchInput);
        Button searchButton = findViewById(R.id.searchBtn);
        Button loadSavedButton = findViewById(R.id.loadSavedBtn);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BookAdapter(this, books, new BookAdapter.OnItemActionClickListener() {
            @Override
            public void onSave(Book book) {
                saveBookToDatabase(book);
            }
        });

        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            if (!query.isEmpty()) {
                performSearch(query);
            } else {
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            }
        });

        loadSavedButton.setOnClickListener(v -> loadFromDatabase());
    }


    private void performSearch(String query) {
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();

        bookService.searchBooks(query).enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    books.clear();

                    if (response.body().bookList != null) {
                        books.addAll(response.body().bookList);
                    }

                    adapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "Found " + books.size() + " books", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFromDatabase() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Book> savedBooks = db.bookDao().getAllBooks();

            runOnUiThread(() -> {
                books.clear();
                books.addAll(savedBooks);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Loaded " + books.size() + " saved books", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void saveBookToDatabase(Book book) {
        Executors.newSingleThreadExecutor().execute(() -> {
            Book newBook = new Book();
            newBook.title = book.title;
            newBook.publishYear = book.publishYear;
            newBook.coverId = book.coverId;
            newBook.myNotes = "Added from Search";

            db.bookDao().insert(newBook);

            runOnUiThread(() ->
                    Toast.makeText(MainActivity.this, "Saved: " + book.title, Toast.LENGTH_SHORT).show()
            );
        });
    }
}