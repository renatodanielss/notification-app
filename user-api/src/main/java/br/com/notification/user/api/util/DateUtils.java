package br.com.notification.user.api.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class DateUtils {

    /**
     * Utility method to parse a String to a Date object by FormatType passed
     *
     * @param dateString - String object containing the date to be parsed
     * @param format     - FormatType enum
     * @return java.util.Date object
     */
    public static Date parse(String dateString, FormatType format) throws ParseException {
        return new SimpleDateFormat(format.getFormat()).parse(dateString);
    }

    public enum FormatType {
        HH_MM("HH:mm", null), DD_MM_YYYY("dd/MM/yyyy", 2), MM_YYYY("MM/yyyy", 1), YYYY_MM("yyyy-MM", 0), DD_MM_YYYY_HH_MM("dd/MM/yyyy HH:mm", null), YYYY_MM_DD("yyyy-MM-dd", 2), YYYY_MM_DD_HH_MM_ISO("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", null), YYYY_MM_DD_HH_MM("yyyy-MM-dd'T'HH:mm", null);

        private final String format;

        private final Integer yearIndex;

        FormatType(String format, Integer yearIndex) {
            this.format = format;
            this.yearIndex = yearIndex;
        }

        public String getFormat() {
            return this.format;
        }

        public String prepareDate(String formattedDate) {
            try {
                return prepare(formattedDate);
            } catch (Exception e) {
                return null;
            }
        }

        private String prepare(String formattedDate) {
            if (this.yearIndex == null) {
                return formattedDate;
            }

            String[] splitted = formattedDate.split("/");
            Integer year = Integer.valueOf(splitted[yearIndex]);

            if (year < 1900) {
                if (year > 30)
                    year = 1900 + year;
                else
                    year = 2000 + year;
            }

            splitted[yearIndex] = String.valueOf(year);
            return StringUtils.join(splitted, "/");
        }
    }
}
