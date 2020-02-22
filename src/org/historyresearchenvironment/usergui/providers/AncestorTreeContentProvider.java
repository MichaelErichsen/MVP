/*    */ package org.historyresearchenvironment.usergui.providers;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ import org.eclipse.jface.viewers.ITreeContentProvider;
/*    */ import org.historyresearchenvironment.tmg.h2.models.Name;
/*    */ import org.historyresearchenvironment.tmg.h2.models.Person;
/*    */ import org.historyresearchenvironment.usergui.models.AncestorModel;
/*    */ import org.historyresearchenvironment.usergui.models.AncestorVector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AncestorTreeContentProvider
/*    */   extends AbstractHreProvider
/*    */   implements ITreeContentProvider
/*    */ {
/*    */   public Object[] getChildren(Object parentElement)
/*    */   {
/* 25 */     if ((parentElement instanceof AncestorModel)) {
/* 26 */       AncestorModel a = (AncestorModel)parentElement;
/* 27 */       List<AncestorModel> b = new ArrayList();
/* 28 */       Person person = new Person(null, a.getFather());
/* 29 */       Name name = new Name(this.conn, a.getFather());
/* 30 */       AncestorModel item = new AncestorModel(person.getPerNo(), name.getSrnamedisp(), person.getSex(), 
/* 31 */         person.getFather(), person.getMother());
/* 32 */       b.add(item);
/* 33 */       person = new Person(this.conn, a.getMother());
/* 34 */       name = new Name(this.conn, a.getMother());
/* 35 */       item = new AncestorModel(person.getPerNo(), name.getSrnamedisp(), person.getSex(), person.getFather(), 
/* 36 */         person.getMother());
/* 37 */       b.add(item);
/* 38 */       return b.toArray();
/*    */     }
/* 40 */     return getElements(parentElement);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object[] getElements(Object inputElement)
/*    */   {
/* 51 */     if ((inputElement instanceof AncestorTreeProvider)) {
/* 52 */       return ((AncestorTreeProvider)inputElement).getAncestorItems().getVector().toArray();
/*    */     }
/*    */     
/* 55 */     if ((inputElement instanceof AncestorModel)) {
/* 56 */       List<AncestorModel> list = new ArrayList();
/* 57 */       list.add((AncestorModel)inputElement);
/* 58 */       return list.toArray();
/*    */     }
/* 60 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object getParent(Object element)
/*    */   {
/* 71 */     if (element == null) {
/* 72 */       return null;
/*    */     }
/*    */     
/* 75 */     if ((element instanceof AncestorModel)) {
/* 76 */       return element;
/*    */     }
/* 78 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean hasChildren(Object element)
/*    */   {
/* 89 */     AncestorModel item = (AncestorModel)element;
/*    */     
/* 91 */     if (item.getFather() != 0) {
/* 92 */       return true;
/*    */     }
/* 94 */     if (item.getMother() != 0) {
/* 95 */       return true;
/*    */     }
/*    */     
/* 98 */     return false;
/*    */   }
/*    */   
/*    */   public void readFromH2(int i) {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\AncestorTreeContentProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */