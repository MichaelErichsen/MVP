package org.historyresearchenvironment.usergui.clientinterface;

import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;

public abstract interface BusinessLayerInterface
{
  public abstract ServerResponse callBusinessLayer(ServerRequest paramServerRequest);
}


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\clientinterface\BusinessLayerInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */