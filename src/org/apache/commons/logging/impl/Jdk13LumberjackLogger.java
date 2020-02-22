/*     */ package org.apache.commons.logging.impl;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Jdk13LumberjackLogger
/*     */   implements Log, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8649807923527610591L;
/*  51 */   protected static final Level dummyLevel = Level.FINE;
/*     */   
/*     */ 
/*     */ 
/*  55 */   protected transient Logger logger = null;
/*  56 */   protected String name = null;
/*  57 */   private String sourceClassName = "unknown";
/*  58 */   private String sourceMethodName = "unknown";
/*     */   
/*  60 */   private boolean classAndMethodFound = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Jdk13LumberjackLogger(String name)
/*     */   {
/*  71 */     this.name = name;
/*  72 */     this.logger = getLogger();
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
/*     */ 
/*     */   public void debug(Object message)
/*     */   {
/*  86 */     log(Level.FINE, String.valueOf(message), null);
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
/*     */ 
/*     */   public void debug(Object message, Throwable exception)
/*     */   {
/* 100 */     log(Level.FINE, String.valueOf(message), exception);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void error(Object message)
/*     */   {
/* 112 */     log(Level.SEVERE, String.valueOf(message), null);
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
/*     */ 
/*     */   public void error(Object message, Throwable exception)
/*     */   {
/* 126 */     log(Level.SEVERE, String.valueOf(message), exception);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void fatal(Object message)
/*     */   {
/* 138 */     log(Level.SEVERE, String.valueOf(message), null);
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
/*     */ 
/*     */   public void fatal(Object message, Throwable exception)
/*     */   {
/* 152 */     log(Level.SEVERE, String.valueOf(message), exception);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void getClassAndMethod()
/*     */   {
/*     */     try
/*     */     {
/* 161 */       Throwable throwable = new Throwable();
/* 162 */       throwable.fillInStackTrace();
/* 163 */       StringWriter stringWriter = new StringWriter();
/* 164 */       PrintWriter printWriter = new PrintWriter(stringWriter);
/* 165 */       throwable.printStackTrace(printWriter);
/* 166 */       String traceString = stringWriter.getBuffer().toString();
/* 167 */       StringTokenizer tokenizer = new StringTokenizer(traceString, "\n");
/* 168 */       tokenizer.nextToken();
/* 169 */       String line = tokenizer.nextToken();
/* 170 */       while (line.indexOf(getClass().getName()) == -1) {
/* 171 */         line = tokenizer.nextToken();
/*     */       }
/* 173 */       while (line.indexOf(getClass().getName()) >= 0) {
/* 174 */         line = tokenizer.nextToken();
/*     */       }
/* 176 */       int start = line.indexOf("at ") + 3;
/* 177 */       int end = line.indexOf('(');
/* 178 */       String temp = line.substring(start, end);
/* 179 */       int lastPeriod = temp.lastIndexOf('.');
/* 180 */       this.sourceClassName = temp.substring(0, lastPeriod);
/* 181 */       this.sourceMethodName = temp.substring(lastPeriod + 1);
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/* 185 */     this.classAndMethodFound = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Logger getLogger()
/*     */   {
/* 192 */     if (this.logger == null) {
/* 193 */       this.logger = Logger.getLogger(this.name);
/*     */     }
/* 195 */     return this.logger;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void info(Object message)
/*     */   {
/* 207 */     log(Level.INFO, String.valueOf(message), null);
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
/*     */ 
/*     */   public void info(Object message, Throwable exception)
/*     */   {
/* 221 */     log(Level.INFO, String.valueOf(message), exception);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDebugEnabled()
/*     */   {
/* 229 */     return getLogger().isLoggable(Level.FINE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isErrorEnabled()
/*     */   {
/* 237 */     return getLogger().isLoggable(Level.SEVERE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFatalEnabled()
/*     */   {
/* 245 */     return getLogger().isLoggable(Level.SEVERE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInfoEnabled()
/*     */   {
/* 253 */     return getLogger().isLoggable(Level.INFO);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTraceEnabled()
/*     */   {
/* 261 */     return getLogger().isLoggable(Level.FINEST);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isWarnEnabled()
/*     */   {
/* 269 */     return getLogger().isLoggable(Level.WARNING);
/*     */   }
/*     */   
/*     */   private void log(Level level, String msg, Throwable ex) {
/* 273 */     if (getLogger().isLoggable(level)) {
/* 274 */       LogRecord record = new LogRecord(level, msg);
/* 275 */       if (!this.classAndMethodFound) {
/* 276 */         getClassAndMethod();
/*     */       }
/* 278 */       record.setSourceClassName(this.sourceClassName);
/* 279 */       record.setSourceMethodName(this.sourceMethodName);
/* 280 */       if (ex != null) {
/* 281 */         record.setThrown(ex);
/*     */       }
/* 283 */       getLogger().log(record);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void trace(Object message)
/*     */   {
/* 296 */     log(Level.FINEST, String.valueOf(message), null);
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
/*     */ 
/*     */   public void trace(Object message, Throwable exception)
/*     */   {
/* 310 */     log(Level.FINEST, String.valueOf(message), exception);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void warn(Object message)
/*     */   {
/* 322 */     log(Level.WARNING, String.valueOf(message), null);
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
/*     */ 
/*     */   public void warn(Object message, Throwable exception)
/*     */   {
/* 336 */     log(Level.WARNING, String.valueOf(message), exception);
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\apache\commons\logging\impl\Jdk13LumberjackLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */