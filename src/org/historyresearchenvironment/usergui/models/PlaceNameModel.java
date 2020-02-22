/*     */ package org.historyresearchenvironment.usergui.models;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.logging.Logger;
/*     */ import org.historyresearchenvironment.usergui.providers.AbstractHreProvider;
/*     */ 
/*     */ 
/*     */ public class PlaceNameModel
/*     */   extends AbstractHreProvider
/*     */ {
/*  13 */   private final String SELECT1 = "SELECT UID, TYPE FROM PLACEPARTVALUE WHERE RECNO = ? ORDER BY TYPE";
/*  14 */   private final String SELECT2 = "SELECT XVALUE FROM PLACEDICTIONARY WHERE UID = ?";
/*     */   
/*  16 */   private PreparedStatement pst1 = null;
/*  17 */   private PreparedStatement pst2 = null;
/*     */   
/*     */   private int type;
/*  20 */   private LocationModel locationModel = new LocationModel();
/*  21 */   private String[] sa = { "", "", "", "", "", "", "", "", "" };
/*     */   
/*     */   public PlaceNameModel(int recNo) {
/*     */     try {
/*  25 */       this.pst1 = this.conn.prepareStatement("SELECT UID, TYPE FROM PLACEPARTVALUE WHERE RECNO = ? ORDER BY TYPE");
/*  26 */       this.pst2 = this.conn.prepareStatement("SELECT XVALUE FROM PLACEDICTIONARY WHERE UID = ?");
/*  27 */       this.pst1.setInt(1, recNo);
/*     */       
/*  29 */       ResultSet rs1 = this.pst1.executeQuery();
/*     */       
/*  31 */       while (rs1.next()) {
/*  32 */         this.pst2.setInt(1, rs1.getInt("UID"));
/*  33 */         this.type = rs1.getInt("TYPE");
/*     */         
/*  35 */         ResultSet rs2 = this.pst2.executeQuery();
/*     */         
/*     */ 
/*  38 */         if (rs2.next()) {
/*  39 */           String s = rs2.getString("XVALUE");
/*  40 */           switch (this.type) {
/*     */           case 1: 
/*  42 */             this.sa[this.type] = s;
/*  43 */             this.locationModel.setType1(s);
/*  44 */             break;
/*     */           case 2: 
/*  46 */             this.sa[this.type] = s;
/*  47 */             this.locationModel.setType2(s);
/*  48 */             break;
/*     */           case 3: 
/*  50 */             this.sa[this.type] = s;
/*  51 */             this.locationModel.setType3(s);
/*  52 */             break;
/*     */           case 4: 
/*  54 */             this.sa[this.type] = s;
/*  55 */             this.locationModel.setType4(s);
/*  56 */             break;
/*     */           case 5: 
/*  58 */             this.sa[this.type] = s;
/*  59 */             this.locationModel.setType5(s);
/*  60 */             break;
/*     */           case 6: 
/*  62 */             this.sa[this.type] = s;
/*  63 */             this.locationModel.setType6(s);
/*  64 */             break;
/*     */           case 7: 
/*  66 */             this.sa[this.type] = s;
/*  67 */             this.locationModel.setType7(s);
/*  68 */             break;
/*     */           case 8: 
/*  70 */             this.sa[this.type] = s;
/*  71 */             this.locationModel.setType8(s);
/*  72 */             break;
/*     */           
/*     */           case 9: 
/*  75 */             this.locationModel.setType9(s);
/*  76 */             break;
/*     */           
/*     */           default: 
/*  79 */             LOGGER.severe("Invalid type " + this.type);
/*     */           }
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/*  85 */         this.pst2.close();
/*  86 */         this.pst1.close();
/*  87 */         this.conn.close();
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  96 */       return;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  93 */       e.printStackTrace();
/*  94 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public LocationModel getLocationModel()
/*     */   {
/* 102 */     return this.locationModel;
/*     */   }
/*     */   
/*     */   public String getPlaceName() {
/* 106 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 108 */     Boolean flag = Boolean.valueOf(false);
/*     */     String[] arrayOfString;
/* 110 */     int j = (arrayOfString = this.sa).length; for (int i = 0; i < j; i++) { String element = arrayOfString[i];
/*     */       
/* 112 */       if (!element.equals("")) {
/* 113 */         if (flag.booleanValue()) {
/* 114 */           sb.append(", ");
/*     */         } else {
/* 116 */           flag = Boolean.valueOf(true);
/*     */         }
/*     */         
/* 119 */         sb.append(element);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 124 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getSa()
/*     */   {
/* 132 */     return this.sa;
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
/*     */   public void setLocationModel(LocationModel locationModel)
/*     */   {
/* 145 */     this.locationModel = locationModel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSa(String[] sa)
/*     */   {
/* 153 */     this.sa = sa;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PlaceNameModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */