/*    */ package org.historyresearchenvironment.usergui;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.util.logging.FileHandler;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import java.util.logging.SimpleFormatter;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
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
/*    */ public class HRELogger
/*    */ {
/*    */   private static FileHandler handler;
/*    */   
/*    */   public static void setup()
/*    */     throws IOException
/*    */   {
/* 28 */     ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/* 29 */       "org.historyresearchenvironment.usergui");
/* 30 */     Logger LOGGER = Logger.getLogger("global");
/*    */     
/* 32 */     String levelName = store.getString("LOGLEVEL");
/*    */     
/* 34 */     if (levelName.equals("OFF")) {
/* 35 */       LOGGER.setLevel(Level.OFF);
/* 36 */     } else if (levelName.equals("SEVERE")) {
/* 37 */       LOGGER.setLevel(Level.SEVERE);
/* 38 */     } else if (levelName.equals("WARNING")) {
/* 39 */       LOGGER.setLevel(Level.WARNING);
/* 40 */     } else if (levelName.equals("CONFIG")) {
/* 41 */       LOGGER.setLevel(Level.CONFIG);
/* 42 */     } else if (levelName.equals("FINE")) {
/* 43 */       LOGGER.setLevel(Level.FINE);
/* 44 */     } else if (levelName.equals("FINER")) {
/* 45 */       LOGGER.setLevel(Level.FINER);
/* 46 */     } else if (levelName.equals("FINEST")) {
/* 47 */       LOGGER.setLevel(Level.FINEST);
/* 48 */     } else if (levelName.equals("ALL")) {
/* 49 */       LOGGER.setLevel(Level.ALL);
/*    */     } else {
/* 51 */       LOGGER.setLevel(Level.INFO);
/*    */     }
/*    */     
/* 54 */     String logFilePath = store.getString("LOGFILEPATH");
/*    */     
/* 56 */     if (logFilePath.endsWith(";")) {
/* 57 */       logFilePath = logFilePath.substring(0, logFilePath.length() - 1);
/*    */     }
/*    */     
/* 60 */     System.out.println("Log file path: " + logFilePath);
/*    */     
/* 62 */     handler = new FileHandler(logFilePath + "hre-log.%u.%g.txt", 1048576, 10, true);
/* 63 */     handler.setFormatter(new SimpleFormatter());
/* 64 */     LOGGER.addHandler(handler);
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\HRELogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */