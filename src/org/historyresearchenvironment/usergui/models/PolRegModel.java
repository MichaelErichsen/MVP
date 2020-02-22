/*     */ package org.historyresearchenvironment.usergui.models;
/*     */ 
/*     */ 
/*     */ public class PolRegModel
/*     */ {
/*     */   private String id;
/*     */   
/*     */   private String registerblad_id;
/*     */   
/*     */   private String firstnames;
/*     */   
/*     */   private String person_type;
/*     */   
/*     */   private String lastname;
/*     */   
/*     */   private String birthplace;
/*     */   
/*     */   private String dateofbirth;
/*     */   
/*     */   private String dateofdeath;
/*     */   
/*     */   public PolRegModel()
/*     */   {
/*  24 */     this.id = "";
/*  25 */     this.registerblad_id = "";
/*  26 */     this.firstnames = "";
/*  27 */     this.person_type = "";
/*  28 */     this.lastname = "";
/*  29 */     this.birthplace = "";
/*  30 */     this.dateofbirth = "";
/*  31 */     this.dateofdeath = "";
/*     */   }
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
/*     */ 
/*     */   public PolRegModel(String id, String registerblad_id, String firstnames, String person_type, String lastname, String birthplace, String dateofbirth, String dateofdeath)
/*     */   {
/*  47 */     this.id = id;
/*  48 */     this.registerblad_id = registerblad_id;
/*  49 */     this.firstnames = firstnames;
/*  50 */     this.person_type = person_type;
/*  51 */     this.lastname = lastname;
/*  52 */     this.birthplace = birthplace;
/*  53 */     this.dateofbirth = dateofbirth;
/*  54 */     this.dateofdeath = dateofdeath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getBirthplace()
/*     */   {
/*  61 */     return this.birthplace;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDateofbirth()
/*     */   {
/*  68 */     return this.dateofbirth;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDateofdeath()
/*     */   {
/*  75 */     return this.dateofdeath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getFirstnames()
/*     */   {
/*  82 */     return this.firstnames;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getId()
/*     */   {
/*  89 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getLastname()
/*     */   {
/*  96 */     return this.lastname;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getPerson_type()
/*     */   {
/* 103 */     return this.person_type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getRegisterblad_id()
/*     */   {
/* 110 */     return this.registerblad_id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBirthplace(String birthplace)
/*     */   {
/* 118 */     this.birthplace = birthplace;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDateofbirth(String dateofbirth)
/*     */   {
/* 126 */     this.dateofbirth = dateofbirth;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDateofdeath(String dateofdeath)
/*     */   {
/* 134 */     this.dateofdeath = dateofdeath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFirstnames(String firstnames)
/*     */   {
/* 142 */     this.firstnames = firstnames;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(String id)
/*     */   {
/* 150 */     this.id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLastname(String lastname)
/*     */   {
/* 158 */     this.lastname = lastname;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPerson_type(String person_type)
/*     */   {
/* 166 */     this.person_type = person_type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRegisterblad_id(String registerblad_id)
/*     */   {
/* 174 */     this.registerblad_id = registerblad_id;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PolRegModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */