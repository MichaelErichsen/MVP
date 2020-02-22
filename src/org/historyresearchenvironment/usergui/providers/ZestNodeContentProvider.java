/*    */ package org.historyresearchenvironment.usergui.providers;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.eclipse.jface.viewers.ArrayContentProvider;
/*    */ import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;
/*    */ import org.historyresearchenvironment.usergui.models.ZestPersonNode;
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
/*    */ public class ZestNodeContentProvider
/*    */   extends ArrayContentProvider
/*    */   implements IGraphEntityContentProvider
/*    */ {
/*    */   public Object[] getConnectedTo(Object entity)
/*    */   {
/* 25 */     if ((entity instanceof ZestPersonNode)) {
/* 26 */       ZestPersonNode node = (ZestPersonNode)entity;
/* 27 */       return node.getConnectedTo().toArray();
/*    */     }
/* 29 */     throw new RuntimeException("Type not supported");
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\ZestNodeContentProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */