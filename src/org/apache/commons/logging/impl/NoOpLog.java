/*     */ package org.apache.commons.logging.impl;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class NoOpLog
/*     */   implements Log, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 561423906191706148L;
/*     */   
/*     */   public NoOpLog() {}
/*     */   
/*     */   public NoOpLog(String name) {}
/*     */   
/*     */   public void debug(Object message) {}
/*     */   
/*     */   public void debug(Object message, Throwable t) {}
/*     */   
/*     */   public void error(Object message) {}
/*     */   
/*     */   public void error(Object message, Throwable t) {}
/*     */   
/*     */   public void fatal(Object message) {}
/*     */   
/*     */   public void fatal(Object message, Throwable t) {}
/*     */   
/*     */   public void info(Object message) {}
/*     */   
/*     */   public void info(Object message, Throwable t) {}
/*     */   
/*     */   public final boolean isDebugEnabled()
/*     */   {
/*  90 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isErrorEnabled()
/*     */   {
/* 100 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isFatalEnabled()
/*     */   {
/* 110 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isInfoEnabled()
/*     */   {
/* 120 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isTraceEnabled()
/*     */   {
/* 130 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isWarnEnabled()
/*     */   {
/* 140 */     return false;
/*     */   }
/*     */   
/*     */   public void trace(Object message) {}
/*     */   
/*     */   public void trace(Object message, Throwable t) {}
/*     */   
/*     */   public void warn(Object message) {}
/*     */   
/*     */   public void warn(Object message, Throwable t) {}
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\apache\commons\logging\impl\NoOpLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */