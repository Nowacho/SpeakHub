package hardling.us.hub.tablist.impl.utils;

import hardling.us.hub.tablist.impl.TabListCommons;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@Accessors(chain = true)
@AllArgsConstructor
public class TabLayout {

    private TabColumn column;
    private int slot;

    private String text;
    private int ping;
    private SkinTexture skinTexture;

    public TabLayout(TabColumn column, int slot) {
        this(column, slot, "");
    }

    public TabLayout(TabColumn column, int slot, String text) {
        this(column, slot, text, -1);
    }

    public TabLayout(TabColumn column, int slot, String text, int ping) {
        this(column, slot, text, ping, TabListCommons.defaultTexture);
    }

    public TabLayout(TabColumn column, int slot, String text, SkinTexture texture) {
        this(column, slot, text, -1, texture);
    }

    public TabLayout(TabColumn column, int slot, String text, SkinTexture texture, int ping) {
        this(column, slot, text, ping, texture);
    }
}
