package org.sana.core;

import org.sana.api.IProtocol;

import java.util.Date;

public class Protocol extends Model implements IProtocol {
    private String mTitle;
    private Date mDateModified;
    private boolean mSelected;

    @Override
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
       mTitle = title;
    }

    @Override
    public Date getDateModified() {
        return mDateModified;
    }

    public void setDateModified(Date dateModified) {
        mDateModified = dateModified;
    }

    @Override
    public boolean getSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }
}
