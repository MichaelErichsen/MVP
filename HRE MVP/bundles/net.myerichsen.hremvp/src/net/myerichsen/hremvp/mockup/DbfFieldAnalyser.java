package net.myerichsen.hremvp.mockup;

import java.io.IOException;
import java.util.logging.Logger;

import org.xBaseJ.DBF;
import org.xBaseJ.xBaseJException;

public class DbfFieldAnalyser {
	private static final Logger LOGGER = Logger.getLogger("global");

	private DBF dbf;

	private int i;

	public DbfFieldAnalyser(String dbfFileName) {
		try {
			this.dbf = new DBF(dbfFileName, 'r');
		} catch (xBaseJException e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.dbf.close();
		} catch (IOException e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public int getFieldCount() {
		LOGGER.info("Field Count: " + this.dbf.getFieldCount());
		return this.dbf.getFieldCount();
	}

	public String getFieldNames() {
		StringBuilder sb = new StringBuilder("(");
		StringBuilder sb1 = null;
		StackTraceElement[] arrayOfStackTraceElement = null;
		int j = 0;
		try {
			for (int f = 1; f < this.dbf.getFieldCount(); f++) {
				String s = this.dbf.getField(f).getName();
				if (s.equals("GROUP")) {
					s = "XGROUP";
				} else if (s.equals("PRIMARY")) {
					s = "XPRIMARY";
				} else if (s.equals("VALUE")) {
					s = "XVALUE";
				}
				sb.append(s + ", ");
			}

			sb.append(this.dbf.getField(this.dbf.getFieldCount()).getName());
			sb.append(") VALUES (");
			for (int i1 = 0; i1 < this.dbf.getFieldCount() - 1; i1++) {
				sb.append("?, ");
			}
			sb.append("?)");
		} catch (Exception e) {
			sb1 = new StringBuilder(e.getClass() + " " + e.getMessage());

			j = (arrayOfStackTraceElement = e.getStackTrace()).length;
			i = 0;
		}
		for (i = 0; i < j; i++) {
			StackTraceElement element = arrayOfStackTraceElement[i];
			sb1.append("\n" + element.toString());
		}

		return sb.toString().toUpperCase();
	}
}

/*
 * Location: C:\Temp\HRE Mockup
 * Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0
 * .0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\Dbf2H2\
 * DbfFieldAnalyser.class Java compiler version: 8 (52.0) JD-Core Version: 0.7.1
 */