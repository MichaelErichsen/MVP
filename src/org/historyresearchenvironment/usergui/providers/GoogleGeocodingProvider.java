/*    */ package org.historyresearchenvironment.usergui.providers;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.util.logging.Logger;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GoogleGeocodingProvider
/*    */ {
/* 17 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String geocode(String placeName)
/*    */   {
/* 31 */     String returnValue = "";
/*    */     try
/*    */     {
/* 34 */       String address = "https://maps.googleapis.com/maps/api/geocode/json?address=" + 
/* 35 */         placeName.replace(' ', '+');
/* 36 */       URL url = new URL(address);
/*    */       
/* 38 */       LOGGER.info(url.toString());
/* 39 */       BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
/*    */       
/* 41 */       StringBuilder sb = new StringBuilder();
/* 42 */       String s = "";
/*    */       
/* 44 */       while ((s = br.readLine()) != null)
/*    */       {
/* 46 */         sb.append(s);
/*    */       }
/*    */       
/* 49 */       JSONObject jsonObject = new JSONObject(sb.toString());
/* 50 */       JSONArray results = (JSONArray)jsonObject.get("results");
/*    */       
/* 52 */       LOGGER.fine(results.toString(2));
/*    */       
/* 54 */       JSONObject result = (JSONObject)results.get(0);
/* 55 */       JSONObject geometry = (JSONObject)result.get("geometry");
/* 56 */       JSONObject location = (JSONObject)geometry.get("location");
/* 57 */       Double lng = Double.valueOf(location.getDouble("lng"));
/* 58 */       Double lat = Double.valueOf(location.getDouble("lat"));
/* 59 */       returnValue = lng + ", " + lat;
/* 60 */       LOGGER.info("Lat " + lat + ", lng " + lng);
/*    */     } catch (Exception e) {
/* 62 */       LOGGER.severe(e.getClass() + " " + e.getMessage());
/* 63 */       returnValue = "Error: " + e.getClass() + " " + e.getMessage();
/*    */     }
/*    */     
/* 66 */     return returnValue;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\GoogleGeocodingProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */