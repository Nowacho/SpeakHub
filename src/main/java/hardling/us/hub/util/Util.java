package hardling.us.hub.util;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Util {
    public static String getDate() {
        return (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
    }

    public static String getHour() {
        return (new SimpleDateFormat("HH:mm")).format(new Date());
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static long parse(String input) {
        if (input == null || input.isEmpty())
            return -1L;
        long result = 0L;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convert(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    private static String secondsToMinutes(int seconds) {
        if(seconds < 60) {
            return "00:" + (seconds < 10 ? "0" + seconds : "" + seconds);
        }

        int secondsModulo = seconds % 60;
        int minutes = seconds / 60;

        return (minutes < 10 ? "0" + minutes : minutes) +
                ":" + (secondsModulo < 10 ? "0" + secondsModulo : secondsModulo);
    }

    private static String secondsToHours(int seconds) {
        if(seconds < 60) {
            return "00:00:" + (seconds < 10 ? "0" + seconds : "" + seconds);
        }

        int secondsModulo = seconds % 60;
        int minutes = seconds / 60;

        String secondsDisplay = secondsModulo < 10 ? "0" + secondsModulo : "" + secondsModulo;

        if(minutes < 60) {
            return "00:" + (minutes < 10 ? "0" + minutes : "" + minutes) + ":" + secondsDisplay;
        }

        int minutesModulo = minutes % 60;
        int hours = minutes / 60;

        return (hours < 10 ? "0" + hours : hours) + ":" + (minutesModulo < 10 ? "0" + minutesModulo : "" + minutesModulo) + ":" + secondsDisplay;
    }


    public static String formatTime(long time, FormatType type) {
        switch(type) {
            case MILLIS_TO_SECONDS: return "" + Math.round((time / 1000f) * 10f) / 10f;
            case MILLIS_TO_MINUTES: return secondsToMinutes((int) (time / 1000));
            case MILLIS_TO_HOURS: return secondsToHours((int) (time / 1000));
            case SECONDS_TO_MINUTES: return secondsToMinutes((int) time);
            case SECONDS_TO_HOURS: return secondsToHours((int) time);

            default: return "";
        }
    }

    public enum FormatType {
        MILLIS_TO_SECONDS, MILLIS_TO_MINUTES, MILLIS_TO_HOURS, SECONDS_TO_MINUTES, SECONDS_TO_HOURS
    }

    public static int parseSeconds(String value) {
        if(isInteger(value)) return Math.abs(Integer.parseInt(value));
        if(value.equalsIgnoreCase("0s")) return 0;

        value = value.toLowerCase();
        int seconds = 0;

        for(TimeFormat format : TimeFormat.values()) {
            if(!value.contains(format.getTimeChar())) continue;

            String[] split = value.split(format.getTimeChar());
            if(!isInteger(split[0])) continue;

            seconds += Math.abs(Integer.parseInt(split[0])) * format.getSeconds();
            if(split.length > 1) value = split[1];
        }

        return seconds == 0 ? -1 : seconds;
    }

    private static long convert(int value, char unit) {
        switch (unit) {
            case 'y':
                return value * TimeUnit.DAYS.toMillis(365L);
            case 'M':
                return value * TimeUnit.DAYS.toMillis(30L);
            case 'd':
                return value * TimeUnit.DAYS.toMillis(1L);
            case 'h':
                return value * TimeUnit.HOURS.toMillis(1L);
            case 'm':
                return value * TimeUnit.MINUTES.toMillis(1L);
            case 's':
                return value * TimeUnit.SECONDS.toMillis(1L);
        }
        return -1L;
    }

    private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}");


    private static final long MINUTE = TimeUnit.MINUTES.toMillis(1L);

    private static final long HOUR = TimeUnit.HOURS.toMillis(1L);

    public static String getRemaining(long millis, boolean milliseconds) {
        return getRemaining(millis, milliseconds, true);
    }

    public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
        if (milliseconds && duration < MINUTE)
            return ((trail ? REMAINING_SECONDS_TRAILING : REMAINING_SECONDS).get()).format(duration * 0.001D) + " seconds";
        return DurationFormatUtils.formatDuration(duration, ((duration >= HOUR) ? "HH:" : "") + "mm:ss");
    }

    public static final ThreadLocal<DecimalFormat> REMAINING_SECONDS = new ThreadLocal<DecimalFormat>() {
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0.#");
        }
    };

    public static final ThreadLocal<DecimalFormat> REMAINING_SECONDS_TRAILING = new ThreadLocal<DecimalFormat>() {
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0.0");
        }
    };

    public static boolean isUUID(String string) {
        return UUID_PATTERN.matcher(string).find();
    }

    public static String format(Number number) {
        return format(number, 10);
    }

    public static String format(Number number, int decimalPlaces) {
        return format(number, decimalPlaces, RoundingMode.HALF_DOWN);
    }

    public static String format(Number number, int decimalPlaces, RoundingMode roundingMode) {
        Preconditions.checkNotNull(number, "The number cannot be null");
        return (new BigDecimal(number.toString())).setScale(decimalPlaces, roundingMode).stripTrailingZeros().toPlainString();
    }

    @Getter
    @AllArgsConstructor
    public enum TimeFormat {

        DAY("d", TimeUnit.DAYS.toSeconds(1L)),
        HOUR("h", TimeUnit.HOURS.toSeconds(1L)),
        MINUTE("m", TimeUnit.MINUTES.toSeconds(1L)),
        SECOND("s", 1L);

        private final String timeChar;
        private final long seconds;
    }

}