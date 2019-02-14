package net.myerichsen.hremvp.mockup;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

public abstract class AbstractHREGuiPart {
	protected static final Logger LOGGER = Logger.getLogger("global");
	@Inject
	protected IEventBroker eventBroker;

	protected ScopedPreferenceStore store;

//	protected BusinessLayerInterface bli;

//	protected ServerRequest req;

//	protected ServerResponse resp;

	protected abstract void callBusinessLayer(int paramInt);

	protected Font getHreFont(Composite parent) {
		LOGGER.fine("Get HRE Font");
		Font font;
		try {
			store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
					"org.historyresearchenvironment.usergui");
			final String s = store.getString("HREFONT");

			LOGGER.fine(s);
			final String[] sa = s.split("-");
			for (int i = 0; i < sa.length; i++) {
				LOGGER.fine("sa[" + i + "]: " + sa[i]);
			}
			final String[] sb = sa[0].split("\\|");
			for (int i = 0; i < sb.length; i++) {
				LOGGER.fine("sb[" + i + "]: " + sb[i]);
			}
			LOGGER.fine("HRE font: " + sb[1] + " "
					+ Math.round(Double.valueOf(sb[2]).doubleValue()) + " "
					+ sb[3]);
			font = SWTResourceManager.getFont(sb[1],
					(int) Math.round(Double.valueOf(sb[2]).doubleValue()),
					Integer.parseInt(sb[3]));
		} catch (final NumberFormatException e) {
//			Font font;
			LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line "
					+ e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();

			font = parent.getShell().getFont();
			final FontData fd = font.getFontData()[0];
			font = SWTResourceManager.getFont(fd.getName(), 12, fd.getStyle());
		}
		return font;
	}

	protected abstract void updateGui();
}
