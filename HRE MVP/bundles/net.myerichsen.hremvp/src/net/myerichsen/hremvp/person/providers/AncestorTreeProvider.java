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
//	/* 15 */ protected int perNo = 1;
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
//		/* 29 */ setKey(Integer.toString(perNo));
//		/* 30 */ this.ancestorItems = new AncestorVector(this.conn, perNo);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void addAncestor(AncestorModel item)
//	/*     */ {
//		/* 37 */ if (this.ancestorItems == null) {
//			/* 38 */ this.ancestorItems = new AncestorVector(this.conn, item.getPerNo());
//			/*     */ }
//		/* 40 */ this.ancestorItems.add(item);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void addAncestor(int perNo, String name, String sex, int father, int mother)
//	/*     */ {
//		/* 48 */ AncestorModel di = new AncestorModel(perNo, name, sex, father, mother);
//		/*     */
//		/* 50 */ if (this.ancestorItems == null) {
//			/* 51 */ this.ancestorItems = new AncestorVector(this.conn, perNo);
//			/*     */ }
//		/* 53 */ this.ancestorItems.add(di);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public AncestorVector getAncestorItems()
//	/*     */ {
//		/* 60 */ return this.ancestorItems;
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public int getPerNo()
//	/*     */ {
//		/* 67 */ return this.perNo;
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
//		/* 79 */ this.ancestorItems = new AncestorVector(this.conn, this.perNo);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void setAncestorItems(AncestorVector AncestorItems)
//	/*     */ {
//		/* 87 */ this.ancestorItems = AncestorItems;
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void setPerNo(int perNo)
//	/*     */ {
//		/* 95 */ this.perNo = perNo;
//		/* 96 */ setKey(Integer.toString(perNo));
//		/* 97 */ this.ancestorItems = new AncestorVector(this.conn, perNo);
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */
//	/*     */ public void dispose()
//	/*     */ {
//		/*     */ try
//		/*     */ {
//			/* 106 */ this.conn.close();
//			/*     */ } catch (SQLException e) {
//			/* 108 */ LOGGER
//					.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
//			/*     */ }
//		/*     */ }
//	/*     */ }
