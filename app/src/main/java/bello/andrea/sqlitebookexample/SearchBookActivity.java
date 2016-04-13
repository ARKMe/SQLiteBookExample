package bello.andrea.sqlitebookexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchBookActivity extends Activity {

    private LibraryManager libraryManager;
    private BooksAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_book_layout);

        libraryManager = new LibraryManager(this);

        booksAdapter = new BooksAdapter();
        ((ListView) findViewById(R.id.listview)).setAdapter(booksAdapter);


        EditText editText = (EditText)findViewById(R.id.book_search_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Book> books = libraryManager.getAllBooks(s.toString());
                booksAdapter.reset();
                for (Book book : books) {
                    booksAdapter.addBook(book);
                }
                booksAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_books) {
            Intent intent = new Intent(this, AddBookActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private class BooksAdapter extends BaseAdapter{

        private ArrayList<Book> books;

        public BooksAdapter() {
            this.books = new ArrayList<>();
        }

        public void addBook(Book book){
            books.add(book);
        }

        public void reset(){
            this.books = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return books.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.book_layout, null);
            }
            final Book book = books.get(position);
            ((TextView)convertView.findViewById(R.id.book_title)).setText(book.getTitle());
            ((TextView)convertView.findViewById(R.id.book_author)).setText(book.getAuthor());
            convertView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    libraryManager.deleteBook(book);
                    books.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        libraryManager.open();

        List<Book> books = libraryManager.getAllBooks();
        booksAdapter.reset();
        for(Book book : books){
            booksAdapter.addBook(book);
        }
        booksAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        libraryManager.close();
    }

}
