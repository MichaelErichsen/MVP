/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.util.logging.Logger;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractHreVector
/*    */ {
/* 16 */   protected static final Logger LOGGER = Logger.getLogger("global");
/* 17 */   protected ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/* 18 */     "org.historyresearchenvironment.usergui");
/*    */   
/* 20 */   protected PreparedStatement pst = null;
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\AbstractHreVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */