package org.sana.android.activity.protocol_builder;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.sana.R;
import org.sana.android.service.protocol_builder.ProtocolBuilderProcedureService;
import org.sana.core.protocol_builder.Procedure;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProtocolListActivity extends ListActivity {

    private ProtocolBuilderProcedureService mProcedureService = new ProtocolBuilderProcedureService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_list);

        mProcedureService.requestService(new ProtocolBuilderProcedureService.Callback() {
            @Override
            public void onSuccess(List<Procedure> procedures) {
                ProtocolListActivityAdapter adapter = new ProtocolListActivityAdapter(ProtocolListActivity.this, procedures);
                setListAdapter(adapter);
            }

            @Override
            public void onFailure() {
                Log.d("ProtocolActivity", "Did not fetch protocols");
            }
        });
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
    }

    private static class ProtocolListActivityAdapter extends ArrayAdapter<Procedure> {
       private Context mContext;
       private List<Procedure> mProcedures;

       public ProtocolListActivityAdapter(Context context, List<Procedure> procedures) {
           super(context, 0, procedures);
           mContext = context;
           mProcedures = procedures;
       }

       @Override
       public View getView(int position, View procedureItem, ViewGroup parent) {
           if (procedureItem == null) {
               procedureItem = LayoutInflater.from(mContext).inflate(R.layout.protocol_item, parent, false);
           }

           Procedure procedure = mProcedures.get(position);

           TextView textTitle = (TextView) procedureItem.findViewById(R.id.text_title);
           textTitle.setText(procedure.title);

           TextView textDate = (TextView) procedureItem.findViewById(R.id.text_date_modified);
           String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(procedure.last_modified);
           String lastModified = mContext.getString(R.string.pb_last_modified, formattedDate);
           textDate.setText(lastModified);

           CheckBox checkBoxSelected = (CheckBox) procedureItem.findViewById(R.id.check_box_selected);
           checkBoxSelected.setSelected(false);

           return procedureItem;
        }
    }
}
