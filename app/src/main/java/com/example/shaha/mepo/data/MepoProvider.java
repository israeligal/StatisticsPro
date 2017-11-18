package com.example.shaha.mepo.data;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.shaha.mepo.data.MepoContracts.EventsEntry;

public class MepoProvider extends ContentProvider {
    private MepoDbHelper mMepoDbHelper;

    /**
     * URI matcher code for the content URI for the pets table
     */
    private static final int EVENTS = 100;

    /**
     * URI matcher code for the content URI for a single pet in the pets table
     */
    private static final int EVENT_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(MepoContracts.CONTENT_AUTHORITY, MepoContracts.PATH_EVENTS, EVENTS);
        sUriMatcher.addURI(MepoContracts.CONTENT_AUTHORITY, MepoContracts.PATH_EVENTS + "/#", EVENT_ID);




    }
    @Override
    public boolean onCreate() {
        mMepoDbHelper = new MepoDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mMepoDbHelper.getWritableDatabase();

        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch(match){
            case EVENTS:
                cursor = database.query(EventsEntry.TABLE_NAME,projection,null, null, null, null, sortOrder);
                break;
            case EVENT_ID:
                selection = EventsEntry.COLUMN_EVENT_FIREBASE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(EventsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Mepo Provider Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match){
            case EVENTS:
                return EventsEntry.CONTENT_LIST_TYPE;
            case EVENT_ID:
                return EventsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Mepo Provider Unknown uri "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case EVENTS:
                return insertEvent(uri,values);
            default:
                throw new IllegalArgumentException("Mepo Provider Insertion to database got invalid uri " + uri);
        }
    }

    private Uri insertEvent(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = mMepoDbHelper.getWritableDatabase();
        long id = database.insertWithOnConflict(EventsEntry.TABLE_NAME, null, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mMepoDbHelper.getWritableDatabase();
        long rowDel = database.delete(EventsEntry.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mMepoDbHelper.getWritableDatabase();
        long rowsUpdated = database.update(EventsEntry.TABLE_NAME,values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
