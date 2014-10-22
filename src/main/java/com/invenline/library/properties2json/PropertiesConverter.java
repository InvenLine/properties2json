package com.invenline.library.properties2json;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

class PropertiesConverter {

	private boolean hasErrors;

	public String process(File file) {

		Properties props = readPropertiesFromFile(file);
		return propertiesToJson(props);
	}

	protected Properties readPropertiesFromFile(File file) {

		logInfo("Reading properties from file: " + file.getName() + " (" + file.getAbsolutePath() + ')');

		Properties properties = new Properties();
		InputStream inStream;
		try {
			inStream = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
			properties.load(reader);
		} catch (FileNotFoundException fileNotFoundException) {
			logError("FILE NOT FOUND EXCEPTION WHILE READING THE FILE: " + file.getName() + '\n' +
					fileNotFoundException);
		} catch (IOException ioException) {
			logError("IO EXCEPTION WHILE READING THE FILE: " + file.getName() + '\n' + ioException);
		}

		return properties;
	}

	protected String propertiesToJson(Properties p) {

		Map propertiesTree = getPropertiesTree(p);

		StringBuilder buffer = new StringBuilder();
		buffer.append("{\n");
		recursiveMapToString(propertiesTree, buffer, 1);
		buffer.append('}');

		return buffer.toString();
	}

	private Map getPropertiesTree(Properties properties) {
		Map<String, Object> tree = new LinkedHashMap<>();
		for (String name : properties.stringPropertyNames()) {
			String[] parts = name.split("\\.");

			Map<String, Object> nextTree = tree;
			for (int i = 0, partsLength = parts.length; i < partsLength; i++) {
				String part = parts[i];
				Object value = nextTree.get(part);

				if (i < partsLength - 1) { // We must build nested tree
					if (value == null) {
						Map<String, Object> newNextTree = new LinkedHashMap<>();
						nextTree.put(part, newNextTree);
						nextTree = newNextTree;
					} else if (value instanceof Map) {
						@SuppressWarnings("unchecked")
						Map<String, Object> mapFromValue = (Map<String, Object>) value;
						nextTree = mapFromValue;
					} else {
						throw new IllegalStateException("Conversion error: key '" + name +
								"' should be instance of Map, but '" + value + "' of type '" +
								value.getClass().getName() + "' found");
					}
				} else { // We must insert value
					if (value == null) {
						nextTree.put(part, properties.getProperty(name));
					} else {
						throw new IllegalStateException("Conversion error: key '" + name +
								"' already exists with value '" + value + "' of type '" +
								value.getClass().getName() + '\'');
					}
				}
			}
		}
		return tree;
	}

	private void recursiveMapToString(Map tree, StringBuilder buffer, int deep) {

		boolean first = true;

		for (Object key : tree.keySet()) {

			if (!first) {
				buffer.append(",\n");
			} else {
				first = false;
			}

			for (int t = 0; t < deep; t++) {
				buffer.append('\t');
			}

			buffer.append('"').append(key).append('"').append(" : ");

			Object value = tree.get(key);
			if (value instanceof Map) {
				buffer.append("{\n");
				recursiveMapToString((Map) value, buffer, deep + 1);

				for (int t = 0; t < deep; t++) {
					buffer.append('\t');
				}

				buffer.append('}');
			} else {
				value = value.toString().replaceAll("\"", "\'");
				buffer.append('"').append(value).append('"');
			}
		}
		buffer.append('\n');
	}

	private void logInfo(String message) {
		System.out.println(message);
	}

	private void logError(String message) {
		hasErrors = true;
		System.err.println(message);
	}

	public boolean hasErrors() {
		return hasErrors;
	}
}
