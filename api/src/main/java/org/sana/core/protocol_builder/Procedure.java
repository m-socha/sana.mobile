package org.sana.core.protocol_builder;

import com.google.gson.annotations.Expose;

import org.sana.core.Model;

import java.util.Date;

public class Procedure extends Model {
    @Expose
    public int id;

    @Expose
    public String title;

    @Expose
    public String author;

    @Expose
    public Date last_modified;
}
