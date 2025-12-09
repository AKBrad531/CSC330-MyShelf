package com.example.myshelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private Context context;
    private OnItemActionClickListener listener;

    public interface OnItemActionClickListener {
        void onSave(Book book);
    }

    public BookAdapter(Context context, List<Book> bookList, OnItemActionClickListener listener) {
        this.context = context;
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.title.setText(book.title);
        holder.year.setText("Year: " + book.publishYear);


        if (book.coverId > 0) {
            String imageUrl = "https://covers.openlibrary.org/b/id/" + book.coverId + "-M.jpg";

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.coverImage);
        } else {
            holder.coverImage.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        holder.saveBtn.setOnClickListener(v -> {
            listener.onSave(book);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, year;
        ImageView coverImage;
        Button saveBtn;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            year = itemView.findViewById(R.id.tvYear);
            coverImage = itemView.findViewById(R.id.ivCover);
            saveBtn = itemView.findViewById(R.id.btnSave);
        }
    }
}