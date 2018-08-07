package org.sana.core.protocol_builder;

import com.google.gson.annotations.Expose;

import org.sana.core.Model;

public class AuthData extends Model {
    @Expose
    public boolean success;

    @Expose
    public String token;
}
