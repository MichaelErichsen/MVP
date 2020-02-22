/*     */ package org.historyresearchenvironment.usergui.models;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AncestorModel
/*     */   extends AbstractHreModel
/*     */ {
/*     */   private int perNo;
/*     */   
/*     */ 
/*     */   private String name;
/*     */   
/*     */ 
/*     */   private String sex;
/*     */   
/*     */ 
/*     */   private int father;
/*     */   
/*     */ 
/*     */   private int mother;
/*     */   
/*     */ 
/*     */ 
/*     */   public AncestorModel() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public AncestorModel(int perNo, String name, String sex, int father, int mother)
/*     */   {
/*  30 */     this.perNo = perNo;
/*  31 */     this.name = name;
/*  32 */     this.sex = sex;
/*  33 */     this.father = father;
/*  34 */     this.mother = mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFather()
/*     */   {
/*  41 */     return this.father;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMother()
/*     */   {
/*  48 */     return this.mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  55 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getPerNo()
/*     */   {
/*  62 */     return this.perNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getSex()
/*     */   {
/*  69 */     return this.sex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFather(int father)
/*     */   {
/*  77 */     this.father = father;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMother(int mother)
/*     */   {
/*  85 */     this.mother = mother;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  93 */     this.name = name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPerNo(int perNo)
/*     */   {
/* 101 */     this.perNo = perNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSex(String sex)
/*     */   {
/* 109 */     this.sex = sex;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\AncestorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */