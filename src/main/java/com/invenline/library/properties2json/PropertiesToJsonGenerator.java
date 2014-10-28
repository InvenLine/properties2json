package com.invenline.library.properties2json;

public class PropertiesToJsonGenerator {

	private final String propertiesDirectoryPathname;

	private final String jsonDirectoryPathname;

	private final JsonFilesWriter jsonFilesWriter;

	private final PropertiesConverter propertiesConverter = new PropertiesConverter();

	/**
	 * PropertiesToJsonGenerator with default {@link PropertiesFilesMatcher#PropertiesFilesMatcher(java.util.regex.Pattern,
	 * int, int)}
	 *
	 * @param propertiesDirectoryPathname Directory that contains properties files.
	 * @param jsonDirectoryPathname       Directory that will be contained generated JSON files.
	 */
	public PropertiesToJsonGenerator(String propertiesDirectoryPathname, String jsonDirectoryPathname) {
		this.jsonFilesWriter = new JsonFilesWriter();
		this.propertiesDirectoryPathname = propertiesDirectoryPathname;
		this.jsonDirectoryPathname = jsonDirectoryPathname;
	}

	/**
	 * PropertiesToJsonGenerator with given {@link PropertiesFilesMatcher#PropertiesFilesMatcher(java.util.regex.Pattern,
	 * int, int)}
	 *
	 * @param propertiesFilesMatcher      Matcher that defines what files will be read and converted.
	 * @param propertiesDirectoryPathname Directory that contains properties files.
	 * @param jsonDirectoryPathname       Directory that will be contained generated JSON files.
	 */
	public PropertiesToJsonGenerator(PropertiesFilesMatcher propertiesFilesMatcher, String propertiesDirectoryPathname,
			String jsonDirectoryPathname) {
		this.jsonFilesWriter = new JsonFilesWriter(propertiesFilesMatcher);
		this.propertiesDirectoryPathname = propertiesDirectoryPathname;
		this.jsonDirectoryPathname = jsonDirectoryPathname;
	}

	public void generate() {
		jsonFilesWriter.generate(propertiesConverter, propertiesDirectoryPathname, jsonDirectoryPathname);
	}
}
