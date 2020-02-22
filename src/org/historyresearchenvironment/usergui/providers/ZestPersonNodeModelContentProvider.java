/*     */ package org.historyresearchenvironment.usergui.providers;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import org.historyresearchenvironment.tmg.h2.models.Name;
/*     */ import org.historyresearchenvironment.tmg.h2.models.Person;
/*     */ import org.historyresearchenvironment.usergui.models.ZestPersonConnection;
/*     */ import org.historyresearchenvironment.usergui.models.ZestPersonNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZestPersonNodeModelContentProvider
/*     */   extends AbstractHreProvider
/*     */ {
/*  18 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */   private static int maxLevels;
/*     */   
/*     */   private final List<ZestPersonConnection> connections;
/*     */   
/*     */   private final List<ZestPersonNode> nodes;
/*     */   
/*     */ 
/*     */   public ZestPersonNodeModelContentProvider(int perNo)
/*     */   {
/*  29 */     this.nodes = new ArrayList();
/*  30 */     this.connections = new ArrayList();
/*  31 */     maxLevels = 100;
/*     */     
/*  33 */     Person person = new Person(this.conn, perNo);
/*  34 */     Name name = new Name(this.conn, perNo);
/*     */     
/*  36 */     String birth = person.getPbirth().length() > 0 ? person.getPbirth().substring(1, 5) : "";
/*  37 */     String death = person.getPdeath().length() > 0 ? "-" + person.getPdeath().substring(1, 5) : "-";
/*  38 */     String contents = (name.getSrnamedisp() + "\n" + birth + death).trim();
/*  39 */     LOGGER.fine("Person " + perNo + ", " + contents + ", " + person.getSex());
/*     */     
/*  41 */     ZestPersonNode node = new ZestPersonNode(perNo, contents, person.getSex());
/*  42 */     this.nodes.add(node);
/*     */     int mother;
/*  44 */     if (maxLevels > 0) {
/*  45 */       maxLevels -= 1;
/*     */       
/*  47 */       int father = person.getFather();
/*     */       
/*  49 */       if (father != 0) {
/*  50 */         addNode(node, father);
/*     */       }
/*     */       
/*  53 */       mother = person.getMother();
/*     */       
/*  55 */       if (mother != 0) {
/*  56 */         addNode(node, mother);
/*     */       }
/*     */     }
/*     */     
/*  60 */     for (ZestPersonConnection connection : this.connections) {
/*     */       try {
/*  62 */         connection.getSource().getConnectedTo().add(connection.getDestination());
/*     */       } catch (Exception e) {
/*  64 */         LOGGER.severe(
/*  65 */           e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addNode(ZestPersonNode childNode, int perNo)
/*     */   {
/*  75 */     Person person = new Person(this.conn, perNo);
/*  76 */     Name name = new Name(this.conn, perNo);
/*     */     
/*  78 */     String birth = person.getPbirth().length() > 0 ? person.getPbirth().substring(1, 5) : "";
/*  79 */     String death = person.getPdeath().length() > 0 ? "-" + person.getPdeath().substring(1, 5) : "-";
/*  80 */     String contents = (name.getSrnamedisp() + "\n" + birth + death).trim();
/*  81 */     LOGGER.fine("Person " + perNo + ", " + contents + ", " + person.getSex());
/*     */     
/*  83 */     ZestPersonNode node = new ZestPersonNode(perNo, contents, person.getSex());
/*  84 */     this.nodes.add(node);
/*  85 */     ZestPersonConnection connect = new ZestPersonConnection(perNo, Integer.toString(perNo), childNode, node);
/*  86 */     this.connections.add(connect);
/*  87 */     LOGGER.fine("Connection " + perNo + " from " + childNode.getPerNo() + " to " + node.getPerNo());
/*     */     
/*  89 */     if (maxLevels > 0) {
/*  90 */       maxLevels -= 1;
/*  91 */       int father = person.getFather();
/*     */       
/*  93 */       if (father != 0) {
/*  94 */         addNode(node, father);
/*     */       }
/*     */       
/*  97 */       int mother = person.getMother();
/*     */       
/*  99 */       if (mother != 0) {
/* 100 */         addNode(node, mother);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<ZestPersonNode> getNodes()
/*     */   {
/* 109 */     return this.nodes;
/*     */   }
/*     */   
/*     */   public void readFromH2(int i) {}
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\ZestPersonNodeModelContentProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */