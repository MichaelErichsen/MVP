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
//		/* 45 */ LOGGER.fine("Create Controls");
//		/* 46 */ this.shell = parent.getShell();
//		/* 47 */ parent.setLayout(new GridLayout(1, false));
//		/*     */
//		/* 49 */ this.tableViewerFSSearch = new TableViewer(parent, 67584);
//		/*     */
//		/* 51 */ TableViewerColumn tableViewerColumn = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		/* 52 */ TableColumn tblclmnName = tableViewerColumn.getColumn();
//		/* 53 */ tblclmnName.setWidth(200);
//		/* 54 */ tblclmnName.setText("Name");
//		/* 55 */ tableViewerColumn.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				/* 58 */ FamilySearchModel item = (FamilySearchModel) element;
//				/* 59 */ return item.getName();
//				/*     */ }
//			/*     */
//			/* 62 */ });
//		/* 63 */ TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		/* 64 */ TableColumn tblclmnGender = tableViewerColumn_5.getColumn();
//		/* 65 */ tblclmnGender.setWidth(75);
//		/* 66 */ tblclmnGender.setText("Gender");
//		/* 67 */ tableViewerColumn_5.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				/* 70 */ FamilySearchModel item = (FamilySearchModel) element;
//				/* 71 */ return item.getGender();
//				/*     */ }
//			/*     */
//			/* 74 */ });
//		/* 75 */ TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		/* 76 */ TableColumn tblclmnBirthDate = tableViewerColumn_1.getColumn();
//		/* 77 */ tblclmnBirthDate.setWidth(100);
//		/* 78 */ tblclmnBirthDate.setText("Birth Date");
//		/* 79 */ tableViewerColumn_1.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				/* 82 */ FamilySearchModel item = (FamilySearchModel) element;
//				/* 83 */ return item.getBirthDate();
//				/*     */ }
//			/*     */
//			/* 86 */ });
//		/* 87 */ TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		/* 88 */ TableColumn tblclmnBirthPlace = tableViewerColumn_2.getColumn();
//		/* 89 */ tblclmnBirthPlace.setWidth(200);
//		/* 90 */ tblclmnBirthPlace.setText("Birth Place");
//		/* 91 */ tableViewerColumn_2.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				/* 94 */ FamilySearchModel item = (FamilySearchModel) element;
//				/* 95 */ return item.getBirthPlace();
//				/*     */ }
//			/*     */
//			/* 98 */ });
//		/* 99 */ TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		/* 100 */ TableColumn tblclmnDeathDate = tableViewerColumn_3.getColumn();
//		/* 101 */ tblclmnDeathDate.setWidth(100);
//		/* 102 */ tblclmnDeathDate.setText("Death Date");
//		/* 103 */ tableViewerColumn_3.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				/* 106 */ FamilySearchModel item = (FamilySearchModel) element;
//				/* 107 */ return item.getDeathDate();
//				/*     */ }
//			/*     */
//			/* 110 */ });
//		/* 111 */ TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(this.tableViewerFSSearch, 0);
//		/* 112 */ TableColumn tblclmnDeathPlace = tableViewerColumn_4.getColumn();
//		/* 113 */ tblclmnDeathPlace.setWidth(200);
//		/* 114 */ tblclmnDeathPlace.setText("Death Place");
//		/* 115 */ tableViewerColumn_4.setLabelProvider(new HREColumnLabelProvider()
//		/*     */ {
//			/*     */ public String getText(Object element) {
//				/* 118 */ FamilySearchModel item = (FamilySearchModel) element;
//				/* 119 */ return item.getDeathPlace();
//				/*     */ }
//			/*     */
//			/* 122 */ });
//		/* 123 */ this.tableFSSearch = this.tableViewerFSSearch.getTable();
//		/* 124 */ this.tableFSSearch.setLayoutData(new GridData(4, 4, true, true, 1, 1));
//		/* 125 */ this.tableFSSearch.setLinesVisible(true);
//		/* 126 */ this.tableFSSearch.setHeaderVisible(true);
//		/* 127 */ this.tableFSSearch.setFont(getHreFont(parent));
//		/*     */
//		/* 129 */ this.tableViewerFSSearch.setContentProvider(ArrayContentProvider.getInstance());
//		/*     */
//		/* 131 */ this.model = new FamilySearchProvider(this.shell);
//		/* 132 */ this.tableViewerFSSearch.setInput(this.model.getItemList());
//		/* 133 */ this.tableViewerFSSearch.refresh();
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
//		/* 147 */ LOGGER.fine("Subscribing to " + perName);
//		/*     */
//		/* 149 */ if ((this.tableFSSearch != null) && (this.tableViewerFSSearch != null)
//				&& (this.tableFSSearch.isVisible())) {
//			/* 150 */ updateGui();
//			/*     */ } else {
//			/* 152 */ LOGGER.fine("Not visible");
//			/*     */ }
//		/*     */ }
//
//	/*     */
//	/*     */ protected void updateGui()
//	/*     */ {
//		/* 158 */ LOGGER.fine("Updating GUI");
//		/*     */
//		/* 160 */ this.model = new FamilySearchProvider(this.shell);
//		/* 161 */ LOGGER.fine("Set input: Get item list");
//		/* 162 */ this.tableViewerFSSearch.setInput(this.model.getItemList());
//		/* 163 */ LOGGER.fine("Refresh");
//		/* 164 */ this.tableViewerFSSearch.refresh();
//		/*     */ }
//	/*     */ }
