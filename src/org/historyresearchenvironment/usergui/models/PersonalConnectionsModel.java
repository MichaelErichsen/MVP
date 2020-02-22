/*     */ package org.historyresearchenvironment.usergui.models;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import org.historyresearchenvironment.usergui.providers.AbstractHreProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonalConnectionsModel
/*     */   extends AbstractHreProvider
/*     */ {
/*     */   private int Id;
/*     */   private String identity;
/*     */   private String father;
/*     */   private String mother;
/*     */   private int noChildren;
/*  17 */   private Vector<ConnectionEventsItem> ceiv = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */   public void addEvent(ConnectionEventsItem cei)
/*     */   {
/*  23 */     this.ceiv.addElement(cei);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Vector<ConnectionEventsItem> getCaiv()
/*     */   {
/*  30 */     return this.ceiv;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getFather()
/*     */   {
/*  37 */     return this.father;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  44 */     return this.Id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getIdentity()
/*     */   {
/*  51 */     return this.identity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getMother()
/*     */   {
/*  58 */     return this.mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getNoChildren()
/*     */   {
/*  65 */     return this.noChildren;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromH2(int i) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCeiv(Vector<ConnectionEventsItem> ceiv)
/*     */   {
/*  84 */     this.ceiv = ceiv;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFather(String father)
/*     */   {
/*  92 */     this.father = father;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(int id)
/*     */   {
/* 100 */     this.Id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setIdentity(String identity)
/*     */   {
/* 108 */     this.identity = identity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMother(String mother)
/*     */   {
/* 116 */     this.mother = mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNoChildren(int noChildren)
/*     */   {
/* 124 */     this.noChildren = noChildren;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PersonalConnectionsModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */