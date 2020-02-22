/*     */ package org.historyresearchenvironment.usergui.providers;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.historyresearchenvironment.usergui.models.PolRegLocationModel;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolRegLocationProvider
/*     */   extends AbstractHreProvider
/*     */ {
/*  25 */   ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/*  26 */     "org.historyresearchenvironment.usergui");
/*     */   
/*     */   private List<PolRegLocationModel> modelList;
/*  29 */   private final String POLREG_URI = "http://www.politietsregisterblade.dk/api/1/?type=address&registerblad_id=";
/*  30 */   private final String USER_AGENT = "Mozilla/5.0";
/*  31 */   private final String ACCEPT = "application/json";
/*  32 */   private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PolRegLocationProvider()
/*     */   {
/*  39 */     LOGGER.info("Constructor");
/*     */     try
/*     */     {
/*  42 */       String registerBlad = this.store.getString("POLREGID");
/*  43 */       URL url = new URL("http://www.politietsregisterblade.dk/api/1/?type=address&registerblad_id=" + registerBlad);
/*  44 */       HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/*  45 */       conn.setRequestMethod("GET");
/*  46 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0");
/*  47 */       conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*  48 */       conn.setRequestProperty("Accept", "application/json");
/*     */       
/*  50 */       int responseCode = conn.getResponseCode();
/*  51 */       System.out.println("Response code: " + responseCode);
/*     */       
/*  53 */       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */       
/*  55 */       StringBuilder sb = new StringBuilder();
/*  56 */       String s = "";
/*     */       
/*  58 */       while ((s = br.readLine()) != null) {
/*  59 */         sb.append(s);
/*     */       }
/*     */       
/*  62 */       JSONArray resultArray = new JSONArray(sb.toString());
/*     */       
/*  64 */       this.modelList = new ArrayList();
/*     */       
/*     */ 
/*  67 */       int l = resultArray.length() - 1;
/*     */       
/*  69 */       for (int i = 0; i < l; i++) {
/*  70 */         JSONObject jsonAddress = (JSONObject)resultArray.get(i);
/*  71 */         PolRegLocationModel model = new PolRegLocationModel();
/*     */         
/*  73 */         LOGGER.fine(jsonAddress.toString(2));
/*     */         try
/*     */         {
/*  76 */           sb = new StringBuilder();
/*  77 */           s = jsonAddress.getString("roadname");
/*  78 */           if (!s.equals("")) {
/*  79 */             sb.append(s);
/*     */           }
/*  81 */           s = jsonAddress.getString("number");
/*  82 */           if (!s.equals("")) {
/*  83 */             sb.append(" " + s);
/*     */           }
/*  85 */           s = jsonAddress.getString("letter");
/*  86 */           if (!s.equals("")) {
/*  87 */             sb.append(" " + s);
/*     */           }
/*  89 */           s = jsonAddress.getString("floor");
/*  90 */           if (!s.equals("")) {
/*  91 */             sb.append(" " + s);
/*     */           }
/*  93 */           s = jsonAddress.getString("side");
/*  94 */           if (!s.equals("")) {
/*  95 */             sb.append(" " + s);
/*     */           }
/*  97 */           s = jsonAddress.getString("place");
/*  98 */           if (!s.equals("")) {
/*  99 */             sb.append(" " + s);
/*     */           }
/* 101 */           model.setAddress(sb.toString());
/*     */         }
/*     */         catch (Exception localException1) {}
/*     */         try {
/* 105 */           model.setDate(jsonAddress.getString("date"));
/*     */         }
/*     */         catch (Exception localException2) {}
/*     */         try {
/* 109 */           model.setLatitude(jsonAddress.getString("latitude"));
/*     */         }
/*     */         catch (Exception localException3) {}
/*     */         try {
/* 113 */           model.setLongitude(jsonAddress.getString("longitude"));
/*     */         }
/*     */         catch (Exception localException4) {}
/*     */         
/* 117 */         this.modelList.add(model);
/*     */       }
/*     */     } catch (Exception e) {
/* 120 */       LOGGER.severe(e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<PolRegLocationModel> getModelList()
/*     */   {
/* 128 */     LOGGER.info("Get Model List");
/* 129 */     return this.modelList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromH2(int i) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setModelList(List<PolRegLocationModel> modelList)
/*     */   {
/* 149 */     this.modelList = modelList;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\PolRegLocationProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */