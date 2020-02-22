/*      */ package org.apache.commons.logging;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class LogFactory
/*      */ {
/*      */   public static final String PRIORITY_KEY = "priority";
/*      */   public static final String TCCL_KEY = "use_tccl";
/*      */   public static final String FACTORY_PROPERTY = "org.apache.commons.logging.LogFactory";
/*      */   public static final String FACTORY_DEFAULT = "org.apache.commons.logging.impl.LogFactoryImpl";
/*      */   public static final String FACTORY_PROPERTIES = "commons-logging.properties";
/*      */   protected static final String SERVICE_ID = "META-INF/services/org.apache.commons.logging.LogFactory";
/*      */   public static final String DIAGNOSTICS_DEST_PROPERTY = "org.apache.commons.logging.diagnostics.dest";
/*  132 */   private static PrintStream diagnosticsStream = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String diagnosticPrefix;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String HASHTABLE_IMPLEMENTATION_PROPERTY = "org.apache.commons.logging.LogFactory.HashtableImpl";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String WEAK_HASHTABLE_CLASSNAME = "org.apache.commons.logging.impl.WeakHashtable";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final ClassLoader thisClassLoader;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  194 */   protected static Hashtable<ClassLoader, LogFactory> factories = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*  214 */   protected static volatile LogFactory nullClassLoaderFactory = null;
/*      */   
/*      */ 
/*      */   static
/*      */   {
/*  219 */     thisClassLoader = getClassLoader(LogFactory.class);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     String classLoaderName;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  231 */       ClassLoader classLoader = thisClassLoader;
/*  232 */       if (thisClassLoader == null) {
/*  233 */         classLoaderName = "BOOTLOADER";
/*      */       } else
/*  235 */         classLoaderName = objectId(classLoader);
/*      */     } catch (SecurityException localSecurityException) {
/*  238 */       classLoaderName = "UNKNOWN";
/*      */     }
/*  240 */     diagnosticPrefix = "[LogFactory from " + classLoaderName + "] ";
/*  241 */     diagnosticsStream = initDiagnostics();
/*  242 */     logClassLoaderEnvironment(LogFactory.class);
/*  243 */     factories = createFactoryStore();
/*  244 */     if (isDiagnosticsEnabled()) {
/*  245 */       logDiagnostic("BOOTSTRAP COMPLETED");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void cacheFactory(ClassLoader classLoader, LogFactory factory)
/*      */   {
/*  264 */     if (factory != null) {
/*  265 */       if (classLoader == null) {
/*  266 */         nullClassLoaderFactory = factory;
/*      */       } else {
/*  268 */         factories.put(classLoader, factory);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static Object createFactory(String factoryClass, ClassLoader classLoader)
/*      */   {
/*  288 */     Class<?> logFactoryClass = null;
/*      */     try {
/*  290 */       if (classLoader != null)
/*      */       {
/*      */ 
/*      */         try
/*      */         {
/*      */ 
/*  296 */           logFactoryClass = classLoader.loadClass(factoryClass);
/*  297 */           if (LogFactory.class.isAssignableFrom(logFactoryClass)) {
/*  298 */             if (isDiagnosticsEnabled()) {
/*  299 */               logDiagnostic(
/*  300 */                 "Loaded class " + logFactoryClass.getName() + " from classloader " + objectId(classLoader));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*  314 */           else if (isDiagnosticsEnabled()) {
/*  315 */             logDiagnostic(
/*      */             
/*  317 */               "Factory class " + logFactoryClass.getName() + " loaded from classloader " + objectId(logFactoryClass.getClassLoader()) + " does not extend '" + LogFactory.class.getName() + "' as loaded by this classloader.");
/*  318 */             logHierarchy("[BAD CL TREE] ", classLoader);
/*      */           }
/*      */           
/*      */ 
/*  322 */           return logFactoryClass.newInstance();
/*      */         }
/*      */         catch (ClassNotFoundException ex) {
/*  325 */           if (classLoader == thisClassLoader)
/*      */           {
/*  327 */             if (isDiagnosticsEnabled()) {
/*  328 */               logDiagnostic(
/*  329 */                 "Unable to locate any class called '" + factoryClass + "' via classloader " + objectId(classLoader));
/*      */             }
/*  331 */             throw ex;
/*      */           }
/*      */         }
/*      */         catch (NoClassDefFoundError e) {
/*  335 */           if (classLoader == thisClassLoader)
/*      */           {
/*  337 */             if (isDiagnosticsEnabled()) {
/*  338 */               logDiagnostic(
/*      */               
/*  340 */                 "Class '" + factoryClass + "' cannot be loaded" + " via classloader " + objectId(classLoader) + " - it depends on some other class that cannot be found.");
/*      */             }
/*  342 */             throw e;
/*      */           }
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*  346 */           if (classLoader == thisClassLoader)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  355 */             boolean implementsLogFactory = implementsLogFactory(logFactoryClass);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  364 */             StringBuffer msg = new StringBuffer();
/*  365 */             msg.append("The application has specified that a custom LogFactory implementation ");
/*  366 */             msg.append("should be used but Class '");
/*  367 */             msg.append(factoryClass);
/*  368 */             msg.append("' cannot be converted to '");
/*  369 */             msg.append(LogFactory.class.getName());
/*  370 */             msg.append("'. ");
/*  371 */             if (implementsLogFactory) {
/*  372 */               msg.append("The conflict is caused by the presence of multiple LogFactory classes ");
/*  373 */               msg.append("in incompatible classloaders. ");
/*  374 */               msg.append("Background can be found in http://commons.apache.org/logging/tech.html. ");
/*  375 */               msg.append("If you have not explicitly specified a custom LogFactory then it is likely ");
/*  376 */               msg.append("that the container has set one without your knowledge. ");
/*  377 */               msg.append("In this case, consider using the commons-logging-adapters.jar file or ");
/*  378 */               msg.append("specifying the standard LogFactory from the command line. ");
/*      */             } else {
/*  380 */               msg.append("Please check the custom implementation. ");
/*      */             }
/*  382 */             msg.append("Help can be found @http://commons.apache.org/logging/troubleshooting.html.");
/*      */             
/*  384 */             if (isDiagnosticsEnabled()) {
/*  385 */               logDiagnostic(msg.toString());
/*      */             }
/*      */             
/*  388 */             throw new ClassCastException(msg.toString());
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  431 */       if (isDiagnosticsEnabled()) {
/*  432 */         logDiagnostic(
/*  433 */           "Unable to load factory class via classloader " + objectId(classLoader) + " - trying the classloader associated with this LogFactory.");
/*      */       }
/*  435 */       logFactoryClass = Class.forName(factoryClass);
/*  436 */       return logFactoryClass.newInstance();
/*      */     }
/*      */     catch (Exception e) {
/*  439 */       if (isDiagnosticsEnabled()) {
/*  440 */         logDiagnostic("Unable to create LogFactory instance.");
/*      */       }
/*  442 */       if ((logFactoryClass != null) && (!LogFactory.class.isAssignableFrom(logFactoryClass))) {
/*  443 */         return new LogConfigurationException("The chosen LogFactory implementation does not extend LogFactory. Please check your configuration.", 
/*  444 */           e);
/*      */       }
/*  446 */       return new LogConfigurationException(e);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final Hashtable<ClassLoader, LogFactory> createFactoryStore()
/*      */   {
/*  466 */     Hashtable<ClassLoader, LogFactory> result = null;
/*      */     String storeImplementationClass;
/*      */     try {
/*  469 */       storeImplementationClass = getSystemProperty("org.apache.commons.logging.LogFactory.HashtableImpl", null);
/*      */     }
/*      */     catch (SecurityException localSecurityException)
/*      */     {
/*  474 */       storeImplementationClass = null;
/*      */     }
/*      */     
/*  477 */     if (storeImplementationClass == null) {
/*  478 */       storeImplementationClass = "org.apache.commons.logging.impl.WeakHashtable";
/*      */     }
/*      */     try {
/*  481 */       Class<?> implementationClass = Class.forName(storeImplementationClass);
/*  482 */       result = (Hashtable)implementationClass.newInstance();
/*      */     } catch (Throwable t) {
/*  484 */       handleThrowable(t);
/*      */       
/*      */ 
/*  487 */       if (!"org.apache.commons.logging.impl.WeakHashtable".equals(storeImplementationClass))
/*      */       {
/*      */ 
/*  490 */         if (isDiagnosticsEnabled())
/*      */         {
/*  492 */           logDiagnostic("[ERROR] LogFactory: Load of custom hashtable failed");
/*      */         }
/*      */         else
/*      */         {
/*  496 */           System.err.println("[ERROR] LogFactory: Load of custom hashtable failed");
/*      */         }
/*      */       }
/*      */     }
/*  500 */     if (result == null) {
/*  501 */       result = new Hashtable();
/*      */     }
/*  503 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ClassLoader directGetContextClassLoader()
/*      */     throws LogConfigurationException
/*      */   {
/*  527 */     ClassLoader classLoader = null;
/*      */     try
/*      */     {
/*  530 */       classLoader = Thread.currentThread().getContextClassLoader();
/*      */     }
/*      */     catch (SecurityException localSecurityException) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  544 */     return classLoader;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static LogFactory getCachedFactory(ClassLoader contextClassLoader)
/*      */   {
/*  563 */     if (contextClassLoader == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  568 */       return nullClassLoaderFactory;
/*      */     }
/*  570 */     return (LogFactory)factories.get(contextClassLoader);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ClassLoader getClassLoader(Class<LogFactory> clazz)
/*      */   {
/*      */     try
/*      */     {
/*  605 */       return clazz.getClassLoader();
/*      */     } catch (SecurityException ex) {
/*  607 */       if (isDiagnosticsEnabled()) {
/*  608 */         logDiagnostic(
/*  609 */           "Unable to get classloader for class '" + clazz + "' due to security restrictions - " + ex.getMessage());
/*      */       }
/*  611 */       throw ex;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final Properties getConfigurationFile(ClassLoader classLoader, String fileName)
/*      */   {
/*  635 */     Properties props = null;
/*  636 */     double priority = 0.0D;
/*  637 */     URL propsUrl = null;
/*      */     try {
/*  639 */       Enumeration<?> urls = getResources(classLoader, fileName);
/*      */       
/*  641 */       if (urls == null) {
/*  642 */         return null;
/*      */       }
/*      */       
/*  645 */       while (urls.hasMoreElements()) {
/*  646 */         URL url = (URL)urls.nextElement();
/*      */         
/*  648 */         Properties newProps = getProperties(url);
/*  649 */         if (newProps != null) {
/*  650 */           if (props == null) {
/*  651 */             propsUrl = url;
/*  652 */             props = newProps;
/*  653 */             String priorityStr = props.getProperty("priority");
/*  654 */             priority = 0.0D;
/*  655 */             if (priorityStr != null) {
/*  656 */               priority = Double.parseDouble(priorityStr);
/*      */             }
/*      */             
/*  659 */             if (isDiagnosticsEnabled()) {
/*  660 */               logDiagnostic(
/*  661 */                 "[LOOKUP] Properties file found at '" + url + "'" + " with priority " + priority);
/*      */             }
/*      */           } else {
/*  664 */             String newPriorityStr = newProps.getProperty("priority");
/*  665 */             double newPriority = 0.0D;
/*  666 */             if (newPriorityStr != null) {
/*  667 */               newPriority = Double.parseDouble(newPriorityStr);
/*      */             }
/*      */             
/*  670 */             if (newPriority > priority) {
/*  671 */               if (isDiagnosticsEnabled()) {
/*  672 */                 logDiagnostic(
/*      */                 
/*  674 */                   "[LOOKUP] Properties file at '" + url + "'" + " with priority " + newPriority + " overrides file at '" + propsUrl + "'" + " with priority " + priority);
/*      */               }
/*      */               
/*  677 */               propsUrl = url;
/*  678 */               props = newProps;
/*  679 */               priority = newPriority;
/*      */             }
/*  681 */             else if (isDiagnosticsEnabled()) {
/*  682 */               logDiagnostic(
/*      */               
/*  684 */                 "[LOOKUP] Properties file at '" + url + "'" + " with priority " + newPriority + " does not override file at '" + propsUrl + "'" + " with priority " + priority);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SecurityException localSecurityException)
/*      */     {
/*  692 */       if (isDiagnosticsEnabled()) {
/*  693 */         logDiagnostic("SecurityException thrown while trying to find/read config files.");
/*      */       }
/*      */     }
/*      */     
/*  697 */     if (isDiagnosticsEnabled()) {
/*  698 */       if (props == null) {
/*  699 */         logDiagnostic("[LOOKUP] No properties file of name '" + fileName + "' found.");
/*      */       } else {
/*  701 */         logDiagnostic("[LOOKUP] Properties file of name '" + fileName + "' found at '" + propsUrl + '"');
/*      */       }
/*      */     }
/*      */     
/*  705 */     return props;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ClassLoader getContextClassLoader()
/*      */     throws LogConfigurationException
/*      */   {
/*  727 */     return directGetContextClassLoader();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ClassLoader getContextClassLoaderInternal()
/*      */     throws LogConfigurationException
/*      */   {
/*  747 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/*  750 */         return LogFactory.directGetContextClassLoader();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static LogFactory getFactory()
/*      */     throws LogConfigurationException
/*      */   {
/*  787 */     ClassLoader contextClassLoader = getContextClassLoaderInternal();
/*      */     
/*  789 */     if (contextClassLoader == null)
/*      */     {
/*      */ 
/*      */ 
/*  793 */       if (isDiagnosticsEnabled()) {
/*  794 */         logDiagnostic("Context classloader is null.");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  799 */     LogFactory factory = getCachedFactory(contextClassLoader);
/*  800 */     if (factory != null) {
/*  801 */       return factory;
/*      */     }
/*      */     
/*  804 */     if (isDiagnosticsEnabled()) {
/*  805 */       logDiagnostic(
/*  806 */         "[LOOKUP] LogFactory implementation requested for the first time for context classloader " + objectId(contextClassLoader));
/*  807 */       logHierarchy("[LOOKUP] ", contextClassLoader);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  820 */     Properties props = getConfigurationFile(contextClassLoader, "commons-logging.properties");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  825 */     ClassLoader baseClassLoader = contextClassLoader;
/*  826 */     if (props != null) {
/*  827 */       String useTCCLStr = props.getProperty("use_tccl");
/*  828 */       if (useTCCLStr != null)
/*      */       {
/*      */ 
/*  831 */         if (!Boolean.valueOf(useTCCLStr).booleanValue())
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  843 */           baseClassLoader = thisClassLoader;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  850 */     if (isDiagnosticsEnabled()) {
/*  851 */       logDiagnostic("[LOOKUP] Looking for system property [org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  856 */       String factoryClass = getSystemProperty("org.apache.commons.logging.LogFactory", null);
/*  857 */       if (factoryClass != null) {
/*  858 */         if (isDiagnosticsEnabled()) {
/*  859 */           logDiagnostic(
/*  860 */             "[LOOKUP] Creating an instance of LogFactory class '" + factoryClass + "' as specified by system property " + "org.apache.commons.logging.LogFactory");
/*      */         }
/*  862 */         factory = newFactory(factoryClass, baseClassLoader, contextClassLoader);
/*      */       }
/*  864 */       else if (isDiagnosticsEnabled()) {
/*  865 */         logDiagnostic("[LOOKUP] No system property [org.apache.commons.logging.LogFactory] defined.");
/*      */       }
/*      */     }
/*      */     catch (SecurityException e) {
/*  869 */       if (isDiagnosticsEnabled()) {
/*  870 */         logDiagnostic(
/*      */         
/*  872 */           "[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [" + trim(e.getMessage()) + "]. Trying alternative implementations...");
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (RuntimeException e)
/*      */     {
/*      */ 
/*      */ 
/*  883 */       if (isDiagnosticsEnabled()) {
/*  884 */         logDiagnostic(
/*      */         
/*  886 */           "[LOOKUP] An exception occurred while trying to create an instance of the custom factory class: [" + trim(e.getMessage()) + "] as specified by a system property.");
/*      */       }
/*  888 */       throw e;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  897 */     if (factory == null) {
/*  898 */       if (isDiagnosticsEnabled()) {
/*  899 */         logDiagnostic("[LOOKUP] Looking for a resource file of name [META-INF/services/org.apache.commons.logging.LogFactory] to define the LogFactory subclass to use...");
/*      */       }
/*      */       try
/*      */       {
/*  903 */         InputStream is = getResourceAsStream(contextClassLoader, "META-INF/services/org.apache.commons.logging.LogFactory");
/*      */         
/*  905 */         if (is != null)
/*      */         {
/*      */           BufferedReader rd;
/*      */           try
/*      */           {
/*  910 */             rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/*      */           } catch (UnsupportedEncodingException localUnsupportedEncodingException) { BufferedReader rd;
/*  912 */             rd = new BufferedReader(new InputStreamReader(is));
/*      */           }
/*      */           
/*  915 */           String factoryClassName = rd.readLine();
/*  916 */           rd.close();
/*      */           
/*  918 */           if ((factoryClassName != null) && (!"".equals(factoryClassName))) {
/*  919 */             if (isDiagnosticsEnabled()) {
/*  920 */               logDiagnostic(
/*      */               
/*  922 */                 "[LOOKUP]  Creating an instance of LogFactory class " + factoryClassName + " as specified by file '" + "META-INF/services/org.apache.commons.logging.LogFactory" + "' which was present in the path of the context classloader.");
/*      */             }
/*  924 */             factory = newFactory(factoryClassName, baseClassLoader, contextClassLoader);
/*      */           }
/*      */           
/*      */         }
/*  928 */         else if (isDiagnosticsEnabled()) {
/*  929 */           logDiagnostic("[LOOKUP] No resource file with name 'META-INF/services/org.apache.commons.logging.LogFactory' found.");
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*      */ 
/*  938 */         if (isDiagnosticsEnabled()) {
/*  939 */           logDiagnostic(
/*      */           
/*  941 */             "[LOOKUP] A security exception occurred while trying to create an instance of the custom factory class: [" + trim(ex.getMessage()) + "]. Trying alternative implementations...");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  949 */     if (factory == null) {
/*  950 */       if (props != null) {
/*  951 */         if (isDiagnosticsEnabled()) {
/*  952 */           logDiagnostic("[LOOKUP] Looking in properties file for entry with key 'org.apache.commons.logging.LogFactory' to define the LogFactory subclass to use...");
/*      */         }
/*      */         
/*  955 */         String factoryClass = props.getProperty("org.apache.commons.logging.LogFactory");
/*  956 */         if (factoryClass != null) {
/*  957 */           if (isDiagnosticsEnabled()) {
/*  958 */             logDiagnostic("[LOOKUP] Properties file specifies LogFactory subclass '" + factoryClass + "'");
/*      */           }
/*  960 */           factory = newFactory(factoryClass, baseClassLoader, contextClassLoader);
/*      */ 
/*      */         }
/*  963 */         else if (isDiagnosticsEnabled()) {
/*  964 */           logDiagnostic("[LOOKUP] Properties file has no entry specifying LogFactory subclass.");
/*      */         }
/*      */         
/*      */       }
/*  968 */       else if (isDiagnosticsEnabled()) {
/*  969 */         logDiagnostic("[LOOKUP] No properties file available to determine LogFactory subclass from..");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  976 */     if (factory == null) {
/*  977 */       if (isDiagnosticsEnabled()) {
/*  978 */         logDiagnostic("[LOOKUP] Loading the default LogFactory implementation 'org.apache.commons.logging.impl.LogFactoryImpl' via the same classloader that loaded this LogFactory class (ie not looking in the context classloader).");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  994 */       factory = newFactory("org.apache.commons.logging.impl.LogFactoryImpl", thisClassLoader, contextClassLoader);
/*      */     }
/*      */     
/*  997 */     if (factory != null)
/*      */     {
/*      */ 
/*      */ 
/* 1001 */       cacheFactory(contextClassLoader, factory);
/*      */       
/* 1003 */       if (props != null) {
/* 1004 */         Enumeration<?> names = props.propertyNames();
/* 1005 */         while (names.hasMoreElements()) {
/* 1006 */           String name = (String)names.nextElement();
/* 1007 */           String value = props.getProperty(name);
/* 1008 */           factory.setAttribute(name, value);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1013 */     return factory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Log getLog(Class<?> clazz)
/*      */     throws LogConfigurationException
/*      */   {
/* 1026 */     return getFactory().getInstance(clazz);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Log getLog(String name)
/*      */     throws LogConfigurationException
/*      */   {
/* 1041 */     return getFactory().getInstance(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Properties getProperties(URL url)
/*      */   {
/* 1053 */     PrivilegedAction<Object> action = new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/* 1056 */         InputStream stream = null;
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 1061 */           URLConnection connection = LogFactory.this.openConnection();
/* 1062 */           connection.setUseCaches(false);
/* 1063 */           stream = connection.getInputStream();
/* 1064 */           if (stream != null) {
/* 1065 */             Properties props = new Properties();
/* 1066 */             props.load(stream);
/* 1067 */             stream.close();
/* 1068 */             stream = null;
/* 1069 */             return props;
/*      */           }
/*      */         } catch (IOException localIOException2) {
/* 1072 */           if (LogFactory.isDiagnosticsEnabled()) {
/* 1073 */             LogFactory.logDiagnostic("Unable to read URL " + LogFactory.this);
/*      */           }
/*      */           
/* 1076 */           if (stream == null)
/*      */             break label246;
/* 1078 */           try { stream.close();
/*      */           }
/*      */           catch (IOException localIOException3) {
/* 1081 */             if (!LogFactory.isDiagnosticsEnabled()) break label246; }
/* 1082 */           LogFactory.logDiagnostic("Unable to close stream for URL " + LogFactory.this);
/*      */         }
/*      */         finally
/*      */         {
/* 1076 */           if (stream != null) {
/*      */             try {
/* 1078 */               stream.close();
/*      */             }
/*      */             catch (IOException localIOException4) {
/* 1081 */               if (LogFactory.isDiagnosticsEnabled()) {
/* 1082 */                 LogFactory.logDiagnostic("Unable to close stream for URL " + LogFactory.this);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 1076 */         if (stream != null) {
/*      */           try {
/* 1078 */             stream.close();
/*      */           }
/*      */           catch (IOException localIOException5) {
/* 1081 */             if (LogFactory.isDiagnosticsEnabled()) {
/* 1082 */               LogFactory.logDiagnostic("Unable to close stream for URL " + LogFactory.this);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */         label246:
/* 1088 */         return null;
/*      */       }
/* 1090 */     };
/* 1091 */     return (Properties)AccessController.doPrivileged(action);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static InputStream getResourceAsStream(ClassLoader loader, final String name)
/*      */   {
/* 1101 */     (InputStream)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/* 1104 */         if (LogFactory.this != null) {
/* 1105 */           return LogFactory.this.getResourceAsStream(name);
/*      */         }
/* 1107 */         return ClassLoader.getSystemResourceAsStream(name);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Enumeration<?> getResources(ClassLoader loader, final String name)
/*      */   {
/* 1127 */     PrivilegedAction<?> action = new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/*      */         try {
/* 1131 */           if (LogFactory.this != null) {
/* 1132 */             return LogFactory.this.getResources(name);
/*      */           }
/* 1134 */           return ClassLoader.getSystemResources(name);
/*      */         }
/*      */         catch (IOException e) {
/* 1137 */           if (LogFactory.isDiagnosticsEnabled()) {
/* 1138 */             LogFactory.logDiagnostic(
/* 1139 */               "Exception while trying to find configuration file " + name + ":" + e.getMessage());
/*      */           }
/* 1141 */           return null;
/*      */         }
/*      */         catch (NoSuchMethodError localNoSuchMethodError) {}
/*      */         
/*      */ 
/* 1146 */         return null;
/*      */       }
/*      */       
/* 1149 */     };
/* 1150 */     Object result = AccessController.doPrivileged(action);
/* 1151 */     return (Enumeration)result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getSystemProperty(String key, final String def)
/*      */     throws SecurityException
/*      */   {
/* 1166 */     (String)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/* 1169 */         return System.getProperty(LogFactory.this, def);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static void handleThrowable(Throwable t)
/*      */   {
/* 1188 */     if ((t instanceof ThreadDeath)) {
/* 1189 */       throw ((ThreadDeath)t);
/*      */     }
/* 1191 */     if ((t instanceof VirtualMachineError)) {
/* 1192 */       throw ((VirtualMachineError)t);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean implementsLogFactory(Class<?> logFactoryClass)
/*      */   {
/* 1212 */     boolean implementsLogFactory = false;
/* 1213 */     if (logFactoryClass != null) {
/*      */       try {
/* 1215 */         ClassLoader logFactoryClassLoader = logFactoryClass.getClassLoader();
/* 1216 */         if (logFactoryClassLoader == null) {
/* 1217 */           logDiagnostic("[CUSTOM LOG FACTORY] was loaded by the boot classloader");
/*      */         } else {
/* 1219 */           logHierarchy("[CUSTOM LOG FACTORY] ", logFactoryClassLoader);
/* 1220 */           Class<?> factoryFromCustomLoader = Class.forName("org.apache.commons.logging.LogFactory", 
/* 1221 */             false, logFactoryClassLoader);
/* 1222 */           implementsLogFactory = factoryFromCustomLoader.isAssignableFrom(logFactoryClass);
/* 1223 */           if (implementsLogFactory) {
/* 1224 */             logDiagnostic(
/* 1225 */               "[CUSTOM LOG FACTORY] " + logFactoryClass.getName() + " implements LogFactory but was loaded by an incompatible classloader.");
/*      */           } else {
/* 1227 */             logDiagnostic(
/* 1228 */               "[CUSTOM LOG FACTORY] " + logFactoryClass.getName() + " does not implement LogFactory.");
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (SecurityException e)
/*      */       {
/*      */ 
/* 1238 */         logDiagnostic(
/* 1239 */           "[CUSTOM LOG FACTORY] SecurityException thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: " + e.getMessage());
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       catch (LinkageError e)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1249 */         logDiagnostic(
/* 1250 */           "[CUSTOM LOG FACTORY] LinkageError thrown whilst trying to determine whether the compatibility was caused by a classloader conflict: " + e.getMessage());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       catch (ClassNotFoundException localClassNotFoundException)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1263 */         logDiagnostic("[CUSTOM LOG FACTORY] LogFactory class cannot be loaded by classloader which loaded the custom LogFactory implementation. Is the custom factory in the right classloader?");
/*      */       }
/*      */     }
/*      */     
/* 1267 */     return implementsLogFactory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static PrintStream initDiagnostics()
/*      */   {
/*      */     try
/*      */     {
/* 1279 */       String dest = getSystemProperty("org.apache.commons.logging.diagnostics.dest", null);
/* 1280 */       if (dest == null) {
/* 1281 */         return null;
/*      */       }
/*      */     }
/*      */     catch (SecurityException localSecurityException)
/*      */     {
/* 1286 */       return null;
/*      */     }
/*      */     String dest;
/* 1289 */     if (dest.equals("STDOUT"))
/* 1290 */       return System.out;
/* 1291 */     if (dest.equals("STDERR")) {
/* 1292 */       return System.err;
/*      */     }
/*      */     try
/*      */     {
/* 1296 */       FileOutputStream fos = new FileOutputStream(dest, true);
/* 1297 */       return new PrintStream(fos);
/*      */     }
/*      */     catch (IOException localIOException) {}
/* 1300 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static boolean isDiagnosticsEnabled()
/*      */   {
/* 1315 */     return diagnosticsStream != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void logClassLoaderEnvironment(Class<LogFactory> clazz)
/*      */   {
/* 1336 */     if (!isDiagnosticsEnabled()) {
/* 1337 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1347 */       logDiagnostic("[ENV] Extension directories (java.ext.dir): " + System.getProperty("java.ext.dir"));
/* 1348 */       logDiagnostic("[ENV] Application classpath (java.class.path): " + System.getProperty("java.class.path"));
/*      */     } catch (SecurityException localSecurityException1) {
/* 1350 */       logDiagnostic("[ENV] Security setting prevent interrogation of system classpaths.");
/*      */     }
/*      */     
/* 1353 */     String className = clazz.getName();
/*      */     
/*      */     try
/*      */     {
/* 1357 */       classLoader = getClassLoader(clazz);
/*      */     } catch (SecurityException localSecurityException2) {
/*      */       ClassLoader classLoader;
/* 1360 */       logDiagnostic("[ENV] Security forbids determining the classloader for " + className); return;
/*      */     }
/*      */     
/*      */     ClassLoader classLoader;
/* 1364 */     logDiagnostic("[ENV] Class " + className + " was loaded via classloader " + objectId(classLoader));
/* 1365 */     logHierarchy("[ENV] Ancestry of classloader which loaded " + className + " is ", classLoader);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final void logDiagnostic(String msg)
/*      */   {
/* 1387 */     if (diagnosticsStream != null) {
/* 1388 */       diagnosticsStream.print(diagnosticPrefix);
/* 1389 */       diagnosticsStream.println(msg);
/* 1390 */       diagnosticsStream.flush();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void logHierarchy(String prefix, ClassLoader classLoader)
/*      */   {
/* 1403 */     if (!isDiagnosticsEnabled()) {
/* 1404 */       return;
/*      */     }
/*      */     
/* 1407 */     if (classLoader != null) {
/* 1408 */       String classLoaderString = classLoader.toString();
/* 1409 */       logDiagnostic(prefix + objectId(classLoader) + " == '" + classLoaderString + "'");
/*      */     }
/*      */     try
/*      */     {
/* 1413 */       systemClassLoader = ClassLoader.getSystemClassLoader();
/*      */     } catch (SecurityException localSecurityException1) { ClassLoader systemClassLoader;
/* 1415 */       logDiagnostic(prefix + "Security forbids determining the system classloader."); return;
/*      */     }
/*      */     ClassLoader systemClassLoader;
/* 1418 */     if (classLoader != null) {
/* 1419 */       StringBuffer buf = new StringBuffer(prefix + "ClassLoader tree:");
/*      */       do {
/* 1421 */         buf.append(objectId(classLoader));
/* 1422 */         if (classLoader == systemClassLoader) {
/* 1423 */           buf.append(" (SYSTEM) ");
/*      */         }
/*      */         try
/*      */         {
/* 1427 */           classLoader = classLoader.getParent();
/*      */         } catch (SecurityException localSecurityException2) {
/* 1429 */           buf.append(" --> SECRET");
/* 1430 */           break;
/*      */         }
/*      */         
/* 1433 */         buf.append(" --> ");
/* 1434 */       } while (classLoader != null);
/* 1435 */       buf.append("BOOT");
/*      */       
/*      */ 
/*      */ 
/* 1439 */       logDiagnostic(buf.toString());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final void logRawDiagnostic(String msg)
/*      */   {
/* 1451 */     if (diagnosticsStream != null) {
/* 1452 */       diagnosticsStream.println(msg);
/* 1453 */       diagnosticsStream.flush();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static LogFactory newFactory(String factoryClass, ClassLoader classLoader)
/*      */   {
/* 1472 */     return newFactory(factoryClass, classLoader, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static LogFactory newFactory(String factoryClass, final ClassLoader classLoader, ClassLoader contextClassLoader)
/*      */     throws LogConfigurationException
/*      */   {
/* 1523 */     Object result = AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/* 1526 */         return LogFactory.createFactory(LogFactory.this, classLoader);
/*      */       }
/*      */     });
/*      */     
/* 1530 */     if ((result instanceof LogConfigurationException)) {
/* 1531 */       LogConfigurationException ex = (LogConfigurationException)result;
/* 1532 */       if (isDiagnosticsEnabled()) {
/* 1533 */         logDiagnostic("An error occurred while loading the factory class:" + ex.getMessage());
/*      */       }
/* 1535 */       throw ex;
/*      */     }
/* 1537 */     if (isDiagnosticsEnabled()) {
/* 1538 */       logDiagnostic(
/* 1539 */         "Created object " + objectId(result) + " to manage classloader " + objectId(contextClassLoader));
/*      */     }
/* 1541 */     return (LogFactory)result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String objectId(Object o)
/*      */   {
/* 1558 */     if (o == null) {
/* 1559 */       return "null";
/*      */     }
/* 1561 */     return o.getClass().getName() + "@" + System.identityHashCode(o);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void release(ClassLoader classLoader)
/*      */   {
/* 1574 */     if (isDiagnosticsEnabled()) {
/* 1575 */       logDiagnostic("Releasing factory for classloader " + objectId(classLoader));
/*      */     }
/*      */     
/* 1578 */     Hashtable<ClassLoader, LogFactory> factories = factories;
/* 1579 */     synchronized (factories) {
/* 1580 */       if (classLoader == null) {
/* 1581 */         if (nullClassLoaderFactory != null) {
/* 1582 */           nullClassLoaderFactory.release();
/* 1583 */           nullClassLoaderFactory = null;
/*      */         }
/*      */       } else {
/* 1586 */         LogFactory factory = (LogFactory)factories.get(classLoader);
/* 1587 */         if (factory != null) {
/* 1588 */           factory.release();
/* 1589 */           factories.remove(classLoader);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void releaseAll()
/*      */   {
/* 1603 */     if (isDiagnosticsEnabled()) {
/* 1604 */       logDiagnostic("Releasing factory for all classloaders.");
/*      */     }
/*      */     
/* 1607 */     Hashtable<ClassLoader, LogFactory> factories = factories;
/* 1608 */     synchronized (factories) {
/* 1609 */       Enumeration<LogFactory> elements = factories.elements();
/* 1610 */       while (elements.hasMoreElements()) {
/* 1611 */         LogFactory element = (LogFactory)elements.nextElement();
/* 1612 */         element.release();
/*      */       }
/* 1614 */       factories.clear();
/*      */       
/* 1616 */       if (nullClassLoaderFactory != null) {
/* 1617 */         nullClassLoaderFactory.release();
/* 1618 */         nullClassLoaderFactory = null;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static String trim(String src)
/*      */   {
/* 1625 */     if (src == null) {
/* 1626 */       return null;
/*      */     }
/* 1628 */     return src.trim();
/*      */   }
/*      */   
/*      */   public abstract Object getAttribute(String paramString);
/*      */   
/*      */   public abstract String[] getAttributeNames();
/*      */   
/*      */   public abstract Log getInstance(Class<?> paramClass)
/*      */     throws LogConfigurationException;
/*      */   
/*      */   public abstract Log getInstance(String paramString)
/*      */     throws LogConfigurationException;
/*      */   
/*      */   public abstract void release();
/*      */   
/*      */   public abstract void removeAttribute(String paramString);
/*      */   
/*      */   public abstract void setAttribute(String paramString, Object paramObject);
/*      */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\apache\commons\logging\LogFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */