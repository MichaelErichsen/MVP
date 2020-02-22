/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.util.logging.Logger;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*    */ import org.eclipse.e4.core.services.events.IEventBroker;
/*    */ import org.eclipse.swt.graphics.Font;
/*    */ import org.eclipse.swt.graphics.FontData;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Shell;
/*    */ import org.eclipse.wb.swt.SWTResourceManager;
/*    */ import org.historyresearchenvironment.usergui.clientinterface.BusinessLayerInterface;
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
/*    */ public abstract class AbstractHREGuiPart
/*    */ {
/*    */   @Inject
/*    */   protected IEventBroker eventBroker;
/* 30 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */   protected ScopedPreferenceStore store;
/*    */   
/*    */   protected BusinessLayerInterface bli;
/*    */   
/*    */   protected ServerRequest req;
/*    */   
/*    */   protected ServerResponse resp;
/*    */   
/*    */ 
/*    */   protected abstract void callBusinessLayer(int paramInt);
/*    */   
/*    */ 
/*    */   protected Font getHreFont(Composite parent)
/*    */   {
/* 47 */     LOGGER.fine("Get HRE Font");
/*    */     Font font;
/*    */     try
/*    */     {
/* 51 */       this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/* 52 */       String s = this.store.getString("HREFONT");
/*    */       
/* 54 */       LOGGER.fine(s);
/* 55 */       String[] sa = s.split("-");
/* 56 */       for (int i = 0; i < sa.length; i++) {
/* 57 */         LOGGER.fine("sa[" + i + "]: " + sa[i]);
/*    */       }
/* 59 */       String[] sb = sa[0].split("\\|");
/* 60 */       for (int i = 0; i < sb.length; i++) {
/* 61 */         LOGGER.fine("sb[" + i + "]: " + sb[i]);
/*    */       }
/* 63 */       LOGGER.fine("HRE font: " + sb[1] + " " + Math.round(Double.valueOf(sb[2]).doubleValue()) + " " + sb[3]);
/* 64 */       font = SWTResourceManager.getFont(sb[1], (int)Math.round(Double.valueOf(sb[2]).doubleValue()), Integer.parseInt(sb[3]));
/*    */     } catch (NumberFormatException e) { Font font;
/* 66 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/* 67 */       e.printStackTrace();
/*    */       
/* 69 */       font = parent.getShell().getFont();
/* 70 */       FontData fd = font.getFontData()[0];
/* 71 */       font = SWTResourceManager.getFont(fd.getName(), 12, fd.getStyle());
/*    */     }
/* 73 */     return font;
/*    */   }
/*    */   
/*    */   protected abstract void updateGui();
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\AbstractHREGuiPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */