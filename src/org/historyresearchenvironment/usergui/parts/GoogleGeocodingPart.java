/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.e4.core.di.annotations.Optional;
/*    */ import org.eclipse.e4.ui.di.UIEventTopic;
/*    */ import org.eclipse.swt.dnd.Clipboard;
/*    */ import org.eclipse.swt.dnd.TextTransfer;
/*    */ import org.eclipse.swt.dnd.Transfer;
/*    */ import org.eclipse.swt.layout.GridData;
/*    */ import org.eclipse.swt.layout.GridLayout;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Display;
/*    */ import org.eclipse.swt.widgets.Label;
/*    */ import org.eclipse.swt.widgets.Text;
/*    */ import org.eclipse.wb.swt.SWTResourceManager;
/*    */ import org.historyresearchenvironment.usergui.providers.GoogleGeocodingProvider;
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
/*    */ public class GoogleGeocodingPart
/*    */   extends AbstractHREGuiPart
/*    */ {
/*    */   private Text textLocation;
/*    */   private Text textCoordinates;
/*    */   private GoogleGeocodingProvider geocoder;
/*    */   
/*    */   protected void callBusinessLayer(int i) {}
/*    */   
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/* 47 */     parent.setLayout(new GridLayout(2, false));
/* 48 */     parent.setFont(getHreFont(parent));
/*    */     
/* 50 */     Label lblLocation = new Label(parent, 0);
/* 51 */     lblLocation.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/* 52 */     lblLocation.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 53 */     lblLocation.setText("Location");
/*    */     
/* 55 */     this.textLocation = new Text(parent, 2048);
/* 56 */     this.textLocation.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/* 57 */     this.textLocation.setText("None");
/* 58 */     this.textLocation.setEditable(false);
/* 59 */     this.textLocation.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*    */     
/* 61 */     Label lblCoordinates = new Label(parent, 0);
/* 62 */     lblCoordinates.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/* 63 */     lblCoordinates.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 64 */     lblCoordinates.setText("Coordinates");
/*    */     
/* 66 */     this.textCoordinates = new Text(parent, 2048);
/* 67 */     this.textCoordinates.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/* 68 */     this.textCoordinates.setEditable(false);
/* 69 */     this.textCoordinates.setText("0, 0");
/* 70 */     this.textCoordinates.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*    */     
/* 72 */     Label lblNewLabel = new Label(parent, 0);
/* 73 */     lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/* 74 */     lblNewLabel.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 75 */     lblNewLabel.setText("Coordinates are copied to the clipboard");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Inject
/*    */   @Optional
/*    */   private void subscribePersonUpdateTopic(@UIEventTopic("LOCATION_UPDATE_TOPIC") String city)
/*    */   {
/* 84 */     this.geocoder = new GoogleGeocodingProvider();
/* 85 */     String coordinates = this.geocoder.geocode(city);
/* 86 */     this.textLocation.setText(city);
/* 87 */     this.textCoordinates.setText(coordinates);
/*    */     
/* 89 */     Display display = Display.getDefault();
/* 90 */     Clipboard cb = new Clipboard(display);
/* 91 */     TextTransfer textTransfer = TextTransfer.getInstance();
/* 92 */     cb.setContents(new Object[] { coordinates }, new Transfer[] { textTransfer });
/*    */   }
/*    */   
/*    */   protected void updateGui() {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\GoogleGeocodingPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */