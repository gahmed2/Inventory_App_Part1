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

package com.example.gehad.bookstore1.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Books app.
 */
//final class won't be extended or implemented again by another classes, as it contains
//constants for our database
public final class BookContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty private constructor.
    private BookContract(){}

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

        /** Name of database table for books */
        public final static String TABLE_NAME = "Books";

        /**
         * Unique ID number for the book (only for use in the database table).
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the book.
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_NAME = "Book_Name";

        /**
         * Price of the book.
         * Type: REAL
         */
        public final static String COLUMN_BOOK_PRICE = "Price_USD";

        /**
         * Quantity of the book.
         * Type: INTEGER
         */
        public final static String COLUMN_BOOK_QUANTITY = "Quantity";

        /**
         * Supplier Name of the book.
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_SUPP_NAME = "Supplier_Name";

        /**
         * Supplier Phone Number of the book.
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_SUPP_NUM = "Supplier_Phone_Number";
    }
}
