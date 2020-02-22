/*     */ package org.historyresearchenvironment.usergui.models;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import org.historyresearchenvironment.usergui.providers.AbstractHreProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonalRelativesProvider
/*     */   extends AbstractHreProvider
/*     */ {
/*     */   private int Id;
/*     */   private String identity;
/*  15 */   private String ComAncMPrev = "";
/*  16 */   private String ComAncFPrev = "";
/*  17 */   private String ComAncMThis = "";
/*  18 */   private String ComAncFThis = "";
/*  19 */   private String ComAncMNext = "";
/*  20 */   private String ComAncFNext = "";
/*  21 */   private Vector<PersonalRelativesItem> lastGen = new Vector();
/*  22 */   private Vector<PersonalRelativesItem> thisGen = new Vector();
/*  23 */   private Vector<PersonalRelativesItem> nextGen = new Vector();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PersonalRelativesProvider() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PersonalRelativesProvider(String identity, Vector<PersonalRelativesItem> lastGen, Vector<PersonalRelativesItem> thisGen, Vector<PersonalRelativesItem> nextGen)
/*     */   {
/*  39 */     this.identity = identity;
/*  40 */     this.lastGen = lastGen;
/*  41 */     this.thisGen = thisGen;
/*  42 */     this.nextGen = nextGen;
/*     */   }
/*     */   
/*     */   public void addLastGen(String name, String relationship, String sex, String lifespan, String gen, String children) {
/*  46 */     PersonalRelativesItem item = new PersonalRelativesItem(name, relationship, sex, lifespan, gen, children);
/*  47 */     this.lastGen.add(item);
/*     */   }
/*     */   
/*     */   public void addNextGen(String name, String relationship, String sex, String lifespan, String gen, String children) {
/*  51 */     PersonalRelativesItem item = new PersonalRelativesItem(name, relationship, sex, lifespan, gen, children);
/*  52 */     this.nextGen.add(item);
/*     */   }
/*     */   
/*     */   public void addThisGen(String name, String relationship, String sex, String lifespan, String gen, String children) {
/*  56 */     PersonalRelativesItem item = new PersonalRelativesItem(name, relationship, sex, lifespan, gen, children);
/*  57 */     this.thisGen.add(item);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getComAncFNext()
/*     */   {
/*  64 */     return this.ComAncFNext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getComAncFPrev()
/*     */   {
/*  71 */     return this.ComAncFPrev;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getComAncFThis()
/*     */   {
/*  78 */     return this.ComAncFThis;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getComAncMNext()
/*     */   {
/*  85 */     return this.ComAncMNext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getComAncMPrev()
/*     */   {
/*  92 */     return this.ComAncMPrev;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getComAncMThis()
/*     */   {
/*  99 */     return this.ComAncMThis;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 103 */     return this.Id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getIdentity()
/*     */   {
/* 110 */     return this.identity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Vector<PersonalRelativesItem> getLastGen()
/*     */   {
/* 117 */     return this.lastGen;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Vector<PersonalRelativesItem> getNextGen()
/*     */   {
/* 124 */     return this.nextGen;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Vector<PersonalRelativesItem> getThisGen()
/*     */   {
/* 131 */     return this.thisGen;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromH2(int i) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setComAncFNext(String comAncFNext)
/*     */   {
/* 144 */     this.ComAncFNext = comAncFNext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setComAncFPrev(String comAncFPrev)
/*     */   {
/* 152 */     this.ComAncFPrev = comAncFPrev;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setComAncFThis(String comAncFThis)
/*     */   {
/* 160 */     this.ComAncFThis = comAncFThis;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setComAncMNext(String comAncMNext)
/*     */   {
/* 168 */     this.ComAncMNext = comAncMNext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setComAncMPrev(String comAncMPrev)
/*     */   {
/* 176 */     this.ComAncMPrev = comAncMPrev;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setComAncMThis(String comAncMThis)
/*     */   {
/* 184 */     this.ComAncMThis = comAncMThis;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 188 */     this.Id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setIdentity(String identity)
/*     */   {
/* 196 */     this.identity = identity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLastGen(Vector<PersonalRelativesItem> lastGen)
/*     */   {
/* 204 */     this.lastGen = lastGen;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNextGen(Vector<PersonalRelativesItem> nextGen)
/*     */   {
/* 212 */     this.nextGen = nextGen;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setThisGen(Vector<PersonalRelativesItem> thisGen)
/*     */   {
/* 220 */     this.thisGen = thisGen;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PersonalRelativesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */