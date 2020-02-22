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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerRequest
/*    */ {
/*    */   private String operation;
/*    */   private String modelName;
/*    */   private AbstractHreProvider provider;
/*    */   
/*    */   public ServerRequest() {}
/*    */   
/*    */   public ServerRequest(String operation, String modelName, AbstractHreProvider provider)
/*    */   {
/* 33 */     this.operation = operation;
/* 34 */     this.modelName = modelName;
/* 35 */     this.provider = provider;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public AbstractHreProvider getProvider()
/*    */   {
/* 42 */     return this.provider;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getModelName()
/*    */   {
/* 49 */     return this.modelName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getOperation()
/*    */   {
/* 56 */     return this.operation;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setProvider(AbstractHreProvider provider)
/*    */   {
/* 63 */     this.provider = provider;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setModelName(String modelName)
/*    */   {
/* 71 */     this.modelName = modelName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setOperation(String operation)
/*    */   {
/* 79 */     this.operation = operation;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\serverinterface\ServerRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */