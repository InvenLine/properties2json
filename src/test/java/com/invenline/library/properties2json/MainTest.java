package com.invenline.library.properties2json;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class MainTest {

	public static void main(String[] args) throws URISyntaxException {

		String propertiesDirectory =
				Paths.get(PropertiesToJsonGenerator.class.getResource("/properties").toURI()).toString();

		Properties2JsonApp.main(new String[]{});

		Properties2JsonApp.main(new String[]{
				"--input-dir", propertiesDirectory,
				"--output-dir", propertiesDirectory,
				"--matcher-mode", "DEFAULT"
		});

		Properties2JsonApp.main(new String[]{
				"--input-dir", propertiesDirectory,
				"--output-dir", propertiesDirectory,
				"--matcher-mode", "PLAY"
		});
	}
}
