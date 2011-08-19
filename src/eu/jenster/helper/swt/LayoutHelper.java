package eu.jenster.helper.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class LayoutHelper {

	public static <T extends Control> T fillGrid(T control) {
		// use existing grid data if available
		GridData layoutData = safeGetGridData(control);

		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;

		return control;
	}

	/**
	 * Expands the given control to maximum width, by setting its respective
	 * {@link GridData} properties.
	 * 
	 * @param <T>
	 *            a subtype of {@link Control}
	 * @param control
	 *            the control to expand
	 * @return the given control
	 */
	public static <T extends Control> T fillWidth(T control) {

		// use existing grid data if available
		GridData layoutData = safeGetGridData(control);
		// reset the hints, since they take precedence
		layoutData.widthHint = SWT.DEFAULT;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;

		return control;
	}

	/**
	 * Expands the given control to maximum height, by setting its respective
	 * {@link GridData} properties.
	 * 
	 * @param <T>
	 *            a subtype of {@link Control}
	 * @param control
	 *            the control to expand
	 * @return the given control
	 */
	public static <T extends Control> T fillHeight(T control) {

		// use existing grid data if available
		GridData layoutData = safeGetGridData(control);
		// reset the hints, since they take precedence
		layoutData.heightHint = SWT.DEFAULT;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.grabExcessVerticalSpace = true;

		return control;
	}

	public static GridData safeGetGridData(Control control) {
		GridData layoutData = (GridData) control.getLayoutData();
		if (layoutData == null) {
			layoutData = new GridData();
			control.setLayoutData(layoutData);
		}
		return layoutData;
	}

	// TODO move to components
	public static Label centeredLabel(Composite parent, int containerWidth,
			int containerHeight, int containerStyle) {
		Composite container = new Composite(parent, containerStyle);
		container.setLayout(new GridLayout());
		GridData containerLayoutData = new GridData();

		containerLayoutData.heightHint = containerHeight;
		containerLayoutData.widthHint = containerWidth;

		container.setLayoutData(containerLayoutData);

		Label label = new Label(container, SWT.HORIZONTAL);
		label.setAlignment(SWT.CENTER);

		GridData labelLayoutData = new GridData();
		labelLayoutData.grabExcessHorizontalSpace = true;
		labelLayoutData.grabExcessVerticalSpace = true;
		labelLayoutData.verticalAlignment = SWT.CENTER;
		labelLayoutData.horizontalAlignment = SWT.CENTER;
		label.setLayoutData(labelLayoutData);

		return label;
	}

	public static GridLayout setGridLayout(Composite composite) {
		return setGridLayout(composite, 1);
	}

	public static GridLayout setGridLayout(Composite composite, int columns) {
		GridLayout gridLayout = new GridLayout(columns, false);
		composite.setLayout(gridLayout);
		return gridLayout;
	}

	public static void setWidth(Control control, int width) {
		GridData layoutData = safeGetGridData(control);
		layoutData.widthHint = width;

	}

	public static final int TOP_LEFT = 0x01;
	public static final int TOP_RIGHT = 0x02;
	public static final int BOTTOM_RIGHT = 0x03;
	public static final int BOTTOM_LEFT = 0x04;
	public static final int CENTER = 0x05;

	/**
	 * Moves the given shell to the given position. Should be called after
	 * {@link Shell#pack()}
	 * 
	 * @param shell
	 *            the shell to move
	 * @param position
	 *            the constant indicating the position
	 * @param margin
	 *            the margin
	 */
	public static void move(Shell shell, int position, int margin) {

		Rectangle shellBounds = shell.getBounds();
		int shellWidth = shellBounds.width + margin;
		int shellHeight = shellBounds.height + margin;

		Rectangle displayBounds = shell.getDisplay().getBounds();
		int displayWidth = displayBounds.width;
		int displayHeight = displayBounds.height;

		int x = 0;
		int y = 0;

		switch (position) {
		case TOP_LEFT:
			x = margin;
			y = margin;
			break;
		case TOP_RIGHT:
			x = displayWidth - shellWidth;
			y = margin;
			break;
		case BOTTOM_RIGHT:
			x = displayWidth - shellWidth;
			y = displayHeight - shellHeight;
			break;
		case BOTTOM_LEFT:
			x = margin;
			y = displayHeight - shellHeight;
			break;
		case CENTER:
			x = (displayWidth / 2) - (shellWidth / 2);
			y = (displayHeight / 2) - (shellHeight / 2);
			break;
		default:
			break;
		}

		shell.setBounds(x, y, shellBounds.width, shellBounds.height);
	}

	public static void hide(Control control) {
		GridData gridData = safeGetGridData(control);
		gridData.exclude = true;
		control.setVisible(false);
		control.getParent().layout(false);
	}
	
	public static void show(Control control) {
		GridData gridData = safeGetGridData(control);
		gridData.exclude = false;
		control.setVisible(true);
		control.getParent().layout(false);
	}
}