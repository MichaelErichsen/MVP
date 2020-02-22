/*    */ package org.historyresearchenvironment.usergui.clientinterface;
/*    */ 
/*    */ import org.historyresearchenvironment.usergui.businesslogic.SomeBusinessLogic;
/*    */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*    */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DirectBusinessLayerInterface
/*    */   implements BusinessLayerInterface
/*    */ {
/*    */   public ServerResponse callBusinessLayer(ServerRequest request)
/*    */   {
/* 25 */     SomeBusinessLogic sbl = new SomeBusinessLogic(request);
/* 26 */     ServerResponse response = sbl.doSomethingWithRequest();
/* 27 */     return response;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\clientinterface\DirectBusinessLayerInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */