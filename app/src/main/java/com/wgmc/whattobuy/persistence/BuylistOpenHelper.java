package com.wgmc.whattobuy.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wgmc.whattobuy.pojo.Item;

/**
 * Created by proxie on 31.01.17.
 */

public class BuylistOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "whattobuy";
    private static final int DB_VERSION = 3;

    // items table
    private static final String ITEM_TABLE_NAME = "items";
    private static final String ITEM_TABLE_CREATE = "create table " + ITEM_TABLE_NAME + " (id integer primary key, name text, desc text, cnt real)";

    // shops table
    private static final String SHOP_TABLE_NAME = "shops";
    private static final String SHOP_TABLE_CREATE = "create table " + SHOP_TABLE_NAME + " (id integer primary key, name text, address text, shoptype integer)";

    // shoppinglists table
    private static final String SHOPPINGLIST_TABLE_NAME = "shoppinglist";
    private static final String SHOPPINGLIST_TABLE_CREATE = "create table " + SHOPPINGLIST_TABLE_NAME + " (id integer primary key, name text, shopId integer, dueTo date)";

    // list item assignment table
    private static final String ITEM_LIST_TABLE_NAME = "shoppinglist_item";
    private static final String ITEM_LIST_TABLE_CREATE = "create table " + ITEM_LIST_TABLE_NAME + " (listId integer primary key, itemId integer primary key)";


    public BuylistOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ITEM_TABLE_CREATE);
        sqLiteDatabase.execSQL(SHOP_TABLE_CREATE);
        sqLiteDatabase.execSQL(SHOPPINGLIST_TABLE_CREATE);
        sqLiteDatabase.execSQL(ITEM_LIST_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + ITEM_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + SHOP_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + SHOPPINGLIST_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + ITEM_LIST_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    // <editor-fold desc="Item crud methods">
    public Item getItemById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + ITEM_TABLE_NAME + " where id = " + id, null);

        if (cr != null) {
            if (cr.moveToFirst()) {
                Item i = new Item();
                i.setId(id);
                i.setName(cr.getString(cr.getColumnIndex("name")));
            }
        }

        return null;
    }
    // </editor-fold>
}
