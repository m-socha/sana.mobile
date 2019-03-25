package org.sana.android.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.sana.R;
import org.sana.android.app.Locales;
import org.sana.android.procedure.Procedure;
import org.sana.android.procedure.ProcedureParseException;
import org.sana.android.provider.Patients;
import org.sana.android.provider.Procedures;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

//TODO

/**
 * Application utilities
 *
 * @author Sana Development Team
 */
public class SanaUtil {
    public static final String TAG = SanaUtil.class.getSimpleName();

    private static final String[] PROJECTION = new String[]{
            Procedures.Contract._ID,
            Procedures.Contract.TITLE,
            Procedures.Contract.AUTHOR,
            Procedures.Contract.VERSION
    };

    private static final String alphabet =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Generates a random string.
     *
     * @param prefix a set string to prepend
     * @param length the total length of the
     * @return a new randomized string.
     */
    public static String randomString(String prefix, int length) {
        return randomString(prefix, length, alphabet);
    }

    /**
     * Generates a random string in a specified alphabet.
     *
     * @param prefix   a set string to prepend
     * @param length   the total length of the
     * @param alphabet the set of valid characters
     * @return a new random string
     */
    public static String randomString(String prefix, int length, String alphabet) {
        StringBuilder sb = new StringBuilder(prefix);
        Random r = new Random();
        int alphabetlength = alphabet.length();

        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(r.nextInt(alphabetlength - 1)));
        }

        return sb.toString();
    }

    /**
     * Creates an error message as a dialog.
     *
     * @param context the current Context
     * @param message the error message
     */
    public static void errorAlert(Context context, String message) {
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing())
                createDialog(context, "Error", message).show();
        }
    }

    /**
     * Creates a message dialog.
     *
     * @param context the current Context
     * @param title   the dialog title
     * @param message the dialog message
     * @return a new dialogf for alerting the user.
     */
    public static AlertDialog createDialog(Context context, String title,
                                           String message) {
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setPositiveButton(context.getResources().getString(
                R.string.general_ok), null);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        return dialogBuilder.create();
    }

    public static AlertDialog okCancelDialog(Context context, String title,
                                             String message, DialogInterface.OnClickListener okCancel) {
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setPositiveButton(context.getResources().getString(
                R.string.general_ok), okCancel);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setNegativeButton(context.getResources().getString(
                R.string.general_cancel), okCancel);
        return dialogBuilder.create();
    }

    // TODO

    /**
     * Retrieves the value for a xml Node attribute or a default if not found.
     *
     * @param node         The Node to fetch the value from.
     * @param name         The attribute name.
     * @param defaultValue The default value to return if not found.
     * @return and attribute value or a default if not found.
     */
    public static String getNodeAttributeOrDefault(Node node, String name,
                                                   String defaultValue) {
        NamedNodeMap attributes = node.getAttributes();
        Node valueNode = attributes.getNamedItem(name);
        String value = defaultValue;
        if (valueNode != null)
            value = valueNode.getNodeValue();
        return value;
    }

    // TODO

    /**
     * Retrieves the value for a xml Node attribute or fails if not found.
     *
     * @param <T>  the exception type to throw
     * @param node The Node to fetch the value from.
     * @param name The attribute name.
     * @param e    an Exception instance
     * @return the attribute value
     * @throws T
     */
    public static <T extends Exception> String getNodeAttributeOrFail(Node node,
                                                                      String name, T e) throws T {
        NamedNodeMap attributes = node.getAttributes();
        Node valueNode = attributes.getNamedItem(name);
        if (valueNode == null)
            throw e;
        return valueNode.getNodeValue();
    }

    /**
     * Utility method for deleting all the elements from a given content URI.
     * You have to provide the name of the primary key column.
     *
     * @param ctx        the context whose content resolver to use to lookup the URI
     * @param contentUri the content URI to delete all the items from
     * @param idColumn   the column of the primary key for the URI
     */
    private static void deleteContentUri(Context ctx, Uri contentUri,
                                         String idColumn) {
        ctx.getContentResolver().delete(contentUri, null, null);
    }

    /**
     * Deletes all stored user content from the database including:
     * <ul>
     * <li>Procedures</li>
     * <li>SavedProcedures</li>
     * <li>Images</li>
     * <li>Sounds</li>
     * <li>Notifications</li>
     * </ul>
     *
     * @param ctx the Context where the data is stored
     */
    public static void clearDatabase(Context ctx) {
        /*
        deleteContentUri(ctx, Procedures.CONTENT_URI,
                Procedures.Contract._ID);
        deleteContentUri(ctx, Encounters.CONTENT_URI,
                Encounters.Contract._ID);
        deleteContentUri(ctx, ImageSQLFormat.CONTENT_URI,
                ImageSQLFormat._ID);
        deleteContentUri(ctx, SoundSQLFormat.CONTENT_URI,
                SoundSQLFormat._ID);
        deleteContentUri(ctx, Notifications.CONTENT_URI,
                Notifications.Contract._ID);
        deleteContentUri(ctx, BinarySQLFormat.CONTENT_URI,
                BinarySQLFormat._ID);
        if (SanaDB.DATABASE_VERSION > 4)
            deleteContentUri(ctx, Observations.CONTENT_URI, 
                    Observations.Contract._ID);
        */
    }

    /**
     * Removes all stored patient information
     *
     * @param ctx the Context where the data is stored
     */
    public static void clearPatientData(Context ctx) {
        deleteContentUri(ctx, Patients.CONTENT_URI,
                Patients.Contract._ID);
    }

    /**
     * Inserts a procedure into the data store from a resource id
     *
     * @param ctx the Context where the data is stored
     * @param id  the raw resource id
     */
    private static void insertProcedureFromResourceId(Context ctx, int id) {
        try {
            InputStream rs = ctx.getResources().openRawResource(id);
            byte[] data = new byte[rs.available()];
            rs.read(data);

            String xmlFullProcedure = new String(data);
            Procedure procedure = Procedure.fromXMLString(xmlFullProcedure);
            insertProcedure(procedure, xmlFullProcedure, ctx);
        } catch (Exception e) {
            Log.e(TAG, "Couldn't add procedure with resource id=" + id
                    + " to db. Exception : " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Inserts a procedure into the data store from an sd card
     *
     * @param ctx the Context where the data is stored
     * @param location the location of the resource
     *
     * @throws IOException
     * @throws ProcedureParseException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static Integer insertProcedureFromSd(final Context ctx, String location)
            throws IOException, ParserConfigurationException, SAXException,
            ProcedureParseException {
        Log.v(TAG, location);

        FileInputStream rs = new FileInputStream(location);
        byte[] data = new byte[rs.available()];
        rs.read(data);

        String xmlFullProcedure = new String(data);
        Procedure procedure = Procedure.fromXMLString(xmlFullProcedure);
        insertProcedure(procedure, xmlFullProcedure, ctx);
        return 0;
    }

    /**
     * Inserts a procedure into the data store from its procedure XML
     *
     * @param ctx the Context where the data is stored
     * @param procedureXML the xml of the procedure
     */
    public static void insertProcedureFromXML(final Context ctx, String procedureXML) {
        try {
            Procedure procedure = Procedure.fromXMLString(procedureXML);
            insertProcedure(procedure, procedureXML, ctx);
        } catch (Exception e) {
            Log.e(TAG, "Couldn't add procedure with xml=" + procedureXML
                    + " to db. Exception : " + e.toString());
            e.printStackTrace();
        }
    }

    private static void insertProcedure(Procedure procedure, String procedureXML, Context ctx) {
        String title = procedure.getTitle();
        String author = procedure.getAuthor();
        String guid = procedure.getGuid();
        String version = procedure.getVersion();

        final ContentValues cv = new ContentValues();
        cv.put(Procedures.Contract.TITLE, title);
        cv.put(Procedures.Contract.AUTHOR, author);
        cv.put(Procedures.Contract.UUID, guid);
        cv.put(Procedures.Contract.VERSION, version);
        cv.put(Procedures.Contract.PROCEDURE, procedureXML);

        if (searchDuplicateTitleAuthor(ctx, title, author)) {
            Log.i(TAG, "Duplicate found! Updating...");
            // TODO Versioning
            ctx.getContentResolver().update(Procedures.CONTENT_URI,
                    cv,
                    "(title LIKE\"" + title + "\")",
                    null);
            Log.i(TAG, "Updated");
        } else {
            Log.i(TAG, "Inserting record.");
            ctx.getContentResolver().insert(
                    Procedures.CONTENT_URI, cv);
        }
    }

    private static boolean searchDuplicateTitleAuthor(Context ctx, String title,
                                                      String author) {
        Cursor cursor = null;
        try {
            cursor = ctx.getContentResolver().query(
                    Procedures.CONTENT_URI, PROJECTION,
                    "(title LIKE\"" + title + "\")", null, null);
            if (cursor.getCount() > 0) {
                return true;
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null)
                cursor.close();
        }
        return false;
    }


    /**
     * Loading Sana with XML-described procedures is currently hard-coded. New
     * files can be added or removed here.
     */
    public static void loadDefaultDatabase(Context ctx) {
        /*
         * insertProcedureFromResourceId(ctx, R.raw.bronchitis); insertProcedureFromResourceId(ctx,
         * R.raw.cervicalcancer); insertProcedureFromResourceId(ctx, R.raw.surgery_demo);
         * insertProcedureFromResourceId(ctx, R.raw.tbcontact); insertProcedureFromResourceId(ctx,
         * R.raw.multiupload_test);
         * insertProcedureFromResourceId(ctx, R.raw.upload_test); insertProcedureFromResourceId(ctx,
         * R.raw.hiv); insertProcedureFromResourceId(ctx, R.raw.cervicalcancer);
         * insertProcedureFromResourceId(ctx, R.raw.prenatal); insertProcedureFromResourceId(ctx,
         * R.raw.surgery); insertProcedureFromResourceId(ctx, R.raw.derma);
         * insertProcedureFromResourceId(ctx, R.raw.teleradiology); insertProcedureFromResourceId(ctx,
         * R.raw.ophthalmology); insertProcedureFromResourceId(ctx, R.raw.tbcontact2);
         * insertProcedureFromResourceId(ctx, R.raw.tbpatient); insertProcedureFromResourceId(ctx,
         * R.raw.oral_cancer);

        insertProcedureFromResourceId(ctx, R.raw.cvd_protocol);
        insertProcedureFromResourceId(ctx, R.raw.api_test);
        insertProcedureFromResourceId(ctx, R.raw.ssi_two_site);
        insertProcedureFromResourceId(ctx, R.raw.audio_upload_test);
        */
        //insertProcedureFromResourceId(ctx, R.raw.demonstration);
        /*
        insertProcedureFromResourceId(ctx, R.raw.chain_test1);
        insertProcedureFromResourceId(ctx, R.raw.chain_test2);
        insertProcedureFromResourceId(ctx, R.raw.api_test_entry);
        insertProcedureFromResourceId(ctx, R.raw.api_test_select);
        */
        /* Haiti procedures */
        //insertProcedureFromResourceId(ctx, R.raw.ssi);
    }

    /**
     * Returns true if the phone has telphony or wifi service
     *
     * @param c - The current context
     * @return true if Android has either a wifi or cellular connection active
     */
    public static boolean checkConnection(Context c) {
        try {
            TelephonyManager telMan = (TelephonyManager) c.getSystemService(
                    Context.TELEPHONY_SERVICE);
            WifiManager wifiMan = (WifiManager) c.getSystemService(
                    Context.WIFI_SERVICE);

            if (telMan != null && wifiMan != null) {
                int dataState = telMan.getDataState();
                if (dataState == TelephonyManager.DATA_CONNECTED ||
                        (wifiMan.isWifiEnabled() && wifiMan.pingSupplicant()))
                    return true;
            }

            return false;
        } catch (Exception e) {
            Log.e(TAG, "Exception in checkConnection(): " + e.toString());
            return false;
        }
    }

    /**
     * Utility for creating an returning a dialog with no click listener
     *
     * @param c            The Context the dialog will be created in
     * @param alertMessage The dialog text
     * @return a new AlertDialog with no listener
     */
    public static AlertDialog createAlertMessage(Context c, String alertMessage) {
        return createAlertMessage(c, alertMessage, null);
    }

    /**
     * Utility for creating an returning a dialog with a listener for receiving
     * click value.
     *
     * @param c            The Context the dialog will be created in
     * @param alertMessage The dialog text
     * @param listener     A listener for receiving click events, may be <b>null</b>
     * @return a new AlertDialog with a specified listener
     */
    public static AlertDialog createAlertMessage(Context c, String alertMessage,
                                                 DialogInterface.OnClickListener listener) {
        Locales.updateLocale(c, c.getString(R.string.force_locale));
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(alertMessage).setCancelable(false)
                .setPositiveButton(
                        c.getResources().getString(R.string.general_ok), listener);
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    /**
     * Format a list of primary keys into a SQLite-formatted list of ids. Ex
     * 1,2,3 is formatted as (1,2,3)
     */
    public static String formatPrimaryKeyList(List<?> idList) {
        StringBuilder sb = new StringBuilder("(");
        Iterator<?> it = idList.iterator();
        while (it.hasNext()) {
            sb.append(String.valueOf(it.next()));
            if (it.hasNext()) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Convenience wrapper around Log.d to print a debug string as:
     * <code>onActivityResult: requestCode = <b>value</b>, resultCode = <b>value</b></code>
     *
     * @param tag         THe calling classes tag
     * @param requestCode the request code used when launching the Activity
     * @param resultCode  the result code returned by the Activity
     */
    public static void logActivityResult(String tag, int requestCode,
                                         int resultCode) {
        Log.d(tag, "onActivityResult: requestCode = " + requestCode
                + ", resultCode = " + resultCode);
    }

    public static final boolean exportDatabase(Context ctx, String dbName) throws IOException {

        boolean result = false;
        File db = ctx.getDatabasePath(dbName);
        File out = new File(Environment.getExternalStorageDirectory(), dbName);
        InputStream is = null;
        OutputStream os = null;

        try {
            is = new BufferedInputStream(new FileInputStream(db));
            os = new BufferedOutputStream(new FileOutputStream(out));
            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }
            result = true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) is.close();
            if (os != null) os.close();
        }
        return result;
    }
}
