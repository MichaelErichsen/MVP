///*     */ package org.historyresearchenvironment.usergui.dialogs;
///*     */ 
///*     */ import java.io.BufferedReader;
///*     */ import java.io.IOException;
///*     */ import java.io.InputStreamReader;
///*     */ import java.lang.reflect.InvocationTargetException;
///*     */ import java.net.URL;
///*     */ import java.util.ArrayList;
///*     */ import java.util.List;
///*     */ import java.util.logging.Logger;
///*     */ import javax.inject.Inject;
///*     */ import javax.net.ssl.HttpsURLConnection;
///*     */ import org.eclipse.core.runtime.IProgressMonitor;
///*     */ import org.eclipse.e4.core.services.events.IEventBroker;
///*     */ import org.eclipse.jface.operation.IRunnableWithProgress;
///*     */ import org.historyresearchenvironment.usergui.models.FamilySearchModel;
///*     */ import org.historyresearchenvironment.usergui.providers.FamilySearchProvider;
///*     */ import org.json.JSONArray;
///*     */ import org.json.JSONException;
///*     */ import org.json.JSONObject;
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ public class FamilySearchProgressDialog
///*     */   implements IRunnableWithProgress
///*     */ {
///*     */   @Inject
///*     */   protected static IEventBroker eventBroker;
///*  36 */   protected static final Logger LOGGER = Logger.getLogger("global");
///*     */   
///*  38 */   private final String USER_AGENT = "Mozilla/5.0";
///*  39 */   private final String ACCEPT = "application/json";
///*  40 */   private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
///*  41 */   private final String SEARCH_URL = "https://integration.familysearch.org/platform/tree/search";
///*     */   
///*     */ 
///*     */   private List<FamilySearchModel> itemList;
///*     */   
///*     */ 
///*     */   private final String accessToken;
///*     */   
///*     */ 
///*     */   private final String name;
///*     */   
///*     */ 
///*     */   private final FamilySearchProvider familySearchProvider;
///*     */   
///*     */ 
///*     */   public FamilySearchProgressDialog(String accessToken, String name, FamilySearchProvider familySearchProvider)
///*     */   {
///*  58 */     this.accessToken = accessToken;
///*  59 */     this.name = name;
///*  60 */     this.familySearchProvider = familySearchProvider;
///*     */   }
///*     */   
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */   public synchronized void run(IProgressMonitor monitor)
///*     */     throws InvocationTargetException, InterruptedException
///*     */   {
///*  71 */     int counter = 0;
///*  72 */     String message = "";
///*     */     
///*  74 */     monitor.beginTask("Fetching Family Search results", -1);
///*     */     
///*  76 */     this.itemList = new ArrayList();
///*     */     
///*  78 */     String getData = "?access_token=" + this.accessToken + "&q=name:" + this.name.toLowerCase().trim();
///*     */     try
///*     */     {
///*  81 */       URL url = new URL("https://integration.familysearch.org/platform/tree/search" + getData);
///*  82 */       HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
///*  83 */       conn.setRequestMethod("GET");
///*  84 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0");
///*  85 */       conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
///*  86 */       conn.setRequestProperty("Accept", "application/json");
///*  87 */       conn.setRequestProperty("Authorization", "Bearer " + this.accessToken);
///*     */       
///*  89 */       int responseCode = conn.getResponseCode();
///*  90 */       LOGGER.fine("Sending 'GET' request to URL: " + url);
///*  91 */       LOGGER.fine("Response code: " + responseCode);
///*     */       
///*  93 */       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
///*     */       
///*  95 */       StringBuilder sb = new StringBuilder();
///*  96 */       String s = "";
///*     */       
///*  98 */       while ((s = br.readLine()) != null) {
///*  99 */         sb.append(s);
///*     */       }
///*     */       
///* 102 */       JSONObject jsonObject = new JSONObject(sb.toString());
///* 103 */       int results = jsonObject.getInt("results");
///* 104 */       LOGGER.info("Results: " + results);
///*     */       
///* 106 */       JSONArray entries = (JSONArray)jsonObject.get("entries");
///*     */       
///* 108 */       int lim = entries.length();
///* 109 */       LOGGER.info("Entries: " + lim);
///* 110 */       if (lim > 10) {
///* 111 */         lim = 10;
///*     */       }
///*     */       
///* 114 */       for (int i = 0; i < lim; i++) {
///* 115 */         JSONObject content = (JSONObject)entries.get(i);
///* 116 */         JSONObject content2 = (JSONObject)content.get("content");
///* 117 */         JSONObject gedcomx = (JSONObject)content2.get("gedcomx");
///* 118 */         JSONArray persons = (JSONArray)gedcomx.get("persons");
///*     */         
///* 120 */         int arrayLength = persons.length();
///* 121 */         LOGGER.info("Persons: " + arrayLength);
///* 122 */         if (arrayLength > 10) {
///* 123 */           arrayLength = 10;
///*     */         }
///*     */         
///* 126 */         for (int j = 0; j < arrayLength; j++) {
///* 127 */           JSONObject person = (JSONObject)persons.get(j);
///* 128 */           JSONObject display = (JSONObject)person.get("display");
///*     */           
///* 130 */           LOGGER.fine("Item " + j + ": " + display.toString(2));
///*     */           
///* 132 */           FamilySearchModel item = new FamilySearchModel();
///*     */           try
///*     */           {
///* 135 */             item.setName(display.getString("name"));
///*     */           }
///*     */           catch (Exception localException1) {}
///*     */           try {
///* 139 */             item.setGender(display.getString("gender"));
///*     */           }
///*     */           catch (Exception localException2) {}
///*     */           try {
///* 143 */             item.setBirthDate(display.getString("birthDate"));
///*     */           }
///*     */           catch (Exception localException3) {}
///*     */           try {
///* 147 */             item.setBirthPlace(display.getString("birthPlace"));
///*     */           }
///*     */           catch (Exception localException4) {}
///*     */           try {
///* 151 */             item.setDeathDate(display.getString("deathDate"));
///*     */           }
///*     */           catch (Exception localException5) {}
///*     */           try {
///* 155 */             item.setDeathPlace(display.getString("deathPlace"));
///*     */           }
///*     */           catch (Exception localException6) {}
///*     */           
///*     */ 
///* 160 */           if (monitor.isCanceled()) {
///* 161 */             monitor.done();
///* 162 */             return;
///*     */           }
///*     */           
///* 165 */           wait(100L);
///* 166 */           monitor.subTask("Getting person " + counter++ + " of " + results);
///* 167 */           this.itemList.add(item);
///* 168 */           monitor.worked(1);
///*     */           
///* 170 */           if (this.itemList.size() > 50) {
///* 171 */             message = "Items added: " + (this.itemList.size() - 1) + " (" + results + " was found)";
///* 172 */             LOGGER.info(message);
///*     */             
///* 174 */             eventBroker.post("MESSAGE", message);
///* 175 */             monitor.done();
///* 176 */             return;
///*     */           }
///*     */         }
///*     */       }
///* 180 */       message = "Items added: " + (this.itemList.size() - 1);
///* 181 */       LOGGER.info(message);
///* 182 */       this.familySearchProvider.setItemList(this.itemList);
///* 183 */       monitor.done();
///*     */     } catch (IOException|JSONException e) {
///* 185 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
///*     */     }
///*     */   }
///*     */ }
//
