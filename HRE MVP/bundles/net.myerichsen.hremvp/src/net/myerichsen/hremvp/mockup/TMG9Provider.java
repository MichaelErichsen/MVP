package net.myerichsen.hremvp.mockup;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class TMG9Provider implements IRunnableWithProgress {
	private static final Logger LOGGER = Logger.getLogger("global");
	@Inject
	private IEventBroker eventBroker;
	private final ScopedPreferenceStore store = new ScopedPreferenceStore(
			InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");

	private String dbName;

	private String project;

	public TMG9Provider(String dbName, String project) {
		this.dbName = dbName;
		this.project = project;
	}

	public String getDbName() {
		return this.dbName;
	}

	public String getProject() {
		return this.project;
	}

	public void run(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		LOGGER.info("Run");
		SubMonitor subMonitor = SubMonitor.convert(monitor, 30);

		try {
			subMonitor.beginTask(
					"Converting TMG9 Project " + this.project + " to H2", 30);
			subMonitor.subTask("Creating H2 Database " + this.dbName);

			this.store.putValue("DBNAME", this.dbName);
			CreateH2Database createH2Database = new CreateH2Database(
					this.dbName, subMonitor.split(2, 0));
			createH2Database.run();
			subMonitor.worked(2);
			LOGGER.info("DB " + this.dbName + " created");

			TableLoader ld = new TableLoader(this.project, "D", "DATASET",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "DNA", "DNA", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "G", "EVENT", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "E", "EVENTWITNESS", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "XD", "EXCLUDEDPAIR",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "I", "EXHIBIT", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "C", "FLAG", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "O", "FOCUSGROUP", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "B", "FOCUSGROUPMEMBER",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "N", "NAME", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "ND", "NAMEDICTIONARY",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "NPT", "NAMEPARTTYPE",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "NPV", "NAMEPARTVALUE",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "F", "PARENTCHILDRELATIONSHIP",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "$", "PERSON", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "P", "PLACE", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "PD", "PLACEDICTIONARY",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "PPT", "PLACEPARTTYPE",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "PPV", "PLACEPARTVALUE",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "R", "REPOSITORY", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "W", "REPOSITORYLINK",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "L", "RESEARCHLOG", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "M", "SOURCE", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "S", "SOURCECITATION",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "U", "SOURCEELEMENT",
					this.dbName, subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "A", "SOURCETYPE", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "ST", "STYLE", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "T", "TAGTYPE", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			ld = new TableLoader(this.project, "K", "TIMELINELOCK", this.dbName,
					subMonitor.split(1, 0));
			ld.run();
			subMonitor.worked(1);

			this.eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(1));
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(
					e.getClass() + " " + e.getMessage());
			StackTraceElement[] arrayOfStackTraceElement;
			int j = (arrayOfStackTraceElement = e.getStackTrace()).length;
			for (int i = 0; i < j; i++) {
				StackTraceElement element = arrayOfStackTraceElement[i];
				sb.append("\n" + element.toString());
			}
		}
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setProject(String project) {
		this.project = project;
	}
}