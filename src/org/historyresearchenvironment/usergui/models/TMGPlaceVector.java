/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TMGPlaceVector
/*    */ {
/* 16 */   private Logger LOGGER = Logger.getLogger("global");
/*    */   private static Vector<TMGPlaceModel> vector;
/*    */   private TMGPlaceModel item;
/* 19 */   private final String SELECT1 = "SELECT UID, TYPE FROM PLACEPARTVALUE WHERE RECNO = ? ORDER BY TYPE";
/* 20 */   private final String SELECT2 = "SELECT XVALUE FROM PLACEDICTIONARY WHERE UID = ?";
/*    */   
/*    */ 
/*    */ 
/*    */   public TMGPlaceVector()
/*    */   {
/* 26 */     vector = new Vector();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void add(Connection conn, int recNo)
/*    */   {
/* 34 */     ResultSet rs = null;
/* 35 */     int uid = 0;
/* 36 */     int type = 0;
/* 37 */     PreparedStatement pst = null;
/* 38 */     PreparedStatement pst1 = null;
/* 39 */     ResultSet rs1 = null;
/* 40 */     String xValue = "";
/*    */     try
/*    */     {
/* 43 */       this.item = new TMGPlaceModel(recNo);
/*    */       
/* 45 */       pst = conn.prepareStatement("SELECT UID, TYPE FROM PLACEPARTVALUE WHERE RECNO = ? ORDER BY TYPE");
/* 46 */       pst.setInt(1, recNo);
/*    */       
/* 48 */       rs = pst.executeQuery();
/*    */       
/* 50 */       while (rs.next()) {
/* 51 */         uid = rs.getInt("UID");
/* 52 */         type = rs.getInt("TYPE");
/*    */         
/* 54 */         pst1 = conn.prepareStatement("SELECT XVALUE FROM PLACEDICTIONARY WHERE UID = ?");
/* 55 */         pst1.setInt(1, uid);
/*    */         
/* 57 */         rs1 = pst1.executeQuery();
/*    */         
/* 59 */         if (rs1.next()) {
/* 60 */           xValue = rs1.getString("XVALUE");
/* 61 */           if (xValue.startsWith("-")) {
/* 62 */             xValue = xValue.substring(1, xValue.length());
/*    */           }
/* 64 */           this.item.setPlacePart(type, xValue);
/*    */         }
/* 66 */         pst1.close();
/*    */       }
/* 68 */       vector.add(this.item);
/* 69 */       pst.close();
/*    */     } catch (Exception e) {
/* 71 */       this.LOGGER.severe(e.getClass() + ": " + e.getMessage());
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public TMGPlaceModel getItem(int recNo)
/*    */   {
/* 80 */     Iterator<TMGPlaceModel> i = iterator();
/*    */     
/* 82 */     while (i.hasNext()) {
/* 83 */       TMGPlaceModel tmgPlaceItem = (TMGPlaceModel)i.next();
/*    */       
/* 85 */       if (tmgPlaceItem.getRecNo() == recNo) {
/* 86 */         return tmgPlaceItem;
/*    */       }
/*    */     }
/* 89 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Iterator<TMGPlaceModel> iterator()
/*    */   {
/* 96 */     return vector.iterator();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\TMGPlaceVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */