/*     */ package org.historyresearchenvironment.usergui.models;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonNavigatorModel
/*     */   extends AbstractHreModel
/*     */ {
/*     */   protected String name;
/*     */   
/*     */ 
/*     */   protected int perNo;
/*     */   
/*     */ 
/*     */   protected String pBirth;
/*     */   
/*     */ 
/*     */   protected String pDeath;
/*     */   
/*     */ 
/*     */   protected int father;
/*     */   
/*     */ 
/*     */   protected int mother;
/*     */   
/*     */ 
/*     */ 
/*     */   public PersonNavigatorModel() {}
/*     */   
/*     */ 
/*     */   public PersonNavigatorModel(String name, int perNo, String pBirth, String pDeath, int father, int mother)
/*     */   {
/*  32 */     this.name = name;
/*  33 */     this.perNo = perNo;
/*  34 */     this.pBirth = pBirth;
/*  35 */     this.pDeath = pDeath;
/*  36 */     this.father = father;
/*  37 */     this.mother = mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFather()
/*     */   {
/*  44 */     return this.father;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMother()
/*     */   {
/*  51 */     return this.mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  58 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getPbirth()
/*     */   {
/*  65 */     return this.pBirth;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getPdeath()
/*     */   {
/*  72 */     return this.pDeath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getPerNo()
/*     */   {
/*  79 */     return this.perNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFather(int father)
/*     */   {
/*  87 */     this.father = father;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMother(int mother)
/*     */   {
/*  95 */     this.mother = mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 103 */     this.name = name;
/*     */   }
/*     */   
/*     */   public void setPbirth(String string) {
/* 107 */     this.pBirth = string;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPdeath(String string)
/*     */   {
/* 114 */     this.pDeath = string;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPerNo(int perNo)
/*     */   {
/* 122 */     this.perNo = perNo;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PersonNavigatorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */