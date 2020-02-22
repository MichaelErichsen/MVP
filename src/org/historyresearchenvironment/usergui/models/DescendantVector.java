/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.Vector;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DescendantVector
/*    */   extends AbstractHreVector
/*    */ {
/* 14 */   private final String SELECT = "SELECT NAME.SRNAMEDISP, PERSON.PER_NO FROM PERSON, NAME WHERE NAME.NPER = PERSON.PER_NO AND PERSON.PER_NO = ? ORDER BY PERSON.PER_NO";
/*    */   
/* 16 */   private final String SELECTCHILDREN = "SELECT CHILD FROM PARENTCHILDRELATIONSHIP WHERE PARENT = ?";
/*    */   
/*    */   private Vector<DescendantModel> vector;
/*    */   
/*    */   private DescendantModel item;
/*    */   private Vector<Integer> children;
/* 22 */   private PreparedStatement pstc = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DescendantVector() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public DescendantVector(Connection conn, int perNo)
/*    */   {
/* 35 */     this.vector = new Vector();
/*    */     try
/*    */     {
/* 38 */       this.pst = conn.prepareStatement("SELECT NAME.SRNAMEDISP, PERSON.PER_NO FROM PERSON, NAME WHERE NAME.NPER = PERSON.PER_NO AND PERSON.PER_NO = ? ORDER BY PERSON.PER_NO");
/* 39 */       this.pst.setInt(1, perNo);
/*    */       
/* 41 */       ResultSet rs = this.pst.executeQuery();
/*    */       
/* 43 */       if (rs.next()) {
/* 44 */         this.item = new DescendantModel();
/* 45 */         this.item.setCurrent(perNo);
/* 46 */         this.item.setName(perNo + ", " + rs.getString("SRNAMEDISP"));
/*    */       }
/*    */       
/* 49 */       this.children = new Vector();
/*    */       
/* 51 */       this.pstc = conn.prepareStatement("SELECT CHILD FROM PARENTCHILDRELATIONSHIP WHERE PARENT = ?");
/* 52 */       this.pstc.setInt(1, perNo);
/*    */       
/* 54 */       ResultSet rsc = this.pstc.executeQuery();
/*    */       
/* 56 */       while (rsc.next()) {
/* 57 */         this.children.add(Integer.valueOf(rsc.getInt("CHILD")));
/*    */       }
/*    */       
/* 60 */       this.item.setChildren(this.children);
/* 61 */       this.vector.addElement(this.item);
/*    */       try
/*    */       {
/* 64 */         this.pst.close();
/* 65 */         this.pstc.close();
/*    */       } catch (Exception e) {
/* 67 */         LOGGER.severe(
/* 68 */           e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */       }
/*    */       return;
/*    */     }
/*    */     catch (Exception e) {
/* 73 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */     }
/*    */   }
/*    */   
/*    */   public void add(DescendantModel di) {
/* 78 */     this.vector.addElement(di);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Vector<DescendantModel> getVector()
/*    */   {
/* 85 */     return this.vector;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setVector(Vector<DescendantModel> vector)
/*    */   {
/* 93 */     this.vector = vector;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\DescendantVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */