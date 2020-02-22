/*    */ package org.historyresearchenvironment.usergui.serverinterface;
/*    */ 
/*    */ import org.historyresearchenvironment.usergui.providers.AbstractHreProvider;
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
/*    */ 
/*    */ public class ServerResponse
/*    */ {
/*    */   private AbstractHreProvider provider;
/*    */   private int returnCode;
/*    */   private String returnMessage;
/*    */   
/*    */   public ServerResponse() {}
/*    */   
/*    */   public ServerResponse(AbstractHreProvider provider, int returnCode, String returnMessage)
/*    */   {
/* 29 */     this.provider = provider;
/* 30 */     this.returnCode = returnCode;
/* 31 */     this.returnMessage = returnMessage;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public AbstractHreProvider getProvider()
/*    */   {
/* 38 */     return this.provider;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getReturnCode()
/*    */   {
/* 45 */     return this.returnCode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getReturnMessage()
/*    */   {
/* 52 */     return this.returnMessage;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setProvider(AbstractHreProvider provider)
/*    */   {
/* 60 */     this.provider = provider;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setReturnCode(int returnCode)
/*    */   {
/* 68 */     this.returnCode = returnCode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setReturnMessage(String returnMessage)
/*    */   {
/* 76 */     this.returnMessage = returnMessage;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\serverinterface\ServerResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */