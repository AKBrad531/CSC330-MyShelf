# MyShelf - Personal Library Manager

MyShelf is a  Android application developed for CSC 330. It solves the problem of tracking personal book collections by allowing users to search the OpenLibrary API, fetch metadata/cover art, and save items to a local persistent database.

## Features
* **Search & Discover:** Live query of the OpenLibrary API for millions of books.
* **Persistent Library:** Saves books to a local SQLite database (via Room) for offline access.
* **Detail View:** View high-resolution cover art and metadata.
* **Collection Management:** Add new books and delete items you no longer own.

## Tech Stack
* **Language:** Java
* **Architecture:** MVVM (Model-View-ViewModel) pattern concepts
* **Networking:** Retrofit 2 + Gson
* **Image Loading:** Glide
* **Database:** Android Room Persistence Library
* **UI:** RecyclerView, ConstraintLayout, CardView

## How to Build
1.  Clone this repository.
2.  Open in Android Studio.
3.  Sync Gradle files.
4.  Run on Emulator.

