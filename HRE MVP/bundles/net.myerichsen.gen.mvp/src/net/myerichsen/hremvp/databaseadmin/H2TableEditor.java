package net.myerichsen.hremvp.databaseadmin;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.myerichsen.hremvp.Constants;
import net.myerichsen.hremvp.listeners.HexFocusListener;
import net.myerichsen.hremvp.listeners.HexVerifyListener;
import net.myerichsen.hremvp.listeners.IntegerListener;
import net.myerichsen.hremvp.listeners.LengthFocusListener;
import net.myerichsen.hremvp.listeners.NumericVerifyListener;
import net.myerichsen.hremvp.listeners.SmallIntListener;

/**
 * Dynamically create an editor with the fields in the database catalog
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 2. jan. 2019
 *
 */

public class H2TableEditor {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Inject
	private EPartService partService;
	@Inject
	private EModelService modelService;
	@Inject
	private MApplication application;

	@Inject
	private IEventBroker eventBroker;
	private String tableName;
	private int recordNum = 0;
	private H2TableProvider provider;
	private List<Object> row;
	private List<H2TableModel> columns;
	private final List<Object> lineList = new ArrayList<>();
	private Composite compositeButtons;
	private int count;
	private ScrolledComposite scrolledComposite;

	/**
	 * Constructor
	 *
	 */
	public H2TableEditor() {
	}

	/**
	 * @param parent Shell
	 */
	private void createButtons(Composite parent) {
		// Only create buttons once
		if (compositeButtons != null) {
			return;
		}
		new Label(parent, SWT.NONE);

		compositeButtons = new Composite(parent, SWT.NONE);
		compositeButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button btnSelect = new Button(compositeButtons, SWT.NONE);
		btnSelect.addSelectionListener(new SelectionAdapter() {
			/**
			 * @param e Event
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Text text = (Text) lineList.get(0);
				recordNum = Integer.parseInt(text.getText());
				try {
					row = provider.select(recordNum);

					if (row.size() == 0) {
						eventBroker.post("MESSAGE", "Record " + recordNum + " does not exist");
						return;
					}

					for (int i = 0; i < lineList.size(); i++) {
						final Object lineObject = lineList.get(i);
						final String type = lineObject.getClass().getName();

						if (type.equals("org.eclipse.swt.widgets.Button")) {
							final Button line = (Button) lineObject;
							line.setSelection((boolean) row.get(i));
						} else if (type.equals("org.eclipse.swt.widgets.Text")) {
							final Text line = (Text) lineObject;
							line.setText(row.get(i).toString());
						} else if (type.equals("org.eclipse.swt.widgets.Composite")) {
							final Timestamp timeStamp = (Timestamp) row.get(i);
							final Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(timeStamp.getTime());

							final Composite dateTime = (Composite) lineObject;
							final Control[] children = dateTime.getChildren();

							final DateTime date = (DateTime) children[0];
							date.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
									calendar.get(Calendar.DATE));
							final DateTime time = (DateTime) children[1];
							time.setTime(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
									calendar.get(Calendar.SECOND));
						} else {
							LOGGER.info("Unimplemented type: " + type);
							System.exit(16);
						}
					}
					eventBroker.post("MESSAGE", "Record " + recordNum + " has been selected");
				} catch (final SQLException e2) {
					e2.printStackTrace();
					eventBroker.post("MESSAGE", e2.getMessage());
				}

				btnSelect.getParent().redraw();
			}
		});
		btnSelect.setText("Select");

		final Button btnInsert = new Button(compositeButtons, SWT.NONE);
		btnInsert.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events
			 * .SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Text insertText = (Text) lineList.get(0);
				final int insertRecordNum = Integer.parseInt(insertText.getText());

				for (int i = 0; i < lineList.size(); i++) {
					final Object lineObject = lineList.get(i);
					final String type = lineObject.getClass().getName();

					if (type.equals("org.eclipse.swt.widgets.Button")) {
						final Button line = (Button) lineObject;
						final Button checkButton = (Button) lineList.get(i);
						line.setSelection(checkButton.getSelection());
						columns.get(i).setValue(checkButton.getSelection());
					} else if (type.equals("org.eclipse.swt.widgets.Text")) {
						final Text line = (Text) lineObject;
						final Text text = (Text) lineList.get(i);
						line.setText(text.getText());
						columns.get(i).setValue(text.getText());
					} else if (type.equals("org.eclipse.swt.widgets.Composite")) {
						final Composite dateTime = (Composite) lineObject;
						final Control[] children = dateTime.getChildren();
						final DateTime date = (DateTime) children[0];
						final DateTime time = (DateTime) children[1];

						@SuppressWarnings("deprecation")
						final Timestamp timeStamp = new Timestamp(date.getYear() - 1900, date.getMonth(), date.getDay(),
								time.getHours(), time.getMinutes(), time.getSeconds(), 0);
						columns.get(i).setValue(timeStamp);
					} else {
						LOGGER.info("Unimplemented type: " + type);
						System.exit(16);
					}
				}

				try {
					provider.insert(columns);
					eventBroker.post(Constants.DATABASE_UPDATE_TOPIC, "Dummy");
					eventBroker.post(Constants.TABLENAME_UPDATE_TOPIC, tableName);
					eventBroker.post("MESSAGE", "Record " + insertRecordNum + " has been inserted");
				} catch (final SQLException e1) {
					e1.printStackTrace();
					eventBroker.post("MESSAGE", e1.getMessage());
				}
			}
		});
		btnInsert.setText("Insert");

		final Button btnUpdate = new Button(compositeButtons, SWT.NONE);
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events
			 * .SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Text updateText = (Text) lineList.get(0);
				final int updateRecordNum = Integer.parseInt(updateText.getText());

				for (int i = 0; i < lineList.size(); i++) {
					final Object lineObject = lineList.get(i);
					final String type = lineObject.getClass().getName();

					if (type.equals("org.eclipse.swt.widgets.Button")) {
						final Button line = (Button) lineObject;
						final Button checkButton = (Button) lineList.get(i);
						line.setSelection(checkButton.getSelection());
						columns.get(i).setValue(checkButton.getSelection());
					} else if (type.equals("org.eclipse.swt.widgets.Text")) {
						final Text line = (Text) lineObject;
						final Text text = (Text) lineList.get(i);
						line.setText(text.getText());
						columns.get(i).setValue(text.getText());
					} else if (type.equals("org.eclipse.swt.widgets.Composite")) {
						final Composite dateTime = (Composite) lineObject;
						final Control[] children = dateTime.getChildren();
						final DateTime date = (DateTime) children[0];
						final DateTime time = (DateTime) children[1];

						@SuppressWarnings("deprecation")
						final Timestamp timeStamp = new Timestamp(date.getYear() - 1900, date.getMonth(), date.getDay(),
								time.getHours(), time.getMinutes(), time.getSeconds(), 0);
						columns.get(i).setValue(timeStamp);
					} else {
						LOGGER.info("Unimplemented type: " + type);
						System.exit(16);
					}
				}

				try {
					provider.update(columns);
					eventBroker.post(Constants.DATABASE_UPDATE_TOPIC, "Dummy");
					eventBroker.post(Constants.TABLENAME_UPDATE_TOPIC, tableName);
					eventBroker.post("MESSAGE", "Record " + updateRecordNum + " has been updated");
				} catch (final SQLException e1) {
					e1.printStackTrace();
					eventBroker.post("MESSAGE", e1.getMessage());
				}
			}
		});
		btnUpdate.setText("Update");

		final Button btnDelete = new Button(compositeButtons, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events
			 * .SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				delete();
			}
		});
		btnDelete.setText("Delete");

		final Button btnResetDialog = new Button(compositeButtons, SWT.NONE);
		btnResetDialog.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events
			 * .SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (final Object lineObject : lineList) {
					final String type = lineObject.getClass().getName();

					if (type.equals("org.eclipse.swt.widgets.Button")) {
						final Button line = (Button) lineObject;
						line.setSelection(Boolean.FALSE);
					} else if (type.equals("org.eclipse.swt.widgets.Text")) {
						final Text line = (Text) lineObject;
						line.setText("0");
					} else if (type.equals("org.eclipse.swt.widgets.Composite")) {
						final Composite dateTime = (Composite) lineObject;
						final Control[] children = dateTime.getChildren();
						final DateTime date = (DateTime) children[0];
						date.setDate(2000, 1, 1);
						final DateTime time = (DateTime) children[1];
						time.setTime(0, 0, 0);
					} else {
						LOGGER.info("Unimplemented type: " + type);
						System.exit(16);
					}
				}
			}
		});
		btnResetDialog.setText("Reset fields");

		final Button btnClose = new Button(compositeButtons, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
				final MPart part = (MPart) stacks.get(stacks.size() - 1).getSelectedElement();
				partService.hidePart(part, true);
			}
		});
		btnClose.setText("Close");

	}

	/**
	 * Create contents of the view part.
	 *
	 * @param parent Shell
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.V_SCROLL);
		final GridData gd_scrolledComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		try {
			createLines();
		} catch (final SQLException e) {
			e.printStackTrace();
			eventBroker.post("MESSAGE", e.getMessage());
		}
		createButtons(parent);
		new Label(parent, SWT.NONE);
	}

	/**
	 * @param compositeFields
	 * @param i
	 * @return
	 */
	private Text createFieldLine(Composite compositeFields, int i) {
		final Label label = new Label(compositeFields, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		label.setText(columns.get(i).getName());

		final Label label2 = new Label(compositeFields, SWT.NONE);
		label2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		final int scale = columns.get(i).getScale();

		if (scale > 0) {
			label2.setText(columns.get(i).getType() + "(" + scale + ")");
		} else {
			label2.setText(columns.get(i).getType());
		}

		final Text text = new Text(compositeFields, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		return text;
	}

	/**
	 * @param scrolledComposite
	 * @throws SQLException When failing
	 */
	private void createLines() throws SQLException {
		Text text;
		Label label2;

		if ((tableName == null) || (tableName == "")) {
			return;
		}

		provider = new H2TableProvider(tableName);
		count = provider.getCount();
		columns = provider.getModelList();
		row = provider.select(recordNum);

		final Composite compositeFields = new Composite(scrolledComposite, SWT.NONE);
		compositeFields.setLayout(new GridLayout(3, false));

		lineList.clear();

		for (int i = 0; i < count; i++) {
			switch (columns.get(i).getNumericType()) {
			case Constants.BIGINT:
				text = createFieldLine(compositeFields, i);
				text.addVerifyListener(new NumericVerifyListener());
				columns.get(i).setValue(row.get(i));
				text.setText(Long.toString((Long) row.get(i)));
				lineList.add(text);
				break;
			case Constants.BLOB:
				text = createFieldLine(compositeFields, i);
				text.addVerifyListener(new HexVerifyListener());
				text.addFocusListener(new HexFocusListener());
				columns.get(i).setValue(row.get(i));
				final Blob blob = (Blob) row.get(i);

				try {
					final byte[] ba = blob.getBytes(1L, (int) blob.length());
					final String s = new String(DatatypeConverter.printHexBinary(ba));
					text.setText(s);
				} catch (final SQLException e) {
					eventBroker.post("MESSAGE", e.getMessage());
				}
				lineList.add(text);
				break;
			case Constants.BOOLEAN:
				new Label(compositeFields, SWT.NONE);
				label2 = new Label(compositeFields, SWT.NONE);
				label2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
				label2.setText(columns.get(i).getType());

				final Button checkButton = new Button(compositeFields, SWT.CHECK);
				checkButton.setText(columns.get(i).getName());

				if (columns.get(i).getValue() == null) {
					checkButton.setSelection(false);
				} else if (columns.get(i).getValue().equals("TRUE")) {
					checkButton.setSelection(true);
				} else {
					checkButton.setSelection(false);
				}

				columns.get(i).setValue(checkButton.getSelection());
				lineList.add(checkButton);
				break;
			case Constants.CHAR:
				text = createFieldLine(compositeFields, i);
				text.addFocusListener(new LengthFocusListener(columns.get(i).getScale()));
				text.setToolTipText("Input longer than " + columns.get(i).getScale() + " will be truncated");
				columns.get(i).setValue(row.get(i));
				text.setText((String) row.get(i));
				lineList.add(text);
				break;
			case Constants.CLOB:
				text = createFieldLine(compositeFields, i);
				columns.get(i).setValue(row.get(i));
				final Clob clob = (Clob) row.get(i);
				try {
					text.setText(new String(clob.getSubString(1L, (int) clob.length())));
				} catch (final SQLException e) {
					eventBroker.post("MESSAGE", e.getMessage());
				}
				lineList.add(text);
				break;
			case Constants.DATE:
				text = createFieldLine(compositeFields, i);
				columns.get(i).setValue(row.get(i));
				if (row.get(i) != null) {
					text.setText((String) row.get(i));
				}
				lineList.add(text);
				break;
			case Constants.DECIMAL:
				text = createFieldLine(compositeFields, i);
				columns.get(i).setValue(row.get(i));
				if (row.get(i) != null) {
					text.setText((String) row.get(i));
				}
				lineList.add(text);
				break;
			case Constants.DOUBLE:
				text = createFieldLine(compositeFields, i);
				text.addVerifyListener(new NumericVerifyListener());
				columns.get(i).setValue(row.get(i));
				text.setText(Double.toString((Double) row.get(i)));
				lineList.add(text);
				break;
			case Constants.INTEGER:
				text = createFieldLine(compositeFields, i);
				text.addListener(SWT.Verify, new IntegerListener());
				columns.get(i).setValue(row.get(i));
				text.setText(Integer.toString((Integer) row.get(i)));
				lineList.add(text);
				break;
			case Constants.SMALLINT:
				text = createFieldLine(compositeFields, i);
				text.addListener(SWT.Verify, new SmallIntListener());
				columns.get(i).setValue(row.get(i));
				text.setText(Short.toString((Short) row.get(i)));
				lineList.add(text);
				break;
			case Constants.TIMESTAMP:
				final Label label = new Label(compositeFields, SWT.NONE);
				label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
				label.setText(columns.get(i).getName());

				label2 = new Label(compositeFields, SWT.NONE);
				label2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
				label2.setText(columns.get(i).getType());

				final Timestamp timeStamp = (Timestamp) row.get(i);
				final Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(timeStamp.getTime());

				final Composite dateTime = new Composite(compositeFields, SWT.NONE);
				dateTime.setLayout(new GridLayout(2, false));

				final DateTime date = new DateTime(dateTime, SWT.DATE);
				date.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
				final DateTime time = new DateTime(dateTime, SWT.TIME);
				time.setTime(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
				columns.get(i).setValue(row.get(i));
				lineList.add(dateTime);
				break;
			case Constants.VARBINARY:
				text = createFieldLine(compositeFields, i);
				text.addVerifyListener(new HexVerifyListener());
				text.addFocusListener(new HexFocusListener());
				text.addFocusListener(new LengthFocusListener(columns.get(i).getScale() * 2));
				text.setToolTipText("Use 0-9, a-f or A-F. Input longer than " + (columns.get(i).getScale() * 2)
						+ " will be truncated");
				columns.get(i).setValue(row.get(i));
				text.setText(new String(DatatypeConverter.printHexBinary((byte[]) row.get(i))));
				lineList.add(text);
				break;
			case Constants.VARCHAR:
				text = createFieldLine(compositeFields, i);
				text.addFocusListener(new LengthFocusListener(columns.get(i).getScale()));
				text.setToolTipText("Input longer than " + columns.get(i).getScale() + " will be truncated");
				columns.get(i).setValue(row.get(i));
				text.setText((String) row.get(i));
				lineList.add(text);
				break;
			default:
				LOGGER.info("Unimplemented type: " + columns.get(i).getType());
				System.exit(16);
				break;
			}
		}
		scrolledComposite.setContent(compositeFields);
		scrolledComposite.setMinSize(compositeFields.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * @throws NumberFormatException Number is not valid
	 */
	public void delete() throws NumberFormatException {
		final Text text = (Text) lineList.get(0);
		recordNum = Integer.parseInt(text.getText());
		try {
			provider.delete(recordNum);
			eventBroker.post(Constants.DATABASE_UPDATE_TOPIC, "Dummy");
			eventBroker.post(Constants.TABLENAME_UPDATE_TOPIC, tableName);
			eventBroker.post("MESSAGE", "Record " + recordNum + " has been deleted");

		} catch (final SQLException e1) {
			e1.printStackTrace();
			eventBroker.post("MESSAGE", e1.getMessage());
		}
	}

	/**
	 *
	 */
	@PreDestroy
	public void dispose() {
	}

	/**
	 *
	 */
	@Focus
	public void setFocus() {
	}

	/**
	 * @param tableName Name of the table
	 */
	@Inject
	@Optional
	private void subscribeNameUpdateTopic(
			@UIEventTopic(Constants.TABLENAME_UPDATE_TOPIC) String tableName2) {
		tableName = tableName2;
		final List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
		final MPart part = (MPart) stacks.get(stacks.size() - 2).getSelectedElement();
		part.setLabel(tableName2);
	}

	/**
	 * @param recordNumString
	 * @throws SQLException When failing
	 */
	@Inject
	@Optional
	private void subscribeRecordNumUpdateTopic(
			@UIEventTopic(Constants.RECORDNUM_UPDATE_TOPIC) String recordNumString)
			throws SQLException {
		recordNum = Integer.parseInt(recordNumString);
		createLines();
	}
}