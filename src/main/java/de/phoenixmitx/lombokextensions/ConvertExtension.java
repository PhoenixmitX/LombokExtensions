package de.phoenixmitx.lombokextensions;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConvertExtension {

	// FROM STRING

	public int asInt(String t) {
		return Integer.parseInt(t);
	}

	public int asInt(String t, int radix) {
		return Integer.parseInt(t, radix);
	}

	public int asInt(String t, int radix, int defaultValue) {
		try {
			return Integer.parseInt(t, radix);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public long asLong(String t) {
		return Long.parseLong(t);
	}

	public long asLong(String t, int radix) {
		return Long.parseLong(t, radix);
	}

	public long asLong(String t, int radix, long defaultValue) {
		try {
			return Long.parseLong(t, radix);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public double asDouble(String t) {
		return Double.parseDouble(t);
	}

	public double asDouble(String t, int defaultValue) {
		try {
			return Double.parseDouble(t);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	// TO STRING

	public String asString(int t) {
		return Integer.toString(t);
	}

	public String asString(int t, int radix) {
		return Integer.toString(t, radix);
	}

	public String asString(long t) {
		return Long.toString(t);
	}

	public String asString(long t, int radix) {
		return Long.toString(t, radix);
	}

	public String asString(double t) {
		return Double.toString(t);
	}
}
