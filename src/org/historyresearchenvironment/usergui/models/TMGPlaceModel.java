/*     */ package org.historyresearchenvironment.usergui.models;
/*     */ 
/*     */ 
/*     */ public class TMGPlaceModel
/*     */   extends AbstractHreModel
/*     */ {
/*     */   private int recNo;
/*     */   
/*     */   private int uid;
/*     */   
/*     */   private int type;
/*     */   
/*     */   private String xValue;
/*     */   
/*  15 */   private String[] placeParts = { "", "", "", "", "", "", "", "", "", "", "", "" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public TMGPlaceModel(int recNo)
/*     */   {
/*  22 */     this.recNo = recNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TMGPlaceModel(int recNo, String[] placeParts)
/*     */   {
/*  33 */     this.recNo = recNo;
/*  34 */     this.placeParts = placeParts;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPlacePart(int i)
/*     */   {
/*  42 */     return this.placeParts[i];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String[] getPlaceParts()
/*     */   {
/*  49 */     return this.placeParts;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getRecNo()
/*     */   {
/*  56 */     return this.recNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getType()
/*     */   {
/*  63 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getUid()
/*     */   {
/*  70 */     return this.uid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getxValue()
/*     */   {
/*  77 */     return this.xValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlacePart(int i, String s)
/*     */   {
/*  85 */     this.placeParts[i] = s;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPlaceParts(String[] placeParts)
/*     */   {
/*  93 */     this.placeParts = placeParts;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRecNo(int recNo)
/*     */   {
/* 101 */     this.recNo = recNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setType(int type)
/*     */   {
/* 109 */     this.type = type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUid(int uid)
/*     */   {
/* 117 */     this.uid = uid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setxValue(String xValue)
/*     */   {
/* 125 */     this.xValue = xValue;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 130 */     StringBuilder sb = new StringBuilder();
/* 131 */     boolean first = true;
/*     */     String[] arrayOfString;
/* 133 */     int j = (arrayOfString = this.placeParts).length; for (int i = 0; i < j; i++) { String placePart = arrayOfString[i];
/* 134 */       if (placePart.length() > 0) {
/* 135 */         if (first) {
/* 136 */           first = false;
/*     */         } else {
/* 138 */           sb.append(", ");
/*     */         }
/* 140 */         sb.append(placePart);
/*     */       }
/*     */     }
/* 143 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\TMGPlaceModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */