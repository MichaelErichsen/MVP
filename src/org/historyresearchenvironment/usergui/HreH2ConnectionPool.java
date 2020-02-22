/*    */ package org.historyresearchenvironment.usergui;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import java.util.logging.Logger;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*    */ import org.h2.jdbcx.JdbcConnectionPool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HreH2ConnectionPool
/*    */ {
/* 20 */   private static final Logger LOGGER = Logger.getLogger("global");
/* 21 */   private static final ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/* 22 */     "org.historyresearchenvironment.usergui");
/* 23 */   private static JdbcConnectionPool connectionPool = null;
/*    */   
/*    */ 
/*    */   private static int h2TraceLevel;
/*    */   
/*    */ 
/*    */   public static Connection getConnection()
/*    */   {
/* 31 */     h2TraceLevel = 0;
/*    */     
/* 33 */     if (store.getString("H2TRACELEVEL").equals("OFF")) {
/* 34 */       h2TraceLevel = 0;
/* 35 */     } else if (store.getString("H2TRACELEVEL").equals("ERROR")) {
/* 36 */       h2TraceLevel = 1;
/* 37 */     } else if (store.getString("H2TRACELEVEL").equals("DEBUG")) {
/* 38 */       h2TraceLevel = 3;
/*    */     } else {
/* 40 */       h2TraceLevel = 2;
/*    */     }
/*    */     try
/*    */     {
/* 44 */       if (connectionPool == null) {
/* 45 */         String jdbcUrl = "jdbc:h2:" + store.getString("DBNAME") + ";IFEXISTS=TRUE;TRACE_LEVEL_FILE=" + 
/* 46 */           h2TraceLevel + ";TRACE_LEVEL_SYSTEM_OUT=" + h2TraceLevel;
/* 47 */         LOGGER.fine("JDBC URL: " + jdbcUrl);
/* 48 */         connectionPool = JdbcConnectionPool.create(jdbcUrl, store.getString("H2USERID"), 
/* 49 */           store.getString("H2PASSWORD"));
/* 50 */         LOGGER.info("Pool created for " + store.getString("DBNAME"));
/* 51 */         connectionPool.setMaxConnections(500);
/*    */       }
/*    */       
/* 54 */       LOGGER.info("Active H2 Connections: " + connectionPool.getActiveConnections());
/* 55 */       return connectionPool.getConnection();
/*    */     } catch (SQLException e) {
/* 57 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + ": " + e.getSQLState()); }
/* 58 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void dispose()
/*    */   {
/* 72 */     connectionPool.dispose();
/* 73 */     connectionPool = null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static void createNew()
/*    */   {
/* 80 */     connectionPool.dispose();
/*    */     
/* 82 */     String jdbcUrl = "jdbc:h2:" + store.getString("DBNAME") + ";TRACE_LEVEL_FILE=" + 
/* 83 */       h2TraceLevel + ";TRACE_LEVEL_SYSTEM_OUT=" + h2TraceLevel;
/* 84 */     LOGGER.fine("JDBC URL: " + jdbcUrl);
/* 85 */     connectionPool = JdbcConnectionPool.create(jdbcUrl, store.getString("H2USERID"), store.getString("H2PASSWORD"));
/* 86 */     LOGGER.fine("Pool created for " + store.getString("DBNAME"));
/* 87 */     connectionPool.setMaxConnections(500);
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\HreH2ConnectionPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */