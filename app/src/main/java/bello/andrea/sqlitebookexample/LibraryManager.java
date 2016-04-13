package bello.andrea.sqlitebookexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class LibraryManager {

    private SQLiteDatabase database;
    private LibrarySQLiteHelper dbHelper;

    public LibraryManager(Context context) {
        dbHelper = new LibrarySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addBook(String title, String author) {
        ContentValues values = new ContentValues();
        values.put(LibrarySQLiteHelper.COLUMN_BOOK_TITLE, title);
        values.put(LibrarySQLiteHelper.COLUMN_BOOK_AUTHOR, author);
        long insertId = database.insert(
                LibrarySQLiteHelper.TABLE_BOOKS,
                null,
                values
        );
        return insertId;
    }

    public void deleteBook(Book book) {
        long id = book.getId();
        database.delete(
                LibrarySQLiteHelper.TABLE_BOOKS,
                LibrarySQLiteHelper.COLUMN_BOOK_ID + " = " + id,
                null
        );
    }

    public List<Book> getAllBooks(){
        return getAllBooks(null);
    }

    public List<Book> getAllBooks(String search) {
        List<Book> books = new ArrayList<>();

        String query = null;
        if(!TextUtils.isEmpty(search))
            query = LibrarySQLiteHelper.COLUMN_BOOK_TITLE + " LIKE '%" + search + "%'";

        Cursor cursor = database.query(
                LibrarySQLiteHelper.TABLE_BOOKS,
                null,
                query,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = cursorToBook(cursor);
            books.add(book);
            cursor.moveToNext();
        }

        cursor.close();
        return books;
    }

    private Book cursorToBook(Cursor cursor) {
        Book book = new Book();
        book.setId(cursor.getLong(LibrarySQLiteHelper.INDEX_COLUMN_BOOK_ID));
        book.setTitle(cursor.getString(LibrarySQLiteHelper.INDEX_COLUMN_BOOK_TITLE));
        book.setAuthor(cursor.getString(LibrarySQLiteHelper.INDEX_COLUMN_BOOK_AUTHOR));
        return book;
    }
}
