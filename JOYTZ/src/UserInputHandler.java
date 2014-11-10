//@author A0112162Y

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

public class UserInputHandler {

	/**
	 * Converts the input string into a string array.
	 * 
	 * @param input
	 *            The user input
	 * @return String array containing all relevant attributes according to the
	 *         user action
	 */
	public static String[] convertUserInput(String input) {
		assertNotNull("User input is null", input);

		int index;
		String temp1, temp2;

		// preprocess of the input string to ease the progress of detecting
		// deadline indicators which are "due at" and "due on"
		if (input.contains(StringFormat.DUE_INDICATOR)) {
			index = input.indexOf(StringFormat.DUE_INDICATOR);
			temp1 = input.substring(index + 4, index + 6);
			temp2 = input.substring(index + 6);
			input = input.substring(0, index + 3);
			if (temp1.equals(StringFormat.ON_INDICATOR)) {
				input = input.concat(StringFormat.ON_INDICATOR);
			} else {
				input = input.concat(StringFormat.AT_INDICATOR);
			}
			input = input.concat(temp2);
		}

		String[] arg = input.trim().split(StringFormat.SPACE_INDICATOR);
		String[] parsedInput;
		String userAction = arg[0].toLowerCase(); // arg[0] stores the action of
													// the user

		switch (userAction) {
		case StringFormat.ADD:
			parsedInput = handleAddInput(arg);
			break;
		case StringFormat.DELETE:
			parsedInput = handleDeleteInput(arg);
			break;
		case StringFormat.UPDATE:
			parsedInput = handleUpdateInput(arg);
			break;
		case StringFormat.SORT:
			parsedInput = handleSortInput(arg);
			break;
		case StringFormat.SEARCH:
			parsedInput = handleSearchInput(arg);
			break;
		case StringFormat.DONE:
			parsedInput = handleDoneInput(arg);
			break;
		case StringFormat.DISPLAY:
			parsedInput = handleDisplayInput(arg);
			break;
		default:
			parsedInput = new String[1];
			parsedInput[0] = arg[0];
		}

		return parsedInput;
	}

	/**
	 * Creates an string array with "add" action and contains all relevant
	 * information input by the user: task name, task description, task start
	 * time, task end time, task location, task priority. Task name must be
	 * indicated while the rest can be absent.
	 * 
	 * @param str
	 *            The user input which stored in string array
	 * @return String array with "add" action containing all relevant attributes
	 *         stated above wherever applicable
	 */
	private static String[] handleAddInput(String[] str) {
		assertNotNull("User input is null", str);

		String[] output = { StringFormat.ADD, StringFormat.EMPTY,
				StringFormat.EMPTY, StringFormat.EMPTY, StringFormat.EMPTY,
				StringFormat.EMPTY, StringFormat.EMPTY };
		boolean nameExistence = false;
		boolean descriptionExistence = false;
		boolean startTimeExistence = false;
		boolean startDateExistence = false;
		boolean endTimeExistence = false;
		boolean endDateExistence = false;
		boolean locationExistence = false;
		boolean fromIndicatorExistence = false;
		boolean toIndicatorExistence = false;
		String tempStartDate = "";

		for (int i = 1; i < str.length; i++) {
			String temp = str[i];

			if (nameExistence) {
				output[1] = output[1].concat(temp); 
				output[1] = output[1].concat(StringFormat.SPACE_INDICATOR);
				if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
					output[1] = output[1].substring(0, output[1].length() - 2);
					nameExistence = false;
					descriptionExistence = true;
				} else if (str.length > i + 1) {
					if (StringFormat.isInputIndicator(str[i + 1])
							|| (StringFormat
									.isAmbiguousInputIndicator(str[i + 1])
									&& str.length > i + 2 && StringFormat
										.isTimeOrDate(str[i + 2]))) {
						nameExistence = false;
					}
				}
			} else if (descriptionExistence) {
				output[2] = output[2].concat(temp);
				output[2] = output[2].concat(StringFormat.SPACE_INDICATOR);

				if (str.length > i + 1) {
					if (StringFormat.isInputIndicator(str[i + 1])
							|| (StringFormat
									.isAmbiguousInputIndicator(str[i + 1])
									&& str.length > i + 2 && StringFormat
										.isTimeOrDate(str[i + 2]))) {
						descriptionExistence = false;
					}
				}
			} else if (startDateExistence) {
				output[3] = temp;
				startDateExistence = false;

				if (str.length > i + 1
						&& str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
					startTimeExistence = true;
				}
			} else if (startTimeExistence) {
				if (output[3].contains(StringFormat.DATE_INDICATOR)) {
					output[3] = output[3].concat(StringFormat.SPACE_INDICATOR);
					output[3] = output[3].concat(temp);
				} else {
					output[3] = temp;
				}
				startTimeExistence = false;
			} else if (endDateExistence) {
				output[4] = temp;
				endDateExistence = false;
				if (str.length > i + 1
						&& str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
					endTimeExistence = true;
				}

			} else if (endTimeExistence) {
				if (output[4].contains(StringFormat.DATE_INDICATOR)) {
					output[4] = output[4].concat(StringFormat.SPACE_INDICATOR);
					output[4] = output[4].concat(temp);
				} else {
					if (fromIndicatorExistence && toIndicatorExistence) {
						output[4] = output[4].concat(tempStartDate);
						output[4] = output[4].concat(" ");
						output[4] = output[4].concat(temp);

						fromIndicatorExistence = false;
						toIndicatorExistence = false;
					} else {
						output[4] = temp;
					}
				}

				endTimeExistence = false;
			} else if (locationExistence) {
				if (!output[5].equals(StringFormat.EMPTY)) {
					output[5] = output[5].concat(StringFormat.SPACE_INDICATOR);
					output[5] = output[5].concat(temp);
				} else {
					output[5] = temp.substring(1);
				}

				if (str.length > i + 1
						&& StringFormat.isInputIndicator(str[i + 1])) {
					locationExistence = false;
				}
			} else if (temp.equals(StringFormat.TO_INDICATOR)) {
				toIndicatorExistence = true;
				if (str.length > i + 1) {
					if (StringFormat.isTimeOrDate(str[i + 1])) {
						if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
							endTimeExistence = true;
						} else {
							endDateExistence = true;
						}
					}
				} else {
					output[4] = StringFormat.NULL;
					break;
				}
			} else if (temp.equals(StringFormat.DUE_ON_INDICATOR)) {
				if (str.length > i + 1) {
					if (StringFormat.isDate(str[i + 1])) {
						endDateExistence = true;
					} else if (StringFormat.isTime(str[i + 1])) {
						output[4] = StringFormat.INVALID;
						break;
					}
				} else {
					output[3] = StringFormat.NULL;
					break;
				}
			} else if (temp.equals(StringFormat.FROM_INDICATOR)) {
				fromIndicatorExistence = true;
				if (str.length > i + 1) {
					if (StringFormat.isTimeOrDate(str[i + 1])) {
						if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
							startTimeExistence = true;
						} else {
							tempStartDate = str[i + 1];
							startDateExistence = true;
						}
					}
				} else {
					output[3] = StringFormat.NULL;
					break;
				}
			} else if (temp.equals(StringFormat.ON_INDICATOR)) {
				if (str.length > i + 1) {
					if (StringFormat.isDate(str[i + 1])) {
						startDateExistence = true;
					} else if (StringFormat.isTime(str[i + 1])) {
						output[3] = StringFormat.INVALID;
						break;
					}
				} else {
					output[3] = StringFormat.NULL;
					break;
				}
			} else if (temp.equals(StringFormat.DUE_AT_INDICATOR)) {
				if (str.length > i + 1) {
					if (StringFormat.isTime(str[i + 1])) {
						endTimeExistence = true;
					} else if (StringFormat.isDate(str[i + 1])) {
						output[4] = StringFormat.INVALID;
						break;
					}
				} else {
					output[3] = StringFormat.NULL;
					break;
				}
			} else if (temp.equals(StringFormat.AT_INDICATOR)) {
				if (str.length > i + 1) {
					if (StringFormat.isTime(str[i + 1])) {
						startTimeExistence = true;
					} else if (StringFormat.isDate(str[i + 1])) {
						output[3] = StringFormat.INVALID;
						break;
					}
				} else {
					output[3] = StringFormat.NULL;
					break;
				}
			} else if (temp.contains(StringFormat.LOCATION_INDICATOR)) {
				output[5] = temp.substring(1);

				if (str.length > i + 1) {
					if (StringFormat.isAmbiguousInputIndicator(str[i + 1])) {
						if (str.length > i + 2
								&& !StringFormat.isTimeOrDate(str[i + 2])) {
							locationExistence = true;
						}
					} else if (!StringFormat.isInputIndicator(str[i + 1])) {
						locationExistence = true;
					}
				}
			} else if (temp.contains(StringFormat.PRIORITY_INDICATOR)) {
				String priority = temp.substring(1).toLowerCase();

				if (priority.equals(StringFormat.IMPORTANT)
						|| priority.equals(StringFormat.HIGH_PRIORITY)) {
					output[6] = StringFormat.HIGH_PRIORITY;
				} else if (priority.equals(StringFormat.UNIMPORTANT)
						|| priority.equals(StringFormat.LOW_PRIORITY)) {
					output[6] = StringFormat.LOW_PRIORITY;
				} else if (priority.equals(StringFormat.MEDIUM_PRIORITY)) {
					output[6] = StringFormat.MEDIUM_PRIORITY;
				} else {
					output[6] = StringFormat.INVALID;
				}
			} else {
				output[1] = output[1].concat(temp);
				output[1] = output[1].concat(StringFormat.SPACE_INDICATOR);

				if (str.length > i + 1) {
					if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
						output[1] = output[1].substring(0,
								output[1].length() - 2);
						descriptionExistence = true;
					} else if (StringFormat
							.isAmbiguousInputIndicator(str[i + 1])) {
						if (str.length > i + 2) {
							if (StringFormat.isTimeOrDate(str[i + 2])) {
								nameExistence = false;
							} else {
								nameExistence = true;
							}
						}
					} else if (!StringFormat.isInputIndicator(str[i + 1])) {
						nameExistence = true;
					}
				}
			}
		}

		return output;
	}

	/**
	 * Creates an string array with "delete" action and contains all relevant
	 * information input by the user: task index. Task index must be indicated
	 * and it can be duplicated.
	 * 
	 * @param str
	 *            The user input which stored in string array
	 * @return String array with "delete" action containing all relevant
	 *         attributes stated above wherever applicable
	 */
	private static String[] handleDeleteInput(String[] str) {
		assertNotNull("User input is null", str);

		ArrayList<String> output = new ArrayList<String>();

		output.add(StringFormat.DELETE);

		if (str.length > 1) {
			for (int i = 1; i < str.length; i++) {
				String temp = str[i];
				if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
					output.add(temp.substring(0, temp.length() - 1));
				} else {
					output.add(temp);
				}
			}
		}

		String[] outputArr = new String[output.size()];
		return output.toArray(outputArr);
	}

	/**
	 * Creates an string array with "update" action and contains all relevant
	 * information: task index, update indicator, update key. All attributes
	 * stated above must be indicated.
	 * 
	 * @param str
	 *            The user input which stored in string array
	 * @return String array with "update" action containing all relevant
	 *         attributes stated above wherever applicable
	 */
	private static String[] handleUpdateInput(String[] str) {
		assertNotNull("User input is null", str);

		ArrayList<String> output = new ArrayList<String>();
		output.add(StringFormat.UPDATE);

		if (str.length > 1) {
			output.add(str[1]);
		}
		if (str.length > 2) {
			output.add(str[2]);
		}
		if (str.length > 3) {
			String itemToBeUpdated = StringFormat.EMPTY;
			for (int i = 3; i < str.length; i++) {
				itemToBeUpdated = itemToBeUpdated.concat(str[i]);
				itemToBeUpdated = itemToBeUpdated
						.concat(StringFormat.SPACE_INDICATOR);
			}
			output.add(itemToBeUpdated);
		}

		String[] outputArr = new String[output.size()];
		return output.toArray(outputArr);
	}

	/**
	 * Creates an string array with "search" action and contains all relevant
	 * information:search indicator and search key. Search indicator and search
	 * key must be indicated and they can be duplicated.
	 * 
	 * @param str
	 *            The user input which stored in string array
	 * @return String array with "search" action containing all relevant
	 *         attributes stated above wherever applicable
	 */
	private static String[] handleSearchInput(String[] str) {
		assertNotNull("User input is null", str);

		ArrayList<String> output = new ArrayList<String>();

		boolean nameExistence = false;
		boolean descriptionExistence = false;
		boolean dateTimeExistence = false;
		boolean locationExistence = false;
		boolean priorityExistence = false;
		boolean startExistence = false;
		boolean endExistence = false;

		String key = StringFormat.EMPTY;

		output.add(StringFormat.SEARCH);

		if (str.length > 1) {
			for (int i = 1; i < str.length; i++) {
				String temp = str[i];

				if (nameExistence) {
					key = key.concat(temp);

					if (str.length > i + 1) {
						if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							key = StringFormat.EMPTY;
							nameExistence = false;
						} else {
							key = key.concat(StringFormat.SPACE_INDICATOR);
						}
					} else {
						if (key.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							nameExistence = false;
						} else {
							output.add(key);
						}
					}
				} else if (descriptionExistence) {
					key = key.concat(temp);

					if (str.length > i + 1) {
						if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							key = StringFormat.EMPTY;
							descriptionExistence = false;
						} else {
							key = key.concat(StringFormat.SPACE_INDICATOR);
						}
					} else {
						if (key.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							descriptionExistence = false;
						} else {
							output.add(key);
						}
					}
				} else if (dateTimeExistence) {
					output.add(temp);
					dateTimeExistence = false;
				} else if (startExistence) {
					key = key.concat(temp);

					if (str.length > i + 1) {
						if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							key = StringFormat.EMPTY;
							startExistence = false;
						} else {
							key = key.concat(StringFormat.SPACE_INDICATOR);
						}
					} else {
						if (key.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							startExistence = false;
						} else {
							output.add(key);
						}
					}
				} else if (endExistence) {
					key = key.concat(temp);

					if (str.length > i + 1) {
						if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							key = StringFormat.EMPTY;
							endExistence = false;
						} else {
							key = key.concat(StringFormat.SPACE_INDICATOR);
						}
					} else {
						if (key.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							endExistence = false;
						} else {
							output.add(key);
						}
					}
				} else if (locationExistence) {
					key = key.concat(temp);

					if (str.length > i + 1) {
						if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							key = StringFormat.EMPTY;
							locationExistence = false;
						} else {
							key = key.concat(StringFormat.SPACE_INDICATOR);
						}
					} else {
						if (key.contains(StringFormat.SEPARATE_INDICATOR)) {
							output.add(key.substring(0, key.length() - 1));
							locationExistence = false;
						} else {
							output.add(key);
						}
					}
				} else if (priorityExistence) {
					output.add(temp);
					priorityExistence = false;
				} else if (StringFormat.isValidIndicator(temp.toLowerCase())) {
					switch (temp) {
					case StringFormat.NAME:
						output.add(StringFormat.NAME);
						nameExistence = true;
						break;
					case StringFormat.DESCRIPTION:
						output.add(StringFormat.DESCRIPTION);
						descriptionExistence = true;
						break;
					case StringFormat.START:
						output.add(StringFormat.START);
						startExistence = true;
						break;
					case StringFormat.END:
						output.add(StringFormat.END);
						endExistence = true;
						break;
					case StringFormat.LOCATION:
						output.add(StringFormat.LOCATION);
						locationExistence = true;
						break;
					default:
						output.add(StringFormat.PRIORITY);
						priorityExistence = true;
						break;
					}
				} else {
					output.add(StringFormat.INVALID);
					break;
				}
			}
		}

		String[] outputArr = new String[output.size()];
		return output.toArray(outputArr);
	}

	/**
	 * Creates an string array with "sort" action and contains all relevant
	 * information: sort indicator. Sort indicator must be indicated and it can
	 * be duplicated.
	 * 
	 * @param str
	 *            The user input which stored in string array
	 * @return String array with "sort" action containing all relevant
	 *         attributes stated above wherever applicable
	 */
	private static String[] handleSortInput(String[] str) {
		assertNotNull("User input is null", str);

		ArrayList<String> output = new ArrayList<String>();
		output.add(StringFormat.SORT);

		if (str.length > 1) {
			for (int i = 1; i < str.length; i++) {
				String temp = str[i];
				if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
					output.add(temp.substring(0, temp.length() - 1));
				} else {
					output.add(temp);
				}
			}
		}

		String[] outputArr = new String[output.size()];
		return output.toArray(outputArr);
	}

	/**
	 * Creates an string array with "done" action and contains all relevant
	 * information: task index. Task index must be indicated and it can be
	 * duplicated.
	 * 
	 * @param str
	 *            The user input which stored in string array
	 * @return String array with "done" action containing all relevant
	 *         attributes stated above wherever applicable
	 */
	private static String[] handleDoneInput(String[] str) {
		assertNotNull("User input is null", str);

		ArrayList<String> output = new ArrayList<String>();

		output.add(StringFormat.DONE);

		if (str.length > 1) {
			for (int i = 1; i < str.length; i++) {
				String temp = str[i];
				if (temp.contains(StringFormat.SEPARATE_INDICATOR)) {
					output.add(temp.substring(0, temp.length() - 1));
				} else {
					output.add(temp);
				}
			}
		}

		String[] outputArr = new String[output.size()];
		return output.toArray(outputArr);
	}

	/**
	 * Creates an string array with "display" action and contains all relevant
	 * information: display indicator. Display indicator can be ignored. In this
	 * case, default display operation will be executed.
	 * 
	 * @param str
	 *            The user input which stored in string array
	 * @return String array with "display" action containing all relevant
	 *         attributes stated above wherever applicable
	 */
	private static String[] handleDisplayInput(String[] str) {
		assertNotNull("User input is null", str);

		ArrayList<String> output = new ArrayList<String>();

		output.add(StringFormat.DISPLAY);

		if (str.length > 1) {
			output.add(str[1].toLowerCase());
		}

		String[] outputArr = new String[output.size()];
		return output.toArray(outputArr);
	}

}
