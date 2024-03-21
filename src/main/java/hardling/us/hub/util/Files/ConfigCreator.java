package hardling.us.hub.util.Files;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigCreator extends YamlConfiguration {

    @Getter
    private final File file;

    public ConfigCreator(String name) throws RuntimeException {
        this.file = new File(SpeakHub.getInst().getDataFolder(), name);
        if (!this.file.exists()) {
            SpeakHub.getInst().saveResource(name, false);
        }
        try {
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            CC.log("");
            CC.log("&cError occurred while loading " + name);
            CC.log("");
            Stream.of(e.getMessage().split("\n")).forEach(line -> CC.log(line));
            CC.log("");
            throw new RuntimeException();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigurationSection getSection(String name) {
        return super.getConfigurationSection(name);
    }

    @Override
    public int getInt(String path) {
        return super.getInt(path, 0);
    }



    @Override
    public boolean getBoolean(String path) {
        return super.getBoolean(path, false);
    }

    @Override
    public String getString(String path) {
        return CC.translate(super.getString(path, ""));
    }

    @Override
    public List<String> getStringList(String path) {
        return super.getStringList(path).stream().map(CC::translate).collect(Collectors.toList());
    }
}
