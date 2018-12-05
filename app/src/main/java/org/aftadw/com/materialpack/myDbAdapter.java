package org.aftadw.com.materialpack;

/**
 * Created by Afta Dizca Wahana on 19/02/2018.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class myDbAdapter {
    myDbHelper myhelper;

    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public void saveDataKarton(Activity act,myDbAdapter db, Karton[] karton)
    {
        int id = db.getLastId("id", "savekarton")+1;


        if(db.getDbCount("id", "savekarton")==15){
            db.deleteSaveData(db.getFirstId("id", "savekarton"),"savekarton", "id");
        }
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i<karton.length; i++){
            contentValues.put(myDbHelper.KARTON_ID, id);
            contentValues.put(myDbHelper.KARTON_LINE, karton[i].line.getSelectedItemPosition());
            contentValues.put(myDbHelper.KARTON_PRODUK, karton[i].produk.getSelectedItemPosition());
            contentValues.put(myDbHelper.KARTON_SALDOAWAL, karton[i].saldoAwal.getText().toString());
            contentValues.put(myDbHelper.KARTON_TERIMA, karton[i].terima.getText().toString());
            dbb.insert(myDbHelper.TABLE_SAVE_KARTON, null , contentValues);
        }

        System.out.println("Saved Karton");
        act.invalidateOptionsMenu();
    }


    public Karton[] getDataKarton(Karton[] karton, int id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs= {Integer.toString(id)};
        String[] columns = {myDbHelper.KARTON_PRODUK,myDbHelper.KARTON_LINE,myDbHelper.KARTON_SALDOAWAL,myDbHelper.KARTON_TERIMA};
        Cursor cursor =db.query(myDbHelper.TABLE_SAVE_KARTON,columns,myDbHelper.KARTON_ID +" = ?",whereArgs,null,null,null);
        int i = 0;
        while (cursor.moveToNext()) {
                karton[i].produk.setSelection(cursor.getInt(cursor.getColumnIndex(myDbHelper.KARTON_PRODUK)));
                karton[i].line.setSelection(cursor.getInt(cursor.getColumnIndex(myDbHelper.KARTON_LINE)));
                karton[i].saldoAwal.setText(cursor.getString(cursor.getColumnIndex(myDbHelper.KARTON_SALDOAWAL)));
                karton[i].terima.setText(cursor.getString(cursor.getColumnIndex(myDbHelper.KARTON_TERIMA)));
                i++;
            }
        return karton;
    }

    public int getLastId(String columnsID, String Tablename)
    {
        int i = 0;
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {columnsID};
        Cursor cursor =db.query(Tablename,columns,null,null,null,null,null);
        if(cursor.moveToLast()) {
            i = cursor.getInt(cursor.getColumnIndex(columnsID));
        }
        return i;
    }

    public int getDbCount(String columnsID, String Tablename)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {columnsID};
        Cursor cursor =db.query(Tablename,columns,null,null,null,null,null);
        return cursor.getCount()/8;
    }

    public int getFirstId(String columnsID, String Tablename)
    {
        int i = 0;
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {columnsID};
        Cursor cursor =db.query(Tablename,columns,null,null,null,null,null);
        if(cursor.moveToFirst()){
            i = cursor.getInt(cursor.getColumnIndex(columnsID));
        }
        return i;
    }

    public void deleteSaveData(int id, String tableName, String columnsID)
    {
            SQLiteDatabase dbb = myhelper.getWritableDatabase();
            String[] whereArgs = {Integer.toString(id)};
            dbb.delete(tableName,columnsID +" = ?",whereArgs);

    }

    public void deleteTableKarton(){
        try {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            db.execSQL("DELETE FROM "+myDbHelper.TABLE_SAVE_KARTON);
            db.execSQL(myDbHelper.INSERT_TABLE1);
        }catch (SQLException e){
            System.out.println("Delete Table :"+e);
        }

    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "mydb.db";    // Database Name

        private static final String TABLE_SAVE_KARTON = "savekarton";
        private static final String TABLE_SAVE_STRAW = "savestraw";
        private static final String TABLE_ARCHIVE_KARTON = "archivekarton";
        private static final String TABLE_ARCHIVE_STRAW = "archivestraw";// Table Name

        private static final String KARTON_ID = "id";
        private static final String KARTON_LINE = "line";
        private static final String KARTON_PRODUK = "produk";
        private static final String KARTON_SALDOAWAL = "saldoawal";
        private static final String KARTON_TERIMA = "terima";

        private static final String STRAW_ITEMNAME = "itemname";
        private static final String STRAW_ITEMSTATUS = "itemstatus";
        private static final String STRAW_SALDOAWAL = "saldoawal";
        private static final String STRAW_TERIMA = "terima";
        private static final String STRAW_COUNT = "count";

        private static final int DATABASE_Version = 1;    // Database Version

        private static final String CREATE_TABLE1 = "CREATE TABLE " + TABLE_SAVE_KARTON +" ("+ KARTON_ID +"	TEXT,"+ KARTON_LINE +"	TEXT,"+ KARTON_PRODUK +"	TEXT,"+ KARTON_SALDOAWAL +"	TEXT,"+ KARTON_TERIMA +" TEXT )";
        private static final String CREATE_TABLE2 = "CREATE TABLE " + TABLE_SAVE_STRAW +" ("+ STRAW_ITEMNAME +"	TEXT,"+ STRAW_ITEMSTATUS +"	TEXT, "+ STRAW_SALDOAWAL +"	TEXT,"+ STRAW_TERIMA +"	TEXT,"+ STRAW_COUNT +" TEXT )";
        private static final String CREATE_TABLE3 = "CREATE TABLE " + TABLE_ARCHIVE_KARTON +" ("+ KARTON_ID +"	INTEGER,"+ KARTON_LINE +"	TEXT, "+ KARTON_PRODUK +"	INTEGER,"+ KARTON_SALDOAWAL +"	TEXT,"+ KARTON_TERIMA +" TEXT )";
        private static final String CREATE_TABLE4 = "CREATE TABLE " + TABLE_ARCHIVE_STRAW +" ("+ KARTON_ID +"	INTEGER,"+ STRAW_ITEMNAME +"	TEXT,"+ STRAW_ITEMSTATUS +"	TEXT, "+ STRAW_SALDOAWAL +"	TEXT,"+ STRAW_TERIMA +"	TEXT,"+ STRAW_COUNT +" TEXT )";
        private static final String DROP_TABLE_SAVE_KARTON = "DROP TABLE IF EXISTS "+TABLE_SAVE_KARTON;
        private static final String INSERT_TABLE1 = "INSERT INTO "+TABLE_SAVE_KARTON+" VALUES (1,'0','0','',''),(1,'0','0','',''),(1,'0','0','',''),(1,'0','0','',''),(1,'0','0','',''),(1,'0','0','',''),(1,'0','0','',''),(1,'0','0','','')";
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE1);
                db.execSQL(CREATE_TABLE2);
                db.execSQL(CREATE_TABLE3);
                db.execSQL(CREATE_TABLE4);
                db.execSQL(INSERT_TABLE1);
                Toast.makeText(context,"DB Created",Toast.LENGTH_SHORT);
            } catch (Exception e) {
                Toast.makeText(context,"SQLError : "+e,Toast.LENGTH_SHORT);
            }
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}