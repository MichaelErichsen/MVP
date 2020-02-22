/*    */ package org.historyresearchenvironment.usergui.preferences;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultValuesInitializer
/*    */   extends AbstractPreferenceInitializer
/*    */ {
/*    */   public void initializeDefaultPreferences()
/*    */   {
/* 24 */     ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/* 25 */       "org.historyresearchenvironment.usergui");
/*    */     
/* 27 */     store.setDefault("CSMODE", "DIRECT");
/* 28 */     store.setDefault("DBNAME", "./Sample");
/* 29 */     store.setDefault("HELPSYSTEMPORT", "8081");
/* 30 */     store.setDefault("HREFONT", "1|Segoe UI|12.0|0|WINDOWS|1|-16|0|0|0|400|0|0|0|0|3|2|1|34|Segoe UI");
/* 31 */     store.setDefault("H2PASSWORD", "");
/* 32 */     store.setDefault("H2USERID", "sa");
/* 33 */     store.setDefault("H2TRACELEVEL", "INFO");
/* 34 */     store.setDefault("LOGFILEPATH", ".");
/* 35 */     store.setDefault("LOGLEVEL", "INFO");
/* 36 */     store.setDefault("PERNAME", "John+Doe");
/* 37 */     store.setDefault("PERNO", "1");
/* 38 */     store.setDefault("POLREGID", "1");
/* 39 */     store.setDefault("SERVERADDRESS", "127.0.0.1:8000");
/* 40 */     store.setDefault("SERVERPORT", "8000");
/* 41 */     store.setDefault("TLS", false);
/* 42 */     store.setDefault("UPDATESITE", "http://www.myerichsen.net/HRE/Repository");
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\preferences\DefaultValuesInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */