package de.phoenixmitx.lombokextensions.codegen.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javassist.CannotCompileException;
import javassist.CtMethod;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrdinalParameter {

	Pattern ORDINAL_PATTERN = Pattern.compile("\\$\\$(\\d+-\\d+)?|\\$\\d+");

	public String parseOrdinalParameters(String src) {
		return ORDINAL_PATTERN.matcher(src).replaceAll(match -> {
			String group = match.group();
			if (group.equals("$$")) {
				group = "$$";
			} else if (group.startsWith("$$")) {
				String[] startEnd = group.substring(2).split("-");
				group = IntStream.rangeClosed(Integer.parseInt(startEnd[0])+1, Integer.parseInt(startEnd[1])+1)
						.mapToObj(i -> "$" + i)
						.collect(Collectors.joining(","));
			} else if (group.startsWith("$")) {
				group = "$" + (Integer.parseInt(group.substring(1))+1);
			} else {
				throw new IllegalArgumentException("Unknown ordinal parameter: " + group);
			}
			return Matcher.quoteReplacement(group);
		});
	}

	public void setBodyWithOrdinalParameters(CtMethod method, String body) throws CannotCompileException {
		method.setBody(parseOrdinalParameters(body));
	}
}
