package hardling.us.hub.util.cooldown;


import hardling.us.hub.util.Util;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ScheduledExecutorService;

@Getter
public abstract class Timer {

    protected final String name;

    protected final ScheduledExecutorService executor;
    protected final int delay;
    protected final boolean persistable;

    @Setter protected Util.FormatType format;
    @Setter protected String expiryMessage;

    protected Timer(ScheduledExecutorService executor, String name, int delay, boolean persistable) {
        this.name = name;

        this.executor = executor;
        this.delay = delay;
        this.persistable = persistable;
    }

    public abstract void disable();
}
