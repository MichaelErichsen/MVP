/*     */ package org.historyresearchenvironment.usergui.providers;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import org.historyresearchenvironment.usergui.models.EventAssociatesModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventAssociatesProvider
/*     */   extends AbstractHreProvider
/*     */ {
/*     */   private int Id;
/*     */   private String identity;
/*     */   private String location;
/*     */   private String date;
/*  19 */   private Vector<EventAssociatesModel> eaiv = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */   public void addAssociate(EventAssociatesModel eai)
/*     */   {
/*  25 */     this.eaiv.addElement(eai);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDate()
/*     */   {
/*  32 */     return this.date;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Vector<EventAssociatesModel> getEaiv()
/*     */   {
/*  39 */     return this.eaiv;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  46 */     return this.Id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getIdentity()
/*     */   {
/*  53 */     return this.identity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getLocation()
/*     */   {
/*  60 */     return this.location;
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
/*     */   public void setDate(String date)
/*     */   {
/*  79 */     this.date = date;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEaiv(Vector<EventAssociatesModel> eaiv)
/*     */   {
/*  87 */     this.eaiv = eaiv;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(int id)
/*     */   {
/*  95 */     this.Id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setIdentity(String identity)
/*     */   {
/* 103 */     this.identity = identity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLocation(String location)
/*     */   {
/* 111 */     this.location = location;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\EventAssociatesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */