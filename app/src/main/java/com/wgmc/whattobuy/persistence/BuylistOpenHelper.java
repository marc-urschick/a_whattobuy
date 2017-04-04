package com.wgmc.whattobuy.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wgmc.whattobuy.pojo.Item;
import com.wgmc.whattobuy.pojo.Shop;
import com.wgmc.whattobuy.pojo.ShoppingList;
import com.wgmc.whattobuy.pojo.Shoptype;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SocketHandler;

/**
 * Created by proxie on 31.01.17.
 */

public class BuylistOpenHelper extends SQLiteOpenHelper {
    // General Database Settings
    /**
     * Database Name
     */
    private static final String DB_NAME = "wgmc-whattobuy";

    /**
     * Database Version Number
     */
    private static final int DB_VERSION = 1;

    // <editor-fold desc="items table">
    private static final String ITEM_TABLE_NAME = "items";
    private static final String ITEM_TABLE_COL_ID = "id";
    private static final String ITEM_TABLE_COL_NAME = "name";
    private static final String ITEM_TABLE_COL_INFO = "infos";
    private static final String ITEM_TABLE_COL_AMOUNT = "amount";
    private static final String ITEM_TABLE_COL_CHECKED = "checked";
    private static final String ITEM_TABLE_COL_LIST_ID = "listid";

    private static final String ITEM_TABLE_CREATE = "create table " + ITEM_TABLE_NAME + " (" +
                ITEM_TABLE_COL_ID + " integer primary key, " +
                ITEM_TABLE_COL_NAME + " text, " +
                ITEM_TABLE_COL_INFO + " text, " +
                ITEM_TABLE_COL_AMOUNT + " text, " +
                ITEM_TABLE_COL_CHECKED + " number, " +
                ITEM_TABLE_COL_LIST_ID + " integer" +
            ");";
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
            ");";
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
                SHOPPINGLIST_TABLE_COL_DUETO + " long" +
            ");";
    // </editor-fold>

    public BuylistOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ITEM_TABLE_CREATE);
        sqLiteDatabase.execSQL(SHOP_TABLE_CREATE);
        sqLiteDatabase.execSQL(SHOPPINGLIST_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + ITEM_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + SHOP_TABLE_NAME);
        sqLiteDatabase.execSQL("drop table if exists " + SHOPPINGLIST_TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);
    }

    // <editor-fold desc="Item crud methods">
    private Item createItemFromCursor(Cursor c) {
        Item i = new Item();

        i.setId(c.getInt(c.getColumnIndex(ITEM_TABLE_COL_ID)));
        i.setName(c.getString(c.getColumnIndex(ITEM_TABLE_COL_NAME)));
        i.setInfos(c.getString(c.getColumnIndex(ITEM_TABLE_COL_INFO)));
        i.setMenge(c.getString(c.getColumnIndex(ITEM_TABLE_COL_AMOUNT)));
        i.setChecked(c.getInt(c.getColumnIndex(ITEM_TABLE_COL_CHECKED)) != 0);
        i.setListId(c.getInt(c.getColumnIndex(ITEM_TABLE_COL_LIST_ID)));

        return i;
    }

    private ContentValues createValuesForItem(Item i) {
        ContentValues values = new ContentValues();

        values.put(ITEM_TABLE_COL_NAME, i.getName());
        values.put(ITEM_TABLE_COL_INFO, i.getInfos());
        values.put(ITEM_TABLE_COL_AMOUNT, i.getMenge());
        values.put(ITEM_TABLE_COL_CHECKED, i.isChecked());
        values.put(ITEM_TABLE_COL_LIST_ID, i.getListId());

        return values;
    }

    public List<Item> getItemsInList(long listId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + ITEM_TABLE_NAME + " where " + ITEM_TABLE_COL_LIST_ID + " = " + listId, null);
        List<Item> items = new ArrayList<>();

        if (cr != null) {
            while (cr.moveToNext()) {
                items.add(createItemFromCursor(cr));
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
            cr.close();
        }

        return null;
    }

    public void createItem(Item i) {
        if (i.getId() > 0)
            return;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createValuesForItem(i);

        i.setId(db.insert(ITEM_TABLE_NAME, null, values));
        System.out.println(i.getId());
    }

    public void updateItem(Item i) {
        if (i.getId() < 0)
            return;

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createValuesForItem(i);

        db.update(ITEM_TABLE_NAME, values, ITEM_TABLE_COL_ID + " = ?", new String[]{i.getId() + ""});
    }

    public void deleteItem(Item i) {
        if (i.getId() < 0)
            return;

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("delete from " + ITEM_TABLE_NAME + " where " + ITEM_TABLE_COL_ID + " = " + i.getId());
    }
    // </editor-fold>

    // <editor-fold desc="Shop crud methods">
    private Shop createShopFromCursor(Cursor c) {
        Shop s = new Shop(-1);

        s.setId(c.getInt(c.getColumnIndex(SHOP_TABLE_COL_ID)));
        s.setName(c.getString(c.getColumnIndex(SHOP_TABLE_COL_NAME)));
        s.setAddress(c.getString(c.getColumnIndex(SHOP_TABLE_COL_ADDRESS)));
        s.setType(Shoptype.decodeFromId(c.getInt(c.getColumnIndex(SHOP_TABLE_COL_SHOPTYPE))));

        return s;
    }

    private ContentValues createContentValuesForShop(Shop s) {
        ContentValues vals = new ContentValues();

        vals.put(SHOP_TABLE_COL_NAME, s.getName());
        vals.put(SHOP_TABLE_COL_ADDRESS, s.getAddress());
        vals.put(SHOP_TABLE_COL_SHOPTYPE, s.getType().getId());

        return vals;
    }

    public Shop getShopById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + SHOP_TABLE_NAME + " where id = " + id, null);

        if (cr != null) {
            if (cr.moveToFirst()) {
                return createShopFromCursor(cr);
            }
        }

        return null;
    }

    public List<Shop> getAllShops() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + SHOP_TABLE_NAME, null);
        List<Shop> items = new ArrayList<>();

        if (cr != null) {
            while (cr.moveToNext()) {
                items.add(createShopFromCursor(cr));
            }
        }

        return items;
    }

    public void createShop(Shop i) {
        if (i.getId() > 0) {
            updateShop(i);
            return;
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValuesForShop(i);

        i.setId(db.insertOrThrow(SHOP_TABLE_NAME, null, values));
    }

    public void updateShop(Shop i) {
        if (i.getId() < 0) {
            createShop(i);
            return;
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValuesForShop(i);

        db.update(SHOP_TABLE_NAME, values, SHOP_TABLE_COL_ID + " = ?", new String[]{i.getId() + ""});
    }

    public void deleteShop(Shop i) {
        if (i.getId() < 0)
            return;

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("delete from " + SHOP_TABLE_NAME + " where " + SHOP_TABLE_COL_ID + " = " + i.getId());
    }
    // </editor-fold>

    // <editor-fold desc="Shoplist crud methods">
    private ShoppingList createShoppingListFromCursor(Cursor c) {
        ShoppingList s = new ShoppingList(-1L);

        s.setId(c.getLong(c.getColumnIndex(SHOPPINGLIST_TABLE_COL_ID)));
        s.setName(c.getString(c.getColumnIndex(SHOPPINGLIST_TABLE_COL_NAME)));
        s.setDueTo(new Date(c.getLong(c.getColumnIndex(SHOPPINGLIST_TABLE_COL_DUETO))));
        s.setWhereToBuy(getShopById(c.getLong(c.getColumnIndex(SHOPPINGLIST_TABLE_COL_SHOPID))));

        s.getItems().addAll(getItemsInList(s.getId()));

        return s;
    }

    private ContentValues createContentValuesForShoppingList(ShoppingList l) {
        ContentValues vals = new ContentValues();

        vals.put(SHOPPINGLIST_TABLE_COL_NAME, l.getName());
        vals.put(SHOPPINGLIST_TABLE_COL_SHOPID, l.getWhereToBuy().getId());
        vals.put(SHOPPINGLIST_TABLE_COL_DUETO, l.getDueTo().getTime());

        return vals;
    }

    public ShoppingList getListById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + SHOPPINGLIST_TABLE_NAME + " where " + SHOPPINGLIST_TABLE_COL_ID + " = " + id, null);
        ShoppingList list = null;

        if (cr != null) {
            if (cr.moveToFirst()) {
                list = createShoppingListFromCursor(cr);
            }
            cr.close();
        }

        return list;
    }

    public List<ShoppingList> getAllShoppingLists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + SHOPPINGLIST_TABLE_NAME, null);
        List<ShoppingList> list = new ArrayList<>();

        if (cr != null) {
            while (cr.moveToNext()) {
                list.add(createShoppingListFromCursor(cr));
            }
            cr.close();
        }

        return list;
    }

    public void createList(ShoppingList list) {
        if (list.getId() > 0)
            return;

        SQLiteDatabase db = getWritableDatabase();

        list.setId(db.insert(SHOPPINGLIST_TABLE_NAME, null, createContentValuesForShoppingList(list)));
    }

    public void updateList(ShoppingList list) {
        if (list.getId() < 0)
            return;

        SQLiteDatabase db = getWritableDatabase();

        db.update(SHOPPINGLIST_TABLE_NAME, createContentValuesForShoppingList(list), SHOPPINGLIST_TABLE_COL_ID + " = ?", new String[]{list.getId() + ""});
    }

    public void deleteShoppingList(ShoppingList i) {
        if (i.getId() < 0)
            return;

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("delete from " + SHOPPINGLIST_TABLE_NAME + " where " + SHOPPINGLIST_TABLE_COL_ID + " = " + i.getId());
    }
    // </editor-fold>
}
