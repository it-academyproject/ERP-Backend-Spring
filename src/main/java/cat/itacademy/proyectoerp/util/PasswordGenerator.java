package cat.itacademy.proyectoerp.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * PasswordGenerator class used to generate a random password of 10 characters,
 * where at least there are two numbers, two letters in uppercase and lowercase
 * and two special characters.
 */

public class PasswordGenerator {

	/**
	 * Method to generate a new password
	 * 
	 * @return password
	 */
	public String generatePassword() {

		// create random strings whose length is the number of characters specified
		String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
		String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
		String numbers = RandomStringUtils.randomNumeric(2);
		String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
		String totalChars = RandomStringUtils.randomAlphanumeric(2);

		String combinedChars = upperCaseLetters.concat(lowerCaseLetters).concat(numbers).concat(specialChar)
				.concat(totalChars);

		List<Character> pwdChars = combinedChars.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

		Collections.shuffle(pwdChars);

		String password = pwdChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();

		return password;
	}
}
