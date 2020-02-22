/*     */ package org.historyresearchenvironment.usergui.providers;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import org.eclipse.core.internal.runtime.InternalPlatform;
/*     */ import org.eclipse.core.runtime.IProgressMonitor;
/*     */ import org.eclipse.core.runtime.OperationCanceledException;
/*     */ import org.eclipse.core.runtime.SubMonitor;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.e4.core.contexts.EclipseContextFactory;
/*     */ import org.eclipse.e4.core.services.events.IEventBroker;
/*     */ import org.eclipse.jface.dialogs.ProgressMonitorDialog;
/*     */ import org.eclipse.jface.operation.IRunnableWithProgress;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.historyresearchenvironment.usergui.models.FamilySearchModel;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.osgi.framework.BundleContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FamilySearchProvider
/*     */   extends AbstractHreProvider
/*     */ {
/*  41 */   private final ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, 
/*  42 */     "org.historyresearchenvironment.usergui");
/*     */   
/*     */   private final BundleContext context;
/*     */   private List<FamilySearchModel> itemList;
/*  46 */   private final String SOCKETS_URL = "identint.familysearch.org";
/*  47 */   private final String USER_AGENT = "Mozilla/5.0";
/*  48 */   private final String AUTH_URL = "https://identint.familysearch.org/cis-web/oauth2/v3/token";
/*  49 */   private final String ACCEPT = "application/json";
/*  50 */   private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
/*     */   
/*  52 */   private final String CLIENT_ID = "a02f100000RNRXSAA5";
/*  53 */   private final String SEARCH_URL = "https://integration.familysearch.org/platform/tree/search";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FamilySearchProvider(Shell shell)
/*     */   {
/*  63 */     LOGGER.fine("Constructor");
/*  64 */     this.context = InternalPlatform.getDefault().getBundleContext();
/*  65 */     EclipseContextFactory.getServiceContext(this.context);
/*     */     try
/*     */     {
/*  68 */       final String localIpAddress = getLocalIpAddress();
/*     */       
/*  70 */       ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
/*  71 */       dialog.run(true, true, new IRunnableWithProgress()
/*     */       {
/*     */         public void run(IProgressMonitor monitor)
/*     */         {
/*  75 */           SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
/*     */           try
/*     */           {
/*  78 */             String accessToken = FamilySearchProvider.this.doAuth(localIpAddress, 
/*  79 */               subMonitor.split(50, 0));
/*     */             
/*  81 */             String name = FamilySearchProvider.this.store.getString("PERNAME");
/*  82 */             FamilySearchProvider.LOGGER.fine("Name: " + name);
/*     */             
/*  84 */             FamilySearchProvider.this.doSearch(accessToken, subMonitor.split(50, 0));
/*  85 */             FamilySearchProvider.LOGGER.fine("Search done");
/*     */           } catch (OperationCanceledException e) {
/*  87 */             FamilySearchProvider.LOGGER.info("Operation cancelled, " + e.getClass() + " " + e.getMessage());
/*     */           } catch (Exception e) {
/*  89 */             e.printStackTrace();
/*  90 */             String message = e.getClass() + " " + e.getMessage();
/*  91 */             FamilySearchProvider.LOGGER.severe(message);
/*  92 */             if ((message != null) && (FamilySearchProvider.eventBroker != null)) {
/*  93 */               FamilySearchProvider.eventBroker.post("MESSAGE", message);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (Exception e) {
/* 100 */       e.printStackTrace();
/* 101 */       String message = e.getClass() + " " + e.getMessage();
/* 102 */       LOGGER.severe(message);
/* 103 */       if (message != null) {
/* 104 */         eventBroker.post("MESSAGE", message);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized String doAuth(String localIpAddress, IProgressMonitor monitor)
/*     */     throws Exception
/*     */   {
/* 116 */     SubMonitor subMonitor = SubMonitor.convert(monitor, 50);
/* 117 */     subMonitor.beginTask("Authorizing access to Family Search", 50);
/* 118 */     subMonitor.subTask("Connecting");
/*     */     try
/*     */     {
/* 121 */       wait(100L);
/*     */     } catch (Exception e) {
/* 123 */       LOGGER.info(e.getClass() + " " + e.getMessage());
/* 124 */       e.printStackTrace();
/*     */     }
/*     */     
/* 127 */     URL url = new URL("https://identint.familysearch.org/cis-web/oauth2/v3/token");
/* 128 */     HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
/* 129 */     conn.setRequestMethod("POST");
/* 130 */     conn.setRequestProperty("User-Agent", "Mozilla/5.0");
/* 131 */     conn.setRequestProperty("Content_Type", "application/x-www-form-urlencoded");
/* 132 */     conn.setRequestProperty("Accept", "application/json");
/*     */     
/* 134 */     String postData = "grant_type=unauthenticated_session&ip_address=" + localIpAddress + "&client_id=" + 
/* 135 */       "a02f100000RNRXSAA5";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 141 */     conn.setDoOutput(true);
/* 142 */     DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
/* 143 */     wr.writeBytes(postData);
/* 144 */     wr.flush();
/* 145 */     wr.close();
/* 146 */     subMonitor.subTask("Posting authorization request");
/* 147 */     subMonitor.worked(25);
/*     */     try
/*     */     {
/* 150 */       wait(100L);
/*     */     } catch (Exception e) {
/* 152 */       LOGGER.info(e.getClass() + " " + e.getMessage());
/* 153 */       e.printStackTrace();
/*     */     }
/*     */     
/* 156 */     int responseCode = conn.getResponseCode();
/* 157 */     LOGGER.fine(
/* 158 */       "Sending 'POST' request to URL: " + url + "\nData: " + postData + "\nResponse code: " + responseCode);
/*     */     
/* 160 */     BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */     
/* 162 */     StringBuffer response = new StringBuffer();
/*     */     String output;
/* 164 */     while ((output = in.readLine()) != null) { String output;
/* 165 */       response.append(output);
/*     */     }
/*     */     
/* 168 */     in.close();
/*     */     
/* 170 */     JSONObject jsonObject = new JSONObject(response.toString());
/*     */     
/* 172 */     LOGGER.fine("JSON response: " + jsonObject.toString(2));
/*     */     
/* 174 */     return jsonObject.getString("access_token");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected synchronized void doSearch(String accessToken, IProgressMonitor monitor)
/*     */   {
/* 183 */     int counter = 0;
/* 184 */     String message = "";
/* 185 */     String name = this.store.getString("PERNAME").toLowerCase().trim();
/* 186 */     LOGGER.fine("Name: " + name);
/*     */     
/* 188 */     SubMonitor subMonitor = SubMonitor.convert(monitor, 50);
/* 189 */     subMonitor.beginTask("Fetching Family Search results for " + name, 50);
/*     */     
/* 191 */     this.itemList = new ArrayList();
/*     */     
/* 193 */     String getData = "?access_token=" + accessToken + "&q=name:" + name;
/*     */     try
/*     */     {
/* 196 */       URL url = new URL("https://integration.familysearch.org/platform/tree/search" + getData);
/* 197 */       HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
/* 198 */       conn.setRequestMethod("GET");
/* 199 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0");
/* 200 */       conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/* 201 */       conn.setRequestProperty("Accept", "application/json");
/* 202 */       conn.setRequestProperty("Authorization", "Bearer " + accessToken);
/*     */       
/* 204 */       int responseCode = conn.getResponseCode();
/* 205 */       LOGGER.fine("Sending 'GET' request to URL: " + url);
/* 206 */       LOGGER.fine("Response code: " + responseCode);
/*     */       
/* 208 */       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */       
/* 210 */       StringBuilder sb = new StringBuilder();
/* 211 */       String s = "";
/*     */       
/* 213 */       while ((s = br.readLine()) != null) {
/* 214 */         sb.append(s);
/*     */       }
/*     */       
/* 217 */       JSONObject jsonObject = new JSONObject(sb.toString());
/* 218 */       int results = jsonObject.getInt("results");
/* 219 */       LOGGER.fine("Results: " + results);
/*     */       
/* 221 */       JSONArray entries = (JSONArray)jsonObject.get("entries");
/*     */       
/* 223 */       int lim = entries.length();
/* 224 */       LOGGER.fine("Entries: " + lim);
/*     */       
/* 226 */       for (int i = 0; i < lim; i++) {
/* 227 */         JSONObject content = (JSONObject)entries.get(i);
/* 228 */         JSONObject content2 = (JSONObject)content.get("content");
/* 229 */         JSONObject gedcomx = (JSONObject)content2.get("gedcomx");
/* 230 */         JSONArray persons = (JSONArray)gedcomx.get("persons");
/*     */         
/* 232 */         int arrayLength = persons.length();
/* 233 */         LOGGER.fine("Persons: " + arrayLength);
/*     */         
/* 235 */         for (int j = 0; j < arrayLength; j++) {
/* 236 */           JSONObject person = (JSONObject)persons.get(j);
/*     */           
/* 238 */           FamilySearchModel item = new FamilySearchModel();
/*     */           try
/*     */           {
/* 241 */             item.setAfn(person.getString("id"));
/* 242 */             LOGGER.fine(item.getAfn());
/*     */           }
/*     */           catch (Exception localException1) {}
/*     */           
/* 246 */           JSONObject display = (JSONObject)person.get("display");
/*     */           
/* 248 */           LOGGER.fine("Item " + j + ": " + display.toString(2));
/*     */           try
/*     */           {
/* 251 */             item.setName(display.getString("name"));
/*     */           }
/*     */           catch (Exception localException2) {}
/*     */           try {
/* 255 */             item.setGender(display.getString("gender"));
/*     */           }
/*     */           catch (Exception localException3) {}
/*     */           try {
/* 259 */             item.setBirthDate(display.getString("birthDate"));
/*     */           }
/*     */           catch (Exception localException4) {}
/*     */           try {
/* 263 */             item.setBirthPlace(display.getString("birthPlace"));
/*     */           }
/*     */           catch (Exception localException5) {}
/*     */           try {
/* 267 */             item.setDeathDate(display.getString("deathDate"));
/*     */           }
/*     */           catch (Exception localException6) {}
/*     */           try {
/* 271 */             item.setDeathPlace(display.getString("deathPlace"));
/*     */           }
/*     */           catch (Exception localException7) {}
/*     */           try {
/* 275 */             item.setAfn(display.getString("id"));
/*     */           }
/*     */           catch (Exception localException8) {}
/*     */           
/*     */           try
/*     */           {
/* 281 */             wait(100L);
/*     */           } catch (InterruptedException e) {
/* 283 */             LOGGER.info(e.getClass() + " " + e.getMessage());
/*     */           }
/*     */           
/* 286 */           this.itemList.add(item);
/* 287 */           subMonitor.worked(1);
/* 288 */           subMonitor.subTask("Fetching Family Search result " + counter++ + " of " + results);
/*     */           
/* 290 */           if (this.itemList.size() > 50) {
/* 291 */             message = "Items added: " + (this.itemList.size() - 1) + " (" + results + " was found)";
/* 292 */             LOGGER.info(message);
/* 293 */             if ((message != null) && (eventBroker != null)) {
/* 294 */               eventBroker.post("MESSAGE", message);
/*     */             }
/* 296 */             return;
/*     */           }
/*     */         }
/*     */       }
/* 300 */       message = "Items added: " + (this.itemList.size() - 1);
/* 301 */       LOGGER.info(message);
/* 302 */       if (message != null) {
/* 303 */         eventBroker.post("MESSAGE", message);
/*     */       }
/*     */     } catch (IOException|JSONException e) {
/* 306 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/* 307 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<FamilySearchModel> getItemList()
/*     */   {
/* 315 */     return this.itemList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private String getLocalIpAddress()
/*     */     throws Exception
/*     */   {
/* 323 */     String localIpAddress = "";
/*     */     
/* 325 */     Socket socket = new Socket();
/* 326 */     socket.connect(new InetSocketAddress("identint.familysearch.org", 80));
/* 327 */     localIpAddress = socket.getLocalAddress().getHostAddress();
/* 328 */     socket.close();
/*     */     
/* 330 */     return localIpAddress;
/*     */   }
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
/*     */   public void setItemList(List<FamilySearchModel> itemList)
/*     */   {
/* 349 */     this.itemList = itemList;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\FamilySearchProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */