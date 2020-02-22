/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.URLEncoder;
/*    */ import java.util.logging.Logger;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.e4.core.di.annotations.Optional;
/*    */ import org.eclipse.e4.ui.di.UIEventTopic;
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
/*    */ public class GoogleMaps
/*    */   extends AbstractHREGuiPart
/*    */ {
/*    */   private Browser browser;
/*    */   
/*    */   protected void callBusinessLayer(int i) {}
/*    */   
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/* 44 */     parent.setLayout(new GridLayout(2, false));
/* 45 */     this.browser = new Browser(parent, 0);
/* 46 */     this.browser.setLayoutData(new GridData(4, 4, true, true, 2, 1));
/*    */     
/*    */     try
/*    */     {
/* 50 */       this.browser.setUrl("https://www.google.com/maps/place/" + URLEncoder.encode("1205 Dolley Madison Boulevard, McLean, VA, USA", "UTF-8") + "/&output=embed");
/*    */     } catch (UnsupportedEncodingException e1) {
/* 52 */       LOGGER.severe(e1.getMessage());
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   @Inject
/*    */   @Optional
/*    */   private void subscribePersonUpdateTopic(@UIEventTopic("LOCATION_UPDATE_TOPIC") String city)
/*    */   {
/*    */     try
/*    */     {
/* 63 */       this.browser.setUrl("https://www.google.com/maps/place/" + URLEncoder.encode(city, "UTF-8") + "/&output=embed");
/*    */     } catch (UnsupportedEncodingException e) {
/* 65 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */     }
/*    */   }
/*    */   
/*    */   protected void updateGui() {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\GoogleMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */