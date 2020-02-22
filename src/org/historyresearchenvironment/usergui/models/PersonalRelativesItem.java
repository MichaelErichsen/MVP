/*     */ package org.historyresearchenvironment.usergui.models;
/*     */ 
/*     */ import org.historyresearchenvironment.usergui.providers.AbstractHreProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonalRelativesItem
/*     */   extends AbstractHreProvider
/*     */ {
/*     */   private String name;
/*     */   private String relationship;
/*     */   private String sex;
/*     */   private String lifespan;
/*     */   private String gen;
/*     */   private String children;
/*     */   
/*     */   public PersonalRelativesItem(String name, String relationship, String sex, String lifespan, String gen, String children)
/*     */   {
/*  27 */     this.name = name;
/*  28 */     this.relationship = relationship;
/*  29 */     this.sex = sex;
/*  30 */     this.lifespan = lifespan;
/*  31 */     this.gen = gen;
/*  32 */     this.children = children;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getChildren()
/*     */   {
/*  39 */     return this.children;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getGen()
/*     */   {
/*  46 */     return this.gen;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getLifespan()
/*     */   {
/*  53 */     return this.lifespan;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  60 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getRelationship()
/*     */   {
/*  67 */     return this.relationship;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getSex()
/*     */   {
/*  74 */     return this.sex;
/*     */   }
/*     */   
/*     */   public String[] getStrings() {
/*  78 */     String[] sa = new String[6];
/*  79 */     sa[0] = this.name;
/*  80 */     sa[1] = this.relationship;
/*  81 */     sa[2] = this.sex;
/*  82 */     sa[3] = this.lifespan;
/*  83 */     sa[4] = this.gen;
/*  84 */     sa[5] = this.children;
/*     */     
/*  86 */     return sa;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromH2(int i) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void setChildren(String children)
/*     */   {
/*  98 */     this.children = children;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGen(String gen)
/*     */   {
/* 106 */     this.gen = gen;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLifespan(String lifespan)
/*     */   {
/* 114 */     this.lifespan = lifespan;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 122 */     this.name = name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRelationship(String relationship)
/*     */   {
/* 130 */     this.relationship = relationship;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSex(String sex)
/*     */   {
/* 138 */     this.sex = sex;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PersonalRelativesItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */