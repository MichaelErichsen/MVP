///*    */ package org.historyresearchenvironment.usergui.parts;
///*    */ 
///*    */ import javax.annotation.PostConstruct;
///*    */ import javax.annotation.PreDestroy;
///*    */ import org.eclipse.e4.ui.di.Focus;
///*    */ import org.eclipse.swt.custom.CLabel;
///*    */ import org.eclipse.swt.graphics.Image;
///*    */ import org.eclipse.swt.layout.GridLayout;
///*    */ import org.eclipse.swt.widgets.Composite;
///*    */ import org.eclipse.wb.swt.ResourceManager;
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ public class ImageViewer
///*    */   extends AbstractHREGuiPart
///*    */ {
///*    */   private Image i;
///*    */   
///*    */   protected void callBusinessLayer(int i) {}
///*    */   
///*    */   @PostConstruct
///*    */   public void createControls(Composite parent)
///*    */   {
///* 40 */     parent.setLayout(new GridLayout(1, false));
///*    */     
///* 42 */     CLabel lblNewLabel = new CLabel(parent, 0);
///* 43 */     lblNewLabel
///* 44 */       .setImage(ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "pictures/UK.jpg"));
///* 45 */     CLabel lblNewLabel2 = new CLabel(parent, 0);
///* 46 */     lblNewLabel2.setText("The Church of St Christopher at Willingale Doe, Essex.\r\nFrederick and Charlotte Davis ran the school attached\r\nto the church");
///*    */     
///* 48 */     lblNewLabel2.setFont(getHreFont(parent));
///*    */   }
///*    */   
///*    */   @PreDestroy
///*    */   public void dispose() {
///* 53 */     this.i.dispose();
///*    */   }
///*    */   
///*    */   @Focus
///*    */   public void setFocus() {}
///*    */   
///*    */   protected void updateGui() {}
///*    */ }
//
//
