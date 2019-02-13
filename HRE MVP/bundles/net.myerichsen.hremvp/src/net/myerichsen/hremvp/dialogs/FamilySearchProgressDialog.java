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
//       JSONObject jsonObject = new JSONObject(sb.toString());
//       int results = jsonObject.getInt("results");
//       LOGGER.info("Results: " + results);
///*     */       
//       JSONArray entries = (JSONArray)jsonObject.get("entries");
///*     */       
//       int lim = entries.length();
//       LOGGER.info("Entries: " + lim);
//       if (lim > 10) {
//         lim = 10;
///*     */       }
///*     */       
//       for (int i = 0; i < lim; i++) {
//         JSONObject content = (JSONObject)entries.get(i);
//         JSONObject content2 = (JSONObject)content.get("content");
//         JSONObject gedcomx = (JSONObject)content2.get("gedcomx");
//         JSONArray persons = (JSONArray)gedcomx.get("persons");
///*     */         
//         int arrayLength = persons.length();
//         LOGGER.info("Persons: " + arrayLength);
//         if (arrayLength > 10) {
//           arrayLength = 10;
///*     */         }
///*     */         
//         for (int j = 0; j < arrayLength; j++) {
//           JSONObject person = (JSONObject)persons.get(j);
//           JSONObject display = (JSONObject)person.get("display");
///*     */           
//           LOGGER.fine("Item " + j + ": " + display.toString(2));
///*     */           
//           FamilySearchModel item = new FamilySearchModel();
///*     */           try
///*     */           {
//             item.setName(display.getString("name"));
///*     */           }
///*     */           catch (Exception localException1) {}
///*     */           try {
//             item.setGender(display.getString("gender"));
///*     */           }
///*     */           catch (Exception localException2) {}
///*     */           try {
//             item.setBirthDate(display.getString("birthDate"));
///*     */           }
///*     */           catch (Exception localException3) {}
///*     */           try {
//             item.setBirthPlace(display.getString("birthPlace"));
///*     */           }
///*     */           catch (Exception localException4) {}
///*     */           try {
//             item.setDeathDate(display.getString("deathDate"));
///*     */           }
///*     */           catch (Exception localException5) {}
///*     */           try {
//             item.setDeathPlace(display.getString("deathPlace"));
///*     */           }
///*     */           catch (Exception localException6) {}
///*     */           
///*     */ 
//           if (monitor.isCanceled()) {
//             monitor.done();
//             return;
///*     */           }
///*     */           
//           wait(100L);
//           monitor.subTask("Getting person " + counter++ + " of " + results);
//           this.itemList.add(item);
//           monitor.worked(1);
///*     */           
//           if (this.itemList.size() > 50) {
//             message = "Items added: " + (this.itemList.size() - 1) + " (" + results + " was found)";
//             LOGGER.info(message);
///*     */             
//             eventBroker.post("MESSAGE", message);
//             monitor.done();
//             return;
///*     */           }
///*     */         }
///*     */       }
//       message = "Items added: " + (this.itemList.size() - 1);
//       LOGGER.info(message);
//       this.familySearchProvider.setItemList(this.itemList);
//       monitor.done();
///*     */     } catch (IOException|JSONException e) {
//       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
///*     */     }
///*     */   }
///*     */ }
//
