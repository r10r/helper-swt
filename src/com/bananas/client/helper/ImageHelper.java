package com.bananas.client.helper;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageHelper {
	
	public static Image getImage(Display display, String path) {
		return new Image(display, ClassLoader.getSystemResourceAsStream(path));
	}
}
