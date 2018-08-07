package org.sana.android.activity.protocol_builder;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.sana.R;
import org.sana.api.protocol_builder.IProtocol;
import org.sana.core.protocol_builder.Protocol;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProtocolListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_list);

        List<IProtocol> protocols = new ArrayList();

        Protocol p1 = new Protocol();
        p1.setTitle("Sample Procedure 1");
        p1.setDateModified(new Date(118, 6, 21));
        protocols.add(p1);

        Protocol p2 = new Protocol();
        p2.setTitle("Sample Procedure 2");
        p2.setDateModified(new Date(118, 6, 22));
        p2.setSelected(true);
        protocols.add(p2);

        Protocol p3 = new Protocol();
        p3.setTitle("Sample Procedure 3");
        p3.setDateModified(new Date(118, 6, 22));
        protocols.add(p3);

        ProtocolListActivityAdapter adapter = new ProtocolListActivityAdapter(this, protocols);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
    }

    private static class ProtocolListActivityAdapter extends ArrayAdapter<IProtocol> {
       private Context mContext;
       private List<IProtocol> mProtocols;

       public ProtocolListActivityAdapter(Context context, List<IProtocol> protocols) {
           super(context, 0, protocols);
           mContext = context;
           mProtocols = protocols;
       }

       @Override
       public View getView(int position, View protocolItem, ViewGroup parent) {
           if (protocolItem == null) {
               protocolItem = LayoutInflater.from(mContext).inflate(R.layout.protocol_item, parent, false);
           }

           IProtocol protocol = mProtocols.get(position);

           TextView textTitle = (TextView) protocolItem.findViewById(R.id.text_title);
           textTitle.setText(protocol.getTitle());

           TextView textDate = (TextView) protocolItem.findViewById(R.id.text_date_modified);
           String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(protocol.getDateModified());
           String lastModified = mContext.getString(R.string.pb_last_modified, formattedDate);
           textDate.setText(lastModified);

           CheckBox checkBoxSelected = (CheckBox) protocolItem.findViewById(R.id.check_box_selected);
           checkBoxSelected.setSelected(protocol.getSelected());

           return protocolItem;
        }
    }
}
