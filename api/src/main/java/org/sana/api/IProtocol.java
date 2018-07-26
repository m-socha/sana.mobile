package org.sana.api;

import java.util.Date;

public interface IProtocol extends IModel {
    String getTitle();

    Date getDateModified();

    boolean getSelected();
}
