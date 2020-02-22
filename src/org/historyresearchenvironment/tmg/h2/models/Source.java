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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Source
/*     */ {
/*     */   private int majnum;
/*     */   private String abbrev;
/*     */   private String bform;
/*     */   private String cited;
/*     */   private int compiler;
/*     */   private int custtype;
/*     */   private String defsure;
/*     */   private int dsid;
/*     */   private int editorid;
/*     */   private String fform;
/*     */   private BigDecimal fidelity;
/*     */   private String firstcd;
/*     */   private BigDecimal ibidtype;
/*     */   private BigDecimal indexed;
/*     */   private String info;
/*     */   private String ispicked;
/*     */   private String mactive;
/*     */   private BigDecimal media;
/*     */   private BigDecimal recorder;
/*     */   private int refId;
/*     */   private String reminders;
/*     */   private String sform;
/*     */   private int sperno;
/*     */   private int sperno2;
/*     */   private BigDecimal status;
/*     */   private int subjectid;
/*     */   private String text;
/*     */   private String title;
/*     */   private String tt;
/*     */   private BigDecimal type;
/*     */   private String uncitedfld;
/*     */   
/*     */   public String getAbbrev()
/*     */   {
/*  82 */     return this.abbrev;
/*     */   }
/*     */   
/*     */   public String getBform() {
/*  86 */     return this.bform;
/*     */   }
/*     */   
/*     */   public String getCited() {
/*  90 */     return this.cited;
/*     */   }
/*     */   
/*     */   public int getCompiler() {
/*  94 */     return this.compiler;
/*     */   }
/*     */   
/*     */   public int getCusttype() {
/*  98 */     return this.custtype;
/*     */   }
/*     */   
/*     */   public String getDefsure() {
/* 102 */     return this.defsure;
/*     */   }
/*     */   
/*     */   public int getDsid() {
/* 106 */     return this.dsid;
/*     */   }
/*     */   
/*     */   public int getEditorid() {
/* 110 */     return this.editorid;
/*     */   }
/*     */   
/*     */   public String getFform() {
/* 114 */     return this.fform;
/*     */   }
/*     */   
/*     */   public BigDecimal getFidelity() {
/* 118 */     return this.fidelity;
/*     */   }
/*     */   
/*     */   public String getFirstcd() {
/* 122 */     return this.firstcd;
/*     */   }
/*     */   
/*     */   public BigDecimal getIbidtype() {
/* 126 */     return this.ibidtype;
/*     */   }
/*     */   
/*     */   public BigDecimal getIndexed() {
/* 130 */     return this.indexed;
/*     */   }
/*     */   
/*     */   public String getInfo() {
/* 134 */     return this.info;
/*     */   }
/*     */   
/*     */   public String getIspicked() {
/* 138 */     return this.ispicked;
/*     */   }
/*     */   
/*     */   public String getMactive() {
/* 142 */     return this.mactive;
/*     */   }
/*     */   
/*     */   public int getMajnum() {
/* 146 */     return this.majnum;
/*     */   }
/*     */   
/*     */   public BigDecimal getMedia() {
/* 150 */     return this.media;
/*     */   }
/*     */   
/*     */   public BigDecimal getRecorder() {
/* 154 */     return this.recorder;
/*     */   }
/*     */   
/*     */   public int getRefId() {
/* 158 */     return this.refId;
/*     */   }
/*     */   
/*     */   public String getReminders() {
/* 162 */     return this.reminders;
/*     */   }
/*     */   
/*     */   public String getSform() {
/* 166 */     return this.sform;
/*     */   }
/*     */   
/*     */   public int getSperno() {
/* 170 */     return this.sperno;
/*     */   }
/*     */   
/*     */   public int getSperno2() {
/* 174 */     return this.sperno2;
/*     */   }
/*     */   
/*     */   public BigDecimal getStatus() {
/* 178 */     return this.status;
/*     */   }
/*     */   
/*     */   public int getSubjectid() {
/* 182 */     return this.subjectid;
/*     */   }
/*     */   
/*     */   public String getText() {
/* 186 */     return this.text;
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 190 */     return this.title;
/*     */   }
/*     */   
/*     */   public String getTt() {
/* 194 */     return this.tt;
/*     */   }
/*     */   
/*     */   public BigDecimal getType() {
/* 198 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getUncitedfld() {
/* 202 */     return this.uncitedfld;
/*     */   }
/*     */   
/*     */   public void setAbbrev(String abbrev) {
/* 206 */     this.abbrev = abbrev;
/*     */   }
/*     */   
/*     */   public void setBform(String bform) {
/* 210 */     this.bform = bform;
/*     */   }
/*     */   
/*     */   public void setCited(String cited) {
/* 214 */     this.cited = cited;
/*     */   }
/*     */   
/*     */   public void setCompiler(int compiler) {
/* 218 */     this.compiler = compiler;
/*     */   }
/*     */   
/*     */   public void setCusttype(int custtype) {
/* 222 */     this.custtype = custtype;
/*     */   }
/*     */   
/*     */   public void setDefsure(String defsure) {
/* 226 */     this.defsure = defsure;
/*     */   }
/*     */   
/*     */   public void setDsid(int dsid) {
/* 230 */     this.dsid = dsid;
/*     */   }
/*     */   
/*     */   public void setEditorid(int editorid) {
/* 234 */     this.editorid = editorid;
/*     */   }
/*     */   
/*     */   public void setFform(String fform) {
/* 238 */     this.fform = fform;
/*     */   }
/*     */   
/*     */   public void setFidelity(BigDecimal fidelity) {
/* 242 */     this.fidelity = fidelity;
/*     */   }
/*     */   
/*     */   public void setFirstcd(String firstcd) {
/* 246 */     this.firstcd = firstcd;
/*     */   }
/*     */   
/*     */   public void setIbidtype(BigDecimal ibidtype) {
/* 250 */     this.ibidtype = ibidtype;
/*     */   }
/*     */   
/*     */   public void setIndexed(BigDecimal indexed) {
/* 254 */     this.indexed = indexed;
/*     */   }
/*     */   
/*     */   public void setInfo(String info) {
/* 258 */     this.info = info;
/*     */   }
/*     */   
/*     */   public void setIspicked(String ispicked) {
/* 262 */     this.ispicked = ispicked;
/*     */   }
/*     */   
/*     */   public void setMactive(String mactive) {
/* 266 */     this.mactive = mactive;
/*     */   }
/*     */   
/*     */   public void setMajnum(int majnum) {
/* 270 */     this.majnum = majnum;
/*     */   }
/*     */   
/*     */   public void setMedia(BigDecimal media) {
/* 274 */     this.media = media;
/*     */   }
/*     */   
/*     */   public void setRecorder(BigDecimal recorder) {
/* 278 */     this.recorder = recorder;
/*     */   }
/*     */   
/*     */   public void setRefId(int refId) {
/* 282 */     this.refId = refId;
/*     */   }
/*     */   
/*     */   public void setReminders(String reminders) {
/* 286 */     this.reminders = reminders;
/*     */   }
/*     */   
/*     */   public void setSform(String sform) {
/* 290 */     this.sform = sform;
/*     */   }
/*     */   
/*     */   public void setSperno(int sperno) {
/* 294 */     this.sperno = sperno;
/*     */   }
/*     */   
/*     */   public void setSperno2(int sperno2) {
/* 298 */     this.sperno2 = sperno2;
/*     */   }
/*     */   
/*     */   public void setStatus(BigDecimal status) {
/* 302 */     this.status = status;
/*     */   }
/*     */   
/*     */   public void setSubjectid(int subjectid) {
/* 306 */     this.subjectid = subjectid;
/*     */   }
/*     */   
/*     */   public void setText(String text) {
/* 310 */     this.text = text;
/*     */   }
/*     */   
/*     */   public void setTitle(String title) {
/* 314 */     this.title = title;
/*     */   }
/*     */   
/*     */   public void setTt(String tt) {
/* 318 */     this.tt = tt;
/*     */   }
/*     */   
/*     */   public void setType(BigDecimal type) {
/* 322 */     this.type = type;
/*     */   }
/*     */   
/*     */   public void setUncitedfld(String uncitedfld) {
/* 326 */     this.uncitedfld = uncitedfld;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Source.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */