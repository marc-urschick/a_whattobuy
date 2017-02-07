package com.wgmc.whattobuy.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wgmc.whattobuy.pojo.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by proxie on 31.01.17.
 */

public class BuylistOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "whattobuy";
    private static final int DB_VERSION = 3;

    // <editor-fold desc="items table">
    private static final String ITEM_TABLE_NAME = "items";
    private static final String ITEM_TABLE_COL_ID = "id";
    private static final String ITEM_TABLE_COL_NAME = "name";
    private static final String ITEM_TABLE_COL_INFO = "infos";
    private static final String ITEM_TABLE_COL_AMOUNT = "amount";
    private static final String ITEM_TABLE_COL_CHECKED = "checked";

    private static final String ITEM_TABLE_CREATE = "create table " + ITEM_TABLE_NAME + " (" +
                ITEM_TABLE_COL_ID + " integer primary key, " +
                ITEM_TABLE_COL_NAME + " text, " +
                ITEM_TABLE_COL_INFO + " text, " +
                ITEM_TABLE_COL_AMOUNT + " text, " +
                ITEM_TABLE_COL_CHECKED + " number" +
            ")";
    // </editor-fold>

    // <editor-fold desc="shops table">
    private static final String SHOP_TABLE_NAME = "shops";
    private static final String SHOP_TABLE_COL_ID = "id";
    private static final String SHOP_TABLE_COL_NAME = "name";
    private static final String SHOP_TABLE_COL_ADDRESS = "address";
    private static final String SHOP_TABLE_COL_SHOPTYPE = "shoptype";

    private static final String SHOP_TABLE_CREATE = "create table " + SHOP_TABLE_NAME + " (" +
                SHOP_TABLE_COL_ID + " integer primary key, " +
                SHOP_TABLE_COL_NAME + " text, " +
                SHOP_TABLE_COL_ADDRESS + " text, " +
                SHOP_TABLE_COL_SHOPTYPE + " integer" +
            ")";
    // </editor-fold>

    // <editor-fold desc="shoppinglists table">
    private static final String SHOPPINGLIST_TABLE_NAME = "shoppinglist";
    private static final String SHOPPINGLIST_TABLE_COL_ID = "id";
    private static final String SHOPPINGLIST_TABLE_COL_NAME = "name";
    private static final String SHOPPINGLIST_TABLE_COL_SHOPID = "shop_id";
    private static final String SHOPPINGLIST_TABLE_COL_DUETO = "due_to";

    private static final String SHOPPINGLIST_TABLE_CREATE = "create table " + SHOPPINGLIST_TABLE_NAME + " (" +
                SHOPPINGLIST_TABLE_COL_ID + " integer primary key, " +
                SHOPPINGLIST_TABLE_COL_NAME + " text, " +
                SHOPPINGLIST_TABLE_COL_SHOPID + " integer, " +
                SHOPPINGLIST_TABLE_COL_DUETO + " date" +
            ")";
    // </editor-fold>

    // <editor-fold desc="list item assignment table">
    private static final String ITEM_LIST_TABLE_NAME = "shoppinglist_item";
    private static final String ITEM_LIST_TABLE_COL_LISTID = "list_id";
    private static final String ITEM_LIST_TABLE_COL_ITEMID = "item_id";

    private static final String ITEM_LIST_TABLE_CREATE = "create table " + ITEM_LIST_TABLE_NAME + " (" +
                ITEM_LIST_TABLE_COL_LISTID + " integer primary key, " +
                ITEM_LIST_TABLE_COL_ITEMID + " integer primary key" +
            ")";
    // </editor-fold>


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
    private Item createItemFromCursor(Cursor c) {
        Item i = new Item();

        i.setId(c.getInt(c.getColumnIndex(ITEM_TABLE_COL_ID)));
        i.setName(c.getString(c.getColumnIndex(ITEM_TABLE_COL_NAME)));
        i.setInfos(c.getString(c.getColumnIndex(ITEM_TABLE_COL_INFO)));
        i.setMenge(c.getString(c.getColumnIndex(ITEM_TABLE_COL_AMOUNT)));
        i.setChecked(c.getInt(c.getColumnIndex(ITEM_TABLE_COL_CHECKED)) == 0);

        return i;
    }

    public List<Item> getItemsInList(long listId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + ITEM_LIST_TABLE_NAME + " where " + ITEM_LIST_TABLE_COL_LISTID + " = " + listId, null);
        List<Item> items = new ArrayList<>();

        if (cr != null) {
            while (cr.moveToNext()) {
                items.add(getItemById(
                        cr.getInt(cr.getColumnIndex(ITEM_LIST_TABLE_COL_ITEMID))
                ));
            }
        }

        return items;
    }

    public Item getItemById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + ITEM_TABLE_NAME + " where id = " + id, null);

        if (cr != null) {
            if (cr.moveToFirst()) {
                return createItemFromCursor(cr);
            }
        }

        return null;
    }

    public void createItem(Item i) {
        if (i.getId() > 0)
            return;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues vals = new ContentValues();

        vals.put(ITEM_TABLE_COL_NAME, i.getName());
        vals.put(ITEM_TABLE_COL_INFO, i.getInfos());
        vals.put(ITEM_TABLE_COL_AMOUNT, i.getMenge());
        vals.put(ITEM_TABLE_COL_CHECKED, i.isChecked());

        db.beginTransaction();

        i.setId(db.insert(ITEM_TABLE_NAME, null, vals));

        db.endTransaction();
    }

    public void updateItem(Item i) {
        if (i.getId() < 0)
            return;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        db.beginTransaction();

        db.update(ITEM_TABLE_NAME, values, ITEM_TABLE_COL_ID + " = ?", new String[]{i.getId() + ""});

        db.endTransaction();
    }

    public void deleteItem(Item i) {
        if (i.getId() < 0)
            return;

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        db.execSQL("delete from " + ITEM_LIST_TABLE_NAME + " where " + ITEM_LIST_TABLE_COL_ITEMID + " = " + i.getId());
        db.execSQL("delete from " + ITEM_TABLE_NAME + " where " + ITEM_TABLE_COL_ID + " = " + i.getId());

        db.endTransaction();
    }
    // </editor-fold>
}
