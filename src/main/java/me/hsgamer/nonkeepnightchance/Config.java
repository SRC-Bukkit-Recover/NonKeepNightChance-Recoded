package me.hsgamer.nonkeepnightchance;

import java.util.Collections;

public enum Config {
    PREFIX("message.prefix", "&f[&a&lNKNC&r&f] "),

    KEEP_DAY("message.day-keep", "&f&l[&e&lSunrise&f&l] &bKeep items on death."),
    KEEP_DAY_TITLE("message.day-keep-title", "&d&lSUNRISE"),
    KEEP_DAY_SUBTITLE("message.day-keep-subtitle", "&eKeep items on death"),

    NON_KEEP_NIGHT("message.night-non-keep", "&f&l[&c&lBloody Night&f&l] &bDrop items on death, Be Careful!"),
    NON_KEEP_NIGHT_TITLE("message.night-non-keep-title", "&c&lBLOODY NIGHT"),
    NON_KEEP_NIGHT_SUBTITLE("message.night-non-keep-subtitle", "&eDrop items on death, Be Careful!"),

    KEEP_NIGHT("message.night-keep", "&f&l[&f&lNight&f&l] &bKeep items on death."),
    KEEP_NIGHT_TITLE("message.night-keep-title", "&b&lNIGHT"),
    KEEP_NIGHT_SUBTITLE("message.night-keep-subtitle", "&eKeep items on death"),

    TITLE_FADEIN("title.fadein", 20),
    TITLE_STAY("title.stay", 40),
    TITLE_FADEOUT("title.fadeout", 20),

    WORLDS("settings.worlds", Collections.singletonList(
            "world : 0.5"
    )),
    DELAY("settings.check.delay", 10),
    ASYNC("settings.check.async", false)
    ;
    String path;
    Object def;
    Config(String path, Object def) {
        this.path = path;
        this.def = def;
    }

    public String getPath() {
        return path;
    }

    public Object getDef() {
        return def;
    }
}
