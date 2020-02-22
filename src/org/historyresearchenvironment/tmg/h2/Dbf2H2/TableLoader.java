/*     */ package org.historyresearchenvironment.tmg.h2.Dbf2H2;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Date;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.eclipse.core.runtime.IProgressMonitor;
/*     */ import org.eclipse.core.runtime.SubMonitor;
/*     */ import org.historyresearchenvironment.usergui.HreH2ConnectionPool;
/*     */ import org.xBaseJ.DBF;
/*     */ import org.xBaseJ.fields.Field;
/*     */ import org.xBaseJ.fields.MemoField;
/*     */ import org.xBaseJ.xBaseJException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TableLoader
/*     */ {
/*  29 */   private static final Logger LOGGER = Logger.getLogger("global");
/*  30 */   private Statement stmt = null;
/*     */   
/*     */ 
/*  33 */   private String FPTABLE = null;
/*  34 */   private DBF dbf = null;
/*  35 */   private String DELETE = null;
/*  36 */   private String INSERT = null;
/*  37 */   private Connection conn = null;
/*  38 */   private PreparedStatement pst = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TableLoader() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TableLoader(String project, String type, String table, String dbName, IProgressMonitor monitor)
/*     */   {
/*  55 */     SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
/*  56 */     subMonitor.beginTask("Loading tables", 1);
/*  57 */     subMonitor.subTask("Loading " + table);
/*     */     
/*  59 */     this.FPTABLE = 
/*  60 */       (FilenameUtils.getFullPath(project) + FilenameUtils.getBaseName(project).replace("_", "") + "_" + type + ".dbf");
/*  61 */     LOGGER.info("Project:" + project);
/*  62 */     this.DELETE = ("DELETE FROM " + table);
/*     */     
/*  64 */     LOGGER.info(this.FPTABLE);
/*  65 */     DbfFieldAnalyser analyser = new DbfFieldAnalyser(this.FPTABLE);
/*  66 */     this.INSERT = ("INSERT INTO " + table + " " + analyser.getFieldNames());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TableLoader(String project, String type, String table, String dbName)
/*     */   {
/*  76 */     this.FPTABLE = 
/*  77 */       (FilenameUtils.getFullPath(project) + FilenameUtils.getBaseName(project).replace("_", "") + "_" + type + ".dbf");
/*  78 */     LOGGER.info("Project:" + project);
/*  79 */     this.DELETE = ("DELETE FROM " + table);
/*     */     
/*  81 */     LOGGER.info(this.FPTABLE);
/*  82 */     DbfFieldAnalyser analyser = new DbfFieldAnalyser(this.FPTABLE);
/*  83 */     this.INSERT = ("INSERT INTO " + table + " " + analyser.getFieldNames());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TableLoader(String project, String type, String table, String insertStatement, String dbName)
/*     */   {
/*  94 */     LOGGER.info("Project:" + project);
/*  95 */     this.FPTABLE = 
/*  96 */       (FilenameUtils.getFullPath(project) + FilenameUtils.getBaseName(project).replace("_", "") + "_" + type + ".dbf");
/*  97 */     LOGGER.info("FPTABLE: " + this.FPTABLE);
/*  98 */     this.DELETE = ("DELETE FROM " + table);
/*  99 */     this.INSERT = ("INSERT INTO " + table + " " + insertStatement);
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
/*     */   private void initialize()
/*     */     throws xBaseJException, IOException, IllegalAccessException, InstantiationException, SQLException
/*     */   {
/* 113 */     this.dbf = new DBF(this.FPTABLE, 'r');
/*     */     
/* 115 */     LOGGER.info("Table name: " + this.dbf.getName() + "\nRecord count: " + this.dbf.getRecordCount() + "\nField count: " + 
/* 116 */       this.dbf.getFieldCount());
/*     */     
/* 118 */     this.conn = HreH2ConnectionPool.getConnection();
/* 119 */     this.stmt = this.conn.createStatement();
/* 120 */     this.stmt.execute(this.DELETE);
/* 121 */     this.conn.commit();
/* 122 */     LOGGER.info(this.INSERT);
/* 123 */     this.pst = this.conn.prepareStatement(this.INSERT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void mainLoop()
/*     */   {
/* 134 */     char type = '?';
/* 135 */     String s = null;
/* 136 */     String ssub = null;
/* 137 */     String ds = null;
/* 138 */     Date d = null;
/* 139 */     int l = 0;
/* 140 */     BigDecimal bd = null;
/* 141 */     Field fld = null;
/* 142 */     int a = 0;
/* 143 */     LOGGER.fine("");
/*     */     try
/*     */     {
/* 146 */       for (int i = 0; i < this.dbf.getRecordCount(); i++)
/*     */       {
/* 148 */         this.dbf.read();
/* 149 */         LOGGER.fine("\nRecord " + i);
/*     */         
/*     */ 
/* 152 */         for (int f = 1; f <= this.dbf.getFieldCount(); f++) {
/* 153 */           fld = this.dbf.getField(f);
/* 154 */           type = fld.getType();
/*     */           
/* 156 */           if (fld.isMemoField()) {
/* 157 */             l = ((MemoField)fld).getOriginalSize();
/*     */           } else {
/* 159 */             l = fld.getLength();
/*     */           }
/*     */           
/* 162 */           s = fld.get();
/*     */           
/* 164 */           if (s.length() > 40) {
/* 165 */             ssub = s.substring(0, 40) + "...";
/*     */           } else {
/* 167 */             ssub = s;
/*     */           }
/*     */           
/* 170 */           LOGGER.fine("Field " + f + ":\t" + fld.getName() + ", " + type + ", " + l + "\t>" + ssub + "<");
/*     */           
/* 172 */           switch (type)
/*     */           {
/*     */           case 'I': 
/* 175 */             a = 0;
/*     */             
/* 177 */             for (int k = 3; k >= 0; k--) {
/* 178 */               System.out.print(fld.buffer[k] + " ");
/*     */               
/* 180 */               a *= 256;
/* 181 */               a += (fld.buffer[k] < 0 ? fld.buffer[k] + 256 : fld.buffer[k]);
/*     */             }
/* 183 */             LOGGER.fine("\tInt corrected from " + s + " to " + a);
/* 184 */             this.pst.setInt(f, a);
/* 185 */             break;
/*     */           
/*     */           case 'N': 
/* 188 */             if (s.length() == 0) {
/* 189 */               bd = new BigDecimal(0);
/*     */             } else {
/* 191 */               bd = new BigDecimal(s.trim());
/*     */             }
/* 193 */             this.pst.setBigDecimal(f, bd);
/* 194 */             break;
/*     */           
/*     */           case 'C': 
/* 197 */             this.pst.setString(f, s);
/* 198 */             break;
/*     */           
/*     */           case 'M': 
/* 201 */             if (s.length() > 32760) {
/* 202 */               LOGGER.warning("Truncating MEMO type");
/* 203 */               s = s.substring(0, 32760);
/*     */             }
/*     */             
/* 206 */             this.pst.setString(f, s);
/* 207 */             break;
/*     */           
/*     */           case 'G': 
/* 210 */             this.pst.setString(f, s);
/* 211 */             break;
/*     */           case 'D': 
/*     */             try
/*     */             {
/* 215 */               ds = s.substring(0, 4) + "-" + s.substring(4, 6) + "-" + s.substring(6, 8);
/* 216 */               d = Date.valueOf(ds);
/*     */             } catch (Exception e) {
/* 218 */               e.printStackTrace();
/* 219 */               d = null;
/*     */             }
/*     */             
/* 222 */             this.pst.setDate(f, d);
/* 223 */             break;
/*     */           
/*     */ 
/*     */           case 'L': 
/* 227 */             this.pst.setString(f, s.substring(0, 1));
/* 228 */             break;
/*     */           case 'E': case 'F': 
/*     */           case 'H': 
/*     */           case 'J': 
/*     */           case 'K': 
/*     */           default: 
/* 234 */             LOGGER.severe("Unsupported field type " + type);
/* 235 */             throw new RuntimeException("Unknown field type " + type);
/*     */           }
/*     */         }
/* 238 */         LOGGER.fine("----------------------------");
/*     */         
/*     */ 
/*     */ 
/* 242 */         this.pst.executeUpdate();
/* 243 */         this.conn.commit();
/*     */ 
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */ 
/*     */ 
/* 255 */       StringBuilder sb = new StringBuilder(e.getClass() + " " + e.getMessage());
/*     */       StackTraceElement[] arrayOfStackTraceElement;
/* 257 */       int j = (arrayOfStackTraceElement = e.getStackTrace()).length; for (int i = 0; i < j; i++) { StackTraceElement element = arrayOfStackTraceElement[i];
/* 258 */         sb.append("\n" + element.toString());
/*     */       }
/* 260 */       LOGGER.severe(sb.toString());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/* 270 */       initialize();
/* 271 */       mainLoop();
/* 272 */       terminate();
/*     */     } catch (Exception e) {
/* 274 */       StringBuilder sb = new StringBuilder(e.getClass() + " " + e.getMessage());
/*     */       StackTraceElement[] arrayOfStackTraceElement;
/* 276 */       int j = (arrayOfStackTraceElement = e.getStackTrace()).length; for (int i = 0; i < j; i++) { StackTraceElement element = arrayOfStackTraceElement[i];
/* 277 */         sb.append("\n" + element.toString());
/*     */       }
/* 279 */       LOGGER.severe(sb.toString());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void terminate()
/*     */   {
/*     */     try
/*     */     {
/* 288 */       this.stmt.close();
/* 289 */       this.conn.close();
/* 290 */       this.dbf.close();
/*     */     } catch (Exception e) {
/* 292 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\Dbf2H2\TableLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */