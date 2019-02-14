package net.myerichsen.hremvp.mockup;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class ImageViewer extends AbstractHREGuiPart {
	@Override
	protected void callBusinessLayer(int i) {
	}

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		final CLabel lblNewLabel = new CLabel(parent, 0);
		lblNewLabel.setText("UK.jpg");
		final CLabel lblNewLabel2 = new CLabel(parent, 0);
		lblNewLabel2.setText(
				"The Church of St Christopher at Willingale Doe, Essex.\r\nFrederick and Charlotte Davis ran the school attached\r\nto the church");

		lblNewLabel2.setFont(getHreFont(parent));
	}

	@PreDestroy
	public void dispose() {
//     this.i.dispose();
	}

	@Focus
	public void setFocus() {
	}

	@Override
	protected void updateGui() {
	}
}
//
//
