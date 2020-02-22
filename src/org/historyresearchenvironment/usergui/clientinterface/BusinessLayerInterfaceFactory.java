/*    */ package org.historyresearchenvironment.usergui.clientinterface;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.util.logging.Logger;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
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
/*    */ public class BusinessLayerInterfaceFactory
/*    */ {
/* 18 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */   public static BusinessLayerInterface getBusinessLayerInterface() {
/* 21 */     ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/* 22 */       "org.historyresearchenvironment.usergui");
/* 23 */     String servertype = store.getString("CSMODE");
/*    */     
/* 25 */     if (servertype.equals("TEST"))
/* 26 */       return new TestBusinessLayerInterface();
/* 27 */     if (servertype.equals("DIRECT"))
/* 28 */       return new DirectBusinessLayerInterface();
/* 29 */     if (servertype.equals("SERVER")) {
/* 30 */       return new ServerBusinessLayerInterface();
/*    */     }
/* 32 */     LOGGER.severe("Invalid CSMODE: " + servertype);
/* 33 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\clientinterface\BusinessLayerInterfaceFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */