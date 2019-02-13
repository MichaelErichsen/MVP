///*     */ package net.myerichsen.hremvp.parts;
//
///*     */ import javax.annotation.PostConstruct;
///*     */ import javax.annotation.PreDestroy;
///*     */ import javax.inject.Inject;
//
///*     */ import org.eclipse.e4.core.di.annotations.Optional;
///*     */ import org.eclipse.e4.ui.di.Focus;
///*     */ import org.eclipse.e4.ui.di.UIEventTopic;
///*     */ import org.eclipse.jface.viewers.ArrayContentProvider;
///*     */ import org.eclipse.jface.viewers.ColumnLabelProvider;
///*     */ import org.eclipse.jface.viewers.TableViewer;
///*     */ import org.eclipse.jface.viewers.TableViewerColumn;
///*     */ import org.eclipse.swt.layout.GridData;
///*     */ import org.eclipse.swt.layout.GridLayout;
///*     */ import org.eclipse.swt.widgets.Composite;
///*     */ import org.eclipse.swt.widgets.Shell;
///*     */ import org.eclipse.swt.widgets.Table;
///*     */ import org.eclipse.swt.widgets.TableColumn;
///*     */ import org.historyresearchenvironment.usergui.models.FamilySearchModel;
///*     */ import org.historyresearchenvironment.usergui.providers.FamilySearchProvider;
//
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
///*     */ public class FamilySearchExplorer/*     */ extends AbstractHREGuiPart
///*     */ {
//	/*     */ private Table tableFSSearch;
//	/*     */ private TableViewer tableViewerFSSearch;
//	/*     */ private Shell shell;
//	/*     */ private FamilySearchProvider model;
//
//	/*     */
//	/*     */ protected void callBusinessLayer(int i) {
//	}
//
//	/*     */
//	/*     */ @PostConstruct
//	/*     */ public void createControls(Composite parent)
//	/*     */ {
//		 LOGGER.fine("Create Controls");
//		 this.shell = parent.getShell();
//		 parent.setLayout(new GridLayout(1, false));
//		/*     */
//		 this.tableViewerFSSearch = new TableViewer(parent, 67584);
//		/*     */
//		 TableViewerColumn tableViewerColumn = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		 TableColumn tblclmnName = tableViewerColumn.getColumn();
//		 tblclmnName.setWidth(200);
//		 tblclmnName.setText("Name");
//		 tableViewerColumn.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				 FamilySearchModel item = (FamilySearchModel) element;
//				 return item.getName();
//				/*     */ }
//			/*     */
//			 });
//		 TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		 TableColumn tblclmnGender = tableViewerColumn_5.getColumn();
//		 tblclmnGender.setWidth(75);
//		 tblclmnGender.setText("Gender");
//		 tableViewerColumn_5.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				 FamilySearchModel item = (FamilySearchModel) element;
//				 return item.getGender();
//				/*     */ }
//			/*     */
//			 });
//		 TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		 TableColumn tblclmnBirthDate = tableViewerColumn_1.getColumn();
//		 tblclmnBirthDate.setWidth(100);
//		 tblclmnBirthDate.setText("Birth Date");
//		 tableViewerColumn_1.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				 FamilySearchModel item = (FamilySearchModel) element;
//				 return item.getBirthDate();
//				/*     */ }
//			/*     */
//			 });
//		 TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		 TableColumn tblclmnBirthPlace = tableViewerColumn_2.getColumn();
//		 tblclmnBirthPlace.setWidth(200);
//		 tblclmnBirthPlace.setText("Birth Place");
//		 tableViewerColumn_2.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				 FamilySearchModel item = (FamilySearchModel) element;
//				 return item.getBirthPlace();
//				/*     */ }
//			/*     */
//			 });
//		 TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		 TableColumn tblclmnDeathDate = tableViewerColumn_3.getColumn();
//		 tblclmnDeathDate.setWidth(100);
//		 tblclmnDeathDate.setText("Death Date");
//		 tableViewerColumn_3.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				 FamilySearchModel item = (FamilySearchModel) element;
//				 return item.getDeathDate();
//				/*     */ }
//			/*     */
//			 });
//		 TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		 TableColumn tblclmnDeathPlace = tableViewerColumn_4.getColumn();
//		 tblclmnDeathPlace.setWidth(200);
//		 tblclmnDeathPlace.setText("Death Place");
//		 tableViewerColumn_4.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				 FamilySearchModel item = (FamilySearchModel) element;
//				 return item.getDeathPlace();
//				/*     */ }
//			/*     */
//			 });
//		 this.tableFSSearch = this.tableViewerFSSearch.getTable();
//		 this.tableFSSearch.setLayoutData(new GridData(4, 4, true, true, 1, 1));
//		 this.tableFSSearch.setLinesVisible(true);
//		 this.tableFSSearch.setHeaderVisible(true);
//		 this.tableFSSearch.setFont(getHreFont(parent));
//		/*     */
//		 this.tableViewerFSSearch.setContentProvider(ArrayContentProvider.getInstance());
//		/*     */
//		 this.model = new FamilySearchProvider(this.shell);
//		 this.tableViewerFSSearch.setInput(this.model.getItemList());
//		 this.tableViewerFSSearch.refresh();
//		/*     */ }
//
//	/*     */
//	/*     */
//	/*     */ @PreDestroy
//	/*     */ public void dispose() {
//	}
//
//	/*     */
//	/*     */ @Focus
//	/*     */ public void setFocus() {
//	}
//
//	/*     */
//	/*     */ @Inject
//	/*     */ @Optional
//	/*     */ private void subscribeNameUpdateTopic(@UIEventTopic("NAME_UPDATE_TOPIC") String perName)
//	/*     */ {
//		 LOGGER.fine("Subscribing to " + perName);
//		/*     */
//		 if ((this.tableFSSearch != null) && (this.tableViewerFSSearch != null)
//				&& (this.tableFSSearch.isVisible())) {
//			 updateGui();
//			/*     */ } else {
//			 LOGGER.fine("Not visible");
//			/*     */ }
//		/*     */ }
//
//	/*     */
//	/*     */ protected void updateGui()
//	/*     */ {
//		 LOGGER.fine("Updating GUI");
//		/*     */
//		 this.model = new FamilySearchProvider(this.shell);
//		 LOGGER.fine("Set input: Get item list");
//		 this.tableViewerFSSearch.setInput(this.model.getItemList());
//		 LOGGER.fine("Refresh");
//		 this.tableViewerFSSearch.refresh();
//		/*     */ }
//	/*     */ }
