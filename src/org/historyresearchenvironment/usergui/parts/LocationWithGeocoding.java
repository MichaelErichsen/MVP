/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
/*    */ import org.eclipse.e4.ui.di.Focus;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Table;
/*    */ import org.eclipse.swt.widgets.TableColumn;
/*    */ import org.eclipse.swt.widgets.TableItem;
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
/*    */ public class LocationWithGeocoding
/*    */ {
/*    */   private Table table;
/*    */   
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/* 28 */     this.table = new Table(parent, 67584);
/* 29 */     this.table.setHeaderVisible(true);
/* 30 */     this.table.setLinesVisible(true);
/*    */     
/* 32 */     TableColumn tblclmnLocation = new TableColumn(this.table, 0);
/* 33 */     tblclmnLocation.setWidth(219);
/* 34 */     tblclmnLocation.setText("Location");
/*    */     
/* 36 */     TableColumn tblclmnCoordinates = new TableColumn(this.table, 0);
/* 37 */     tblclmnCoordinates.setWidth(225);
/* 38 */     tblclmnCoordinates.setText("Coordinates");
/*    */     
/* 40 */     GoogleGeocodingProvider googleGeocodingProvider = new GoogleGeocodingProvider();
/*    */     
/* 42 */     TableItem tableItem = new TableItem(this.table, 0);
/* 43 */     tableItem.setText(new String[] { "Oslo", googleGeocodingProvider.geocode("Oslo") });
/*    */     
/* 45 */     tableItem = new TableItem(this.table, 0);
/* 46 */     tableItem.setText(new String[] { "Skive", googleGeocodingProvider.geocode("Skive") });
/*    */     
/* 48 */     tableItem = new TableItem(this.table, 0);
/* 49 */     tableItem.setText(new String[] { "Vejby", googleGeocodingProvider.geocode("Vejby") });
/* 50 */     tableItem = new TableItem(this.table, 0);
/* 51 */     tableItem.setText(new String[] { "Adelaide", googleGeocodingProvider.geocode("Adelaide") });
/* 52 */     tableItem = new TableItem(this.table, 0);
/* 53 */     tableItem.setText(new String[] { "Hereford", googleGeocodingProvider.geocode("Hereford") });
/* 54 */     tableItem = new TableItem(this.table, 0);
/* 55 */     tableItem.setText(new String[] { "Hendon", googleGeocodingProvider.geocode("Hendon") });
/* 56 */     tableItem = new TableItem(this.table, 0);
/* 57 */     tableItem.setText(new String[] { "Agger", googleGeocodingProvider.geocode("Agger") });
/* 58 */     tableItem = new TableItem(this.table, 0);
/* 59 */     tableItem.setText(new String[] { "Aggersborg", googleGeocodingProvider.geocode("Aggersborg") });
/* 60 */     tableItem = new TableItem(this.table, 0);
/* 61 */     tableItem.setText(new String[] { "Thorup, Aggersborg", googleGeocodingProvider.geocode("Thorup, Aggersborg") });
/*    */   }
/*    */   
/*    */   @PreDestroy
/*    */   public void dispose() {}
/*    */   
/*    */   @Focus
/*    */   public void setFocus() {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\LocationWithGeocoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */