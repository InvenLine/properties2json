package com.invenline.library.properties2json;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class JsonFilesWriter {

	public void generate(PropertiesConverter parser, String sourcePath, String targetPath) {

		File dir = new File(sourcePath);
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {

				Pattern pattern = Pattern.compile("([^_]*)_([^\\.]*)\\.properties");
				Matcher matcher = pattern.matcher(file.getName());

				while (matcher.find()) {

					System.out.println("\n------------------------------------------");
					String jsonMessages = parser.process(file);
					if (parser.hasErrors()) {
						throw new RuntimeException("Parsing file: " + file.getName());
					}

					String fileName = matcher.group(1);
					String languagePostfix = matcher.group(2);
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
