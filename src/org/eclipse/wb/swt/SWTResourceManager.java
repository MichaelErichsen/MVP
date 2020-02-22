/*     */ package org.eclipse.wb.swt;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.eclipse.swt.graphics.Color;
/*     */ import org.eclipse.swt.graphics.Cursor;
/*     */ import org.eclipse.swt.graphics.Font;
/*     */ import org.eclipse.swt.graphics.FontData;
/*     */ import org.eclipse.swt.graphics.GC;
/*     */ import org.eclipse.swt.graphics.Image;
/*     */ import org.eclipse.swt.graphics.ImageData;
/*     */ import org.eclipse.swt.graphics.RGB;
/*     */ import org.eclipse.swt.graphics.Rectangle;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SWTResourceManager
/*     */ {
/*  49 */   private static Map<RGB, Color> m_colorMap = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Color getColor(int systemColorID)
/*     */   {
/*  58 */     Display display = Display.getCurrent();
/*  59 */     return display.getSystemColor(systemColorID);
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
/*     */   public static Color getColor(int r, int g, int b)
/*     */   {
/*  73 */     return getColor(new RGB(r, g, b));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Color getColor(RGB rgb)
/*     */   {
/*  83 */     Color color = (Color)m_colorMap.get(rgb);
/*  84 */     if (color == null) {
/*  85 */       Display display = Display.getCurrent();
/*  86 */       color = new Color(display, rgb);
/*  87 */       m_colorMap.put(rgb, color);
/*     */     }
/*  89 */     return color;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void disposeColors()
/*     */   {
/*  95 */     for (Color color : m_colorMap.values()) {
/*  96 */       color.dispose();
/*     */     }
/*  98 */     m_colorMap.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 108 */   private static Map<String, Image> m_imageMap = new HashMap();
/*     */   private static final int MISSING_IMAGE_SIZE = 10;
/*     */   public static final int TOP_LEFT = 1;
/*     */   public static final int TOP_RIGHT = 2;
/*     */   public static final int BOTTOM_LEFT = 3;
/*     */   public static final int BOTTOM_RIGHT = 4;
/*     */   protected static final int LAST_CORNER_KEY = 5;
/*     */   
/*     */   protected static Image getImage(InputStream stream) throws IOException {
/*     */     try {
/* 118 */       Display display = Display.getCurrent();
/* 119 */       ImageData data = new ImageData(stream);
/* 120 */       Image localImage; if (data.transparentPixel > 0) {
/* 121 */         return new Image(display, data, data.getTransparencyMask());
/*     */       }
/* 123 */       return new Image(display, data);
/*     */     } finally {
/* 125 */       stream.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Image getImage(String path)
/*     */   {
/* 136 */     Image image = (Image)m_imageMap.get(path);
/* 137 */     if (image == null) {
/*     */       try {
/* 139 */         image = getImage(new FileInputStream(path));
/* 140 */         m_imageMap.put(path, image);
/*     */       } catch (Exception localException) {
/* 142 */         image = getMissingImage();
/* 143 */         m_imageMap.put(path, image);
/*     */       }
/*     */     }
/* 146 */     return image;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Image getImage(Class<?> clazz, String path)
/*     */   {
/* 158 */     String key = clazz.getName() + '|' + path;
/* 159 */     Image image = (Image)m_imageMap.get(key);
/* 160 */     if (image == null) {
/*     */       try {
/* 162 */         image = getImage(clazz.getResourceAsStream(path));
/* 163 */         m_imageMap.put(key, image);
/*     */       } catch (Exception localException) {
/* 165 */         image = getMissingImage();
/* 166 */         m_imageMap.put(key, image);
/*     */       }
/*     */     }
/* 169 */     return image;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static Image getMissingImage()
/*     */   {
/* 176 */     Image image = new Image(Display.getCurrent(), 10, 10);
/*     */     
/* 178 */     GC gc = new GC(image);
/* 179 */     gc.setBackground(getColor(3));
/* 180 */     gc.fillRectangle(0, 0, 10, 10);
/* 181 */     gc.dispose();
/*     */     
/* 183 */     return image;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 209 */   private static Map<Image, Map<Image, Image>>[] m_decoratedImageMap = new Map[5];
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
/* 220 */     return decorateImage(baseImage, decorator, 4);
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
/*     */   public static Image decorateImage(Image baseImage, Image decorator, int corner)
/*     */   {
/* 234 */     if ((corner <= 0) || (corner >= 5)) {
/* 235 */       throw new IllegalArgumentException("Wrong decorate corner");
/*     */     }
/* 237 */     Map<Image, Map<Image, Image>> cornerDecoratedImageMap = m_decoratedImageMap[corner];
/* 238 */     if (cornerDecoratedImageMap == null) {
/* 239 */       cornerDecoratedImageMap = new HashMap();
/* 240 */       m_decoratedImageMap[corner] = cornerDecoratedImageMap;
/*     */     }
/* 242 */     Map<Image, Image> decoratedMap = (Map)cornerDecoratedImageMap.get(baseImage);
/* 243 */     if (decoratedMap == null) {
/* 244 */       decoratedMap = new HashMap();
/* 245 */       cornerDecoratedImageMap.put(baseImage, decoratedMap);
/*     */     }
/*     */     
/* 248 */     Image result = (Image)decoratedMap.get(decorator);
/* 249 */     if (result == null) {
/* 250 */       Rectangle bib = baseImage.getBounds();
/* 251 */       Rectangle dib = decorator.getBounds();
/*     */       
/* 253 */       result = new Image(Display.getCurrent(), bib.width, bib.height);
/*     */       
/* 255 */       GC gc = new GC(result);
/* 256 */       gc.drawImage(baseImage, 0, 0);
/* 257 */       if (corner == 1) {
/* 258 */         gc.drawImage(decorator, 0, 0);
/* 259 */       } else if (corner == 2) {
/* 260 */         gc.drawImage(decorator, bib.width - dib.width, 0);
/* 261 */       } else if (corner == 3) {
/* 262 */         gc.drawImage(decorator, 0, bib.height - dib.height);
/* 263 */       } else if (corner == 4) {
/* 264 */         gc.drawImage(decorator, bib.width - dib.width, bib.height - dib.height);
/*     */       }
/* 266 */       gc.dispose();
/*     */       
/* 268 */       decoratedMap.put(decorator, result);
/*     */     }
/* 270 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void disposeImages()
/*     */   {
/* 278 */     for (Image image : m_imageMap.values()) {
/* 279 */       image.dispose();
/*     */     }
/* 281 */     m_imageMap.clear();
/*     */     
/*     */ 
/* 284 */     for (int i = 0; i < m_decoratedImageMap.length; i++) {
/* 285 */       Object cornerDecoratedImageMap = m_decoratedImageMap[i];
/* 286 */       if (cornerDecoratedImageMap != null) {
/* 287 */         for (Map<Image, Image> decoratedMap : ((Map)cornerDecoratedImageMap).values()) {
/* 288 */           for (Image image : decoratedMap.values()) {
/* 289 */             image.dispose();
/*     */           }
/* 291 */           decoratedMap.clear();
/*     */         }
/* 293 */         ((Map)cornerDecoratedImageMap).clear();
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
/* 305 */   private static Map<String, Font> m_fontMap = new HashMap();
/*     */   
/*     */ 
/*     */ 
/* 309 */   private static Map<Font, Font> m_fontToBoldFontMap = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Font getFont(String name, int height, int style)
/*     */   {
/* 322 */     return getFont(name, height, style, false, false);
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
/*     */ 
/*     */   public static Font getFont(String name, int size, int style, boolean strikeout, boolean underline)
/*     */   {
/* 341 */     String fontName = name + '|' + size + '|' + style + '|' + strikeout + '|' + underline;
/* 342 */     Font font = (Font)m_fontMap.get(fontName);
/* 343 */     if (font == null) {
/* 344 */       FontData fontData = new FontData(name, size, style);
/* 345 */       if ((strikeout) || (underline)) {
/*     */         try {
/* 347 */           Class<?> logFontClass = Class.forName("org.eclipse.swt.internal.win32.LOGFONT");
/* 348 */           Object logFont = FontData.class.getField("data").get(fontData);
/* 349 */           if ((logFont != null) && (logFontClass != null)) {
/* 350 */             if (strikeout) {
/* 351 */               logFontClass.getField("lfStrikeOut").set(logFont, Byte.valueOf((byte)1));
/*     */             }
/* 353 */             if (underline) {
/* 354 */               logFontClass.getField("lfUnderline").set(logFont, Byte.valueOf((byte)1));
/*     */             }
/*     */           }
/*     */         } catch (Throwable e) {
/* 358 */           System.err.println("Unable to set underline or strikeout (probably on a non-Windows platform). " + e);
/*     */         }
/*     */       }
/* 361 */       font = new Font(Display.getCurrent(), fontData);
/* 362 */       m_fontMap.put(fontName, font);
/*     */     }
/* 364 */     return font;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Font getBoldFont(Font baseFont)
/*     */   {
/* 374 */     Font font = (Font)m_fontToBoldFontMap.get(baseFont);
/* 375 */     if (font == null) {
/* 376 */       FontData[] fontDatas = baseFont.getFontData();
/* 377 */       FontData data = fontDatas[0];
/* 378 */       font = new Font(Display.getCurrent(), data.getName(), data.getHeight(), 1);
/* 379 */       m_fontToBoldFontMap.put(baseFont, font);
/*     */     }
/* 381 */     return font;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void disposeFonts()
/*     */   {
/* 388 */     for (Font font : m_fontMap.values()) {
/* 389 */       font.dispose();
/*     */     }
/* 391 */     m_fontMap.clear();
/*     */     
/* 393 */     for (Font font : m_fontToBoldFontMap.values()) {
/* 394 */       font.dispose();
/*     */     }
/* 396 */     m_fontToBoldFontMap.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 406 */   private static Map<Integer, Cursor> m_idToCursorMap = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Cursor getCursor(int id)
/*     */   {
/* 415 */     Integer key = Integer.valueOf(id);
/* 416 */     Cursor cursor = (Cursor)m_idToCursorMap.get(key);
/* 417 */     if (cursor == null) {
/* 418 */       cursor = new Cursor(Display.getDefault(), id);
/* 419 */       m_idToCursorMap.put(key, cursor);
/*     */     }
/* 421 */     return cursor;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void disposeCursors()
/*     */   {
/* 427 */     for (Cursor cursor : m_idToCursorMap.values()) {
/* 428 */       cursor.dispose();
/*     */     }
/* 430 */     m_idToCursorMap.clear();
/*     */   }
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
/* 442 */     disposeColors();
/* 443 */     disposeImages();
/* 444 */     disposeFonts();
/* 445 */     disposeCursors();
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\eclipse\wb\swt\SWTResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */