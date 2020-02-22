/*     */ package org.historyresearchenvironment.usergui.clientinterface;
/*     */ 
/*     */ import org.historyresearchenvironment.usergui.models.ConnectionEventsItem;
/*     */ import org.historyresearchenvironment.usergui.models.EventAssociatesModel;
/*     */ import org.historyresearchenvironment.usergui.models.PersonalConnectionsModel;
/*     */ import org.historyresearchenvironment.usergui.models.PersonalRelativesProvider;
/*     */ import org.historyresearchenvironment.usergui.providers.AncestorTreeProvider;
/*     */ import org.historyresearchenvironment.usergui.providers.EventAssociatesProvider;
/*     */ import org.historyresearchenvironment.usergui.providers.PersonNavigatorProvider;
/*     */ import org.historyresearchenvironment.usergui.providers.PersonalEventProvider;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerRequest;
/*     */ import org.historyresearchenvironment.usergui.serverinterface.ServerResponse;
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
/*     */ public class TestBusinessLayerInterface
/*     */   implements BusinessLayerInterface
/*     */ {
/*     */   public ServerResponse callBusinessLayer(ServerRequest request)
/*     */   {
/*  35 */     if (request.getModelName().equals("personalevents"))
/*  36 */       return getPersonalEvents(request);
/*  37 */     if (request.getModelName().equals("personalrelatives"))
/*  38 */       return getPersonalRelatives(request);
/*  39 */     if (request.getModelName().equals("personalconnections"))
/*  40 */       return getPersonalConnections(request);
/*  41 */     if (request.getModelName().equals("eventassociates"))
/*  42 */       return getEventAssociates(request);
/*  43 */     if (request.getModelName().equals("personlist"))
/*  44 */       return getPersonList(request);
/*  45 */     if (request.getModelName().equals("descendantlist"))
/*  46 */       return getDescendantList(request);
/*  47 */     if (request.getModelName().equals("ancestorlist")) {
/*  48 */       return getAncestorList(request);
/*     */     }
/*  50 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ServerResponse getAncestorList(ServerRequest request)
/*     */   {
/*  59 */     AncestorTreeProvider model = new AncestorTreeProvider();
/*     */     
/*  61 */     model.addAncestor(1, "Frank ALEXANDER", "M", 10, 11);
/*  62 */     model.addAncestor(3, "Samuel Lee ALEXANDER", "M", 1, 0);
/*  63 */     model.addAncestor(4, "Lula Elizabeth ALEXANDER", "F", 1, 0);
/*  64 */     model.addAncestor(5, "John Wtight ALEXANDER", "M", 1, 0);
/*  65 */     model.addAncestor(10, "John ALEXANDER", "M", 17, 18);
/*  66 */     model.addAncestor(11, "Delilah WOODS", "F", 0, 0);
/*  67 */     model.addAncestor(17, "John ALEXANDER", "M", 0, 0);
/*  68 */     model.addAncestor(18, "Martha FERGUSON", "F", 0, 0);
/*  69 */     model.addAncestor(36, "Samuel ALEXANDER", "M", 10, 11);
/*  70 */     model.addAncestor(37, "Eliza Jane ALEXANDER", "F", 10, 11);
/*     */     
/*  72 */     return new ServerResponse(model, 0, "OK");
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
/*     */   private ServerResponse getDescendantList(ServerRequest request)
/*     */   {
/* 117 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ServerResponse getEventAssociates(ServerRequest request)
/*     */   {
/* 125 */     EventAssociatesProvider model = (EventAssociatesProvider)request.getProvider();
/* 126 */     model.setIdentity("Marriage of Charles, Prince of Wales, and Lady Diana Spencer");
/* 127 */     model.setLocation("St. Paul's Cathedral, London, United Kingdom");
/* 128 */     model.setDate("29 Jul 1981, 11:20am");
/*     */     
/* 130 */     model.addAssociate(new EventAssociatesModel("Person", "* Groom", "Charles, Prince of Wales (73)"));
/* 131 */     model.addAssociate(new EventAssociatesModel("Person", "* Bride", "Lady Diana SPENCER (283)"));
/* 132 */     model.addAssociate(new EventAssociatesModel("Person", "Groom - Mother", "Elizabeth II (83)"));
/* 133 */     model.addAssociate(new EventAssociatesModel("Person", "Groom - Father", "The Duke of Edinburgh (101)"));
/* 134 */     model.addAssociate(new EventAssociatesModel("Person", "Groom - Grand Mother", "The Queen Mother (10)"));
/* 135 */     model.addAssociate(new EventAssociatesModel("Person", "Groom - Sister", "The Princess Anne (563)"));
/* 136 */     model.addAssociate(new EventAssociatesModel("Person", "Groom - Brother", "The Prince Andrew (343)"));
/* 137 */     model.addAssociate(new EventAssociatesModel("Person", "Groom - Brother", "The Prince Edward (48)"));
/* 138 */     model.addAssociate(new EventAssociatesModel("Person", "Bride - Father", "The 8th Earl SPENCER (77)"));
/* 139 */     model.addAssociate(new EventAssociatesModel("Person", "Bride - Mother", "Frances SHAND KYDD (881)"));
/* 140 */     model.addAssociate(new EventAssociatesModel("Person", "Bride - Sister", "Lady Sarah McCORQUODALE (910)"));
/* 141 */     model.addAssociate(new EventAssociatesModel("Person", "Bride - Sister", "Lady Jane FELLOWS (792)"));
/* 142 */     model.addAssociate(
/* 143 */       new EventAssociatesModel("Person", "Bride - Brother", "Charles SPENCER, Viscount Althorp (793)"));
/*     */     
/* 145 */     return new ServerResponse(model, 0, "OK");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ServerResponse getPersonalConnections(ServerRequest request)
/*     */   {
/* 153 */     PersonalConnectionsModel model = (PersonalConnectionsModel)request.getProvider();
/* 154 */     model.setIdentity("John ALEXANDER (10) (>1770-1831)");
/*     */     
/* 156 */     model.setFather("John ALEXANDER (17) (>1770-1831");
/* 157 */     model.setMother("Martha FERGUSON (18) (1784-1863)");
/* 158 */     model.setNoChildren(3);
/*     */     
/* 160 */     model.addEvent(new ConnectionEventsItem("John ALEXANDER (10)", "* Head of H-Hold", 
/* 161 */       "1840 US Census, Carter County", "__ __ 1840"));
/* 162 */     model.addEvent(new ConnectionEventsItem("John ALEXANDER (10)", "* Head of H-Hold", 
/* 163 */       "1860 US Census, Carter County", "1 Jun 1860"));
/* 164 */     model.addEvent(new ConnectionEventsItem("Mary Catherine KEEBLER (2)", "Bride", "ALEXANDER, KEEBLER Marriage", 
/* 165 */       "24 Dec 1861"));
/* 166 */     model.addEvent(new ConnectionEventsItem("Lula Elizabeth ALEXANDER (4)", "* Born", 
/* 167 */       "Birth of Daughter - Lula Elizabeth", "13 Mar 1865"));
/* 168 */     model.addEvent(new ConnectionEventsItem("Annie Eliza ALEXANDER (6)", "* Born", 
/* 169 */       "Birth of Daughter - Annie Eliza", "10 Jan 1870"));
/* 170 */     model.addEvent(new ConnectionEventsItem("Robert Moore BALDWIN (60)", "* Groom", "BALDWIN, ALEXANDER Marriage", 
/* 171 */       "22 Jul 1885"));
/* 172 */     model.addEvent(new ConnectionEventsItem("John Keys FUGATE (42)", "* Groom", "FUGATE, ALEXANDER Marriage", 
/* 173 */       "10 Oct 1876"));
/*     */     
/* 175 */     return new ServerResponse(model, 0, "OK");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ServerResponse getPersonalEvents(ServerRequest request)
/*     */   {
/* 183 */     PersonalEventProvider model = (PersonalEventProvider)request.getProvider();
/* 184 */     model.setIdentity("John ALEXANDER (10) (1812-1876)");
/* 185 */     model.setFather("John ALEXANDER (17) (>1770-1831");
/* 186 */     model.setMother("Martha FERGUSON (18) (1784-1863)");
/* 187 */     model.setNoChildren(3);
/* 188 */     model.addEvent("Birth", "Born", "11 Oct 1812", "-; Washington County, Tennessee");
/* 189 */     model.addEvent("Marriage", "Groom", "22 Jan 1835", 
/* 190 */       "Delilah WOODS (11); -; Elizabethon, Carter County, Tennessee");
/* 191 */     model.addEvent("Son-Biological", "Father", "03 Oct 1838", 
/* 192 */       "Frank ALEXANDER (11); Elizabethon, Carter County, Tennessee");
/* 193 */     model.addEvent("Census", "Head of Household", "01 Jun 1850", "-; Elizabethon, Carter County, Tennessee");
/* 194 */     model.addEvent("Occupation", "Business Owner", "bt 1840-1866", "Frank ALEXANDER (11); Smith County, Virginia");
/* 195 */     model.addEvent("Death", "Deceased", "18 Oct 1876", "-; Marion, Smith County, Virginia");
/*     */     
/* 197 */     return new ServerResponse(model, 0, "OK");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ServerResponse getPersonalRelatives(ServerRequest request)
/*     */   {
/* 205 */     PersonalRelativesProvider model = (PersonalRelativesProvider)request.getProvider();
/* 206 */     model.setIdentity("Frank ALEXANDER (1) (1838-1925)");
/*     */     
/* 208 */     model.setComAncMThis("");
/* 209 */     model.setComAncFThis("");
/* 210 */     model.addLastGen("John ALEXANDER (17)", "Father", "M", "1836-1874", "-1", "0");
/* 211 */     model.addLastGen("Delilah Woods (11)", "Mother", "F", "1838-1925", "-1", "8");
/*     */     
/* 213 */     model.setComAncMThis("John ALEXANDER (17)");
/* 214 */     model.setComAncFThis("Delilah WOODS (11)");
/* 215 */     model.addThisGen("Samuel ALEXANDER (36)", "Brother", "M", "1836-1874", "0", "0");
/* 216 */     model.addThisGen("Frank ALEXANDER (1)", "Target", "M", "1838-1925", "0", "8");
/* 217 */     model.addThisGen("Eliza Jane ALEXANDER (37)", "Sister", "F", "1843-1869", "0", "0");
/*     */     
/* 219 */     model.setComAncMNext("Frank ALEXANDER (1)");
/* 220 */     model.setComAncFNext("Mary Catherine KEEBLER (2)");
/* 221 */     model.addNextGen("Samuel Lee ALEXANDER (3)", "Son", "M", "1862-1932", "+1", "0");
/* 222 */     model.addNextGen("Lula Elizabeth ALEXANDER (4)", "Daughter", "F", "1865-1954", "+1", "1");
/* 223 */     model.addNextGen("John Wright ALEXANDER (5)", "Son", "M", "1807-1866", "+1", "0");
/*     */     
/* 225 */     return new ServerResponse(model, 0, "OK");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ServerResponse getPersonList(ServerRequest request)
/*     */   {
/* 233 */     PersonNavigatorProvider model = new PersonNavigatorProvider();
/*     */     
/* 235 */     model.addPerson("Frank ALEXANDER", 1, "1836", "1925", 0, 0);
/* 236 */     model.addPerson("Samuel Lee ALEXANDER", 3, "1865", "1932", 1, 0);
/* 237 */     model.addPerson("Lula Elizabeth ALEXANDER", 4, "1865", "1954", 1, 0);
/* 238 */     model.addPerson("John Wtight ALEXANDER", 5, "1807", "1866", 1, 0);
/* 239 */     model.addPerson("John ALEXANDER", 10, "11 Oct 1812", "18 Oct 1876", 17, 18);
/* 240 */     model.addPerson("Delilah WOODS", 11, "1838", "1925", 0, 0);
/* 241 */     model.addPerson("Samuel ALEXANDER", 16, "1836", "1874", 10, 11);
/* 242 */     model.addPerson("John ALEXANDER", 17, ">1770", "1831", 0, 0);
/* 243 */     model.addPerson("Martha FERGUSON", 18, "1784", "1863", 0, 0);
/* 244 */     model.addPerson("Eliza Jane ALEXANDER", 37, "1843", "1869", 10, 11);
/*     */     
/* 246 */     return new ServerResponse(model, 0, "OK");
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\clientinterface\TestBusinessLayerInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */