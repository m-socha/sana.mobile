package org.sana.api.protocol_builder;

import org.sana.api.IModel;

import java.util.Date;

public interface IProtocol extends IModel {
    String getTitle();

    Date getDateModified();

    boolean getSelected();
}
