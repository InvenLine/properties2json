package com.invenline.library.properties2json;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Matcher;

class JsonFilesWriter {

	private final PropertiesFilesMatcher propertiesFilesMatcher;

	/**
	 * Writer that reads from files matched by default matcher and writes to {@code filename_lang.json} files.
	 *
	 * @see com.invenline.library.properties2json.PropertiesFilesMatcher#PropertiesFilesMatcher()
	 */
	public JsonFilesWriter() {
		this.propertiesFilesMatcher = new PropertiesFilesMatcher();
	}

	/**
	 * Writer that reads from files matched by {@code propertiesFilesMatcher} and writes to {@code filename_lang.json}
	 * files.
	 *
	 * @see com.invenline.library.properties2json.PropertiesFilesMatcher#PropertiesFilesMatcher(java.util.regex.Pattern,
	 * int, int)
	 */
	public JsonFilesWriter(PropertiesFilesMatcher propertiesFilesMatcher) {
		this.propertiesFilesMatcher = propertiesFilesMatcher;
	}

	public void generate(PropertiesConverter parser, String sourcePath, String targetPath) {

		File dir = new File(sourcePath);
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {

				Matcher matcher = propertiesFilesMatcher.getMatcher(file.getName());

				while (matcher.find()) {

					System.out.println("\n------------------------------------------");
					String jsonMessages = parser.process(file);
					if (parser.hasErrors()) {
						throw new RuntimeException("Parsing file: " + file.getName());
					}

					String fileName = matcher.group(propertiesFilesMatcher.getFileNameGroupIndex());
					String languagePostfix = matcher.group(propertiesFilesMatcher.getLanguageGroupIndex());
					String path = targetPath + '/' + fileName + '_' + languagePostfix + ".json";

					writeResult(jsonMessages, path);
				}
			}
		}
	}

	private void writeResult(String jsonMessages, String path) {

		File file = new File(path);
		System.out.println("Writing json to file: " + file.getName() + " (" + file.getAbsolutePath() + ')');

		try {
			file.createNewFile();
			PrintStream out = new PrintStream(file);
			out.print(jsonMessages);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Writing result to file failed.", e);
		}
	}
}
