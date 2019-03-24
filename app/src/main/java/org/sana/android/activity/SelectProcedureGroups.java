/**
 * Copyright (c) 2013, Sana
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p>
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the Sana nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL Sana BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.sana.android.activity;


import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.sana.R;
import org.sana.core.ProcedureGroup;

import java.util.List;

/**
 * A list activity that lets users initiate a sync for procedure groups.
 *
 * @author Sana Development
 *
 */
public class SelectProcedureGroups extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_procedure_groups);
    }

    private static class SelectProcedureGroupsAdapter extends ArrayAdapter<ProcedureGroup> {
        private Context mContext;
        private List<ProcedureGroup> mProcedureGroups;

        public SelectProcedureGroupsAdapter(Context context, List<ProcedureGroup> procedureGroups) {
            super(context, 0, procedureGroups);
            mContext = context;
            mProcedureGroups = procedureGroups;
        }

        @Override
        public View getView(int position, View procedureItem, ViewGroup parent) {
            if (procedureItem == null) {
                procedureItem = LayoutInflater.from(mContext).inflate(R.layout.procedure_group_item, parent, false);
            }

            ProcedureGroup procedureGroup = mProcedureGroups.get(position);

            TextView textTitle = (TextView) procedureItem.findViewById(R.id.text_title);
            textTitle.setText(procedureGroup.getTitle());

            return procedureItem;
        }
    }
}
