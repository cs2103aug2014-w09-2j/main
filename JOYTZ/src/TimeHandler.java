//@author A0112162Y

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

public class TimeHandler {
	private static final Logger LOGGER = Logger.getLogger(TimeHandler.class
			.getName());

	/**
	 * Checks the validity of the start time and end time.
	 * 
	 * @param start
	 *            Input start time
	 * @param end
	 *            Input end time
	 * @param tempCommand
	 *            Temporary ExecutableCommand object
	 * @return ExecutableCommand containing the start time and end time if they
	 *         are valid or error message if they are invalid
	 */
	public static ExecutableCommand timeAnalyzer(String start, String end,
			ExecutableCommand tempCommand) {
		Long startTime = (long) 0;
		Long endTime = (long) 0;
		Date tempStartDate = new Date();
		Date tempEndDate = new Date();
		Date currentDate = new Date(System.currentTimeMillis());

		if (!start.equals(StringFormat.EMPTY)) {
			startTime = Long.valueOf(start);
			tempStartDate = new Date(startTime);
		}

		if (!end.equals(StringFormat.EMPTY)) {
			endTime = Long.valueOf(end);
			tempEndDate = new Date(endTime);
		}

		if (startTime != 0 && endTime != 0) {
			if ((tempStartDate.after(currentDate) || tempStartDate
					.equals(currentDate))
					&& tempEndDate.after(currentDate)
					&& tempStartDate.before(tempEndDate)) {
				tempCommand.setTaskStart(String.valueOf(startTime));
				tempCommand.setTaskEnd(String.valueOf(endTime));
			} else {
				if (tempStartDate.before(currentDate)) {
					tempCommand.setErrorMessage(String.format(
							StringFormat.ERROR_INVALID_EARLIER_TIME,
							StringFormat.START));
				} else if (tempEndDate.before(currentDate)) {
					tempCommand.setErrorMessage(String.format(
							StringFormat.ERROR_INVALID_EARLIER_TIME,
							StringFormat.END));
				} else {
					tempCommand
							.setErrorMessage(StringFormat.ERROR_INVALID_END_EARLIER_THAN_START);
				}
			}
		} else if (startTime != 0) {
			if (tempStartDate.after(currentDate)
					|| tempStartDate.equals(currentDate)) {
				tempCommand.setTaskStart(String.valueOf(startTime));
			} else {
				tempCommand.setErrorMessage(String.format(
						StringFormat.ERROR_INVALID_EARLIER_TIME,
						StringFormat.START));
			}
		} else if (endTime != 0) {
			if (tempEndDate.after(currentDate)) {
				tempCommand.setTaskEnd(String.valueOf(endTime));
			} else {
				tempCommand.setErrorMessage(String.format(
						StringFormat.ERROR_INVALID_EARLIER_TIME,
						StringFormat.END));
			}
		}

		// Debugging code
		LOGGER.info("==============\n"
				+ "After analyzing time (TimeHandler):  \n" + "	Start time = "
				+ tempCommand.getTaskStart() + "\n" + "	End time = "
				+ tempCommand.getTaskEnd() + "\n" + "	Error message = "
				+ tempCommand.getErrorMessage() + "\n"
				+ "====================\n");

		return tempCommand;
	}

	/**
	 * Converts the input time into Long value using Java Date class getTime()
	 * method.
	 * 
	 * @param time
	 *            Input time
	 * @return Long format of the input time using Java Date class getTime()
	 *         method or null if the input time is invalid
	 */
	@SuppressWarnings("deprecation")
	public static String inputTimeConvertor(String time) {
		if (time.equals(StringFormat.EMPTY)) {
			return StringFormat.EMPTY;
		}

		String[] dateTime = time.trim().split(StringFormat.SPACE_INDICATOR);
		int[] result = { -1, -1, -1, -1, -1 };

		Date convertedDate;

		if (dateTime.length >= 1) {
			try {
				result = dateTimeSeparator(dateTime[0], result);
			} catch (ParseException e) {
				return null;
			}
			if (result == null) {
				return null;
			}
		}

		if (dateTime.length == 2) {
			try {
				result = dateTimeSeparator(dateTime[1], result);
			} catch (ParseException e) {
				return null;
			}
			if (result == null) {
				return null;
			}
		}

		boolean dateExistence = checkDateExistence(result);
		boolean timeExistence = checkTimeExistence(result);

		if (dateExistence && timeExistence) {
			convertedDate = new Date(result[0] - 1900, result[1] - 1,
					result[2], result[3], result[4]);
		} else if (dateExistence) {
			convertedDate = new Date(result[0] - 1900, result[1] - 1, result[2]);
		} else {
			Date currentDate = new Date(System.currentTimeMillis());
			int currentYear = currentDate.getYear();
			int currentMonth = currentDate.getMonth();
			int currentDay = currentDate.getDate();

			convertedDate = new Date(currentYear, currentMonth, currentDay,
					result[3], result[4]);
		}

		// Debugging code
		LOGGER.info("==============\n" + "After converting (TimeHandler):  \n"
				+ "	Year = " + result[0] + "\n" + "	Month = " + result[1]
				+ "\n" + "	Date = " + result[2] + "\n" + "	Hour = " + result[3]
				+ "\n" + "	Minute = " + result[4] + "\n"
				+ "====================\n");

		return String.valueOf(convertedDate.getTime());
	}

	/**
	 * Separates user input into year, month, date, hour and minute.
	 * 
	 * @param dateTime
	 *            Input string containing either date, time or both
	 * @param result
	 *            Integer array containing year, month, date, hour and minute
	 * @return Integer array containing year, month, date, hour and minute or
	 *         return null if input format is invalid
	 * @throws ParseException
	 *             If there is an error during parsing operation
	 */
	private static int[] dateTimeSeparator(String dateTime, int[] result)
			throws ParseException {
		String[] temp;
		int year = result[0];
		int month = result[1];
		int date = result[2];
		int hour = result[3];
		int minute = result[4];
		String indicator = StringFormat.EMPTY;

		if (dateTime.contains(StringFormat.DATE_INDICATOR)) {
			temp = dateTime.trim().split(StringFormat.DATE_INDICATOR);

			try {
				date = Integer.parseInt(temp[0]);
				month = Integer.parseInt(temp[1]);
				year = Integer.parseInt(temp[2]);
			} catch (Exception e) {
				return null;
			}
		} else if (dateTime.contains(StringFormat.TIME_INDICATOR)) {
			if (dateTime.length() == 7) {
				try {
					hour = Integer.parseInt(dateTime.substring(0, 2));
					minute = Integer.parseInt(dateTime.substring(3, 5));
				} catch (Exception e) {
					return null;
				}

				indicator = dateTime.substring(5).toLowerCase();

				if (indicator.equals(StringFormat.PM_INDICATOR) && hour != 12) {
					if (hour == -1) {
						hour = hour + 13;
					} else {
						hour = hour + 12;
					}
				} else if (indicator.equals(StringFormat.AM_INDICATOR)
						&& hour == 12) {
					hour = 0;
				}
			} else {
				try {
					hour = Integer.parseInt(dateTime.substring(0, 1));
					minute = Integer.parseInt(dateTime.substring(2, 4));
				} catch (Exception e) {
					return null;
				}

				indicator = dateTime.substring(4).toLowerCase();

				if (indicator.equals(StringFormat.PM_INDICATOR)) {
					if (hour == -1) {
						hour = hour + 13;
					} else {
						hour = hour + 12;
					}
				}
			}
		}

		result[0] = year;
		result[1] = month;
		result[2] = date;
		result[3] = hour;
		result[4] = minute;

		if (!isValidTime(result)) {
			return null;
		}

		return result;
	}

	/**
	 * Checks if the year, month, day, hour and minute in the input integer
	 * array is valid or not.
	 * 
	 * @param input
	 *            Integer array containing year, month, date, hour and minute
	 * @return True if the information in the input integer array is valid,
	 *         otherwise return false
	 */
	private static boolean isValidTime(int[] input) {
		int year = input[0];
		int month = input[1];
		int day = input[2];
		int hour = input[3];
		int minute = input[4];
		boolean leapYear = isLeapYear(year);

		if (year != -1 && month != -1 && day != -1) {
			if (year < 0 || month < 0 || month > 12 || day < 1 || day > 31) {
				return false;
			}
			if (month == 2) {
				if (leapYear && day > 29) {
					return false;
				} else if (day > 28) {
					return false;
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day > 30) {
					return false;
				}
			}
		}

		if (hour != -1 && minute != -1) {
			if (hour < 0 || hour > 24 || minute < 0 || minute > 59) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if the input year is leap year or not.
	 * 
	 * @param year
	 *            Input year
	 * @return True if the input year is leap year, otherwise return false
	 */
	private static boolean isLeapYear(int year) {
		return (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * Checks if the input integer array contains time or not.
	 * 
	 * @param result
	 *            Integer array containing year, month, date, hour and minute
	 * @return True if time is existed in the integer array, otherwise return
	 *         false
	 */
	private static boolean checkTimeExistence(int[] result) {
		return result[3] != -1 && result[4] != -1;
	}

	/**
	 * Checks if the input integer array contains date or not.
	 * 
	 * @param result
	 *            Integer array containing year, month, date, hour and minute
	 * @return True if date is existed in the integer array, otherwise return
	 *         false
	 */
	private static boolean checkDateExistence(int[] result) {
		return result[0] != -1 && result[1] != -1 && result[2] != -1;
	}

}
