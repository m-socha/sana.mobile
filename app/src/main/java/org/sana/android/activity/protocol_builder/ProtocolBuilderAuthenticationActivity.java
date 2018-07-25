package org.sana.android.activity.protocol_builder;

import android.os.Bundle;

import org.sana.R;
import org.sana.android.activity.BaseActivity;

/**
 * Activity for authenticating with protocol builder.
 */
public class ProtocolBuilderAuthenticationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewLocalized(R.layout.activity_protocol_builder_authentication);
    }
}
