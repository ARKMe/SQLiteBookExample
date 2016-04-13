package bello.andrea.sqlitebookexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddBookActivity extends Activity {

    private LibraryManager libraryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book_layout);

        libraryManager = new LibraryManager(this);

        final EditText bookTitleEditText = (EditText)findViewById(R.id.book_title);
        final EditText bookAuthorEditText = (EditText)findViewById(R.id.book_author);

        findViewById(R.id.add_book_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookTitle = bookTitleEditText.getText().toString().trim();
                if(bookTitle.equals(""))
                    bookTitle = null;
                String bookAuthor = bookAuthorEditText.getText().toString().trim();
                if(bookAuthor.equals(""))
                    bookAuthor = null;
                long result = libraryManager.addBook(bookTitle, bookAuthor);
                if(result == -1){
                    Toast.makeText(AddBookActivity.this, "Inserire sia nome che autore.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddBookActivity.this, "Libro aggiunto!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        libraryManager.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        libraryManager.close();
    }
}
