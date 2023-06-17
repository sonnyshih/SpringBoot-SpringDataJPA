package com.example.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateTimeUtil {
	public static String getNowTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(timestamp);
	}

	public static Timestamp getTimestamp() {
		Long datetime = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(datetime);
		return timestamp;
	}

	// Timestamp convert to Date
	public static Date getDateByTimestamp(Timestamp timestamp){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String DateString = dateFormat.format(timestamp);
		Date date = null;
		try {
			date = dateFormat.parse(DateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return date;
	}

	/**
	 * current Date >= compare date ?
	 */
	public static boolean greaterThanOrEqualTo(String currentDateString, String compareDateString) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date currentDate = dfs.parse(currentDateString);
			Date compareDate = dfs.parse(compareDateString);

			if (currentDate.after(compareDate) || currentDate.equals(compareDate)) {
				return true;
			}

		} catch (ParseException e) {
//			throw new RuntimeException(e);
		}

		return false;
	}

	/**
	 * current Date <= compare Date ?
	 */
	public static boolean lessThanOrEqualTo(String currentDateString, String compareDateString) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date currentDate = dfs.parse(currentDateString);
			Date compareDate = dfs.parse(compareDateString);

			if (currentDate.before(compareDate) || currentDate.equals(compareDate)) {
				return true;
			}

		} catch (ParseException e) {
//			throw new RuntimeException(e);
		}

		return false;

	}

	public static Date getStringToDate(String pattern, String value) {
		Date date = null;
//        String value = "2019/12/31";
//        String pattern = "yyyy-MM-dd HH:mm:ss";
		try {
			date = new SimpleDateFormat(pattern).parse(value);
		} catch (ParseException e) {
			System.out.printf("Parse date string [%1$s] with pattern [%2$s] error.%n", value, pattern);
			return null;
			// Parse date string [2019/12/31] with pattern [yyyy-MM-dd] error.
		}

		return date;
	}

	public static java.sql.Date getStringToSQLDate(String pattern, String value) {
		java.sql.Date date = null;
//        String value = "2019/12/31";
//        String pattern = "yyyy-MM-dd HH:mm:ss";
		try {
			date = new java.sql.Date(new SimpleDateFormat(pattern).parse(value).getTime());
		} catch (ParseException e) {
//            System.out.printf("Parse date string [%1$s] with pattern [%2$s] error.%n", value, pattern);
//            return null;
			// Parse date string [2019/12/31] with pattern [yyyy-MM-dd] error.
		}

		return date;
	}

	public static String getDatetimeAddSeconds(String dateString, int seconds) {

		// 設定日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 進行轉換
		Date date = null;
		Date newDate = null;

		try {
			date = dateFormat.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.SECOND, seconds);

			newDate = cal.getTime();
//            System.out.println("Plus 5 seconds: " + sdf.format(newDate));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateFormat.format(newDate);
	}

	public static String getDatetimeAddHours(String dateString, int hours) {

		// 設定日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 進行轉換
		Date date = null;
		Date newDate = null;

		try {
			date = dateFormat.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR, hours);

			newDate = cal.getTime();
//            System.out.println("Plus 5 seconds: " + sdf.format(newDate));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateFormat.format(newDate);
	}

	public static long getSecondsToNow(String timeString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date startTimeDate = null;

		try {
			startTimeDate = dateFormat.parse(timeString);

			Calendar startTimeCalendar = Calendar.getInstance();
			startTimeCalendar.setTime(startTimeDate);

			Calendar nowCalendar = Calendar.getInstance();

			long diff = nowCalendar.getTimeInMillis() - startTimeCalendar.getTimeInMillis();

			return diff / 1000;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static String localDateTimeToString(LocalDateTime localDateTime, int type) {
		DateTimeFormatter dateFormatter = null;
		if (type == 1) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		} else if (type == 2) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		} else if (type == 3) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		} else if (type == 4) {
			dateFormatter = DateTimeFormatter.ofPattern("HH:mm");
		} else if (type == 5) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		} else if (type == 6) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月");
		} else if (type == 7) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM");
		} else if (type == 8) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		}
		return dateFormatter.format(localDateTime);
	}

	public static LocalDate stringToLocalDate(String localDate, int type) {
		DateTimeFormatter dateFormatter = null;
		if (type == 1) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		} else if (type == 2) {
			dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		}

		return LocalDate.parse(localDate, dateFormatter);
	}

	public static LocalTime stringToLocalTime(String localTime, int type) {
		DateTimeFormatter dateFormatter = null;
		if (type == 1) {
			dateFormatter = DateTimeFormatter.ofPattern("HH:mm");
		}

		return LocalTime.parse(localTime, dateFormatter);
	}

	public static List<Map<String, Object>> getMonthDayMap(String dateString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		List<Map<String, Object>> list =  new ArrayList<>();

		try {
//            System.out.println("Original Date: " + dateString);
			date = sdf.parse(dateString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, 1);
			int month = calendar.get(Calendar.MONTH);

			while (calendar.get(Calendar.MONTH) == month) {

				Map<String, Object> map = new HashMap<>();
				map.put("DayNum", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
				map.put("Date", sdf.format(calendar.getTime()));
				list.add(map);

				// 1: 日, 2: 一, 3: 二 , 4: 三 , 5:四, 6: 五 , 7: 六:
//                System.out.println("日期:" + sdf.format(calendar.getTime()) + " 星期 " + DAY.get(calendar.get(Calendar.DAY_OF_WEEK)) );

				calendar.add(Calendar.DATE, 1);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static List<Map<String, Object>> generateMonthCalendar(List<Map<String, Object>> dayList){
		String dateDummy = "";

		// 當月第一天是星期幾
		Map<String, Object> firstData = dayList.get(0);
		int firstDay = Integer.valueOf((String) firstData.get("DayNum")) ;
//		System.out.println("當月第一天 -- day="+firstDay + "## 星期 " + DAY.get(firstDay));

		// 第一天星期幾，就把前面補滿空白星期 到星期日 (例: 第1天值是 3就是期星二，前面就要補2個空白日)
		for (int i=1; i< firstDay; i++){
			Map<String, Object> entity = new HashMap<>();
			entity.put("DayNum", String.valueOf(i));
			entity.put("Date", dateDummy);
			dayList.add(0, entity);
		}

		// 當月最後一天是星期幾
		int lastIndex = dayList.size() - 1;
		Map<String, Object> lastData = dayList.get(lastIndex);
		int lastDay = Integer.valueOf((String)lastData.get("DayNum")) ;
//		System.out.println("當月最後一天 -- day="+lastDay + "## 星期 " + DAY.get(lastDay));

		// 如果最後一天不是星期六時
		if (lastDay!=7) {
			// 最後一天星期幾，就把「後面」補滿空白星期 到星期六 (例: 最後1天值是 4就是期星三，「後面」就要補3個空白日)
			for (int i = lastDay+1; i <= 7; i++) {
				Map<String, Object> entity = new HashMap<>();
				entity.put("DayNum", String.valueOf(i));
				entity.put("Date", dateDummy);
				dayList.add(entity);
			}
		}

		return dayList;
	}

}
