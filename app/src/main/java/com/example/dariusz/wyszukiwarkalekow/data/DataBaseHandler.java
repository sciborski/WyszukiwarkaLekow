package com.example.dariusz.wyszukiwarkalekow.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.dariusz.wyszukiwarkalekow.data.model.OAuthEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Seweryn on 30.07.2017.
 */

public class DataBaseHandler extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME = "medicines";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, OAuthEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, OAuthEntity.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <D extends Dao<T, ?>, T> D provideDao(Class<T> clazz){
        try {
            return super.getDao(clazz);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
