package net.myerichsen.hremvp.mockup;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.xBaseJ.DBF;
import org.xBaseJ.xBaseJException;
import org.xBaseJ.fields.Field;
import org.xBaseJ.fields.MemoField;

import net.myerichsen.hremvp.HreH2ConnectionPool;

public class TableLoader {
	private static final Logger LOGGER = Logger.getLogger("global");
	private Statement stmt = null;

	private String FPTABLE = null;
	private DBF dbf = null;
	private String DELETE = null;
	private String INSERT = null;
	private Connection conn = null;
	private PreparedStatement pst = null;

	public TableLoader() {
	}

	public TableLoader(String project, String type, String table, String dbName,
			IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		subMonitor.beginTask("Loading tables", 1);
		subMonitor.subTask("Loading " + table);

		this.FPTABLE = (FilenameUtils.getFullPath(project)
				+ FilenameUtils.getBaseName(project).replace("_", "") + "_"
				+ type + ".dbf");
		LOGGER.info("Project:" + project);
		this.DELETE = ("DELETE FROM " + table);

		LOGGER.info(this.FPTABLE);
		DbfFieldAnalyser analyser = new DbfFieldAnalyser(this.FPTABLE);
		this.INSERT = ("INSERT INTO " + table + " " + analyser.getFieldNames());
	}

	public TableLoader(String project, String type, String table,
			String dbName) {
		this.FPTABLE = (FilenameUtils.getFullPath(project)
				+ FilenameUtils.getBaseName(project).replace("_", "") + "_"
				+ type + ".dbf");
		LOGGER.info("Project:" + project);
		this.DELETE = ("DELETE FROM " + table);

		LOGGER.info(this.FPTABLE);
		DbfFieldAnalyser analyser = new DbfFieldAnalyser(this.FPTABLE);
		this.INSERT = ("INSERT INTO " + table + " " + analyser.getFieldNames());
	}

	public TableLoader(String project, String type, String table,
			String insertStatement, String dbName) {
		LOGGER.info("Project:" + project);
		this.FPTABLE = (FilenameUtils.getFullPath(project)
				+ FilenameUtils.getBaseName(project).replace("_", "") + "_"
				+ type + ".dbf");
		LOGGER.info("FPTABLE: " + this.FPTABLE);
		this.DELETE = ("DELETE FROM " + table);
		this.INSERT = ("INSERT INTO " + table + " " + insertStatement);
	}

	private void initialize() throws xBaseJException, IOException,
			IllegalAccessException, InstantiationException, SQLException {
		this.dbf = new DBF(this.FPTABLE, 'r');

		LOGGER.info("Table name: " + this.dbf.getName() + "\nRecord count: "
				+ this.dbf.getRecordCount() + "\nField count: "
				+ this.dbf.getFieldCount());

		this.conn = HreH2ConnectionPool.getConnection();
		this.stmt = this.conn.createStatement();
		this.stmt.execute(this.DELETE);
		this.conn.commit();
		LOGGER.info(this.INSERT);
		this.pst = this.conn.prepareStatement(this.INSERT);
	}

	private void mainLoop() {
		char type = '?';
		String s = null;
		String ssub = null;
		String ds = null;
		Date d = null;
		int l = 0;
		BigDecimal bd = null;
		Field fld = null;
		int a = 0;
		LOGGER.fine("");
		try {
			for (int i = 0; i < this.dbf.getRecordCount(); i++) {
				this.dbf.read();
				LOGGER.fine("\nRecord " + i);

				for (int f = 1; f <= this.dbf.getFieldCount(); f++) {
					fld = this.dbf.getField(f);
					type = fld.getType();

					if (fld.isMemoField()) {
						l = ((MemoField) fld).getOriginalSize();
					} else {
						l = fld.getLength();
					}

					s = fld.get();

					if (s.length() > 40) {
						ssub = s.substring(0, 40) + "...";
					} else {
						ssub = s;
					}

					LOGGER.fine("Field " + f + ":\t" + fld.getName() + ", "
							+ type + ", " + l + "\t>" + ssub + "<");

					switch (type) {
					case 'I':
						a = 0;

						for (int k = 3; k >= 0; k--) {
							System.out.print(fld.buffer[k] + " ");

							a *= 256;
							a += (fld.buffer[k] < 0 ? fld.buffer[k] + 256
									: fld.buffer[k]);
						}
						LOGGER.fine("\tInt corrected from " + s + " to " + a);
						this.pst.setInt(f, a);
						break;

					case 'N':
						if (s.length() == 0) {
							bd = new BigDecimal(0);
						} else {
							bd = new BigDecimal(s.trim());
						}
						this.pst.setBigDecimal(f, bd);
						break;

					case 'C':
						this.pst.setString(f, s);
						break;

					case 'M':
						if (s.length() > 32760) {
							LOGGER.warning("Truncating MEMO type");
							s = s.substring(0, 32760);
						}

						this.pst.setString(f, s);
						break;

					case 'G':
						this.pst.setString(f, s);
						break;
					case 'D':
						try {
							ds = s.substring(0, 4) + "-" + s.substring(4, 6)
									+ "-" + s.substring(6, 8);
							d = Date.valueOf(ds);
						} catch (Exception e) {
							e.printStackTrace();
							d = null;
						}

						this.pst.setDate(f, d);
						break;

					case 'L':
						this.pst.setString(f, s.substring(0, 1));
						break;
					case 'E':
					case 'F':
					case 'H':
					case 'J':
					case 'K':
					default:
						LOGGER.severe("Unsupported field type " + type);
						throw new RuntimeException(
								"Unknown field type " + type);
					}
				}
				LOGGER.fine("----------------------------");

				this.pst.executeUpdate();
				this.conn.commit();

			}

		} catch (Exception e) {

			StringBuilder sb = new StringBuilder(
					e.getClass() + " " + e.getMessage());
			StackTraceElement[] arrayOfStackTraceElement;
			int j = (arrayOfStackTraceElement = e.getStackTrace()).length;
			for (int i = 0; i < j; i++) {
				StackTraceElement element = arrayOfStackTraceElement[i];
				sb.append("\n" + element.toString());
			}
			LOGGER.severe(sb.toString());
		}
	}

	public void run() {
		try {
			initialize();
			mainLoop();
			terminate();
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(
					e.getClass() + " " + e.getMessage());
			StackTraceElement[] arrayOfStackTraceElement;
			int j = (arrayOfStackTraceElement = e.getStackTrace()).length;
			for (int i = 0; i < j; i++) {
				StackTraceElement element = arrayOfStackTraceElement[i];
				sb.append("\n" + element.toString());
			}
			LOGGER.severe(sb.toString());
		}
	}

	private void terminate() {
		try {
			this.stmt.close();
			this.conn.close();
			this.dbf.close();
		} catch (Exception e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line "
					+ e.getStackTrace()[0].getLineNumber());
		}
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\Dbf2H2\TableLoader
 * .class Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */