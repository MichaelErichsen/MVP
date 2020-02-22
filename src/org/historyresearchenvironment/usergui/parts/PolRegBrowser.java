/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.net.URLEncoder;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*    */ import org.eclipse.e4.ui.di.Focus;
/*    */ import org.eclipse.swt.browser.Browser;
/*    */ import org.eclipse.swt.layout.GridData;
/*    */ import org.eclipse.swt.layout.GridLayout;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PolRegBrowser
/*    */   extends AbstractHREGuiPart
/*    */ {
/* 24 */   private final String URL = "http://www.politietsregisterblade.dk/component/sfup/?controller=politregisterblade&task=viewRegisterblad&id=";
/*    */   
/*    */   String searchArgument;
/*    */   
/*    */ 
/*    */   public PolRegBrowser()
/*    */   {
/* 31 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void callBusinessLayer(int i) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/*    */     try
/*    */     {
/* 50 */       parent.setLayout(new GridLayout(1, false));
/* 51 */       Browser browser = new Browser(parent, 0);
/* 52 */       browser.setLayoutData(new GridData(4, 4, true, true, 2, 1));
/* 53 */       this.searchArgument = this.store.getString("POLREGID");
/* 54 */       String polRegUrl = "http://www.politietsregisterblade.dk/component/sfup/?controller=politregisterblade&task=viewRegisterblad&id=" + URLEncoder.encode(this.searchArgument, "UTF-8");
/* 55 */       browser.setUrl(polRegUrl);
/*    */     } catch (Exception e) {
/* 57 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */   @PreDestroy
/*    */   public void dispose() {}
/*    */   
/*    */   @Focus
/*    */   public void setFocus() {}
/*    */   
/*    */   protected void updateGui() {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PolRegBrowser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */