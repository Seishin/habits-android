package co.naughtyspirit.habits.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     * Saving a String value into the SharedPreferences Manager.
     *
     * @param ctx   application's context.
     * @param key   preference's key.
     * @param value preference's value.
     */
    public static void setPreference(Context ctx, String key, String value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();

        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Saving an Int value into the SharedPreferences Manager.
     *
     * @param ctx   application's context.
     * @param key   preference's key.
     * @param value preference's value.
     */
    public static void setPreference(Context ctx, String key, int value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Saving a Boolean value into the SharedPreferences Manager.
     *
     * @param ctx   application's context.
     * @param key   preference's key.
     * @param value preference's value.
     */
    public static void setPreference(Context ctx, String key, boolean value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Saving a Long value into the SharedPreferences Manager.
     *
     * @param ctx   application's context.
     * @param key   preference's key.
     * @param value preference's value.
     */
    public static void setPreference(Context ctx, String key, long value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();

        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Extracting a String value from the SharedPreferences Manager.
     *
     * @param ctx application's context.
     * @param key preference's key.
     */
    public static String getStringPreference(Context ctx, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);

        return sharedPreferences.getString(key, null);
    }

    /**
     * Extracting an Int value from the SharedPreferences Manager.
     *
     * @param ctx application's context.
     * @param key preference's key.
     */
    public static int getIntPreference(Context ctx, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);

        return sharedPreferences.getInt(key, 0);
    }

    /**
     * Extracting an Int value from the SharedPreferences Manager.
     *
     * @param ctx application's context.
     * @param key preference's key.
     * @param defaultValue default return value
     */
    public static int getIntPreference(Context ctx, String key, int defaultValue) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);

        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Extracting a Boolean value from the SharedPreferences Manager.
     *
     * @param ctx application's context.
     * @param key preference's key.
     */
    public static Boolean getBooleanPreference(Context ctx, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * Extracting an Long value from the SharedPreferences Manager.
     *
     * @param ctx application's context.
     * @param key preference's key.
     */
    public static long getLongPreference(Context ctx, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);

        return sharedPreferences.getLong(key, -1);
    }

    /**
     * Removing a value from the SharedPreferences Manager.
     *
     * @param ctx application's context.
     * @param key preference's key.
     */
    public static void removePreference(Context ctx, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();

        editor.remove(key);
        editor.commit();
    }
}