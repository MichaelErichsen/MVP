/*     */ package org.apache.commons.logging;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.logging.impl.NoOpLog;
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
/*     */ @Deprecated
/*     */ public class LogSource
/*     */ {
/*  61 */   protected static Hashtable<String, Log> logs = new Hashtable();
/*     */   
/*     */ 
/*  64 */   protected static boolean log4jIsAvailable = false;
/*     */   
/*     */ 
/*  67 */   protected static boolean jdk14IsAvailable = false;
/*     */   
/*     */ 
/*  70 */   protected static Constructor<?> logImplctor = null;
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  78 */       log4jIsAvailable = Class.forName("org.apache.log4j.Logger") != null;
/*     */     } catch (Throwable localThrowable1) {
/*  80 */       log4jIsAvailable = false;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  85 */       jdk14IsAvailable = (Class.forName("java.util.logging.Logger") != null) && 
/*  86 */         (Class.forName("org.apache.commons.logging.impl.Jdk14Logger") != null);
/*     */     } catch (Throwable localThrowable2) {
/*  88 */       jdk14IsAvailable = false;
/*     */     }
/*     */     
/*     */ 
/*  92 */     String name = null;
/*     */     try {
/*  94 */       name = System.getProperty("org.apache.commons.logging.log");
/*  95 */       if (name == null) {
/*  96 */         name = System.getProperty("org.apache.commons.logging.Log");
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable3) {}
/* 100 */     if (name != null) {
/*     */       try {
/* 102 */         setLogImplementation(name);
/*     */       } catch (Throwable localThrowable4) {
/*     */         try {
/* 105 */           setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
/*     */         }
/*     */         catch (Throwable localThrowable5) {}
/*     */       }
/*     */     } else {
/*     */       try
/*     */       {
/* 112 */         if (log4jIsAvailable) {
/* 113 */           setLogImplementation("org.apache.commons.logging.impl.Log4JLogger");
/* 114 */         } else if (jdk14IsAvailable) {
/* 115 */           setLogImplementation("org.apache.commons.logging.impl.Jdk14Logger");
/*     */         } else {
/* 117 */           setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
/*     */         }
/*     */       } catch (Throwable localThrowable6) {
/*     */         try {
/* 121 */           setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
/*     */         }
/*     */         catch (Throwable localThrowable7) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Log getInstance(Class<?> clazz)
/*     */   {
/* 134 */     return getInstance(clazz.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Log getInstance(String name)
/*     */   {
/* 141 */     Log log = (Log)logs.get(name);
/* 142 */     if (log == null) {
/* 143 */       log = makeNewLogInstance(name);
/* 144 */       logs.put(name, log);
/*     */     }
/* 146 */     return log;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String[] getLogNames()
/*     */   {
/* 153 */     return (String[])logs.keySet().toArray(new String[logs.size()]);
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
/*     */   public static Log makeNewLogInstance(String name)
/*     */   {
/*     */     Log log;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 178 */       Object[] args = { name };
/* 179 */       log = (Log)logImplctor.newInstance(args);
/*     */     } catch (Throwable localThrowable) { Log log;
/* 181 */       log = null;
/*     */     }
/* 183 */     if (log == null) {
/* 184 */       log = new NoOpLog(name);
/*     */     }
/* 186 */     return log;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setLogImplementation(Class<?> logclass)
/*     */     throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException
/*     */   {
/* 197 */     Class[] argtypes = new Class[1];
/* 198 */     argtypes[0] = "".getClass();
/* 199 */     logImplctor = logclass.getConstructor(argtypes);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setLogImplementation(String classname)
/*     */     throws LinkageError, NoSuchMethodException, SecurityException, ClassNotFoundException
/*     */   {
/*     */     try
/*     */     {
/* 210 */       Class<?> logclass = Class.forName(classname);
/*     */       
/* 212 */       Class[] argtypes = new Class[1];
/* 213 */       argtypes[0] = "".getClass();
/* 214 */       logImplctor = logclass.getConstructor(argtypes);
/*     */     } catch (Throwable localThrowable) {
/* 216 */       logImplctor = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\apache\commons\logging\LogSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */