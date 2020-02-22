/*    */ package org.historyresearchenvironment.usergui.providers;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import org.eclipse.jface.viewers.ILabelProvider;
/*    */ import org.eclipse.jface.viewers.ILabelProviderListener;
/*    */ import org.eclipse.swt.graphics.Image;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FamilySearchLabelProvider
/*    */   implements ILabelProvider
/*    */ {
/* 17 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addListener(ILabelProviderListener listener) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void dispose() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Image getImage(Object element)
/*    */   {
/* 48 */     return null;
/*    */   }
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
/*    */   public String getText(Object element)
/*    */   {
/* 63 */     return null;
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
/* 74 */     LOGGER.info(property);
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   public void removeListener(ILabelProviderListener listener) {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\FamilySearchLabelProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */