/*     */ package org.historyresearchenvironment.tmg.h2.models;
/*     */ 
/*     */ import java.math.BigDecimal;
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
/*     */ 
/*     */ public class Researchlog
/*     */ {
/*     */   private String begun;
/*     */   private String comments;
/*     */   private String completed;
/*     */   private String designed;
/*     */   private int dsid;
/*     */   private BigDecimal expenses;
/*     */   private int idEvent;
/*     */   private int idPerson;
/*     */   private int idRepos;
/*     */   private int idSource;
/*     */   private String keywords;
/*     */   private BigDecimal numRec;
/*     */   private String planned;
/*     */   private String progress;
/*     */   private int recno;
/*     */   private String reference;
/*     */   private String rledited;
/*     */   private int rlgtype;
/*     */   private String rlnote;
/*     */   private int rlnum;
/*     */   private int rlper1;
/*     */   private int rlper2;
/*     */   private String rltype;
/*     */   private String s;
/*     */   private String task;
/*     */   private String tt;
/*     */   
/*     */   public String getBegun()
/*     */   {
/*  72 */     return this.begun;
/*     */   }
/*     */   
/*     */   public String getComments() {
/*  76 */     return this.comments;
/*     */   }
/*     */   
/*     */   public String getCompleted() {
/*  80 */     return this.completed;
/*     */   }
/*     */   
/*     */   public String getDesigned() {
/*  84 */     return this.designed;
/*     */   }
/*     */   
/*     */   public int getDsid() {
/*  88 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public BigDecimal getExpenses() {
/*  92 */     return this.expenses;
/*     */   }
/*     */   
/*     */   public int getIdEvent() {
/*  96 */     return this.idEvent;
/*     */   }
/*     */   
/*     */   public int getIdPerson() {
/* 100 */     return this.idPerson;
/*     */   }
/*     */   
/*     */   public int getIdRepos() {
/* 104 */     return this.idRepos;
/*     */   }
/*     */   
/*     */   public int getIdSource() {
/* 108 */     return this.idSource;
/*     */   }
/*     */   
/*     */   public String getKeywords() {
/* 112 */     return this.keywords;
/*     */   }
/*     */   
/*     */   public BigDecimal getNumRec() {
/* 116 */     return this.numRec;
/*     */   }
/*     */   
/*     */   public String getPlanned() {
/* 120 */     return this.planned;
/*     */   }
/*     */   
/*     */   public String getProgress() {
/* 124 */     return this.progress;
/*     */   }
/*     */   
/*     */   public int getRecno() {
/* 128 */     return this.recno;
/*     */   }
/*     */   
/*     */   public String getReference() {
/* 132 */     return this.reference;
/*     */   }
/*     */   
/*     */   public String getRledited() {
/* 136 */     return this.rledited;
/*     */   }
/*     */   
/*     */   public int getRlgtype() {
/* 140 */     return this.rlgtype;
/*     */   }
/*     */   
/*     */   public String getRlnote() {
/* 144 */     return this.rlnote;
/*     */   }
/*     */   
/*     */   public int getRlnum() {
/* 148 */     return this.rlnum;
/*     */   }
/*     */   
/*     */   public int getRlper1() {
/* 152 */     return this.rlper1;
/*     */   }
/*     */   
/*     */   public int getRlper2() {
/* 156 */     return this.rlper2;
/*     */   }
/*     */   
/*     */   public String getRltype() {
/* 160 */     return this.rltype;
/*     */   }
/*     */   
/*     */   public String getS() {
/* 164 */     return this.s;
/*     */   }
/*     */   
/*     */   public String getTask() {
/* 168 */     return this.task;
/*     */   }
/*     */   
/*     */   public String getTt() {
/* 172 */     return this.tt;
/*     */   }
/*     */   
/*     */   public void setBegun(String begun) {
/* 176 */     this.begun = begun;
/*     */   }
/*     */   
/*     */   public void setComments(String comments) {
/* 180 */     this.comments = comments;
/*     */   }
/*     */   
/*     */   public void setCompleted(String completed) {
/* 184 */     this.completed = completed;
/*     */   }
/*     */   
/*     */   public void setDesigned(String designed) {
/* 188 */     this.designed = designed;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 192 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setExpenses(BigDecimal expenses) {
/* 196 */     this.expenses = expenses;
/*     */   }
/*     */   
/*     */   public void setIdEvent(int idEvent) {
/* 200 */     this.idEvent = idEvent;
/*     */   }
/*     */   
/*     */   public void setIdPerson(int idPerson) {
/* 204 */     this.idPerson = idPerson;
/*     */   }
/*     */   
/*     */   public void setIdRepos(int idRepos) {
/* 208 */     this.idRepos = idRepos;
/*     */   }
/*     */   
/*     */   public void setIdSource(int idSource) {
/* 212 */     this.idSource = idSource;
/*     */   }
/*     */   
/*     */   public void setKeywords(String keywords) {
/* 216 */     this.keywords = keywords;
/*     */   }
/*     */   
/*     */   public void setNumRec(BigDecimal numRec) {
/* 220 */     this.numRec = numRec;
/*     */   }
/*     */   
/*     */   public void setPlanned(String planned) {
/* 224 */     this.planned = planned;
/*     */   }
/*     */   
/*     */   public void setProgress(String progress) {
/* 228 */     this.progress = progress;
/*     */   }
/*     */   
/*     */   public void setRecno(int recno) {
/* 232 */     this.recno = recno;
/*     */   }
/*     */   
/*     */   public void setReference(String reference) {
/* 236 */     this.reference = reference;
/*     */   }
/*     */   
/*     */   public void setRledited(String rledited) {
/* 240 */     this.rledited = rledited;
/*     */   }
/*     */   
/*     */   public void setRlgtype(int rlgtype) {
/* 244 */     this.rlgtype = rlgtype;
/*     */   }
/*     */   
/*     */   public void setRlnote(String rlnote) {
/* 248 */     this.rlnote = rlnote;
/*     */   }
/*     */   
/*     */   public void setRlnum(int rlnum) {
/* 252 */     this.rlnum = rlnum;
/*     */   }
/*     */   
/*     */   public void setRlper1(int rlper1) {
/* 256 */     this.rlper1 = rlper1;
/*     */   }
/*     */   
/*     */   public void setRlper2(int rlper2) {
/* 260 */     this.rlper2 = rlper2;
/*     */   }
/*     */   
/*     */   public void setRltype(String rltype) {
/* 264 */     this.rltype = rltype;
/*     */   }
/*     */   
/*     */   public void setS(String s) {
/* 268 */     this.s = s;
/*     */   }
/*     */   
/*     */   public void setTask(String task) {
/* 272 */     this.task = task;
/*     */   }
/*     */   
/*     */   public void setTt(String tt) {
/* 276 */     this.tt = tt;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Researchlog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */