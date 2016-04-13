package bello.andrea.sqlitebookexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LibrarySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_BOOK_ID = "_id";
    public static final String COLUMN_BOOK_TITLE = "title";
    public static final String COLUMN_BOOK_AUTHOR = "author";

    public static final int INDEX_COLUMN_BOOK_ID = 0;
    public static final int INDEX_COLUMN_BOOK_TITLE = 1;
    public static final int INDEX_COLUMN_BOOK_AUTHOR = 2;

    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_BOOKS + " ("
                + COLUMN_BOOK_ID  + " integer primary key autoincrement, "
                + COLUMN_BOOK_TITLE + " text not null, "
                + COLUMN_BOOK_AUTHOR + " text not null"
            + ");";

    public LibrarySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d("CREATION", DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LibrarySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

}