/*
 *  Copyright (C) 2018 The Android Open Source Project
 *
 *  Author: Gehad Ahmed
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *
 */

package com.example.gehad.bookstore1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gehad.bookstore1.data.BookContract.BookEntry;
import com.example.gehad.bookstore1.data.BookDbHelper;

/**
 * Displays list of books that were entered and stored in the app.
 */
public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaring Floating Action Button and its onClick to open Editor Activity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                startActivity(intent);
            }
        });

        /* To access our database, we instantiate our subclass of SQLiteOpenHelper
         * and pass the context, which is the current activity.
         */
        mDbHelper = new BookDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the books database.
     */
    private void displayDatabaseInfo(){
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        /* Define a projection that specifies which columns from the database
         * you will actually use after this query.
         */
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_SUPP_NAME,
                BookEntry.COLUMN_BOOK_SUPP_NUM };

        // Perform a query on the books table
        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,       // The table to query
                projection,                // The columns to return
                null,            // The columns for the WHERE clause
                null,        // The values for the WHERE clause
                null,           // Don't group the rows
                null,            // Don't filter by row groups
                null);          // The sort order

        TextView displayView = findViewById(R.id.main_text);

        try {
            /* Create a header in the Text View that looks like this:
             * The books table contains <number of rows in Cursor> books.
             * _id - name - price - quantity - supplier name - supplier phone
             *
             * In the while loop below, iterate through the rows of the cursor and display
             * the information from each column in this order.
             */
            displayView.setText(getResources().getString(R.string.TextView_header, cursor.getCount()));
            displayView.append(getResources().getString(R.string.table_header));

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int suppNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPP_NAME);
            int suppPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPP_NUM);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()){
                /* Use that index to extract the String or Int value of the word
                 * at the current row the cursor is on.
                 */
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                float currentPrice = cursor.getFloat(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSuppName = cursor.getString(suppNameColumnIndex);
                String currentSuppPhone = cursor.getString(suppPhoneColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSuppName + " - " +
                        currentSuppPhone);
            }

        }finally {
            /* Always close the cursor when you're done reading from it. This releases all its
             resources and makes it invalid.
             */
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu options from the res/menu/menu_main.xml file.
         * This adds menu items to the app bar.
         */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                // Respond to a click on the "Insert dummy data" menu option
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
