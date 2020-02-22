/*    */ package org.historyresearchenvironment.usergui.providers;
/*    */ 
/*    */ import org.eclipse.jface.viewers.ILabelProvider;
/*    */ import org.eclipse.jface.viewers.ILabelProviderListener;
/*    */ import org.eclipse.swt.graphics.Image;
/*    */ import org.historyresearchenvironment.usergui.models.AncestorModel;
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
/*    */ public class AncestorTreeLabelProvider
/*    */   implements ILabelProvider
/*    */ {
/*    */   public void addListener(ILabelProviderListener listener) {}
/*    */   
/*    */   public void dispose() {}
/*    */   
/*    */   public Image getImage(Object element)
/*    */   {
/* 53 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getText(Object element)
/*    */   {
/* 63 */     if ((element instanceof AncestorModel)) {
/* 64 */       AncestorModel item = (AncestorModel)element;
/* 65 */       return item.getPerNo() + ", " + item.getName();
/*    */     }
/* 67 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isLabelProperty(Object element, String property)
/*    */   {
/* 78 */     return false;
/*    */   }
/*    */   
/*    */   public void removeListener(ILabelProviderListener listener) {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\AncestorTreeLabelProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */