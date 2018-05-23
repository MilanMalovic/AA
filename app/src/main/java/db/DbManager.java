package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseContract.BeerTable;
import model.Beer;

public class DbManager {

    private static DbManager manager;

    // Constructor
    private DbManager() {
    }


    public static synchronized DbManager getInstance() {

        if (manager == null) {
            manager = new DbManager();
        }
        return manager;
    }


    public static List<Beer> readAll(Context context) {

        List<Beer> lista = new ArrayList<>();

        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BeerTable.TABLE_NAME, null);

        try {

            while (cursor.moveToNext()) {

                String name = cursor.getString(cursor.getColumnIndexOrThrow(BeerTable.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(BeerTable.COLUMN_DESCRIPTION));
                String avatar = cursor.getString(cursor.getColumnIndexOrThrow(BeerTable.COLUMN_AVATAR));
                String brewersTips = cursor.getString(cursor.getColumnIndexOrThrow(BeerTable.COLUMN_TIPS));

                lista.add(new Beer(name, description, avatar, brewersTips));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return lista;
    }


    public static void insertOrUpdate(Context context, List<Beer> lista) {

        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {

            for (int i = 0; i < lista.size(); i++) {


                ContentValues values = new ContentValues();
                values.put(BeerTable.COLUMN_NAME, lista.get(i).getName());
                values.put(BeerTable.COLUMN_DESCRIPTION, lista.get(i).getDescription());
                values.put(BeerTable.COLUMN_AVATAR, lista.get(i).getAvatar());
                values.put(BeerTable.COLUMN_TIPS, lista.get(i).getTips());

                long row = db.update(BeerTable.TABLE_NAME, values, "name = ?", new String[]{lista.get(i).getName()});
                Log.i("UPDATED ROW = ", String.valueOf(row));
                if (row <= 0) {
                    long rowId = db.insert(BeerTable.TABLE_NAME, null, values);
                    Log.i("INSERTED ROW ---- > ", String.valueOf(rowId));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }


    }


}
