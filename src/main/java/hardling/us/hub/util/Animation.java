package hardling.us.hub.util;


import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.Files.ConfigCreator;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Animation {

    public static String title, footer, Header, tabfooter;

    public static void init() {
        ConfigCreator scoreboardFile = SpeakHub.getInst().getScoreboardFile();
        ConfigCreator tabfile = SpeakHub.getInst().getTabFile();

        // Scoreboard Animation

        // Title
        List<String> Titles = Config.SCOREBOARD_TITLE_ANIMATED_ANIMATION;
        AtomicInteger a = new AtomicInteger();
        TaskUtil.runTimerAsync(() -> {
            if (a.get() == Titles.size())
                a.set(0);
            title = Titles.get(a.getAndIncrement());
        }, 0L, (long) (Config.SCOREBOARD_TITLE_ANIMATED_INTERVAL * 20.0D));

        // Footer
        List<String> Footers = Config.SCOREBOARD_FOOTER_ANIMATED_ANIMATION;
        AtomicInteger b = new AtomicInteger();
        TaskUtil.runTimerAsync(() -> {
            if (b.get() == Footers.size())
                b.set(0);
            footer = Footers.get(b.getAndIncrement());
        }, 0L, (long) (Config.SCOREBOARD_FOOTER_ANIMATED_INTERVAL * 20.0D));

        // TabList Animation

        // Header
        List<String> Headers = Config.TABLIST_HEADER_ANIMATED_ANIMATION;
        AtomicInteger c = new AtomicInteger();
        TaskUtil.runTimerAsync(() -> {
            if (c.get() == Headers.size())
                c.set(0);
            Header = Headers.get(c.getAndIncrement());
        }, 0L, (long) (Config.TABLIST_HEADER_ANIMATED_INTERVAL * 20.0D));

        // Footer
        List<String> TabFooters = Config.TABLIST_FOOTER_ANIMATED_ANIMATION;
        AtomicInteger d = new AtomicInteger();
        TaskUtil.runTimerAsync(() -> {
            if (d.get() == TabFooters.size())
                d.set(0);
            tabfooter = TabFooters.get(d.getAndIncrement());
        }, 0L, (long) (Config.TABLIST_FOOTER_ANIMATED_INTERVAL * 20.0D));
    }

    public static String getScoreboardTitle() {
        return title;
    }

    public static String getScoreboardFooter() {
        return footer;
    }

    public static String getTabListHeader() {
        return Header;
    }

    public static String getTabListFooter() {
        return tabfooter;
    }
}
