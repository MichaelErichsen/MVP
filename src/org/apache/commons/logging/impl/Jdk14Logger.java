/*     */ package org.apache.commons.logging.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.logging.Level;
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
/*     */ public class Jdk14Logger
/*     */   implements Log, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4784713551416303804L;
/*  44 */   protected static final Level dummyLevel = Level.FINE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  51 */   protected transient Logger logger = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  58 */   protected String name = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Jdk14Logger(String name)
/*     */   {
/*  67 */     this.name = name;
/*  68 */     this.logger = getLogger();
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
/*  82 */     log(Level.FINE, String.valueOf(message), null);
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
/*     */ 
/*     */ 
/*     */   public void debug(Object message, Throwable exception)
/*     */   {
/*  98 */     log(Level.FINE, String.valueOf(message), exception);
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
/* 110 */     log(Level.SEVERE, String.valueOf(message), null);
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
/* 124 */     log(Level.SEVERE, String.valueOf(message), exception);
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
/* 136 */     log(Level.SEVERE, String.valueOf(message), null);
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
/* 150 */     log(Level.SEVERE, String.valueOf(message), exception);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Logger getLogger()
/*     */   {
/* 157 */     if (this.logger == null) {
/* 158 */       this.logger = Logger.getLogger(this.name);
/*     */     }
/* 160 */     return this.logger;
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
/* 172 */     log(Level.INFO, String.valueOf(message), null);
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
/* 186 */     log(Level.INFO, String.valueOf(message), exception);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDebugEnabled()
/*     */   {
/* 194 */     return getLogger().isLoggable(Level.FINE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isErrorEnabled()
/*     */   {
/* 202 */     return getLogger().isLoggable(Level.SEVERE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFatalEnabled()
/*     */   {
/* 210 */     return getLogger().isLoggable(Level.SEVERE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInfoEnabled()
/*     */   {
/* 218 */     return getLogger().isLoggable(Level.INFO);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTraceEnabled()
/*     */   {
/* 226 */     return getLogger().isLoggable(Level.FINEST);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isWarnEnabled()
/*     */   {
/* 234 */     return getLogger().isLoggable(Level.WARNING);
/*     */   }
/*     */   
/*     */   protected void log(Level level, String msg, Throwable ex) {
/* 238 */     Logger logger = getLogger();
/* 239 */     if (logger.isLoggable(level))
/*     */     {
/* 241 */       Throwable dummyException = new Throwable();
/* 242 */       StackTraceElement[] locations = dummyException.getStackTrace();
/*     */       
/* 244 */       String cname = this.name;
/* 245 */       String method = "unknown";
/*     */       
/* 247 */       if ((locations != null) && (locations.length > 2)) {
/* 248 */         StackTraceElement caller = locations[2];
/* 249 */         method = caller.getMethodName();
/*     */       }
/* 251 */       if (ex == null) {
/* 252 */         logger.logp(level, cname, method, msg);
/*     */       } else {
/* 254 */         logger.logp(level, cname, method, msg, ex);
/*     */       }
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
/* 268 */     log(Level.FINEST, String.valueOf(message), null);
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
/* 282 */     log(Level.FINEST, String.valueOf(message), exception);
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
/* 294 */     log(Level.WARNING, String.valueOf(message), null);
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
/* 308 */     log(Level.WARNING, String.valueOf(message), exception);
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\apache\commons\logging\impl\Jdk14Logger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */