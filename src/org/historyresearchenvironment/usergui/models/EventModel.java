/*     */ package org.historyresearchenvironment.usergui.models;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventModel
/*     */   extends AbstractHreModel
/*     */ {
/*     */   public String eventTag;
/*     */   
/*     */ 
/*     */ 
/*     */   public String role;
/*     */   
/*     */ 
/*     */ 
/*     */   public String date;
/*     */   
/*     */ 
/*     */ 
/*     */   public String summary;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EventModel() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public EventModel(String eventTag, String role, String date, String summary)
/*     */   {
/*  32 */     this.eventTag = eventTag;
/*  33 */     this.role = role;
/*  34 */     this.date = date;
/*  35 */     this.summary = summary;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getDate()
/*     */   {
/*  42 */     return this.date;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getEventTag()
/*     */   {
/*  49 */     return this.eventTag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getRole()
/*     */   {
/*  56 */     return this.role;
/*     */   }
/*     */   
/*     */   public String[] getStrings()
/*     */   {
/*  61 */     String[] sa = new String[4];
/*  62 */     sa[0] = this.eventTag;
/*  63 */     sa[1] = this.role;
/*  64 */     sa[2] = this.date;
/*  65 */     sa[3] = this.summary;
/*     */     
/*  67 */     return sa;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getSummary()
/*     */   {
/*  74 */     return this.summary;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDate(String date)
/*     */   {
/*  82 */     this.date = date;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEventTag(String eventTag)
/*     */   {
/*  90 */     this.eventTag = eventTag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRole(String role)
/*     */   {
/*  98 */     this.role = role;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSummary(String summary)
/*     */   {
/* 106 */     this.summary = summary;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\EventModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */