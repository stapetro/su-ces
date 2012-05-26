package bg.sofia.uni.fmi.ces.model.util;

import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SucesTestUtil {

	public static <T> Logger getLogger(Class<T> clasS) {
		Logger logger = LogManager.getLogger(clasS);
		return logger;
	}
	
	/**
	 * Gets positive non-zero number.
	 * 
	 * @param random
	 *            Random instance to be specified.
	 * @param maxValue
	 *            Upper bound to be specified (exclusive).
	 * @return Randomly generated non-zero number.
	 */
	public static int getPositiveNonZeroNumber(Random random, int maxValue) {
		return getGreaterPositiveNumber(random, maxValue, 0);
	}

	/**
	 * Gets positive number that is greater than specified value.
	 * 
	 * @param random
	 *            Random instance to be specified.
	 * @param maxValue
	 *            Upper bound to be specified (exclusive).
	 * @param number
	 *            Lower bound to be specified (exclusive).
	 * @return Randomly generated value which conforms to the described
	 *         constraints.
	 */
	public static int getGreaterPositiveNumber(Random random, int maxValue, int number) {
		if (maxValue == 0) {
			return 1;
		}
		if (maxValue < 0) {
			maxValue = Math.abs(maxValue);
		}
		if (number > maxValue) {
			return maxValue - 1;
		}
		int randomNumber;
		do {
			randomNumber = random.nextInt(maxValue);
		} while (randomNumber == number);
		return randomNumber;
	}

	/**
	 * Gets random value from the specified collection.
	 * 
	 * @param random
	 *            Random instance to be specified.
	 * @param values
	 *            Values to be specified.
	 * @return Randomly selected value from values collection.
	 */
	public static <T> T getRandomValue(Random random, T[] values) {
		if (values == null || values.length == 0) {
			return null;
		}
		int valueIndex = random.nextInt(values.length);
		return values[valueIndex];
	}

	/**
	 * Gets random value from the specified collection that is different from
	 * the specified value.
	 * 
	 * @param random
	 *            Random instance to be specified.
	 * @param values
	 *            Values to be specified.
	 * @param value
	 *            Result number is NOT equal to this specified value.
	 * @return Randomly selected value from values collection.
	 */
	public static <T> T getRandomValue(Random random, T[] values, T value) {
		T randomValue;
		do {
			randomValue = getRandomValue(random, values);
		} while (randomValue.equals(value));
		return randomValue;
	}

}
