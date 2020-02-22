/*     */ package org.historyresearchenvironment.usergui.providers;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Logger;
/*     */ import javax.inject.Inject;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.historyresearchenvironment.usergui.HreH2ConnectionPool;
/*     */ import org.historyresearchenvironment.usergui.models.AbstractHreModel;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.json.JSONWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractHreProvider
/*     */ {
/*     */   @Inject
/*     */   protected static IEventBroker eventBroker;
/*  35 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*  36 */   protected ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/*  37 */     "org.historyresearchenvironment.usergui");
/*  38 */   protected Connection conn = null;
/*     */   
/*  40 */   protected PreparedStatement pst = null;
/*     */   
/*     */ 
/*     */ 
/*     */   protected String key;
/*     */   
/*     */ 
/*     */ 
/*     */   private static void getObjectsFromJson(Class<?> c, String type, Object model, JSONObject jsonObject, Field field)
/*     */     throws JSONException, IllegalAccessException, InvocationTargetException
/*     */   {
/*     */     Method[] arrayOfMethod;
/*     */     
/*     */ 
/*  54 */     int j = (arrayOfMethod = c.getMethods()).length; for (int i = 0; i < j; i++) { Method method = arrayOfMethod[i];
/*     */       
/*  56 */       if ((method.getName().startsWith("set")) && (method.getName().length() == field.getName().length() + 3) && 
/*  57 */         (method.getName().toLowerCase().endsWith(field.getName().toLowerCase()))) {
/*  58 */         if (type.equals("int")) {
/*  59 */           int value = jsonObject.getInt(field.getName());
/*  60 */           method.invoke(model, new Object[] { Integer.valueOf(value) });
/*  61 */           break; }
/*  62 */         String value = jsonObject.getString(field.getName());
/*  63 */         method.invoke(model, new Object[] { value });
/*     */         
/*     */ 
/*  66 */         break;
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
/*     */   private static void invokeVararg(Object model, Vector<Object> arglist, Method method)
/*     */     throws IllegalAccessException, InvocationTargetException, Exception
/*     */   {
/*  81 */     switch (arglist.size()) {
/*     */     case 0: 
/*  83 */       method.invoke(model, new Object[0]);
/*  84 */       break;
/*     */     case 1: 
/*  86 */       method.invoke(model, new Object[] { arglist.get(0) });
/*  87 */       break;
/*     */     case 2: 
/*  89 */       method.invoke(model, new Object[] { arglist.get(0), arglist.get(1) });
/*  90 */       break;
/*     */     case 3: 
/*  92 */       method.invoke(model, new Object[] { arglist.get(0), arglist.get(1), arglist.get(2) });
/*  93 */       break;
/*     */     case 4: 
/*  95 */       method.invoke(model, new Object[] { arglist.get(0), arglist.get(1), arglist.get(2), arglist.get(3) });
/*  96 */       break;
/*     */     case 5: 
/*  98 */       method.invoke(model, new Object[] { arglist.get(0), arglist.get(1), arglist.get(2), arglist.get(3), arglist.get(4) });
/*  99 */       break;
/*     */     case 6: 
/* 101 */       method.invoke(model, new Object[] { arglist.get(0), arglist.get(1), arglist.get(2), arglist.get(3), arglist.get(4), 
/* 102 */         arglist.get(5) });
/* 103 */       break;
/*     */     case 7: 
/* 105 */       method.invoke(model, new Object[] { arglist.get(0), arglist.get(1), arglist.get(2), arglist.get(3), arglist.get(4), 
/* 106 */         arglist.get(5), arglist.get(6) });
/* 107 */       break;
/*     */     case 8: 
/* 109 */       method.invoke(model, new Object[] { arglist.get(0), arglist.get(1), arglist.get(2), arglist.get(3), arglist.get(4), 
/* 110 */         arglist.get(5), arglist.get(6), arglist.get(7) });
/* 111 */       break;
/*     */     case 9: 
/* 113 */       method.invoke(model, new Object[] { arglist.get(0), arglist.get(1), arglist.get(2), arglist.get(3), arglist.get(4), 
/* 114 */         arglist.get(5), arglist.get(6), arglist.get(7), arglist.get(8) });
/* 115 */       break;
/*     */     case 10: 
/* 117 */       method.invoke(model, new Object[] { arglist.get(0), arglist.get(1), arglist.get(2), arglist.get(3), arglist.get(4), 
/* 118 */         arglist.get(5), arglist.get(6), arglist.get(7), arglist.get(8), arglist.get(9) });
/* 119 */       break;
/*     */     default: 
/* 121 */       LOGGER.severe("Too many arguments for implementation");
/* 122 */       throw new Exception("Too many arguments for implementation");
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public AbstractHreProvider()
/*     */   {
/*     */     try
/*     */     {
/* 134 */       this.conn = HreH2ConnectionPool.getConnection();
/*     */     } catch (Exception e) {
/* 136 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getKey()
/*     */   {
/* 144 */     return this.key;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void readFromH2(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */   public void readJson(String jsonstring)
/*     */   {
/* 156 */     Class<?> modelClass = null;
/* 157 */     String modelType = "";
/* 158 */     String itemType = "";
/*     */     
/*     */ 
/* 161 */     Field modelField = null;
/* 162 */     Field itemField = null;
/* 163 */     String key = "";
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 168 */       modelClass = getClass();
/* 169 */       Object model = this;
/* 170 */       Field[] modelFields = modelClass.getDeclaredFields();
/*     */       
/* 172 */       JSONObject jsonObject = new JSONObject(jsonstring);
/*     */       Field[] arrayOfField1;
/* 174 */       int j = (arrayOfField1 = modelFields).length; for (int i = 0; i < j; i++) { Field modelField2 = arrayOfField1[i];
/* 175 */         modelField = modelField2;
/* 176 */         modelField.setAccessible(true);
/* 177 */         modelType = modelField.getType().toString();
/*     */         
/* 179 */         if (modelType.contains("java.util.Vector")) {
/* 180 */           JSONArray itemJsonArray = jsonObject.getJSONArray(modelField.getName());
/* 181 */           JSONObject itemJsonObject = null;
/*     */           
/* 183 */           String genericType = modelField.getGenericType().getTypeName();
/* 184 */           String[] a = genericType.split("<");
/* 185 */           String[] b = a[1].split(">");
/* 186 */           String itemClassName = b[0];
/*     */           
/* 188 */           Class<?> itemClass = Class.forName(itemClassName);
/* 189 */           Object itemObject = itemClass.newInstance();
/* 190 */           Field[] itemFields = itemClass.getDeclaredFields();
/*     */           
/*     */ 
/* 193 */           for (int j = 0; j < itemJsonArray.length(); j++) {
/* 194 */             itemJsonObject = itemJsonArray.getJSONObject(j);
/*     */             Object localObject1;
/* 196 */             int m = (localObject1 = itemFields).length; for (int k = 0; k < m; k++) { Field itemField2 = localObject1[k];
/* 197 */               itemField = itemField2;
/* 198 */               itemField.setAccessible(true);
/* 199 */               itemType = itemField.getType().toString();
/*     */               
/* 201 */               getObjectsFromJson(itemClass, itemType, itemObject, itemJsonObject, itemField);
/*     */             }
/*     */             
/* 204 */             Vector<Object> argVector = new Vector();
/*     */             
/* 206 */             m = (localObject1 = modelClass.getMethods()).length; for (k = 0; k < m; k++) { Method method = localObject1[k];
/* 207 */               if ((method.getName().startsWith("add")) && 
/* 208 */                 (modelField.getName().toLowerCase().startsWith(method.getName().toLowerCase().substring(3)))) {
/* 209 */                 itemFields = itemClass.getDeclaredFields();
/*     */                 Field[] arrayOfField2;
/* 211 */                 int i1 = (arrayOfField2 = itemFields).length; for (int n = 0; n < i1; n++) { Field itemField2 = arrayOfField2[n];
/* 212 */                   itemField = itemField2;
/* 213 */                   itemField.setAccessible(true);
/*     */                   
/* 215 */                   for (Iterator<?> iterator = itemJsonObject.keys(); iterator.hasNext();) {
/* 216 */                     key = (String)iterator.next();
/*     */                     
/* 218 */                     if (itemField.getName().equals(key)) {
/* 219 */                       itemType = itemField.getType().toString();
/*     */                       
/* 221 */                       if (itemType.equals("int")) {
/* 222 */                         argVector.add(Integer.valueOf(itemJsonObject.getInt(key)));
/* 223 */                         break; }
/* 224 */                       argVector.add(itemJsonObject.getString(key));
/*     */                       
/* 226 */                       break;
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 try
/*     */                 {
/* 232 */                   invokeVararg(model, argVector, method);
/*     */                 } catch (Exception e) {
/* 234 */                   e.printStackTrace();
/* 235 */                   LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + 
/* 236 */                     e.getStackTrace()[0].getLineNumber());
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 242 */         else if (modelType.contains("Vector")) {
/* 243 */           LOGGER.fine("Not handling type " + modelType);
/*     */         } else {
/* 245 */           getObjectsFromJson(modelClass, modelType, model, jsonObject, modelField);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 249 */       e.printStackTrace();
/* 250 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setKey(String key)
/*     */   {
/* 259 */     this.key = key;
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
/*     */   public String writeJson(String classname)
/*     */   {
/* 276 */     StringWriter sw = new StringWriter();
/*     */     
/* 278 */     LOGGER.fine("Class name: " + classname);
/*     */     try
/*     */     {
/* 281 */       Class<?> c = Class.forName(classname);
/* 282 */       Field[] fa = c.getDeclaredFields();
/*     */       
/* 284 */       JSONWriter jw = new JSONWriter(sw);
/* 285 */       jw.object();
/*     */       
/*     */       Field[] arrayOfField1;
/* 288 */       int j = (arrayOfField1 = fa).length; for (int i = 0; i < j; i++) { Field element = arrayOfField1[i];
/* 289 */         Field field = element;
/*     */         
/* 291 */         String type = field.getType().toString();
/*     */         
/* 293 */         if (type.contains("[[[")) {
/* 294 */           throw new Exception("Too many array levels for this implementation");
/*     */         }
/*     */         
/* 297 */         if (type.contains("[[")) {
/* 298 */           throw new Exception("Too many array levels for this implementation, so far");
/*     */         }
/*     */         
/*     */ 
/* 302 */         if (type.contains("[")) {
/* 303 */           String key = field.getName();
/* 304 */           jw.key(key);
/*     */           
/* 306 */           jw.array();
/*     */           
/*     */ 
/* 309 */           if (type.contains("[I")) {
/* 310 */             int[] valueArray = (int[])field.get(this);
/*     */             
/* 312 */             for (int j = 0; j < valueArray.length; j++) {
/* 313 */               jw.object();
/* 314 */               jw.key(key + "[" + j + "]");
/* 315 */               jw.value(Integer.toString(valueArray[j]));
/* 316 */               jw.endObject();
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 321 */             String[] valueArray = (String[])field.get(this);
/*     */             
/* 323 */             for (int j = 0; j < valueArray.length; j++) {
/* 324 */               LOGGER.info(valueArray[j]);
/* 325 */               jw.object();
/* 326 */               jw.key(key + "[" + j + "]");
/* 327 */               jw.value(valueArray[j]);
/* 328 */               jw.endObject();
/*     */             }
/*     */           }
/*     */           
/* 332 */           jw.endArray();
/*     */ 
/*     */         }
/* 335 */         else if (type.contains("java.util.Vector")) {
/* 336 */           Vector<?> v = (Vector)field.get(this);
/*     */           
/* 338 */           String key = field.getName();
/* 339 */           jw.key(key);
/*     */           
/* 341 */           jw.array();
/*     */           
/* 343 */           for (Object name : v) {
/* 344 */             jw.object();
/* 345 */             AbstractHreModel item = (AbstractHreModel)name;
/* 346 */             Class<?> c1 = item.getClass();
/* 347 */             Field[] fa1 = c1.getDeclaredFields();
/*     */             Field[] arrayOfField2;
/* 349 */             int m = (arrayOfField2 = fa1).length; for (int k = 0; k < m; k++) { Field element2 = arrayOfField2[k];
/* 350 */               Field field1 = element2;
/* 351 */               String key1 = field1.getName();
/* 352 */               jw.key(key1);
/*     */               String value1;
/* 354 */               String value1; if (field1.getType().getName().equals("int")) {
/* 355 */                 value1 = Integer.toString(field1.getInt(item));
/*     */               } else {
/* 357 */                 value1 = (String)field1.get(item);
/*     */               }
/* 359 */               jw.value(value1);
/*     */             }
/*     */             
/* 362 */             jw.endObject();
/*     */           }
/*     */           
/* 365 */           jw.endArray();
/*     */ 
/*     */         }
/* 368 */         else if (!type.contains("Vector"))
/*     */         {
/*     */ 
/* 371 */           String key = field.getName();
/* 372 */           jw.key(key);
/*     */           String value;
/*     */           String value;
/* 375 */           if (type.equals("int")) {
/* 376 */             value = Integer.toString(((Integer)field.get(this)).intValue());
/*     */           }
/*     */           else
/*     */           {
/* 380 */             value = (String)field.get(this);
/*     */           }
/*     */           
/* 383 */           jw.value(value);
/*     */         }
/*     */       }
/* 386 */       jw.endObject();
/*     */     } catch (Exception e) {
/* 388 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */     }
/* 390 */     LOGGER.info(sw.toString());
/* 391 */     return sw.toString();
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\AbstractHreProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */