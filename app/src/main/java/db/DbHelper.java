package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import db.DatabaseContract.BeerTable;

public final class DbHelper extends SQLiteOpenHelper {


    public DbHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME,null,DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BeerTable.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BeerTable.SQL_DELETE_TABLE);
        onCreate(db);
    }
}
