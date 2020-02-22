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
/*     */ import org.historyresearchenvironment.usergui.models.PolRegModel;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolRegProvider
/*     */   extends AbstractHreProvider
/*     */ {
/*  26 */   ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/*  27 */     "org.historyresearchenvironment.usergui");
/*     */   
/*     */   private List<PolRegModel> modelList;
/*  30 */   private final String POLREG_URI = "http://www.politietsregisterblade.dk/api/1?type=person";
/*  31 */   private final String USER_AGENT = "Mozilla/5.0";
/*  32 */   private final String ACCEPT = "application/json";
/*  33 */   private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PolRegProvider()
/*     */   {
/*  40 */     LOGGER.info("Constructor");
/*     */     try
/*     */     {
/*  43 */       String name = this.store.getString("PERNAME");
/*  44 */       String[] nameParts = name.split("\\+");
/*  45 */       StringBuilder sb = new StringBuilder();
/*  46 */       for (int i = 0; i < nameParts.length - 1; i++) {
/*  47 */         sb.append(nameParts[i] + "+");
/*     */       }
/*  49 */       String mid = sb.toString();
/*  50 */       String firstNames = mid.substring(0, mid.length() - 1);
/*  51 */       String lastName = nameParts[(nameParts.length - 1)];
/*  52 */       URL url = new URL("http://www.politietsregisterblade.dk/api/1?type=person&firstnames=" + firstNames + "&lastname=" + lastName);
/*  53 */       HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/*  54 */       conn.setRequestMethod("GET");
/*  55 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0");
/*  56 */       conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*  57 */       conn.setRequestProperty("Accept", "application/json");
/*     */       
/*  59 */       int responseCode = conn.getResponseCode();
/*  60 */       System.out.println("Response code: " + responseCode);
/*     */       
/*  62 */       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */       
/*  64 */       sb = new StringBuilder();
/*  65 */       String s = "";
/*     */       
/*  67 */       while ((s = br.readLine()) != null) {
/*  68 */         sb.append(s);
/*     */       }
/*     */       
/*  71 */       JSONArray resultArray = new JSONArray(sb.toString());
/*     */       
/*  73 */       this.modelList = new ArrayList();
/*     */       
/*     */ 
/*  76 */       int l = resultArray.length();
/*  77 */       l = l > 50 ? 50 : l;
/*     */       
/*  79 */       for (int i = 0; i < l; i++) {
/*  80 */         JSONObject jsonPerson = (JSONObject)resultArray.get(i);
/*  81 */         PolRegModel model = new PolRegModel();
/*     */         try
/*     */         {
/*  84 */           model.setDateofbirth(jsonPerson.getString("dateofbirth"));
/*     */         }
/*     */         catch (Exception localException1) {}
/*     */         try
/*     */         {
/*  89 */           model.setBirthplace(jsonPerson.getString("birthplace"));
/*     */         }
/*     */         catch (Exception localException2) {}
/*     */         try {
/*  93 */           model.setPerson_type(jsonPerson.getString("person_type"));
/*     */         }
/*     */         catch (Exception localException3) {}
/*     */         try {
/*  97 */           model.setDateofdeath(jsonPerson.getString("dateofdeath"));
/*     */         }
/*     */         catch (Exception localException4) {}
/*     */         try {
/* 101 */           model.setId(jsonPerson.getString("id"));
/*     */         }
/*     */         catch (Exception localException5) {}
/*     */         try {
/* 105 */           model.setRegisterblad_id(jsonPerson.getString("registerblad_id"));
/*     */         }
/*     */         catch (Exception localException6) {}
/*     */         try {
/* 109 */           model.setFirstnames(jsonPerson.getString("firstnames"));
/*     */         }
/*     */         catch (Exception localException7) {}
/*     */         try {
/* 113 */           model.setLastname(jsonPerson.getString("lastname"));
/*     */         }
/*     */         catch (Exception localException8) {}
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
/*     */   public List<PolRegModel> getModelList()
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
/*     */   public void setModelList(List<PolRegModel> modelList)
/*     */   {
/* 149 */     this.modelList = modelList;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\PolRegProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */