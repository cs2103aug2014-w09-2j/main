import java.util.Date;


public class TimeHandler {
	private static final String ERROR_INVALID_END_EARLIER_THAN_START = "End time is earlier than start time.\n";
	private static final String ERROR_INVALID_EARLIER_TIME = "Input %s time is earlier than current time.\n";

	public static ExecutableCommand timingAnalyzer(String start, String end,
			ExecutableCommand tempCommand) {
		Long startTiming = (long) 0;
		Long endTiming = (long) 0;
		Date tempStartDate = new Date();
		Date tempEndDate = new Date();
		Date currentDate = new Date(System.currentTimeMillis());

		if (!start.equals("")) {
			startTiming = Long.valueOf(start);
			tempStartDate = new Date(startTiming);
		}

		if (!end.equals("")) {
			endTiming = Long.valueOf(end);
			tempEndDate = new Date(endTiming);
		}

		if (startTiming != 0 && endTiming != 0) {
			if ((tempStartDate.after(currentDate) || tempStartDate
					.equals(currentDate))
					&& tempEndDate.after(currentDate)
					&& tempStartDate.before(tempEndDate)) {
				tempCommand.setTaskStart(String.valueOf(startTiming));
				tempCommand.setTaskEnd(String.valueOf(endTiming));
			} else {
				if (tempStartDate.before(currentDate)) {
					tempCommand.setErrorMessage(String.format(
							ERROR_INVALID_EARLIER_TIME, StringFormat.START));
				} else if (tempEndDate.before(currentDate)) {
					tempCommand.setErrorMessage(String.format(
							ERROR_INVALID_EARLIER_TIME, StringFormat.END));
				} else {
					tempCommand
							.setErrorMessage(ERROR_INVALID_END_EARLIER_THAN_START);
				}
			}
		} else if (startTiming != 0) {
			if (tempStartDate.after(currentDate)
					|| tempStartDate.equals(currentDate)) {
				tempCommand.setTaskStart(String.valueOf(startTiming));
			} else {
				tempCommand.setErrorMessage(String.format(
						ERROR_INVALID_EARLIER_TIME, StringFormat.START));
			}
		} else if (endTiming != 0) {
			if (tempEndDate.after(currentDate)) {
				tempCommand.setTaskEnd(String.valueOf(endTiming));
			} else {
				tempCommand.setErrorMessage(String.format(
						ERROR_INVALID_EARLIER_TIME, StringFormat.END));
			}
		}

		return tempCommand;
	}

	public static String inputTimingConvertor(String timing) {
		if (timing.equals("")) {
			return "";
		}

		String[] dateTime = timing.trim().split(" ");
		int[] result = { -1, -1, -1, -1, -1 };

		Date convertedDate;

		if (dateTime.length >= 1) {
			result = dateTimeSeparator(dateTime[0], result);
			if (result == null) {
				return null;
			}
		}

		if (dateTime.length == 2) {
			result = dateTimeSeparator(dateTime[1], result);
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

		return String.valueOf(convertedDate.getTime());
	}
	
	private static boolean isValidTiming(int[] input) {
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

	private static boolean isLeapYear(int year) {
		return (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
	}

	private static boolean isSameDate(Date first, Date second) {
		return first.getYear() == second.getYear()
				&& first.getMonth() == second.getMonth()
				&& first.getDay() == second.getDay();
	}

	private static boolean isTimeIndicated(Date d) {
		return d.getHours() != 0 || d.getMinutes() != 0;
	}

	private static boolean checkTimeExistence(int[] result) {
		return result[3] != -1 && result[4] != -1;
	}

	private static boolean checkDateExistence(int[] result) {
		return result[0] != -1 && result[1] != -1 && result[2] != -1;
	}

	private static int[] dateTimeSeparator(String dateTime, int[] result) {
		String[] temp;
		int year = result[0];
		int month = result[1];
		int day = result[2];
		int hour = result[3];
		int minute = result[4];
		String indicator = "";

		if (dateTime.contains("/")) {
			temp = dateTime.trim().split("/");
			day = Integer.parseInt(temp[0]);
			month = Integer.parseInt(temp[1]);
			year = Integer.parseInt(temp[2]);
		} else if (dateTime.contains(":")) {
			if (dateTime.length() == 7) {
				hour = Integer.parseInt(dateTime.substring(0, 2));
				minute = Integer.parseInt(dateTime.substring(3, 5));
				indicator = dateTime.substring(5).toLowerCase();

				if (indicator.equals("pm") && hour != 12) {
					if (hour == -1) {
						hour = hour + 13;
					} else {
						hour = hour + 12;
					}
				} else if (indicator.equals("am") && hour == 12) {
					hour = 0;
				}
			} else {
				hour = Integer.parseInt(dateTime.substring(0, 1));
				minute = Integer.parseInt(dateTime.substring(2, 4));
				indicator = dateTime.substring(4).toLowerCase();

				if (indicator.equals("pm")) {
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
		result[2] = day;
		result[3] = hour;
		result[4] = minute;

		if (!isValidTiming(result)) {
			return null;
		}

		return result;
	}
}
