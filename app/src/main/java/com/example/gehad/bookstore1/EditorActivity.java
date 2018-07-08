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

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gehad.bookstore1.data.BookContract.BookEntry;
import com.example.gehad.bookstore1.data.BookDbHelper;

/**
 * Allows user to create a new book or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    // EditText field to enter the book's name
    private EditText mNameEditText;

    // EditText field to enter the book's price
    private EditText mPriceEditText;

    // EditText field to enter the book's quantity
    private EditText mQuantityEditText;

    // EditText field to enter the book's supplier name
    private EditText mSpplierEditText;

    // EditText field to enter the book's supplier phone
    private EditText mSuppPhoneEditText;

    // Will be assigned True, if all Mandatory fields are filled
    private boolean isFilled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from.
        mNameEditText = findViewById(R.id.bookNameEdit);
        mPriceEditText = findViewById(R.id.bookPriceEdit);
        mQuantityEditText = findViewById(R.id.bookQuantityEdit);
        mSpplierEditText = findViewById(R.id.bookSuppEdit);
        mSuppPhoneEditText = findViewById(R.id.bookSuppPhoneEdit);
    }

    /**
     * Get user input from editor and save new book into database.
     */
    private void insertBook(){
        isFilled = false;

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String supplierNameString = mSpplierEditText.getText().toString().trim();
        String supplierPhoneString = mSuppPhoneEditText.getText().toString().trim();

        // Create database helper
        BookDbHelper mDbHelper = new BookDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        /* Create a ContentValues object where column names are the keys,
         * and book attributes from the editor are the values.
         */
        ContentValues values = new ContentValues();

        //Check first that all mandatory fields (Name, Price and Quantity) are not null.
        if(TextUtils.isEmpty(nameString) && TextUtils.isEmpty(priceString) &&
                TextUtils.isEmpty(quantityString)){
            Toast.makeText(this, "Please Fill in All mandatory fields",
                    Toast.LENGTH_LONG).show();
        } else if(TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, "Please Enter the Book Name", Toast.LENGTH_LONG).show();
        } else if(TextUtils.isEmpty(priceString)){
            Toast.makeText(this, "Please Enter the Book Price", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(quantityString)){
            Toast.makeText(this, "Please Enter the Book Quantity", Toast.LENGTH_LONG).show();
        } else {
            // Converting price and quantity from String to float and integer respectively
            float priceValue = Float.parseFloat(priceString);
            int quantityValue = Integer.parseInt(quantityString);

            values.put(BookEntry.COLUMN_BOOK_NAME, nameString);
            values.put(BookEntry.COLUMN_BOOK_PRICE, priceValue);
            values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantityValue);
            values.put(BookEntry.COLUMN_BOOK_SUPP_NAME, supplierNameString);
            values.put(BookEntry.COLUMN_BOOK_SUPP_NUM, supplierPhoneString);

            isFilled = true;
        }
        // Insert a new row for book in the database, returning the ID of that new row.
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving book", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Book saved with row id: " + newRowId,
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Defining Menu to appear in the app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    // Defining Menu items (save button)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save book to database
                insertBook();
                // Exit activity
                if(isFilled)finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
