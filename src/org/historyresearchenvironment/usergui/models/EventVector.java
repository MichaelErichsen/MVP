/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.Vector;
/*    */ import java.util.logging.Logger;
/*    */ import org.historyresearchenvironment.tmg.h2.models.Placedictionary;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventVector
/*    */   extends AbstractHreVector
/*    */ {
/*    */   private Vector<EventModel> vector;
/*    */   private EventModel item;
/* 21 */   private final String SELECT = "SELECT ETYPENAME, EFOOT, EDATE, XVALUE, UID, TYPE, RECNO FROM EVENTVIEW WHERE PER1 = ? OR PER2 = ? ORDER BY EDATE, RECNO, TYPE";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EventVector(Connection conn, int perNo)
/*    */   {
/* 31 */     this.vector = new Vector();
/* 32 */     String eventTag = "";
/*    */     
/* 34 */     String date = "";
/* 35 */     String date1 = "";
/* 36 */     StringBuilder summary = new StringBuilder("");
/* 37 */     int lastRecNo = 0;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */     try
/*    */     {
/* 44 */       this.pst = conn.prepareStatement("SELECT ETYPENAME, EFOOT, EDATE, XVALUE, UID, TYPE, RECNO FROM EVENTVIEW WHERE PER1 = ? OR PER2 = ? ORDER BY EDATE, RECNO, TYPE");
/* 45 */       this.pst.setInt(1, perNo);
/* 46 */       this.pst.setInt(2, perNo);
/*    */       
/* 48 */       ResultSet rs = this.pst.executeQuery();
/*    */       
/* 50 */       while (rs.next()) {
/* 51 */         eventTag = rs.getString("ETYPENAME");
/* 52 */         date1 = rs.getString("EDATE").substring(1, 9);
/* 53 */         date = date1.substring(0, 4) + "-" + date1.substring(4, 6) + "-" + date1.substring(6, 8);
/*    */         
/* 55 */         int recNo = rs.getInt("RECNO");
/* 56 */         int uid = rs.getInt("UID");
/* 57 */         Placedictionary pd = new Placedictionary(conn, uid);
/* 58 */         String placePart = pd.getXvalue();
/*    */         
/* 60 */         if (placePart.startsWith("-")) {
/* 61 */           placePart = placePart.substring(1, placePart.length());
/*    */         }
/* 63 */         LOGGER.fine(recNo + " " + eventTag + " " + "Role" + " " + date + " " + summary.toString());
/*    */         
/* 65 */         if (recNo == lastRecNo) {
/* 66 */           summary.append(", " + placePart);
/* 67 */           LOGGER.fine(recNo + " " + eventTag + " " + "Role" + " " + date + " " + summary.toString());
/*    */         } else {
/* 69 */           this.item = new EventModel(eventTag, "Role", date, summary.toString());
/* 70 */           this.vector.add(this.item);
/* 71 */           lastRecNo = recNo;
/* 72 */           summary = new StringBuilder(
/* 73 */             rs.getString("XVALUE") + ", " + rs.getString("EFOOT") + ", " + placePart);
/*    */         }
/*    */       }
/* 76 */       this.pst.close();
/*    */     } catch (Exception e) {
/* 78 */       LOGGER.severe(e.getClass() + ": " + e.getMessage());
/*    */     }
/*    */   }
/*    */   
/*    */   public void add(EventModel item) {
/* 83 */     this.vector.addElement(item);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Vector<EventModel> getVector()
/*    */   {
/* 90 */     return this.vector;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setVector(Vector<EventModel> vector)
/*    */   {
/* 98 */     this.vector = vector;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\EventVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */