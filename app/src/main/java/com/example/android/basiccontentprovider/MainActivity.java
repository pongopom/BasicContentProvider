package com.example.android.basiccontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.basiccontentprovider.data.NameContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText mNameEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNameEditText = findViewById(R.id.nameEditText);
        mResultTextView = findViewById(R.id.resultTextView);
        Button enterButton = findViewById(R.id.enterButton);
        getSupportLoaderManager().initLoader(1, null, this);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                String name = mNameEditText.getText().toString();
                if (name.equals("")) {
                    return;
                }
                contentValues.put(NameContract.nameEntry.COLUMN_TITLE, name);
                getContentResolver().insert(NameContract.CONTENT_URI, contentValues);
            }
        });
    }

    // Using a cursor loader to query on background thread
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri NAME_URI = NameContract.CONTENT_URI;
        return new CursorLoader(this, NAME_URI, null,
                null, null, null);
    }

    //just put data in a textview to keep things simple
    @Override
    public void onLoadFinished(Loader<Cursor> loader
            , Cursor cursor) {
        if (cursor != null) {
            cursor.moveToPosition(-1);
            StringBuilder sb = new StringBuilder();

            try {
                while (cursor.moveToNext()) {
                    int index = cursor.getColumnIndex(NameContract.nameEntry.COLUMN_TITLE);
                    String title = cursor.getString(index);
                    sb.append(title);
                    sb.append("\n");
                }
            } finally {
                mResultTextView.setText(sb);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        //need to implement this to reset data
    }
}
