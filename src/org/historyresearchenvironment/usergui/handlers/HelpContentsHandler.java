/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.util.logging.Logger;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*    */ import org.eclipse.e4.core.di.annotations.Execute;
/*    */ import org.eclipse.help.browser.IBrowser;
/*    */ import org.eclipse.help.internal.base.BaseHelpSystem;
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
/*    */ public class HelpContentsHandler
/*    */ {
/* 21 */   private final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */   @Execute
/*    */   public void execute()
/*    */   {
/*    */     try
/*    */     {
/* 29 */       ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/* 30 */         "org.historyresearchenvironment.usergui");
/*    */       
/* 32 */       BaseHelpSystem.ensureWebappRunning();
/* 33 */       String helpURL = "http://127.0.0.1:" + store.getInt("HELPSYSTEMPORT") + "/help/index.jsp";
/*    */       
/* 35 */       BaseHelpSystem.getHelpBrowser(true).displayURL(helpURL);
/*    */     } catch (Exception e) {
/* 37 */       this.LOGGER.severe(e.getClass() + ": " + e.getMessage());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\HelpContentsHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */