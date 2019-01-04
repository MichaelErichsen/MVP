package net.myerichsen.hremvp.dialogs;

import java.time.LocalDate;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.hypotemoose.cal.date.GregorianCalendar;
import com.hypotemoose.cal.date.HebrewCalendar;
import com.hypotemoose.cal.date.IndianCivilCalendar;
import com.hypotemoose.cal.date.IslamicCalendar;
import com.hypotemoose.cal.date.JulianCalendar;
import com.hypotemoose.cal.util.AlmanacConverter;

import net.myerichsen.hremvp.listeners.DateVerifyListener;
import net.myerichsen.hremvp.providers.HDateProvider;

/**
 * Dialog to create a date in several formats
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 21. nov. 2018
 *
 */
public class DateDialog extends TitleAreaDialog {
	/**
	 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
	 * @version 15. nov. 2018
	 *
	 */
	private class ArabicFocusListener extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent e) {
			int negative = 1;
			String s = textArabic.getText().trim();

			if (s.matches("[+-]?\\d{1,4}\\-\\d{1,2}\\-\\d{1,2}")) {
				if (s.startsWith("-")) {
					s = s.substring(1);
					negative = -1;
				}

				String[] sa = s.split("\\-");
				int year = Integer.parseInt(sa[0]) * negative;
				int month = Integer.parseInt(sa[1]);
				int day = Integer.parseInt(sa[2]);

				IslamicCalendar calendar = new IslamicCalendar(year, month, day);
				GregorianCalendar gc = AlmanacConverter.toGregorianCalendar(calendar);
				year = gc.getYear();
				month = gc.getMonth();
				day = gc.getDay();

				setStoredFormat(year, month, day);
				setGregorian(year, month, day);
				setGregorianOutput(year, month, day);
				setJulian(year, month, day);
				setArabic(year, month, day);
				setHebrew(year, month, day);
				setIndian(year, month, day);
				textJulian.setText("");
				textJewish.setText("");
				textIndian.setText("");
			} else {
				textArabic.setFocus();
				eventBroker.post("MESSAGE", "Invalid date format");
			}
		}
	}

	/**
	 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
	 * @version 7. nov. 2018
	 *
	 */
	private class IndianFocusListener extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent e) {
			int negative = 1;
			String s = textIndian.getText().trim();

			if (s.matches("[+-]?\\d{1,4}\\-\\d{1,2}\\-\\d{1,2}")) {
				if (s.startsWith("-")) {
					s = s.substring(1);
					negative = -1;
				}

				String[] sa = s.split("\\-");
				int year = Integer.parseInt(sa[0]) * negative;
				int month = Integer.parseInt(sa[1]);
				int day = Integer.parseInt(sa[2]);

				IndianCivilCalendar calendar = new IndianCivilCalendar(year, month, day);
				GregorianCalendar gc = AlmanacConverter.toGregorianCalendar(calendar);
				year = gc.getYear();
				month = gc.getMonth();
				day = gc.getDay();

				setStoredFormat(year, month, day);
				setGregorian(year, month, day);
				setGregorianOutput(year, month, day);
				setJulian(year, month, day);
				setArabic(year, month, day);
				setHebrew(year, month, day);
				setIndian(year, month, day);
				textJulian.setText("");
				textArabic.setText("");
				textJewish.setText("");
			} else {
				textIndian.setFocus();
				eventBroker.post("MESSAGE", "Invalid date format");
			}
		}
	}

	/**
	 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
	 * @version 7. nov. 2018
	 *
	 */
	private class JewishFocusListener extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent e) {
			int negative = 1;
			String s = textJewish.getText().trim();

			if (s.matches("[+-]?\\d{1,4}\\-\\d{1,2}\\-\\d{1,2}")) {
				if (s.startsWith("-")) {
					s = s.substring(1);
					negative = -1;
				}

				String[] sa = s.split("\\-");
				int year = Integer.parseInt(sa[0]) * negative;
				int month = Integer.parseInt(sa[1]);
				int day = Integer.parseInt(sa[2]);

				HebrewCalendar calendar = new HebrewCalendar(year, month, day);
				GregorianCalendar gc = AlmanacConverter.toGregorianCalendar(calendar);
				year = gc.getYear();
				month = gc.getMonth();
				day = gc.getDay();

				setStoredFormat(year, month, day);
				setGregorian(year, month, day);
				setGregorianOutput(year, month, day);
				setJulian(year, month, day);
				setArabic(year, month, day);
				setHebrew(year, month, day);
				setIndian(year, month, day);
				textJulian.setText("");
				textArabic.setText("");
				textIndian.setText("");
			} else {
				textJewish.setFocus();
				eventBroker.post("MESSAGE", "Invalid date format");
			}
		}
	}

	/**
	 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
	 * @version 7. nov. 2018
	 *
	 */
	private class JulianFocusListener extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent e) {
			int negative = 1;
			String s = textJulian.getText().trim();

			if (s.matches("[+-]?\\d{1,4}\\-\\d{1,2}\\-\\d{1,2}")) {
				if (s.startsWith("-")) {
					s = s.substring(1);
					negative = -1;
				}

				String[] sa = s.split("\\-");
				int year = Integer.parseInt(sa[0]) * negative;
				int month = Integer.parseInt(sa[1]);
				int day = Integer.parseInt(sa[2]);

				JulianCalendar calendar = new JulianCalendar(year, month, day);
				GregorianCalendar gc = AlmanacConverter.toGregorianCalendar(calendar);
				year = gc.getYear();
				month = gc.getMonth();
				day = gc.getDay();

				setStoredFormat(year, month, day);
				setGregorian(year, month, day);
				setGregorianOutput(year, month, day);
				setJulian(year, month, day);
				setArabic(year, month, day);
				setHebrew(year, month, day);
				setIndian(year, month, day);
				textArabic.setText("");
				textJewish.setText("");
				textIndian.setText("");
			} else {
				textJewish.setFocus();
				eventBroker.post("MESSAGE", "Invalid date format");
			}
		}
	}

	/**
	 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
	 * @version 7. nov. 2018
	 *
	 */
	private class StoredFocusListener extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent e) {
			int negative = 1;

			String s = textStoredFormat.getText().trim();
			if (s.matches("[+-]?\\d{1,9}-\\d{1,2}-\\d{1,2}")) {
				if (s.startsWith("-")) {
					s = s.substring(1);
					negative = -1;
				}
				String[] sa = s.split("\\-");
				int year = Integer.parseInt(sa[0]) * negative;
				int month = Integer.parseInt(sa[1]);
				int day = Integer.parseInt(sa[2]);

				setGregorian(year, month, day);
				setGregorianOutput(year, month, day);
				setJulian(year, month, day);
				setArabic(year, month, day);
				setHebrew(year, month, day);
				setIndian(year, month, day);
				textJulian.setText("");
				textArabic.setText("");
				textJewish.setText("");
				textIndian.setText("");
				setLocalDate(LocalDate.of(year, month, day));
			} else {
				textStoredFormat.setFocus();
				eventBroker.post("MESSAGE", "Invalid date format");
			}
		}
	}

	// private final static Logger LOGGER =
	// Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private IEventBroker eventBroker;
	private Text textStoredFormat;
	private Text textJulian;
	private Text textJewish;
	private Text textArabic;
	private Text textIndian;
	private Text textOriginal;
	private DateTime dateTimeGregorian;
	private Text textGregorianOutput;
	private Text textJulianOutput;
	private Text textJewishOutput;
	private Text textArabicOutput;
	private Text textIndianOutput;
	private LocalDate localDate;
	private Text textSortDate;
	private Text textSurety;
	private String surety = "";
	private LocalDate sortDate;
	private String original = "";
	private int hDatePid = 0;

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 * @param context
	 * @wbp.parser.constructor
	 */
	public DateDialog(Shell parentShell, IEclipseContext context) {
		super(parentShell);
		eventBroker = context.get(IEventBroker.class);
	}

	/**
	 * Constructor
	 *
	 * @param shell
	 * @param context
	 * @param hdatePid
	 */
	public DateDialog(Shell parentShell, IEclipseContext context, int hdatePid) {
		super(parentShell);
		eventBroker = context.get(IEventBroker.class);
		this.hDatePid = hdatePid;
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Historical Dates");
		setTitle("Date");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		GridData gd_container = new GridData(GridData.FILL_BOTH);
		gd_container.grabExcessHorizontalSpace = false;
		container.setLayoutData(gd_container);

		Label lblOriginalText = new Label(container, SWT.NONE);
		lblOriginalText.setText("Original Text");

		textOriginal = new Text(container, SWT.BORDER);
		textOriginal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				setOriginal(textOriginal.getText());
			}
		});
		textOriginal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("Stored format");

		textStoredFormat = new Text(container, SWT.BORDER);
		textStoredFormat.addVerifyListener(new DateVerifyListener());
		textStoredFormat.addFocusListener(new StoredFocusListener());
		textStoredFormat.setToolTipText("Format: [+-]999999999-01-01");
		textStoredFormat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblSortDate = new Label(container, SWT.NONE);
		lblSortDate.setText("Sort Date");

		textSortDate = new Text(container, SWT.BORDER);
		textSortDate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int negative = 1;

				String s = textSortDate.getText().trim();
				if (s.matches("[+-]?\\d{1,9}-\\d{1,2}-\\d{1,2}")) {
					if (s.startsWith("-")) {
						s = s.substring(1);
						negative = -1;
					}
					String[] sa = s.split("\\-");
					int year = Integer.parseInt(sa[0]) * negative;
					int month = Integer.parseInt(sa[1]);
					int day = Integer.parseInt(sa[2]);

					sortDate = LocalDate.of(year, month, day);
				} else {
					textSortDate.setFocus();
					eventBroker.post("MESSAGE", "Invalid date format");
				}
			}

		});
		textSortDate.addVerifyListener(new DateVerifyListener());
		textSortDate.setToolTipText("Format: [+-]999999999-01-01");
		textSortDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblSurety = new Label(container, SWT.NONE);
		lblSurety.setText("Surety");

		textSurety = new Text(container, SWT.BORDER);
		textSurety.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				setSurety(textSurety.getText());
			}
		});
		textSurety.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				final Text text = (Text) e.getSource();
				if (text.getText().length() > 10) {
					e.doit = false;
					eventBroker.post("MESSAGE", "Surety max length 10");
				} else {
					e.doit = true;
				}
			}
		});
		textSurety.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblGregorian = new Label(container, SWT.NONE);
		lblGregorian.setText("Gregorian");

		dateTimeGregorian = new DateTime(container, SWT.BORDER | SWT.CALENDAR);
		dateTimeGregorian.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int year = dateTimeGregorian.getYear();
				int month = dateTimeGregorian.getMonth() + 1;
				int day = dateTimeGregorian.getDay();

				setStoredFormat(year, month, day);
				setGregorianOutput(year, month, day);
				setJulian(year, month, day);
				setArabic(year, month, day);
				setHebrew(year, month, day);
				setIndian(year, month, day);
				textJulian.setText("");
				textArabic.setText("");
				textJewish.setText("");
				textIndian.setText("");
			}
		});

		textGregorianOutput = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		textGregorianOutput.setEditable(false);
		textGregorianOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Label lblJulian = new Label(container, SWT.NONE);
		lblJulian.setText("Julian");

		textJulian = new Text(container, SWT.BORDER);
		textJulian.setToolTipText("Format: [+-]9999-01-01");
		textJulian.addFocusListener(new JulianFocusListener());
		textJulian.addVerifyListener(new DateVerifyListener());
		textJulian.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textJulianOutput = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		textJulianOutput.setEditable(false);
		textJulianOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setText("Jewish");

		textJewish = new Text(container, SWT.BORDER);
		textJewish.addFocusListener(new JewishFocusListener());
		textJewish.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textJewishOutput = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		textJewishOutput.setEditable(false);
		textJewishOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblArabic = new Label(container, SWT.NONE);
		lblArabic.setText("Arabic");

		textArabic = new Text(container, SWT.BORDER);
		textArabic.addFocusListener(new ArabicFocusListener());
		textArabic.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textArabicOutput = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		textArabicOutput.setEditable(false);
		textArabicOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblIndiansaka = new Label(container, SWT.NONE);
		lblIndiansaka.setText("Indian");

		textIndian = new Text(container, SWT.BORDER);
		textIndian.addFocusListener(new IndianFocusListener());
		textIndian.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textIndianOutput = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		textIndianOutput.setEditable(false);
		textIndianOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		container.setTabList(new Control[] { textOriginal, textStoredFormat, dateTimeGregorian, textJulian, textJewish,
				textArabic, textIndian });

		if (hDatePid != 0) {
			try {
				HDateProvider hdp = new HDateProvider();
				hdp.get(hDatePid);
				getTextStoredFormat().setText(hdp.getDate().toString());
				getTextSortDate().setText(hdp.getSortDate().toString());
				getTextOriginal().setText(hdp.getOriginalText());
				getTextSurety().setText(hdp.getSurety());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return area;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(681, 517);
	}

	/**
	 * @return the localDate
	 */
	public LocalDate getLocalDate() {
		return localDate;
	}

	/**
	 * @return the original
	 */
	public String getOriginal() {
		return original;
	}

	/**
	 * @return the sortDate
	 */
	public LocalDate getSortDate() {
		if (sortDate == null) {
			sortDate = localDate;
		}
		return sortDate;
	}

	/**
	 * @return the surety
	 */
	public String getSurety() {
		return surety;
	}

	/**
	 * @return the textOriginal
	 */
	public Text getTextOriginal() {
		return textOriginal;
	}

	/**
	 * @return the textSortDate
	 */
	public Text getTextSortDate() {
		return textSortDate;
	}

	/**
	 * @return the textStoredFormat
	 */
	public Text getTextStoredFormat() {
		return textStoredFormat;
	}

	/**
	 * @return the textSurety
	 */
	public Text getTextSurety() {
		return textSurety;
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 */
	protected void setArabic(int year, int month, int day) {
		IslamicCalendar calendar = AlmanacConverter.toIslamicCalendar(new GregorianCalendar(year, month, day));
		String date = calendar.getDate();
		textArabicOutput.setText(date);
		if (textOriginal.getText().length() == 0) {
			textOriginal.setText(date);
			setOriginal(date);
		}
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 */
	protected void setGregorian(int year, int month, int day) {
		if (year > 1600) {
			dateTimeGregorian.setEnabled(true);
			dateTimeGregorian.setDate(year, month - 1, day);
		} else {
			dateTimeGregorian.setDate(1700, 1, 1);
			dateTimeGregorian.setEnabled(false);
		}

	}

	/**
	 *
	 * @param year
	 * @param month
	 * @param day
	 */
	protected void setGregorianOutput(int year, int month, int day) {
		if (year > 1600) {
			GregorianCalendar calendar = new GregorianCalendar(year, month, day);
			String date = calendar.getDate();
			textGregorianOutput.setText(date);
			if (textOriginal.getText().length() == 0) {
				textOriginal.setText(date);
				setOriginal(date);
			}
		} else {
			textGregorianOutput.setText("");
		}
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 */
	protected void setHebrew(int year, int month, int day) {
		HebrewCalendar calendar = AlmanacConverter.toHebrewCalendar(new GregorianCalendar(year, month, day));
		String date = calendar.getDate();
		textJewishOutput.setText(date);
		if (textOriginal.getText().length() == 0) {
			textOriginal.setText(date);
			setOriginal(date);
		}
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 */
	protected void setIndian(int year, int month, int day) {
		IndianCivilCalendar calendar = AlmanacConverter.toIndianCivilCalendar(new GregorianCalendar(year, month, day));
		String date = calendar.getDate();
		textIndianOutput.setText(date);
		if (textOriginal.getText().length() == 0) {
			textOriginal.setText(date);
			setOriginal(date);
		}
	}

	/**
	 *
	 * @param year
	 * @param month
	 * @param day
	 */
	protected void setJulian(int year, int month, int day) {
		JulianCalendar calendar = AlmanacConverter.toJulianCalendar(new GregorianCalendar(year, month, day));
		String date = calendar.getDate();
		textJulianOutput.setText(date);
		if (textOriginal.getText().length() == 0) {
			textOriginal.setText(date);
			setOriginal(date);
		}
	}

	/**
	 * @param localDate the localDate to set
	 */
	protected void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	/**
	 * @param original the original to set
	 */
	public void setOriginal(String original) {
		this.original = original;
	}

	/**
	 * @param sortDate the sortDate to set
	 */
	public void setSortDate(LocalDate sortDate) {
		this.sortDate = sortDate;
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 */
	public void setStoredFormat(int year, int month, int day) {
		textStoredFormat.setText(year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day));
		setLocalDate(LocalDate.of(year, month, day));
	}

	/**
	 * @param surety the surety to set
	 */
	public void setSurety(String surety) {
		this.surety = surety;
	}
}