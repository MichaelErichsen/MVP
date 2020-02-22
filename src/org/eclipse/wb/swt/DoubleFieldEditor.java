/*     */ package org.eclipse.wb.swt;
/*     */ 
/*     */ import org.eclipse.jface.preference.IPreferenceStore;
/*     */ import org.eclipse.jface.preference.StringFieldEditor;
/*     */ import org.eclipse.jface.resource.JFaceResources;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleFieldEditor
/*     */   extends StringFieldEditor
/*     */ {
/*     */   private static final int DEFAULT_TEXT_LIMIT = 10;
/*     */   private double m_minValidValue;
/*     */   
/*     */   private static String getMessage_invalidRange(double min, double max)
/*     */   {
/*  30 */     String message = JFaceResources.format("IntegerFieldEditor.errorMessageRange", 
/*  31 */       new Object[] { new Double(min), new Double(max) });
/*  32 */     return replaceInteger_withDouble(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String getMessage_notDouble()
/*     */   {
/*  41 */     String message = JFaceResources.getString("IntegerFieldEditor.errorMessage");
/*  42 */     return replaceInteger_withDouble(message);
/*     */   }
/*     */   
/*     */   private static String replaceInteger_withDouble(String message) {
/*  46 */     int index = message.indexOf("Integer");
/*  47 */     if (index != -1) {
/*  48 */       String prefix = message.substring(0, index);
/*  49 */       String suffix = message.substring(index + "Integer".length());
/*  50 */       message = prefix + "Double" + suffix;
/*     */     }
/*  52 */     return message;
/*     */   }
/*     */   
/*     */ 
/*  56 */   private double m_maxValidValue = Double.MAX_VALUE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected DoubleFieldEditor() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DoubleFieldEditor(String name, String labelText, Composite parent)
/*     */   {
/*  80 */     this(name, labelText, parent, 10);
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
/*     */   public DoubleFieldEditor(String name, String labelText, Composite parent, int textLimit)
/*     */   {
/*  96 */     init(name, labelText);
/*  97 */     setTextLimit(textLimit);
/*  98 */     setEmptyStringAllowed(false);
/*  99 */     setErrorMessage(getMessage_notDouble());
/* 100 */     createControl(parent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean checkState()
/*     */   {
/* 110 */     Text text = getTextControl();
/* 111 */     if (text == null) {
/* 112 */       return false;
/*     */     }
/* 114 */     String numberString = text.getText();
/*     */     try {
/* 116 */       double number = Double.valueOf(numberString).doubleValue();
/* 117 */       if ((number >= this.m_minValidValue) && (number <= this.m_maxValidValue)) {
/* 118 */         clearErrorMessage();
/* 119 */         return true;
/*     */       }
/* 121 */       showErrorMessage();
/* 122 */       return false;
/*     */     } catch (NumberFormatException localNumberFormatException) {
/* 124 */       showErrorMessage();
/*     */     }
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   protected void doLoad()
/*     */   {
/* 131 */     Text text = getTextControl();
/* 132 */     if (text != null) {
/* 133 */       double value = getPreferenceStore().getDouble(getPreferenceName());
/* 134 */       text.setText(value);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doLoadDefault()
/*     */   {
/* 140 */     Text text = getTextControl();
/* 141 */     if (text != null) {
/* 142 */       double value = getPreferenceStore().getDefaultDouble(getPreferenceName());
/* 143 */       text.setText(value);
/*     */     }
/* 145 */     valueChanged();
/*     */   }
/*     */   
/*     */   protected void doStore()
/*     */   {
/* 150 */     Text text = getTextControl();
/* 151 */     if (text != null) {
/* 152 */       Double i = new Double(text.getText());
/* 153 */       getPreferenceStore().setValue(getPreferenceName(), i.doubleValue());
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
/*     */   public double getDoubleValue()
/*     */     throws NumberFormatException
/*     */   {
/* 170 */     return new Double(getStringValue()).doubleValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setToolTipText(String text)
/*     */   {
/* 180 */     getLabelControl().setToolTipText(text);
/* 181 */     getTextControl().setToolTipText(text);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValidRange(double min, double max)
/*     */   {
/* 193 */     this.m_minValidValue = min;
/* 194 */     this.m_maxValidValue = max;
/* 195 */     setErrorMessage(getMessage_invalidRange(min, max));
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\eclipse\wb\swt\DoubleFieldEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */