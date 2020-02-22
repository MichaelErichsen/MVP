/*    */ package org.historyresearchenvironment.usergui.providers;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.historyresearchenvironment.usergui.models.ZestPersonConnection;
/*    */ import org.historyresearchenvironment.usergui.models.ZestPersonNode;
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
/*    */ public class ZestStaticPersonNodeModelContentProvider
/*    */ {
/*    */   private final List<ZestPersonConnection> connections;
/*    */   private final List<ZestPersonNode> nodes;
/*    */   
/*    */   public ZestStaticPersonNodeModelContentProvider()
/*    */   {
/* 24 */     this.nodes = new ArrayList();
/*    */     
/* 26 */     ZestPersonNode node = new ZestPersonNode(0, "Michael\r\nErichsen", "M");
/* 27 */     this.nodes.add(node);
/* 28 */     node = new ZestPersonNode(1, "Leif\r\nErichsen", "M");
/* 29 */     this.nodes.add(node);
/* 30 */     node = new ZestPersonNode(2, "Ingeborg Alexia\r\nJensen Thorsager", "F");
/* 31 */     this.nodes.add(node);
/* 32 */     node = new ZestPersonNode(3, "Villiam Henry\r\nErichsen", "M");
/* 33 */     this.nodes.add(node);
/* 34 */     node = new ZestPersonNode(4, "Anna Marie Boline\r\nChristiansen", "F");
/* 35 */     this.nodes.add(node);
/* 36 */     node = new ZestPersonNode(5, "Hugo Bernhard\r\nJensen Thorsager", "M");
/* 37 */     this.nodes.add(node);
/* 38 */     node = new ZestPersonNode(6, "Tassia\r\nItkis", "F");
/* 39 */     this.nodes.add(node);
/*    */     
/* 41 */     this.connections = new ArrayList();
/* 42 */     ZestPersonConnection connect = new ZestPersonConnection(1, "Father", (ZestPersonNode)this.nodes.get(0), (ZestPersonNode)this.nodes.get(1));
/* 43 */     this.connections.add(connect);
/* 44 */     connect = new ZestPersonConnection(2, "Mother", (ZestPersonNode)this.nodes.get(0), (ZestPersonNode)this.nodes.get(2));
/* 45 */     this.connections.add(connect);
/* 46 */     connect = new ZestPersonConnection(3, "Father", (ZestPersonNode)this.nodes.get(1), (ZestPersonNode)this.nodes.get(3));
/* 47 */     this.connections.add(connect);
/* 48 */     connect = new ZestPersonConnection(4, "Mother", (ZestPersonNode)this.nodes.get(1), (ZestPersonNode)this.nodes.get(4));
/* 49 */     this.connections.add(connect);
/* 50 */     connect = new ZestPersonConnection(5, "Father", (ZestPersonNode)this.nodes.get(2), (ZestPersonNode)this.nodes.get(5));
/* 51 */     this.connections.add(connect);
/* 52 */     connect = new ZestPersonConnection(6, "Mother", (ZestPersonNode)this.nodes.get(2), (ZestPersonNode)this.nodes.get(6));
/* 53 */     this.connections.add(connect);
/*    */     
/* 55 */     for (ZestPersonConnection connection : this.connections) {
/* 56 */       connection.getSource().getConnectedTo().add(connection.getDestination());
/*    */     }
/*    */   }
/*    */   
/*    */   public List<ZestPersonNode> getNodes() {
/* 61 */     return this.nodes;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\ZestStaticPersonNodeModelContentProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */