package com.invenline.library.properties2json;

import java.util.regex.Pattern;

/**
 * Standalone generator for use from command line.
 */
public class Properties2JsonApp {

	public static final Pattern PLAY_FRAMEWORK_I18N_FILES = Pattern.compile("(messages)\\.(.+)");

	public static void main(String[] args) {

		int matcherMode = 1;
		String inputDirectory = null;
		String outputDirectory = null;

		if (args.length == 0) {
			printHelp();
			return;
		}

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			switch (arg) {
				case "--input-dir":
					inputDirectory = args[i + 1];
					break;

				case "--output-dir":
					outputDirectory = args[i + 1];

				case "--matcher-mode":
					arg = args[i + 1].toUpperCase();
					if ("DEFAULT".equals(arg)) {
						matcherMode = 1;
					} else if ("PLAY".equals(arg)) {
						matcherMode = 2;
					}
					break;

				case "--help":
					printHelp();
					return;

				default:
					if (arg.startsWith("--")) {
						throw new RuntimeException("Unknown argument '" + arg + "' found");
					}
			}
		}

		if (inputDirectory == null || outputDirectory == null) {
			throw new RuntimeException("Not enough arguments: only " + args.length + " found");
		}

		if (matcherMode == 1) {
			new PropertiesToJsonGenerator(inputDirectory, outputDirectory).generate();
		} else {
			PropertiesFilesMatcher matcher = new PropertiesFilesMatcher(PLAY_FRAMEWORK_I18N_FILES, 1, 2);
			new PropertiesToJsonGenerator(matcher, inputDirectory, outputDirectory).generate();
		}
	}

	private static void printHelp() {
		System.out.println("Usage:");
		System.out.println("\t--input-dir\t\tInput directory, where properties files are located.");
		System.out.println("\t--output-dir\tOutput directory, where JSON files will be written.");
		System.out.println("\t--matcher-mode\t'DEFAULT' for matching Java properties naming convention,\n" +
				"\t\t\t\t\t'PLAY' for matching Play Framework naming convention (optional parameter, may be omitted).");
	}
}
