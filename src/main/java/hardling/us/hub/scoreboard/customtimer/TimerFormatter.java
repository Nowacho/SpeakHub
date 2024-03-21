package hardling.us.hub.scoreboard.customtimer;

import org.apache.commons.lang.time.DurationFormatUtils;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class TimerFormatter {
    private static long MINUTE = TimeUnit.MINUTES.toMillis(1L);

    private static long HOUR = TimeUnit.HOURS.toMillis(1L);

    public static String getRemaining(long millis, boolean milliseconds) {
        return getRemaining(millis, milliseconds, true);
    }

    public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
        if (milliseconds && duration < MINUTE)
            return ((DecimalFormat) (trail ? REMAINING_SECONDS_TRAILING : REMAINING_SECONDS).get()).format(duration * 0.001D) + 's';
        return DurationFormatUtils.formatDuration(duration, ((duration >= HOUR) ? "HH:" : "") + "mm:ss");
    }

    public static ThreadLocal<DecimalFormat> REMAINING_SECONDS = new ThreadLocal<DecimalFormat>() {
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0.#");
        }
    };

    public static ThreadLocal<DecimalFormat> REMAINING_SECONDS_TRAILING = new ThreadLocal<DecimalFormat>() {
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0.0");
        }
    };
}
