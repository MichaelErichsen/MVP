/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
/*    */ import org.eclipse.e4.ui.di.Focus;
/*    */ import org.eclipse.swt.layout.GridData;
/*    */ import org.eclipse.swt.layout.GridLayout;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Table;
/*    */ import org.eclipse.swt.widgets.TableColumn;
/*    */ import org.eclipse.swt.widgets.TableItem;
/*    */ import org.eclipse.swt.widgets.Text;
/*    */ import org.historyresearchenvironment.usergui.models.LocationModel;
/*    */ import org.historyresearchenvironment.usergui.models.PlaceNameModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocationH2
/*    */   extends AbstractHREGuiPart
/*    */ {
/*    */   private Table placeNameTable;
/*    */   private TableColumn tblclmnPlacePart;
/*    */   private PlaceNameModel placeNameModel;
/*    */   private Text messageText;
/*    */   private TableColumn tblclmnCoordinates;
/*    */   
/*    */   protected void callBusinessLayer(int i) {}
/*    */   
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/* 39 */     parent.setLayout(new GridLayout(1, false));
/*    */     
/* 41 */     this.placeNameTable = new Table(parent, 67584);
/* 42 */     this.placeNameTable.setLayoutData(new GridData(4, 4, false, true, 1, 1));
/* 43 */     this.placeNameTable.setHeaderVisible(true);
/* 44 */     this.placeNameTable.setLinesVisible(true);
/*    */     
/* 46 */     this.tblclmnPlacePart = new TableColumn(this.placeNameTable, 0);
/* 47 */     this.tblclmnPlacePart.setWidth(266);
/* 48 */     this.tblclmnPlacePart.setText("Place Part");
/*    */     
/* 50 */     this.tblclmnCoordinates = new TableColumn(this.placeNameTable, 0);
/* 51 */     this.tblclmnCoordinates.setWidth(167);
/* 52 */     this.tblclmnCoordinates.setText("Coordinates");
/*    */     
/* 54 */     this.messageText = new Text(parent, 2048);
/* 55 */     this.messageText.setEditable(false);
/* 56 */     this.messageText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 63 */     for (int i = 1; i < 200; i++) {
/*    */       try {
/* 65 */         this.placeNameModel = new PlaceNameModel(i);
/* 66 */         String placeName = this.placeNameModel.getPlaceName();
/*    */         
/* 68 */         if ((placeName != null) && (!placeName.equals(""))) {
/* 69 */           TableItem item = new TableItem(this.placeNameTable, 0);
/* 70 */           item.setText(0, placeName);
/*    */           
/* 72 */           String latLong = this.placeNameModel.getLocationModel().getType9();
/*    */           
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 79 */           item.setText(1, latLong);
/*    */         }
/*    */       }
/*    */       catch (Exception e) {
/* 83 */         LOGGER.info(e.getClass() + " " + e.getMessage());
/*    */       }
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


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\LocationH2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */