/*     */ package org.historyresearchenvironment.usergui.providers;
/*     */ 
/*     */ import org.eclipse.draw2d.IFigure;
/*     */ import org.eclipse.jface.viewers.LabelProvider;
/*     */ import org.eclipse.swt.graphics.Color;
/*     */ import org.eclipse.swt.graphics.Device;
/*     */ import org.eclipse.swt.graphics.Image;
/*     */ import org.eclipse.swt.graphics.ImageData;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ import org.eclipse.wb.swt.ResourceManager;
/*     */ import org.eclipse.zest.core.viewers.EntityConnectionData;
/*     */ import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
/*     */ import org.eclipse.zest.core.viewers.IEntityStyleProvider;
/*     */ import org.historyresearchenvironment.usergui.models.ZestPersonConnection;
/*     */ import org.historyresearchenvironment.usergui.models.ZestPersonNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZestLabelProvider
/*     */   extends LabelProvider
/*     */   implements IEntityStyleProvider, IConnectionStyleProvider
/*     */ {
/*     */   public boolean fisheyeNode(Object entity)
/*     */   {
/*  35 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getBackgroundColour(Object entity)
/*     */   {
/*  47 */     if ((entity instanceof ZestPersonNode)) {
/*  48 */       Device device = Display.getCurrent();
/*  49 */       ZestPersonNode node = (ZestPersonNode)entity;
/*     */       
/*  51 */       if (node.getSex() != null) {
/*  52 */         if (node.getSex().equals("M"))
/*  53 */           return new Color(device, 90, 180, 255);
/*  54 */         if (node.getSex().equals("F")) {
/*  55 */           return new Color(device, 255, 180, 90);
/*     */         }
/*     */       }
/*     */     }
/*  59 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getBorderColor(Object entity)
/*     */   {
/*  71 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getBorderHighlightColor(Object entity)
/*     */   {
/*  83 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBorderWidth(Object entity)
/*     */   {
/*  95 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getColor(Object rel)
/*     */   {
/* 107 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getConnectionStyle(Object rel)
/*     */   {
/* 119 */     return 2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getForegroundColour(Object entity)
/*     */   {
/* 131 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getHighlightColor(Object rel)
/*     */   {
/* 143 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Image getImage(Object element)
/*     */   {
/* 155 */     if ((element instanceof ZestPersonNode)) {
/* 156 */       ZestPersonNode node = (ZestPersonNode)element;
/*     */       
/* 158 */       if (node.getSex() != null) { Image img;
/* 159 */         if (node.getSex().equals("M")) {
/* 160 */           img = ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/male.png"); } else { Image img;
/* 161 */           if (node.getSex().equals("F")) {
/* 162 */             img = ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/female.png");
/*     */           } else
/* 164 */             return null;
/*     */         }
/*     */         Image img;
/* 167 */         Image image = new Image(img.getDevice(), img.getImageData().scaledTo(50, 36));
/* 168 */         return image;
/*     */       }
/*     */     }
/* 171 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLineWidth(Object rel)
/*     */   {
/* 183 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getNodeHighlightColor(Object entity)
/*     */   {
/* 195 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getText(Object element)
/*     */   {
/* 205 */     if ((element instanceof ZestPersonNode)) {
/* 206 */       ZestPersonNode node = (ZestPersonNode)element;
/* 207 */       return node.getName();
/*     */     }
/*     */     
/* 210 */     if ((element instanceof ZestPersonConnection)) {
/* 211 */       ZestPersonConnection connection = (ZestPersonConnection)element;
/* 212 */       return connection.getLabel();
/*     */     }
/*     */     
/* 215 */     if ((element instanceof EntityConnectionData)) {
/* 216 */       return "";
/*     */     }
/* 218 */     throw new RuntimeException("Wrong type: " + element.getClass().toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IFigure getTooltip(Object entity)
/*     */   {
/* 229 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\ZestLabelProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */