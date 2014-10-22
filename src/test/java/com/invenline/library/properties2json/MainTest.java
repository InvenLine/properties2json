package com.invenline.library.properties2json;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class MainTest {

	public static void main(String[] args) throws URISyntaxException {
		String propertiesDirectory =
				Paths.get(PropertiesToJsonGenerator.class.getResource("/properties").toURI()).toString();
		new PropertiesToJsonGenerator(propertiesDirectory, propertiesDirectory).generate();
	}
}
