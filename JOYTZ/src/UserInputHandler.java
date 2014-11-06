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

		String[] arg = input.trim().split(" ");
		String[] parsedInput;

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
			} else if (temp.equals(StringFormat.TO_INDICATOR)) {
				if (isTimeOrDate(str[i + 1])) {
					if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
						endTimeExistence = true;
					} else {
						endDateExistence = true;
					}
				}
			} else if (temp.equals(StringFormat.DUE_ON_INDICATOR)) {
				if (isDate(str[i + 1])) {
					endDateExistence = true;
				} else if (isTime(str[i + 1])) {
					output[4] = StringFormat.INVALID;
					break;
				}
			} else if (temp.equals(StringFormat.FROM_INDICATOR)) {
				if (isTimeOrDate(str[i + 1])) {
					if (str[i + 1].contains(StringFormat.TIME_INDICATOR)) {
						startTimeExistence = true;
					} else {
						startDateExistence = true;
					}
				}
			} else if (temp.equals(StringFormat.ON_INDICATOR)) {
				if (isDate(str[i + 1])) {
					startDateExistence = true;
				} else if (isTime(str[i + 1])) {
					output[3] = StringFormat.INVALID;
					break;
				}
			} else if (temp.equals(StringFormat.DUE_AT_INDICATOR)) {
				if (isTime(str[i + 1])) {
					endTimeExistence = true;
				} else if (isDate(str[i + 1])) {
					output[4] = StringFormat.INVALID;
					break;
				}
			} else if (temp.equals(StringFormat.AT_INDICATOR)) {
				if (isTime(str[i + 1])) {
					startTimeExistence = true;
				} else if (isDate(str[i + 1])) {
					output[3] = StringFormat.INVALID;
					break;
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
				} else if (priority.equals(StringFormat.MEDIUM_PRIORITY)) {
					output[6] = StringFormat.MEDIUM_PRIORITY;
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
		ArrayList<String> output = new ArrayList<String>();

		output.add(StringFormat.DELETE);

		if (str.length > 1) {
			for (int i = 1; i < str.length; i++) {
				output.add(str[i]);
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
			String itemToBeUpdated = "";
			for (int i = 3; i < str.length; i++) {
				itemToBeUpdated = itemToBeUpdated.concat(str[i]);
				itemToBeUpdated = itemToBeUpdated.concat(" ");
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

		String key = "";

		output.add(StringFormat.SEARCH);

		if (str.length > 1) {
			for (int i = 0; i < str.length; i++) {
				String temp = str[i];

				if (nameExistence) {
					key = key.concat(temp);

					if (str.length > i) {
						if (isSearchIndicator(str[i + 1])) {
							output.add(key);
							key = "";
							nameExistence = false;
						} else {
							key = key.concat(" ");
						}
					}
				} else if (descriptionExistence) {
					key = key.concat(temp);

					if (str.length > i) {
						if (isSearchIndicator(str[i + 1])) {
							output.add(key);
							key = "";
							descriptionExistence = false;
						} else {
							key = key.concat(" ");
						}
					}
				} else if (dateTimeExistence) {
					output.add(temp);
					dateTimeExistence = false;
				} else if (startExistence) {
					key = key.concat(temp);

					if (str.length > i) {
						if (isSearchIndicator(str[i + 1])) {
							output.add(key);
							key = "";
							startExistence = false;
						} else {
							key = key.concat(" ");
						}
					}
				} else if (endExistence) {
					key = key.concat(temp);

					if (str.length > i) {
						if (isSearchIndicator(str[i + 1])) {
							output.add(key);
							key = "";
							endExistence = false;
						} else {
							key = key.concat(" ");
						}
					}
				} else if (locationExistence) {
					key = key.concat(temp);

					if (str.length > i) {
						if (isSearchIndicator(str[i + 1])) {
							output.add(key);
							key = "";
							locationExistence = false;
						} else {
							key = key.concat(" ");
						}
					}
				} else if (priorityExistence) {
					output.add(temp);
					priorityExistence = false;
				} else if (isSearchIndicator(temp)) {
					switch (temp) {
					case StringFormat.DESCRIPTION:
						output.add(StringFormat.DESCRIPTION);
						descriptionExistence = true;
						break;
					case StringFormat.START_DATE:
						output.add(StringFormat.START_DATE);
						dateTimeExistence = true;
						break;
					case StringFormat.START_TIME:
						output.add(StringFormat.START_TIME);
						dateTimeExistence = true;
						break;
					case StringFormat.END_DATE:
						output.add(StringFormat.END_DATE);
						dateTimeExistence = true;
						break;
					case StringFormat.END_TIME:
						output.add(StringFormat.END_TIME);
						dateTimeExistence = true;
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
					case StringFormat.PRIORITY:
						output.add(StringFormat.PRIORITY);
						priorityExistence = true;
						break;
					default:
						output.add(StringFormat.INVALID);
					}
				} else {
					output.add(StringFormat.ADD);
					key = key.concat(temp);

					if (str.length > i) {
						if (isSearchIndicator(str[i + 1])) {
							output.add(key);
							key = "";
							nameExistence = false;
						} else {
							key = key.concat(" ");
							nameExistence = true;
						}
					}
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
			for (int i = 0; i < str.length; i++) {
				output.add(str[i]);
			}
		}

		String[] outputArr = new String[output.size()];
		return output.toArray(outputArr);
	}

	private static boolean isDate(String temp) {
		return temp.contains(StringFormat.DATE_INDICATOR);
	}

	private static boolean isTime(String temp) {
		return temp.contains(StringFormat.TIME_INDICATOR);
	}

	private static boolean isSearchIndicator(String indicator) {
		return indicator.equals(StringFormat.DESCRIPTION)
				|| indicator.equals(StringFormat.START_DATE)
				|| indicator.equals(StringFormat.START_TIME)
				|| indicator.equals(StringFormat.END_DATE)
				|| indicator.equals(StringFormat.END_TIME)
				|| indicator.equals(StringFormat.LOCATION)
				|| indicator.equals(StringFormat.PRIORITY)
				|| indicator.equals(StringFormat.START)
				|| indicator.equals(StringFormat.END);
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
