///*     */ package net.myerichsen.hremvp.person.providers;
//
///*     */ import java.sql.SQLException;
//
///*     */ import org.historyresearchenvironment.usergui.models.AncestorModel;
///*     */ import org.historyresearchenvironment.usergui.models.AncestorVector;
//
///*     */
///*     */
///*     */
///*     */ public class AncestorTreeProvider/*     */ extends AbstractHreProvider
///*     */ {
//	/*     */ protected AncestorVector ancestorItems;
//	 protected int perNo = 1;
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public AncestorTreeProvider() {
//	}
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public AncestorTreeProvider(int perNo)
//	/*     */ {
//		 setKey(Integer.toString(perNo));
//		 this.ancestorItems = new AncestorVector(this.conn, perNo);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void addAncestor(AncestorModel item)
//	/*     */ {
//		 if (this.ancestorItems == null) {
//			 this.ancestorItems = new AncestorVector(this.conn, item.getPerNo());
//			/*     */ }
//		 this.ancestorItems.add(item);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void addAncestor(int perNo, String name, String sex, int father, int mother)
//	/*     */ {
//		 AncestorModel di = new AncestorModel(perNo, name, sex, father, mother);
//		/*     */
//		 if (this.ancestorItems == null) {
//			 this.ancestorItems = new AncestorVector(this.conn, perNo);
//			/*     */ }
//		 this.ancestorItems.add(di);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public AncestorVector getAncestorItems()
//	/*     */ {
//		 return this.ancestorItems;
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public int getPerNo()
//	/*     */ {
//		 return this.perNo;
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void readFromH2(int i)
//	/*     */ {
//		 this.ancestorItems = new AncestorVector(this.conn, this.perNo);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void setAncestorItems(AncestorVector AncestorItems)
//	/*     */ {
//		 this.ancestorItems = AncestorItems;
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void setPerNo(int perNo)
//	/*     */ {
//		 this.perNo = perNo;
//		 setKey(Integer.toString(perNo));
//		 this.ancestorItems = new AncestorVector(this.conn, perNo);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void dispose()
//	/*     */ {
//		/*     */ try
//		/*     */ {
//			 this.conn.close();
//			/*     */ } catch (SQLException e) {
//			 LOGGER
//					.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
//			/*     */ }
//		/*     */ }
//	/*     */ }
