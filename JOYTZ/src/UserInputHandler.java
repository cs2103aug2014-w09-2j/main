public class UserInputHandler {
	public static String[] convertUserInput(String input) {
		int index;
		String temp1, temp2;

		if (input.contains("due")) {
			index = input.indexOf("due");
			temp1 = input.substring(index + 4, index + 6);
			temp2 = input.substring(index + 6);
			input = input.substring(0, index + 3);
			if (temp1.equals("on")) {
				input = input.concat("on");
			} else {
				input = input.concat("at");
			}
			input = input.concat(temp2);
		}

		String[] arg = input.trim().split(" ");
		String[] parsedInput;

		System.out.println(input);

		switch (arg[0]) {
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
		String[] output = { StringFormat.ADD, "", "", "", "", "", "" };
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
				output[1] = output[1].concat(" ");
				if (temp.contains(StringFormat.DESC_OR_ITEM_INDICATOR)) {
					output[1] = output[1].substring(0, output[1].length() - 2);
					nameExistence = false;
					descriptionExistence = true;
				} else if (str.length > i + 1) {
					if (isInputIndicator(str[i + 1])
							|| (isAmbiguousInputIndicator(str[i + 1]) && isTimeOrDate(str[i + 2]))) {
						nameExistence = false;
					}
				}
			} else if (descriptionExistence) {
				output[2] = output[2].concat(temp);
				output[2] = output[2].concat(" ");

				if (str.length > i + 1) {
					if (isInputIndicator(str[i + 1])
							|| (isAmbiguousInputIndicator(str[i + 1]) && isTimeOrDate(str[i + 2]))) {
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
					output[3] = output[3].concat(" ");
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
					output[4] = output[4].concat(" ");
					output[4] = output[4].concat(temp);
				} else {
					output[4] = temp;
				}

				endTimeExistence = false;
			} else if (locationExistence) {
				if (!output[5].equals("")) {
					output[5] = output[5].concat(" ");
					output[5] = output[5].concat(temp);
				} else {
					output[5] = temp.substring(1);
				}

				if (str.length > i + 1 && isInputIndicator(str[i + 1])) {
					locationExistence = false;
				}
			} else if (temp.equals(StringFormat.TO_INDICATOR)
					|| temp.equals(StringFormat.DUE_ON_INDICATOR)) {
				if (isTimeOrDate(str[i + 1])) {
					if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
						endTimeExistence = true;
					} else {
						endDateExistence = true;
					}
				}
			} else if (temp.equals(StringFormat.ON_INDICATOR)
					|| temp.equals(StringFormat.FROM_INDICATOR)) {
				if (isTimeOrDate(str[i + 1])) {
					if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
						startTimeExistence = true;
					} else {
						startDateExistence = true;
					}
				}
			} else if (temp.equals(StringFormat.DUE_AT_INDICATOR)) {
				endTimeExistence = true;
			} else if (temp.equals(StringFormat.AT_INDICATOR)) {
				if (isTimeOrDate(str[i + 1])) {
					startTimeExistence = true;
				}
			} else if (temp.contains(StringFormat.LOCATION_INDICATOR)) {
				output[5] = temp.substring(1);

				if (str.length > i + 1) {
					if (isAmbiguousInputIndicator(str[i + 1])) {
						if (str.length > i + 2 && !isTimeOrDate(str[i + 2])) {
							locationExistence = true;
						}
					} else if (!isInputIndicator(str[i + 1])) {
						locationExistence = true;
					}
				}
			} else if (temp.contains(StringFormat.PRIORITY_INDICATOR)) {
				String priority = temp.substring(1).toLowerCase();

				if (priority.equals(StringFormat.IMPORTANT)) {
					output[6] = StringFormat.HIGH_PRIORITY;
				} else if (priority.equals(StringFormat.UNIMPORTANT)) {
					output[6] = StringFormat.LOW_PRIORITY;
				} else {
					output[6] = StringFormat.INVALID_PRIORITY;
				}
			} else {
				output[1] = output[1].concat(temp);
				output[1] = output[1].concat(" ");

				if (str.length > i + 1) {
					if (temp.contains(StringFormat.DESC_OR_ITEM_INDICATOR)) {
						output[1] = output[1].substring(0,
								output[1].length() - 2);
						descriptionExistence = true;
					} else if (isAmbiguousInputIndicator(str[i + 1])) {
						if (isTimeOrDate(str[i + 2])) {
							nameExistence = false;
						} else {
							nameExistence = true;
						}
					} else if (!isInputIndicator(str[i + 1])) {
						nameExistence = true;
					}
				}
			}
		}

		return output;
	}

	private static String[] handleDeleteInput(String[] str) {
		String[] output = { StringFormat.DELETE, "" };

		if (str.length > 1) {
			output[1] = str[1];
		}

		return output;
	}

	private static String[] handleUpdateInput(String[] str) {
		String[] output = { StringFormat.UPDATE, "", "", "" };

		if (str.length > 1) {
			output[1] = str[1];
		}
		if (str.length > 2) {
			output[2] = str[2];
		}
		if (str.length > 3) {
			String itemToBeUpdated = "";
			for (int i = 3; i < str.length; i++) {
				itemToBeUpdated = itemToBeUpdated.concat(str[i]);
				itemToBeUpdated = itemToBeUpdated.concat(" ");
			}
			output[3] = itemToBeUpdated;
		}

		return output;
	}

	private static String[] handleSearchInput(String[] str) {
		String[] output = { StringFormat.SEARCH, "", "" };

		if (str.length > 1) {
			output[1] = str[1];
		}

		if (str.length > 2) {
			String itemToSearch = "";
			for (int i = 2; i < str.length; i++) {
				itemToSearch = itemToSearch.concat(str[i]);
				itemToSearch = itemToSearch.concat(" ");
			}
			output[2] = itemToSearch;
		}

		return output;
	}

	private static String[] handleSortInput(String[] str) {
		String[] output = { StringFormat.SORT, "" };

		if (str.length > 1) {
			output[1] = str[1];
		}

		return output;
	}

	private static boolean isInputIndicator(String indicator) {
		return indicator.equals(StringFormat.TIME_INDICATOR)
				|| indicator.equals(StringFormat.DUE_AT_INDICATOR)
				|| indicator.equals(StringFormat.DUE_ON_INDICATOR)
				|| indicator.contains(StringFormat.LOCATION_INDICATOR)
				|| indicator.contains(StringFormat.PRIORITY_INDICATOR);
	}

	private static boolean isTimeOrDate(String str) {
		return str.contains(StringFormat.TIME_INDICATOR)
				|| str.contains(StringFormat.DATE_INDICATOR);
	}

	private static boolean isAmbiguousInputIndicator(String indicator) {
		return indicator.equals(StringFormat.TO_INDICATOR)
				|| indicator.equals(StringFormat.FROM_INDICATOR)
				|| indicator.equals(StringFormat.AT_INDICATOR)
				|| indicator.equals(StringFormat.ON_INDICATOR);
	}
}
