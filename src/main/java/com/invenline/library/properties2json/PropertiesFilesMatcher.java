package com.invenline.library.properties2json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesFilesMatcher {

	private final Pattern filesMatchingPattern;

	private final int fileNameGroupIndex;

	private final int languageGroupIndex;

	/**
	 * Matcher that match {@code filename_lang.properties} file names.
	 */
	public PropertiesFilesMatcher() {
		this(Pattern.compile("([^_]*)_([^\\.]*)\\.properties"), 1, 2);
	}

	/**
	 * Matcher that match {@code filesMatchingPattern} file names.
	 *
	 * @param filesMatchingPattern Pattern that match properties file names.
	 * @param fileNameGroupIndex   Index of matched group in file name that should be output file name.
	 * @param languageGroupIndex   Index of matched group in file name that is language code.
	 */
	public PropertiesFilesMatcher(Pattern filesMatchingPattern, int fileNameGroupIndex,
			int languageGroupIndex) {
		this.filesMatchingPattern = filesMatchingPattern;
		this.fileNameGroupIndex = fileNameGroupIndex;
		this.languageGroupIndex = languageGroupIndex;
	}

	public Matcher getMatcher(CharSequence input) {
		return filesMatchingPattern.matcher(input);
	}

	public int getFileNameGroupIndex() {
		return fileNameGroupIndex;
	}

	public int getLanguageGroupIndex() {
		return languageGroupIndex;
	}
}
