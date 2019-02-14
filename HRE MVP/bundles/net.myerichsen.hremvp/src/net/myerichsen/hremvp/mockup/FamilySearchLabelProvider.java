package net.myerichsen.hremvp.mockup;

import java.util.logging.Logger;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class FamilySearchLabelProvider implements ILabelProvider {
	private static final Logger LOGGER = Logger.getLogger("global");

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		return null;
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		LOGGER.info(property);
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}
}
