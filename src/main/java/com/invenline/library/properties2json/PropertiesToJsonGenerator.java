package com.invenline.library.properties2json;

public class PropertiesToJsonGenerator {

	private final String propertiesDirectoryPathname;

	private final String jsonDirectoryPathname;

	private final JsonFilesWriter jsonFilesWriter = new JsonFilesWriter();

	private final PropertiesConverter propertiesConverter = new PropertiesConverter();

	public PropertiesToJsonGenerator(String propertiesDirectoryPathname, String jsonDirectoryPathname) {
		this.propertiesDirectoryPathname = propertiesDirectoryPathname;
		this.jsonDirectoryPathname = jsonDirectoryPathname;
	}

	public void generate() {
		jsonFilesWriter.generate(propertiesConverter, jsonDirectoryPathname, propertiesDirectoryPathname);
	}
}
