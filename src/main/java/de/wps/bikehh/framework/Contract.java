package de.wps.bikehh.framework;

import java.util.Collection;
import java.util.Objects;

public class Contract {

	public static void check(boolean condition, String beschreibung) {
		checkBeschreibung(beschreibung);

		if (!condition) {
			throw new ContractException("Vertrag verletzt: " + beschreibung);
		}
	}

	public static void notNull(Object value, String what) {
		checkBeschreibung(what);

		check(value != null, what + " != null");
	}

	public static <T> void notEmpty(T[] value, String what) {
		notNull(value, what);

		check(value.length > 0, what + ".length > 0");
	}

	public static void notEmpty(Collection<?> value, String what) {
		notNull(value, what);

		check(!value.isEmpty(), "!" + what + ".isEmpty()");
	}

	public static void notEmpty(String value, String what) {
		notNull(value, what);

		check(!value.isEmpty(), "!" + what + ".isEmpty()");
	}

	public static void isFinite(double value, String what) {
		check(Double.isFinite(value), "Double." + what + ".isFinite()");
	}

	public static void checkEqual(Object actual, Object intended, String beschreibung) {
		checkBeschreibung(beschreibung);

		check(Objects.equals(actual, intended), beschreibung + " equals " + intended);
	}

	public static <N extends Comparable<N>> void lessThan(N actual, N intended, String beschreibung) {
		checkBeschreibung(beschreibung);
		notNull(actual, "actual");
		notNull(intended, "intended");

		check(actual.compareTo(intended) < 0, beschreibung + " < " + intended);
	}

	public static <N extends Comparable<N>> void lessOrEqual(N actual, N intended, String beschreibung) {
		checkBeschreibung(beschreibung);
		notNull(actual, "actual");
		notNull(intended, "intended");

		check(actual.compareTo(intended) <= 0, beschreibung + " <= " + intended);
	}

	public static <N extends Comparable<N>> void greaterThan(N actual, N intended, String beschreibung) {
		checkBeschreibung(beschreibung);
		notNull(actual, "actual");
		notNull(intended, "intended");

		check(actual.compareTo(intended) > 0, beschreibung + " > " + intended);
	}

	public static <N extends Comparable<N>> void greaterOrEqual(N actual, N intended, String beschreibung) {
		checkBeschreibung(beschreibung);
		notNull(actual, "actual");
		notNull(intended, "intended");

		check(actual.compareTo(intended) >= 0, beschreibung + " >= " + intended);
	}

	private static void checkBeschreibung(String what) throws AssertionError {
		if (what == null || what.isEmpty()) {
			throw new AssertionError("Verträge brauchen eine Beschreibung.");
		}
	}

	private Contract() {
	}

}
