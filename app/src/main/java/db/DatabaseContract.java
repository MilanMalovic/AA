package db;

import android.provider.BaseColumns;

public final class DatabaseContract {

    public static final String DATABASE_NAME = "Beer.db";
    public static final int DATABASE_VERSION = 1;

    private DatabaseContract(){}

    public static class BeerTable implements BaseColumns{

        public static final String TABLE_NAME = "beer";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_TIPS = "brewer_tips";


        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_TIPS + " TEXT, " +
                COLUMN_AVATAR + " BLOB)";
        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
