import java.util.ArrayList;

public class UserInputHandler {

	public static String[] convertUserInput(String input) {
		int index;
		String temp1, temp2;

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
		String userAction = arg[0].toLowerCase();

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
		default:
			parsedInput = new String[1];
			parsedInput[0] = arg[0];
		}

		return parsedInput;
	}

	private static String[] handleAddInput(String[] str) {
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
									.isAmbiguousInputIndicator(str[i + 1]) && StringFormat
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
									.isAmbiguousInputIndicator(str[i + 1]) && StringFormat
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
					output[4] = temp;
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
				if (StringFormat.isTimeOrDate(str[i + 1])) {
					if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
						endTimeExistence = true;
					} else {
						endDateExistence = true;
					}
				}
			} else if (temp.equals(StringFormat.DUE_ON_INDICATOR)) {
				if (StringFormat.isDate(str[i + 1])) {
					endDateExistence = true;
				} else if (StringFormat.isTime(str[i + 1])) {
					output[4] = StringFormat.INVALID;
					break;
				}
			} else if (temp.equals(StringFormat.FROM_INDICATOR)) {
				if (StringFormat.isTimeOrDate(str[i + 1])) {
					if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
						startTimeExistence = true;
					} else {
						startDateExistence = true;
					}
				}
			} else if (temp.equals(StringFormat.ON_INDICATOR)) {
				if (StringFormat.isDate(str[i + 1])) {
					startDateExistence = true;
				} else if (StringFormat.isTime(str[i + 1])) {
					output[3] = StringFormat.INVALID;
					break;
				}
			} else if (temp.equals(StringFormat.DUE_AT_INDICATOR)) {
				if (StringFormat.isTime(str[i + 1])) {
					endTimeExistence = true;
				} else if (StringFormat.isDate(str[i + 1])) {
					output[4] = StringFormat.INVALID;
					break;
				}
			} else if (temp.equals(StringFormat.AT_INDICATOR)) {
				if (StringFormat.isTime(str[i + 1])) {
					startTimeExistence = true;
				} else if (StringFormat.isDate(str[i + 1])) {
					output[3] = StringFormat.INVALID;
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

				if (priority.equals(StringFormat.IMPORTANT)) {
					output[6] = StringFormat.HIGH_PRIORITY;
				} else if (priority.equals(StringFormat.UNIMPORTANT)) {
					output[6] = StringFormat.LOW_PRIORITY;
				} else if (priority.equals(StringFormat.MEDIUM_PRIORITY)) {
					output[6] = StringFormat.MEDIUM_PRIORITY;
				} else {
					output[6] = StringFormat.INVALID_PRIORITY;
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
						if (StringFormat.isTimeOrDate(str[i + 2])) {
							nameExistence = false;
						} else {
							nameExistence = true;
						}
					} else if (!StringFormat.isInputIndicator(str[i + 1])) {
						nameExistence = true;
					}
				}
			}
		}

		return output;
	}

	private static String[] handleDeleteInput(String[] str) {
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

	private static String[] handleUpdateInput(String[] str) {
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

	private static String[] handleSearchInput(String[] str) {
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

	private static String[] handleSortInput(String[] str) {
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

}
