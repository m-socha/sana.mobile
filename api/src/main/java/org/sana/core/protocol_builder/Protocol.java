package org.sana.core.protocol_builder;

import org.sana.api.protocol_builder.IProtocol;
import org.sana.core.Model;

import java.util.Date;

public class Protocol extends Model implements IProtocol {
    private String title;
    private Date modified;
    private boolean selected;

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
       this.title = title;
    }

    @Override
    public Date getDateModified() {
        return modified;
    }

    public void setDateModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
