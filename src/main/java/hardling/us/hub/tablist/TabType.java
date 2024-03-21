package hardling.us.hub.tablist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TabType {

    DEFAULT,
    WEIGHT;

    public static TabType getType(String name){
        switch (name.toLowerCase()) {
            case "normal":
                return DEFAULT;
            default:
                return WEIGHT;
        }
    }

}
