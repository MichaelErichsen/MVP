/*    */ package org.historyresearchenvironment.tmg.h2.Dbf2H2;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Logger;

/*    */ import org.xBaseJ.DBF;
/*    */ import org.xBaseJ.xBaseJException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DbfFieldAnalyser
/*    */ {
/* 17 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */   private DBF dbf;
/*    */   
/*    */ 
/*    */   public DbfFieldAnalyser(String dbfFileName)
/*    */   {
/*    */     try
/*    */     {
/* 26 */       this.dbf = new DBF(dbfFileName, 'r');
/*    */     } catch (xBaseJException e) {
/* 28 */       LOGGER.severe(e.getClass() + ": " + e.getMessage());
/* 29 */       e.printStackTrace();
/*    */     } catch (IOException e) {
/* 31 */       LOGGER.severe(e.getClass() + ": " + e.getMessage());
/* 32 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void close()
/*    */   {
/*    */     try
/*    */     {
/* 41 */       this.dbf.close();
/*    */     } catch (IOException e) {
/* 43 */       LOGGER.severe(e.getClass() + ": " + e.getMessage());
/* 44 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getFieldCount()
/*    */   {
/* 52 */     LOGGER.info("Field Count: " + this.dbf.getFieldCount());
/* 53 */     return this.dbf.getFieldCount();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getFieldNames()
/*    */   {
/* 63 */     StringBuilder sb = new StringBuilder("(");
/*    */     StringBuilder sb1;
/*    */     StackTraceElement[] arrayOfStackTraceElement;
/* 66 */     int j; int i; try { for (int f = 1; f < this.dbf.getFieldCount(); f++) {
/* 67 */         String s = this.dbf.getField(f).getName();
/* 68 */         if (s.equals("GROUP")) {
/* 69 */           s = "XGROUP";
/* 70 */         } else if (s.equals("PRIMARY")) {
/* 71 */           s = "XPRIMARY";
/* 72 */         } else if (s.equals("VALUE")) {
/* 73 */           s = "XVALUE";
/*    */         }
/* 75 */         sb.append(s + ", ");
/*    */       }
/*    */       
/* 78 */       sb.append(this.dbf.getField(this.dbf.getFieldCount()).getName());
/* 79 */       sb.append(") VALUES (");
/* 80 */       for (int i = 0; i < this.dbf.getFieldCount() - 1; i++) {
/* 81 */         sb.append("?, ");
/*    */       }
/* 83 */       sb.append("?)");
/*    */     } catch (Exception e) {
/* 85 */       sb1 = new StringBuilder(e.getClass() + " " + e.getMessage());
/*    */       
/* 87 */       j = (arrayOfStackTraceElement = e.getStackTrace()).length;i = 0; } for (; i < j; i++) { StackTraceElement element = arrayOfStackTraceElement[i];
/* 88 */       sb1.append("\n" + element.toString());
/*    */     }
/*    */     
/* 91 */     return sb.toString().toUpperCase();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\Dbf2H2\DbfFieldAnalyser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */