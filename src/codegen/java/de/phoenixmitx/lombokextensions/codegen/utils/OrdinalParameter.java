package de.phoenixmitx.lombokextensions.codegen.utils;

import java.util.function.Function;
import java.util.regex.MatchResult;
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
		return java17ReplceAll(ORDINAL_PATTERN.matcher(src), match -> {
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

	private String java17ReplceAll(Matcher matcher, Function<MatchResult, String> replacer) {
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, replacer.apply(matcher.toMatchResult()));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public void setBodyWithOrdinalParameters(CtMethod method, String body) throws CannotCompileException {
		method.setBody(parseOrdinalParameters(body));
	}
}
