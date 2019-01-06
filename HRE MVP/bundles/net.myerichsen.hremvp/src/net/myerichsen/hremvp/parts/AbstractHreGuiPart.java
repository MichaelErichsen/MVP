package net.myerichsen.hremvp.parts;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

/**
 * Abstract class for GUI parts.
 * 
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 6. jan. 2019
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractHreGuiPart {
	@Inject
	ECommandService commandService;
	@Inject
	EHandlerService handlerService;
	@Inject
	protected IEventBroker eventBroker;
	private static IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "net.myerichsen.hremvp");

	protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
//	protected BusinessLayerInterface bli;
//	protected ServerRequest request;
//	protected ServerResponse response;

	/*
	 * protected void callBusinessLayer(String operation, AbstractHreProvider
	 * provider, AbstractHreBusinessLogic businessLogic, String key) { bli =
	 * BusinessLayerInterfaceFactory.getBusinessLayerInterface();
	 * provider.setKey(key); request = new ServerRequest("GET", provider,
	 * businessLogic);
	 * 
	 * final long before = System.nanoTime();
	 * 
	 * response = bli.callBusinessLayer(request);
	 * 
	 * final long after = System.nanoTime();
	 * 
	 * LOGGER.info("Elapsed time in milliseconds: " + ((after - before) / 1000000));
	 * 
	 * if (response == null) { eventBroker.post("MESSAGE", "Call not successful");
	 * LOGGER.severe("Call not successful"); } else if (response.getReturnCode() !=
	 * 0) { eventBroker.post("MESSAGE", response.getReturnMessage());
	 * LOGGER.severe(response.getReturnMessage()); } else { provider =
	 * response.getProvider();
	 * 
	 * try { updateGui(); } catch (final Exception e2) { e2.printStackTrace();
	 * LOGGER.severe("Error in request " + request.getOperation() + " " +
	 * request.getProvider().getClass().getSimpleName() + ", " +
	 * request.getBusinessLogic().getClass().getSimpleName() + ", " +
	 * e2.getMessage()); eventBroker.post("MESSAGE", e2.getMessage()); } } }
	 */

	/**
	 * @param parent
	 * @return
	 */
	protected Font getHreFont(Composite parent) {
		LOGGER.fine("Get HRE Font");
		Font font;

		try {
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
			LOGGER.fine("HRE font: " + sb[1] + " " + Math.round(Double.valueOf(sb[2])) + " " + sb[3]);
			font = SWTResourceManager.getFont(sb[1], (int) Math.round(Double.valueOf(sb[2])), Integer.parseInt(sb[3]));
		} catch (final NumberFormatException e) {
			LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
			e.printStackTrace();

			font = parent.getShell().getFont();
			final FontData fd = font.getFontData()[0];
			font = SWTResourceManager.getFont(fd.getName(), 12, fd.getStyle());
		}
		return font;
	}

//	/**
//	 * 
//	 */
//	protected abstract void updateGui();

}
