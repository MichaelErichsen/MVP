/*     */ package org.eclipse.wb.swt;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.eclipse.core.runtime.Platform;
/*     */ import org.eclipse.jface.resource.CompositeImageDescriptor;
/*     */ import org.eclipse.jface.resource.ImageDescriptor;
/*     */ import org.eclipse.swt.graphics.Image;
/*     */ import org.eclipse.swt.graphics.Point;
/*     */ import org.eclipse.swt.graphics.Rectangle;
/*     */ import org.osgi.framework.Bundle;
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
/*     */ public class ResourceManager
/*     */   extends SWTResourceManager
/*     */ {
/*  58 */   private static Map<ImageDescriptor, Image> m_descriptorImageMap = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  63 */   private static Map<Image, Map<Image, Image>>[] m_decoratedImageMap = new Map[5];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  72 */   private static Map<String, Image> m_URLImageMap = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  77 */   private static PluginResourceProvider m_designTimePluginResourceProvider = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Image decorateImage(Image baseImage, Image decorator)
/*     */   {
/*  89 */     return decorateImage(baseImage, decorator, 4);
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
/*     */   public static Image decorateImage(Image baseImage, final Image decorator, final int corner)
/*     */   {
/* 104 */     if ((corner <= 0) || (corner >= 5)) {
/* 105 */       throw new IllegalArgumentException("Wrong decorate corner");
/*     */     }
/* 107 */     Map<Image, Map<Image, Image>> cornerDecoratedImageMap = m_decoratedImageMap[corner];
/* 108 */     if (cornerDecoratedImageMap == null) {
/* 109 */       cornerDecoratedImageMap = new HashMap();
/* 110 */       m_decoratedImageMap[corner] = cornerDecoratedImageMap;
/*     */     }
/* 112 */     Map<Image, Image> decoratedMap = (Map)cornerDecoratedImageMap.get(baseImage);
/* 113 */     if (decoratedMap == null) {
/* 114 */       decoratedMap = new HashMap();
/* 115 */       cornerDecoratedImageMap.put(baseImage, decoratedMap);
/*     */     }
/*     */     
/* 118 */     Image result = (Image)decoratedMap.get(decorator);
/* 119 */     if (result == null) {
/* 120 */       final Rectangle bib = baseImage.getBounds();
/* 121 */       final Rectangle dib = decorator.getBounds();
/* 122 */       final Point baseImageSize = new Point(bib.width, bib.height);
/* 123 */       CompositeImageDescriptor compositImageDesc = new CompositeImageDescriptor()
/*     */       {
/*     */         protected void drawCompositeImage(int width, int height) {
/* 126 */           drawImage(ResourceManager.this.getImageData(), 0, 0);
/* 127 */           if (corner == 1) {
/* 128 */             drawImage(decorator.getImageData(), 0, 0);
/* 129 */           } else if (corner == 2) {
/* 130 */             drawImage(decorator.getImageData(), bib.width - dib.width, 0);
/* 131 */           } else if (corner == 3) {
/* 132 */             drawImage(decorator.getImageData(), 0, bib.height - dib.height);
/* 133 */           } else if (corner == 4) {
/* 134 */             drawImage(decorator.getImageData(), bib.width - dib.width, bib.height - dib.height);
/*     */           }
/*     */         }
/*     */         
/*     */         protected Point getSize()
/*     */         {
/* 140 */           return baseImageSize;
/*     */         }
/*     */         
/* 143 */       };
/* 144 */       result = compositImageDesc.createImage();
/* 145 */       decoratedMap.put(decorator, result);
/*     */     }
/* 147 */     return result;
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
/*     */   public static void dispose()
/*     */   {
/* 161 */     disposeColors();
/* 162 */     disposeFonts();
/* 163 */     disposeImages();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void disposeImages()
/*     */   {
/*     */     
/*     */     
/*     */ 
/* 173 */     for (Image image : m_descriptorImageMap.values()) {
/* 174 */       image.dispose();
/*     */     }
/* 176 */     m_descriptorImageMap.clear();
/*     */     
/*     */     Map[] arrayOfMap;
/* 179 */     int j = (arrayOfMap = m_decoratedImageMap).length; for (int i = 0; i < j; i++) { Map<Image, Map<Image, Image>> cornerDecoratedImageMap = arrayOfMap[i];
/* 180 */       if (cornerDecoratedImageMap != null) {
/* 181 */         for (Map<Image, Image> decoratedMap : cornerDecoratedImageMap.values()) {
/* 182 */           for (Image image : decoratedMap.values()) {
/* 183 */             image.dispose();
/*     */           }
/* 185 */           decoratedMap.clear();
/*     */         }
/* 187 */         cornerDecoratedImageMap.clear();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 192 */     for (Image image : m_URLImageMap.values()) {
/* 193 */       image.dispose();
/*     */     }
/* 195 */     m_URLImageMap.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Image getImage(ImageDescriptor descriptor)
/*     */   {
/* 207 */     if (descriptor == null) {
/* 208 */       return null;
/*     */     }
/* 210 */     Image image = (Image)m_descriptorImageMap.get(descriptor);
/* 211 */     if (image == null) {
/* 212 */       image = descriptor.createImage();
/* 213 */       m_descriptorImageMap.put(descriptor, image);
/*     */     }
/* 215 */     return image;
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
/*     */   public static ImageDescriptor getImageDescriptor(Class<?> clazz, String path)
/*     */   {
/* 229 */     return ImageDescriptor.createFromFile(clazz, path);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ImageDescriptor getImageDescriptor(String path)
/*     */   {
/*     */     try
/*     */     {
/* 241 */       return ImageDescriptor.createFromURL(new File(path).toURI().toURL());
/*     */     } catch (MalformedURLException localMalformedURLException) {}
/* 243 */     return null;
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
/*     */   @Deprecated
/*     */   public static Image getPluginImage(Object plugin, String name)
/*     */   {
/*     */     try
/*     */     {
/* 261 */       URL url = getPluginImageURL(plugin, name);
/* 262 */       if (url != null) {
/* 263 */         return getPluginImageFromUrl(url);
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable) {}
/*     */     
/* 268 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Image getPluginImage(String symbolicName, String path)
/*     */   {
/*     */     try
/*     */     {
/* 282 */       URL url = getPluginImageURL(symbolicName, path);
/* 283 */       if (url != null) {
/* 284 */         return getPluginImageFromUrl(url);
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable) {}
/*     */     
/* 289 */     return null;
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
/*     */   @Deprecated
/*     */   public static ImageDescriptor getPluginImageDescriptor(Object plugin, String name)
/*     */   {
/*     */     try
/*     */     {
/* 307 */       URL url = getPluginImageURL(plugin, name);
/* 308 */       return ImageDescriptor.createFromURL(url);
/*     */     }
/*     */     catch (Throwable localThrowable1) {}catch (Throwable localThrowable2) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 315 */     return null;
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
/*     */   public static ImageDescriptor getPluginImageDescriptor(String symbolicName, String path)
/*     */   {
/*     */     try
/*     */     {
/* 331 */       URL url = getPluginImageURL(symbolicName, path);
/* 332 */       if (url != null) {
/* 333 */         return ImageDescriptor.createFromURL(url);
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable) {}
/*     */     
/* 338 */     return null;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private static Image getPluginImageFromUrl(URL url)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual 212	java/net/URL:toExternalForm	()Ljava/lang/String;
/*     */     //   4: astore_1
/*     */     //   5: getstatic 30	org/eclipse/wb/swt/ResourceManager:m_URLImageMap	Ljava/util/Map;
/*     */     //   8: aload_1
/*     */     //   9: invokeinterface 54 2 0
/*     */     //   14: checkcast 62	org/eclipse/swt/graphics/Image
/*     */     //   17: astore_2
/*     */     //   18: aload_2
/*     */     //   19: ifnonnull +40 -> 59
/*     */     //   22: aload_0
/*     */     //   23: invokevirtual 218	java/net/URL:openStream	()Ljava/io/InputStream;
/*     */     //   26: astore_3
/*     */     //   27: aload_3
/*     */     //   28: invokestatic 222	org/eclipse/wb/swt/ResourceManager:getImage	(Ljava/io/InputStream;)Lorg/eclipse/swt/graphics/Image;
/*     */     //   31: astore_2
/*     */     //   32: getstatic 30	org/eclipse/wb/swt/ResourceManager:m_URLImageMap	Ljava/util/Map;
/*     */     //   35: aload_1
/*     */     //   36: aload_2
/*     */     //   37: invokeinterface 58 3 0
/*     */     //   42: pop
/*     */     //   43: goto +12 -> 55
/*     */     //   46: astore 4
/*     */     //   48: aload_3
/*     */     //   49: invokevirtual 225	java/io/InputStream:close	()V
/*     */     //   52: aload 4
/*     */     //   54: athrow
/*     */     //   55: aload_3
/*     */     //   56: invokevirtual 225	java/io/InputStream:close	()V
/*     */     //   59: aload_2
/*     */     //   60: areturn
/*     */     //   61: pop
/*     */     //   62: goto +4 -> 66
/*     */     //   65: pop
/*     */     //   66: aconst_null
/*     */     //   67: areturn
/*     */     // Line number table:
/*     */     //   Java source line #347	-> byte code offset #0
/*     */     //   Java source line #348	-> byte code offset #5
/*     */     //   Java source line #349	-> byte code offset #18
/*     */     //   Java source line #350	-> byte code offset #22
/*     */     //   Java source line #352	-> byte code offset #27
/*     */     //   Java source line #353	-> byte code offset #32
/*     */     //   Java source line #354	-> byte code offset #43
/*     */     //   Java source line #355	-> byte code offset #48
/*     */     //   Java source line #356	-> byte code offset #52
/*     */     //   Java source line #355	-> byte code offset #55
/*     */     //   Java source line #358	-> byte code offset #59
/*     */     //   Java source line #359	-> byte code offset #61
/*     */     //   Java source line #362	-> byte code offset #62
/*     */     //   Java source line #365	-> byte code offset #66
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	68	0	url	URL
/*     */     //   4	32	1	key	String
/*     */     //   17	43	2	image	Image
/*     */     //   26	30	3	stream	java.io.InputStream
/*     */     //   46	7	4	localObject	Object
/*     */     //   61	1	5	localThrowable1	Throwable
/*     */     //   65	1	6	localThrowable2	Throwable
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   27	46	46	finally
/*     */     //   0	60	61	java/lang/Throwable
/*     */     //   0	60	65	java/lang/Throwable
/*     */     //   61	62	65	java/lang/Throwable
/*     */   }
/*     */   
/*     */   private static URL getPluginImageURL(Object plugin, String name)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 381 */       Class<?> BundleClass = Class.forName("org.osgi.framework.Bundle");
/* 382 */       Class<?> BundleContextClass = Class.forName("org.osgi.framework.BundleContext");
/* 383 */       if (BundleContextClass.isAssignableFrom(plugin.getClass())) {
/* 384 */         Method getBundleMethod = BundleContextClass.getMethod("getBundle", new Class[0]);
/* 385 */         Object bundle = getBundleMethod.invoke(plugin, new Object[0]);
/*     */         
/* 387 */         Class<?> PathClass = Class.forName("org.eclipse.core.runtime.Path");
/* 388 */         Constructor<?> pathConstructor = PathClass.getConstructor(new Class[] { String.class });
/* 389 */         Object path = pathConstructor.newInstance(new Object[] { name });
/*     */         
/* 391 */         Class<?> IPathClass = Class.forName("org.eclipse.core.runtime.IPath");
/* 392 */         Class<?> PlatformClass = Class.forName("org.eclipse.core.runtime.Platform");
/* 393 */         Method findMethod = PlatformClass.getMethod("find", new Class[] { BundleClass, IPathClass });
/* 394 */         return (URL)findMethod.invoke(null, new Object[] { bundle, path });
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (Throwable localThrowable)
/*     */     {
/* 401 */       Class<?> PluginClass = Class.forName("org.eclipse.core.runtime.Plugin");
/* 402 */       if (PluginClass.isAssignableFrom(plugin.getClass()))
/*     */       {
/* 404 */         Class<?> PathClass = Class.forName("org.eclipse.core.runtime.Path");
/* 405 */         Constructor<?> pathConstructor = PathClass.getConstructor(new Class[] { String.class });
/* 406 */         Object path = pathConstructor.newInstance(new Object[] { name });
/*     */         
/* 408 */         Class<?> IPathClass = Class.forName("org.eclipse.core.runtime.IPath");
/* 409 */         Method findMethod = PluginClass.getMethod("find", new Class[] { IPathClass });
/* 410 */         return (URL)findMethod.invoke(plugin, new Object[] { path });
/*     */       }
/*     */     }
/* 413 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static URL getPluginImageURL(String symbolicName, String path)
/*     */   {
/* 422 */     Bundle bundle = Platform.getBundle(symbolicName);
/* 423 */     if (bundle != null) {
/* 424 */       return bundle.getEntry(path);
/*     */     }
/*     */     
/*     */ 
/* 428 */     if (m_designTimePluginResourceProvider != null) {
/* 429 */       return m_designTimePluginResourceProvider.getEntry(symbolicName, path);
/*     */     }
/*     */     
/* 432 */     return null;
/*     */   }
/*     */   
/*     */   public static abstract interface PluginResourceProvider
/*     */   {
/*     */     public abstract URL getEntry(String paramString1, String paramString2);
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\eclipse\wb\swt\ResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */