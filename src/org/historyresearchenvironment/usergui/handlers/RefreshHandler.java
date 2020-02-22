/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.util.logging.Logger;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*    */ import org.eclipse.e4.core.di.annotations.Execute;
/*    */ import org.eclipse.e4.core.services.events.IEventBroker;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefreshHandler
/*    */ {
/*    */   @Inject
/*    */   private static IEventBroker eventBroker;
/* 19 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   private ScopedPreferenceStore store;
/*    */   
/*    */   @Execute
/*    */   public void execute() {
/* 24 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/* 25 */     String perNo = this.store.getString("PERNO");
/* 26 */     LOGGER.info("Refreshing Person number " + perNo);
/* 27 */     eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(Integer.parseInt(perNo)));
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\RefreshHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */