package org.sana.android.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.sana.android.net.MDSInterface2;

import java.util.List;

/**
 * Pulls the procedures of procedure groups.
 *
 * @author Sana Development Team
 */
public class ProcedureGroupSyncTask extends AsyncTask<List<String>, Void, Integer> {
    private static final String TAG = ProcedureGroupSyncTask.class.getSimpleName();

    ProgressDialog progressDialog;
    private Context context;

    public ProcedureGroupSyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Syncing procedure groups");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(List<String>... params) {
        Log.i(TAG, "Syncing procedure groups");
        List<String> procedureGroupIds = params[0];
        for (String procedureGroupId: procedureGroupIds) {
            MDSInterface2.syncProcedureGroup(this.context, procedureGroupId);
        }
        return 0;
    }


    @Override
    protected void onPostExecute(Integer result) {
        Log.i(TAG, "Completed sync of procedure groups");
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
