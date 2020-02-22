/*     */ package org.apache.commons.logging.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogConfigurationException;
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
/*     */ public class SimpleLog
/*     */   implements Log, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 136942970684951178L;
/*     */   protected static final String systemPrefix = "org.apache.commons.logging.simplelog.";
/*  82 */   protected static final Properties simpleLogProps = new Properties();
/*     */   
/*     */ 
/*     */   protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
/*     */   
/*     */ 
/*  88 */   protected static volatile boolean showLogName = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  95 */   protected static volatile boolean showShortName = true;
/*     */   
/*     */ 
/*  98 */   protected static volatile boolean showDateTime = false;
/*     */   
/*     */ 
/* 101 */   protected static volatile String dateTimeFormat = "yyyy/MM/dd HH:mm:ss:SSS zzz";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 110 */   protected static DateFormat dateFormatter = null;
/*     */   
/*     */ 
/*     */   public static final int LOG_LEVEL_TRACE = 1;
/*     */   
/*     */ 
/*     */   public static final int LOG_LEVEL_DEBUG = 2;
/*     */   
/*     */ 
/*     */   public static final int LOG_LEVEL_INFO = 3;
/*     */   
/*     */ 
/*     */   public static final int LOG_LEVEL_WARN = 4;
/*     */   
/*     */ 
/*     */   public static final int LOG_LEVEL_ERROR = 5;
/*     */   
/*     */ 
/*     */   public static final int LOG_LEVEL_FATAL = 6;
/*     */   
/*     */ 
/*     */   public static final int LOG_LEVEL_ALL = 0;
/*     */   
/*     */ 
/*     */   public static final int LOG_LEVEL_OFF = 7;
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 140 */     InputStream in = getResourceAsStream("simplelog.properties");
/* 141 */     if (in != null) {
/*     */       try {
/* 143 */         simpleLogProps.load(in);
/* 144 */         in.close();
/*     */       }
/*     */       catch (IOException localIOException) {}
/*     */     }
/*     */     
/*     */ 
/* 150 */     showLogName = getBooleanProperty("org.apache.commons.logging.simplelog.showlogname", showLogName);
/* 151 */     showShortName = getBooleanProperty("org.apache.commons.logging.simplelog.showShortLogname", showShortName);
/* 152 */     showDateTime = getBooleanProperty("org.apache.commons.logging.simplelog.showdatetime", showDateTime);
/*     */     
/* 154 */     if (showDateTime) {
/* 155 */       dateTimeFormat = getStringProperty("org.apache.commons.logging.simplelog.dateTimeFormat", dateTimeFormat);
/*     */       try {
/* 157 */         dateFormatter = new SimpleDateFormat(dateTimeFormat);
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException) {
/* 160 */         dateTimeFormat = "yyyy/MM/dd HH:mm:ss:SSS zzz";
/* 161 */         dateFormatter = new SimpleDateFormat(dateTimeFormat);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean getBooleanProperty(String name, boolean dephault) {
/* 167 */     String prop = getStringProperty(name);
/* 168 */     return prop == null ? dephault : "true".equalsIgnoreCase(prop);
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
/*     */   private static ClassLoader getContextClassLoader()
/*     */   {
/* 181 */     ClassLoader classLoader = null;
/*     */     
/*     */     try
/*     */     {
/* 185 */       Method method = Thread.class.getMethod("getContextClassLoader", null);
/*     */       
/*     */       try
/*     */       {
/* 189 */         classLoader = (ClassLoader)method.invoke(Thread.currentThread(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException) {}catch (InvocationTargetException e)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 206 */         if ((e.getTargetException() instanceof SecurityException)) {
/*     */           break label56;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 212 */       throw new LogConfigurationException("Unexpected InvocationTargetException", e.getTargetException());
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException)
/*     */     {
/*     */       label56:
/*     */       
/*     */ 
/*     */ 
/* 220 */       if (classLoader == null) {
/* 221 */         classLoader = SimpleLog.class.getClassLoader();
/*     */       }
/*     */     }
/*     */     
/* 225 */     return classLoader;
/*     */   }
/*     */   
/*     */   private static InputStream getResourceAsStream(String name) {
/* 229 */     (InputStream)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Object run() {
/* 232 */         ClassLoader threadCL = SimpleLog.access$0();
/*     */         
/* 234 */         if (threadCL != null) {
/* 235 */           return threadCL.getResourceAsStream(SimpleLog.this);
/*     */         }
/* 237 */         return ClassLoader.getSystemResourceAsStream(SimpleLog.this);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static String getStringProperty(String name)
/*     */   {
/* 246 */     String prop = null;
/*     */     try {
/* 248 */       prop = System.getProperty(name);
/*     */     }
/*     */     catch (SecurityException localSecurityException) {}
/*     */     
/* 252 */     return prop == null ? simpleLogProps.getProperty(name) : prop;
/*     */   }
/*     */   
/*     */   private static String getStringProperty(String name, String dephault) {
/* 256 */     String prop = getStringProperty(name);
/* 257 */     return prop == null ? dephault : prop;
/*     */   }
/*     */   
/*     */ 
/* 261 */   protected volatile String logName = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected volatile int currentLogLevel;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 271 */   private volatile String shortLogName = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleLog(String name)
/*     */   {
/* 280 */     this.logName = name;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 285 */     setLevel(3);
/*     */     
/*     */ 
/* 288 */     String lvl = getStringProperty("org.apache.commons.logging.simplelog.log." + this.logName);
/* 289 */     int i = String.valueOf(name).lastIndexOf(".");
/* 290 */     while ((lvl == null) && (i > -1)) {
/* 291 */       name = name.substring(0, i);
/* 292 */       lvl = getStringProperty("org.apache.commons.logging.simplelog.log." + name);
/* 293 */       i = String.valueOf(name).lastIndexOf(".");
/*     */     }
/*     */     
/* 296 */     if (lvl == null) {
/* 297 */       lvl = getStringProperty("org.apache.commons.logging.simplelog.defaultlog");
/*     */     }
/*     */     
/* 300 */     if ("all".equalsIgnoreCase(lvl)) {
/* 301 */       setLevel(0);
/* 302 */     } else if ("trace".equalsIgnoreCase(lvl)) {
/* 303 */       setLevel(1);
/* 304 */     } else if ("debug".equalsIgnoreCase(lvl)) {
/* 305 */       setLevel(2);
/* 306 */     } else if ("info".equalsIgnoreCase(lvl)) {
/* 307 */       setLevel(3);
/* 308 */     } else if ("warn".equalsIgnoreCase(lvl)) {
/* 309 */       setLevel(4);
/* 310 */     } else if ("error".equalsIgnoreCase(lvl)) {
/* 311 */       setLevel(5);
/* 312 */     } else if ("fatal".equalsIgnoreCase(lvl)) {
/* 313 */       setLevel(6);
/* 314 */     } else if ("off".equalsIgnoreCase(lvl)) {
/* 315 */       setLevel(7);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void debug(Object message)
/*     */   {
/* 331 */     if (isLevelEnabled(2)) {
/* 332 */       log(2, message, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void debug(Object message, Throwable t)
/*     */   {
/* 348 */     if (isLevelEnabled(2)) {
/* 349 */       log(2, message, t);
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
/*     */ 
/*     */   public final void error(Object message)
/*     */   {
/* 363 */     if (isLevelEnabled(5)) {
/* 364 */       log(5, message, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void error(Object message, Throwable t)
/*     */   {
/* 383 */     if (isLevelEnabled(5)) {
/* 384 */       log(5, message, t);
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
/*     */ 
/*     */   public final void fatal(Object message)
/*     */   {
/* 398 */     if (isLevelEnabled(6)) {
/* 399 */       log(6, message, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void fatal(Object message, Throwable t)
/*     */   {
/* 415 */     if (isLevelEnabled(6)) {
/* 416 */       log(6, message, t);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getLevel()
/*     */   {
/* 424 */     return this.currentLogLevel;
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
/*     */   public final void info(Object message)
/*     */   {
/* 437 */     if (isLevelEnabled(3)) {
/* 438 */       log(3, message, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void info(Object message, Throwable t)
/*     */   {
/* 454 */     if (isLevelEnabled(3)) {
/* 455 */       log(3, message, t);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isDebugEnabled()
/*     */   {
/* 467 */     return isLevelEnabled(2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isErrorEnabled()
/*     */   {
/* 478 */     return isLevelEnabled(5);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isFatalEnabled()
/*     */   {
/* 489 */     return isLevelEnabled(6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isInfoEnabled()
/*     */   {
/* 500 */     return isLevelEnabled(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isLevelEnabled(int logLevel)
/*     */   {
/* 512 */     return logLevel >= this.currentLogLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isTraceEnabled()
/*     */   {
/* 523 */     return isLevelEnabled(1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isWarnEnabled()
/*     */   {
/* 534 */     return isLevelEnabled(4);
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
/*     */ 
/*     */ 
/*     */   protected void log(int type, Object message, Throwable t)
/*     */   {
/* 552 */     StringBuffer buf = new StringBuffer();
/*     */     
/*     */ 
/* 555 */     if (showDateTime) {
/* 556 */       Date now = new Date();
/*     */       String dateText;
/* 558 */       synchronized (dateFormatter) {
/* 559 */         dateText = dateFormatter.format(now); }
/*     */       String dateText;
/* 561 */       buf.append(dateText);
/* 562 */       buf.append(" ");
/*     */     }
/*     */     
/*     */ 
/* 566 */     switch (type) {
/*     */     case 1: 
/* 568 */       buf.append("[TRACE] ");
/* 569 */       break;
/*     */     case 2: 
/* 571 */       buf.append("[DEBUG] ");
/* 572 */       break;
/*     */     case 3: 
/* 574 */       buf.append("[INFO] ");
/* 575 */       break;
/*     */     case 4: 
/* 577 */       buf.append("[WARN] ");
/* 578 */       break;
/*     */     case 5: 
/* 580 */       buf.append("[ERROR] ");
/* 581 */       break;
/*     */     case 6: 
/* 583 */       buf.append("[FATAL] ");
/*     */     }
/*     */     
/*     */     
/*     */ 
/* 588 */     if (showShortName) {
/* 589 */       if (this.shortLogName == null)
/*     */       {
/* 591 */         String slName = this.logName.substring(this.logName.lastIndexOf(".") + 1);
/* 592 */         this.shortLogName = slName.substring(slName.lastIndexOf("/") + 1);
/*     */       }
/* 594 */       buf.append(String.valueOf(this.shortLogName)).append(" - ");
/* 595 */     } else if (showLogName) {
/* 596 */       buf.append(String.valueOf(this.logName)).append(" - ");
/*     */     }
/*     */     
/*     */ 
/* 600 */     buf.append(String.valueOf(message));
/*     */     
/*     */ 
/* 603 */     if (t != null) {
/* 604 */       buf.append(" <");
/* 605 */       buf.append(t.toString());
/* 606 */       buf.append(">");
/*     */       
/* 608 */       StringWriter sw = new StringWriter(1024);
/* 609 */       PrintWriter pw = new PrintWriter(sw);
/* 610 */       t.printStackTrace(pw);
/* 611 */       pw.close();
/* 612 */       buf.append(sw.toString());
/*     */     }
/*     */     
/*     */ 
/* 616 */     write(buf);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLevel(int currentLogLevel)
/*     */   {
/* 626 */     this.currentLogLevel = currentLogLevel;
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
/*     */   public final void trace(Object message)
/*     */   {
/* 639 */     if (isLevelEnabled(1)) {
/* 640 */       log(1, message, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void trace(Object message, Throwable t)
/*     */   {
/* 656 */     if (isLevelEnabled(1)) {
/* 657 */       log(1, message, t);
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
/*     */ 
/*     */   public final void warn(Object message)
/*     */   {
/* 671 */     if (isLevelEnabled(4)) {
/* 672 */       log(4, message, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void warn(Object message, Throwable t)
/*     */   {
/* 688 */     if (isLevelEnabled(4)) {
/* 689 */       log(4, message, t);
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
/*     */ 
/*     */   protected void write(StringBuffer buffer)
/*     */   {
/* 703 */     System.err.println(buffer.toString());
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\apache\commons\logging\impl\SimpleLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */