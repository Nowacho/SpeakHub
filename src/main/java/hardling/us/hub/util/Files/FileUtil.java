/*package hardling.us.hub.util.Files;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Scanner;

public class FileUtil {

    public static class getConfig {
        private Plugin plugin;
        private String productKey;
        private String server;
        private String authorization;
        private static final String UNKNOWN = "unknown";
        private static String OS = System.getProperty("os.name").toLowerCase();

        public getConfig(Plugin plugin, String licenseKey, String validationServer, String authorization) {
            this.plugin = plugin;
            this.productKey = licenseKey;
            this.server = validationServer;
            this.authorization = authorization;
        }

        public boolean a5() {
            SpeakHub respO = SpeakHub.getInst();
            PluginDescriptionFile reSpo = respO.getDescription();
            String[] respo = registerFile(this);
            try {
                CC.log("");
                CC.log(CC.getLINELICENSE());
                CC.log("             &aVerifying your License&8...");
                CC.log("");
                while (respo[0].equals("2") && Boolean.parseBoolean(respo[3])) {
                    CC.log(" &8| &aYour License is valid.");
                    CC.log(" &8| &aLoading Plugin.");
                    CC.log(CC.getLINELICENSE());
                    CC.log("");
                    CC.log(CC.getLINENORMAL());
                    CC.log("            &dSpeak&5Hub &8- [&aHardling Development&8]");
                    CC.log("");
                    CC.log(" &8| &dAuthor&8: &f" + reSpo.getAuthors());
                    CC.log(" &8| &dVersion&8: &f" + reSpo.getVersion());
                    CC.log(" &8| &dDiscord&8: &fhttps://discord.hardling.us");
                    CC.log("");
                    CC.log(CC.getLINENORMAL());
                    respO.loadAll();
                    return Boolean.parseBoolean(respo[3]);
                }
                while (respo[0].equals("3") && Boolean.parseBoolean(respo[3]) && Boolean.parseBoolean(respo[3])) {
                    CC.log(" &8| &aYour License is valid.");
                    CC.log(" &8| &aLoading Plugin.");
                    CC.log(CC.getLINENORMAL());
                    CC.log("");
                    CC.log(CC.getLINENORMAL());
                    CC.log(" &8| &cHey, &dSpeak&5Hub &cupdate is currently available!");
                    CC.log(" &8| &cYou're using version:&f " + reSpo.getVersion());
                    CC.log(" &8| &clatest version is &f" + respo[1].split("#")[1]);
                    CC.log("");
                    CC.log(" &8| &aDownload latest here: &9https://discord.hardling.us");
                    CC.log(CC.getLINENORMAL());
                    respO.loadAll();
                    return Boolean.parseBoolean(respo[3]);
                }
                Bukkit.getPluginManager().disablePlugin(plugin);
                Bukkit.getScheduler().cancelTasks(plugin);
            } catch (Exception e) {
                CC.log("&c=====&4============&8[&bERROR&8]&4============&c=====");
                CC.log(reSpo.getPrefix() + " " + e.getMessage());
                CC.log("&c=====&4============&8[&bERROR&8]&4============&c=====");
                Bukkit.getPluginManager().disablePlugin(plugin);
                Bukkit.getScheduler().cancelTasks(plugin);
            }
            CC.log(" &8| &cYour license is invalid.");
            CC.log(" &8| &cReason: " + respo[1]);
            CC.log(" &8| &cSupport on &bhttps://discord.hardling.us");
            CC.log(CC.getLINELICENSE());
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.getScheduler().cancelTasks(plugin);
            return Boolean.parseBoolean(respo[3]);
        }


        private String requestManager(String productKey) throws IOException {
            URL url = new URL(this.server);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "uLicense");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            String hwid = getMac();
            String outString = "{\"hwid\":\"password\",\"licensekey\":\"avain\",\"product\":\"NiceCar\",\"version\":\"dogpoop\"}";
            outString = outString.replaceAll("password", getHWID()).replaceAll("avain", productKey).replaceAll("NiceCar", this.plugin.getName()).replaceAll("dogpoop", this.plugin.getDescription().getVersion());
            byte[] out = outString.getBytes(StandardCharsets.UTF_8);
            con.setRequestProperty("Authorization", this.authorization);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.connect();
            try (OutputStream os = con.getOutputStream()) {
                os.write(out);
            }
            if (!url.getHost().equals(con.getURL().getHost()))
                return "successful_authentication";
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);
                return response.toString();
            }
        }

        private String requestManager2(String productKey) throws IOException {
            URL url = new URL(this.server);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "uLicense");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            String hwid = getMac();
            String outString = "{\"hwid\":\"password\",\"licensekey\":\"avain\",\"product\":\"NiceCar\",\"version\":\"dogpoop\"}";
            outString = outString.replaceAll("password", getHWID()).replaceAll("avain", productKey).replaceAll("NiceCar", this.plugin.getName()).replaceAll("dogpoop", this.plugin.getDescription().getVersion());
            byte[] out = outString.getBytes(StandardCharsets.UTF_8);
            con.setRequestProperty("Authorization", this.authorization);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.connect();
            try (OutputStream os = con.getOutputStream()) {
                os.write(out);
            }
            if (!url.getHost().equals(con.getURL().getHost()))
                return "successful_authentication";
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);
                return response.toString();
            }
        }

        public String[] registerFile(getConfig config) {
            try {
                String response;
                if (this.server.contains("http")) {
                    response = requestManager(this.productKey);
                } else {
                    response = requestManager2(this.productKey);
                }
                if (!response.contains("{"))
                    return new String[]{ "1", "ODD_RESULT", "420" };
                int respLength = response.length();
                String hash = null;
                String version = null;
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response);
                String neekeri = json.get("status_msg").toString();
                String status = json.get("status_overview").toString();
                String statusCode = json.get("status_code").toString();
                if (status.contains("success")) {
                    hash = json.get("status_id").toString();
                    version = json.get("version").toString();
                }
                if (hash != null && version != null) {
                    String[] aa = hash.split("694201337");
                    String hashed = aa[0];
                    String decoded = new String(Base64.getDecoder().decode(hashed));
                    if (!decoded.equals(this.productKey.substring(0, 2) + this.productKey.substring(this.productKey.length() - 2) + this.authorization.substring(0, 2)))
                        return new String[]{ "1", "FAILED_AUTHENTICATION", statusCode, String.valueOf(false) };
                    String time = String.valueOf(Instant.now().getEpochSecond());
                    String unix = time.substring(0, time.length() - 2);
                    long t = Long.parseLong(unix);
                    long hashT = Long.parseLong(aa[1]);
                    if (Math.abs(t - hashT) > 1L)
                        return new String[]{ "1", "FAILED_AUTHENTICATION", statusCode, String.valueOf(false) };
                }
                int statusLength = status.length();
                if (version != null && !version.equals(this.plugin.getDescription().getVersion()) && status
                        .contains("success") && response.contains("success") &&
                        String.valueOf(statusLength).equals("7"))
                    return new String[]{ "3", "OUTDATED_VERSION#" + version, statusCode, String.valueOf(true) };
                statusLength = status.length();
                if (!registerFileLength(statusLength))
                    return new String[]{ "1", neekeri, statusCode, String.valueOf(false) };
                boolean valid = (status.contains("success") && response.contains("success") && String.valueOf(statusLength).equals("7"));
                return new String[]{ valid ? "2" : "1", neekeri, statusCode, String.valueOf(valid) };
            } catch (IOException | org.json.simple.parser.ParseException ex) {
                if (ex.getMessage().contains("429"))
                    return new String[]{ "1", "ERROR", "You are being rate limited because of sending too many requests", String.valueOf(false) };
                ex.printStackTrace();
                return new String[]{ "1", "ERROR", ex.getMessage(), String.valueOf(false) };
            }
        }

        public boolean registerFileLength(int reps) {
            return (reps == 7);
        }

        public String getMac() throws SocketException {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            StringBuilder sb = new StringBuilder();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = networkInterfaces.nextElement();
                byte[] hardwareAddress = ni.getHardwareAddress();
                if (hardwareAddress != null) {
                    for (byte address : hardwareAddress) {
                        sb.append(String.format("%02X", new Object[]{ Byte.valueOf(address) }));
                    }
                    return sb.toString();
                }
            }
            return null;
        }

        public static String getHWID() {
            try {
                if (isWindows())
                    return getWindowsIdentifier();
                if (isMac())
                    return getMacOsIdentifier();
                if (isLinux())
                    return getLinuxMacAddress();
                return "unknown";
            } catch (Exception e) {
                return "unknown";
            }
        }

        private static boolean isWindows() {
            return OS.contains("win");
        }

        private static boolean isMac() {
            return OS.contains("mac");
        }

        private static boolean isLinux() {
            return OS.contains("inux");
        }

        private static String getLinuxMacAddress() throws FileNotFoundException, NoSuchAlgorithmException {
            File machineId = new File("/var/lib/dbus/machine-id");
            if (!machineId.exists())
                machineId = new File("/etc/machine-id");
            if (!machineId.exists())
                return "unknown";
            Scanner scanner = null;
            try {
                scanner = new Scanner(machineId);
                String id = scanner.useDelimiter("\\A").next();
                return hexStringify(sha256Hash(id.getBytes()));
            } finally {
                if (scanner != null)
                    scanner.close();
            }
        }

        private static String getMacOsIdentifier() throws SocketException, NoSuchAlgorithmException {
            NetworkInterface networkInterface = NetworkInterface.getByName("en0");
            byte[] hardwareAddress = networkInterface.getHardwareAddress();
            return hexStringify(sha256Hash(hardwareAddress));
        }

        private static String getWindowsIdentifier() throws IOException, NoSuchAlgorithmException {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(new String[]{ "wmic", "csproduct", "get", "UUID" });
            String result = null;
            InputStream is = process.getInputStream();
            Scanner sc = new Scanner(process.getInputStream());
            try {
                while (sc.hasNext()) {
                    String next = sc.next();
                    if (next.contains("UUID")) {
                        result = sc.next().trim();
                        break;
                    }
                }
            } finally {
                is.close();
            }
            return (result == null) ? "unknown" : hexStringify(sha256Hash(result.getBytes()));
        }

        public static byte[] sha256Hash(byte[] data) throws NoSuchAlgorithmException {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(data);
        }

        public static String hexStringify(byte[] data) {
            StringBuilder stringBuilder = new StringBuilder();
            for (byte singleByte : data)
                stringBuilder.append(Integer.toString((singleByte & 0xFF) + 256, 16).substring(1));
            return stringBuilder.toString();
        }
    }

    // FAKE LICENCE
    public static class getFile {
        private Plugin plugin;
        private String productKey;
        private String server;
        private String authorization;
        private static final String UNKNOWN = "unknown";
        private static String OS = System.getProperty("os.name").toLowerCase();

        public getFile(Plugin plugin, String licenseKey, String validationServer, String authorization) {
            this.plugin = plugin;
            this.productKey = licenseKey;
            this.server = validationServer;
            this.authorization = authorization;
        }

        public boolean a4() {
            PluginDescriptionFile reSpo = SpeakHub.getInst().getDescription();
            String[] respo = registerFile(this);
            try {
                while (!reSpo.getAuthors().contains("YoSoyByProxx") || !reSpo.getName().equals("SpeakHub")) {
                    Bukkit.getPluginManager().disablePlugin(plugin);
                    Bukkit.getScheduler().cancelTasks(plugin);
                }
                while (!SpeakHub.getInst().getLicenseFile().getString("LICENSE").contains("LICENSE-HERE") || !SpeakHub.getInst().getLicenseFile().getString("LICENSE").contains(" ")){
                    Bukkit.getPluginManager().disablePlugin(plugin);
                    Bukkit.getScheduler().cancelTasks(plugin);
                }
                while (respo[0].equals("2") && Boolean.parseBoolean(respo[3])) {
                    return Boolean.parseBoolean(respo[3]);
                }
                while (respo[0].equals("3") && Boolean.parseBoolean(respo[3])) {
                    return Boolean.parseBoolean(respo[3]);
                }
                Bukkit.getPluginManager().disablePlugin(plugin);
                Bukkit.getScheduler().cancelTasks(plugin);
            } catch (Exception e) {
                Bukkit.getPluginManager().disablePlugin(plugin);
                Bukkit.getScheduler().cancelTasks(plugin);
                return Boolean.parseBoolean(respo[3]);
            }
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.getScheduler().cancelTasks(plugin);
            return Boolean.parseBoolean(respo[3]);
        }

        private String requestManager(String productKey) throws IOException {
            URL url = new URL(this.server);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "uLicense");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            String hwid = getMac();
            String outString = "{\"hwid\":\"password\",\"licensekey\":\"avain\",\"product\":\"NiceCar\",\"version\":\"dogpoop\"}";
            outString = outString.replaceAll("password", getHWID()).replaceAll("avain", productKey).replaceAll("NiceCar", this.plugin.getName()).replaceAll("dogpoop", this.plugin.getDescription().getVersion());
            byte[] out = outString.getBytes(StandardCharsets.UTF_8);
            con.setRequestProperty("Authorization", this.authorization);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.connect();
            try (OutputStream os = con.getOutputStream()) {
                os.write(out);
            }
            if (!url.getHost().equals(con.getURL().getHost()))
                return "successful_authentication";
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);
                return response.toString();
            }
        }

        private String requestManager2(String productKey) throws IOException {
            URL url = new URL(this.server);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "uLicense");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            String hwid = getMac();
            String outString = "{\"hwid\":\"password\",\"licensekey\":\"avain\",\"product\":\"NiceCar\",\"version\":\"dogpoop\"}";
            outString = outString.replaceAll("password", getHWID()).replaceAll("avain", productKey).replaceAll("NiceCar", this.plugin.getName()).replaceAll("dogpoop", this.plugin.getDescription().getVersion());
            byte[] out = outString.getBytes(StandardCharsets.UTF_8);
            con.setRequestProperty("Authorization", this.authorization);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.connect();
            try (OutputStream os = con.getOutputStream()) {
                os.write(out);
            }
            if (!url.getHost().equals(con.getURL().getHost()))
                return "successful_authentication";
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);
                return response.toString();
            }
        }

        public String[] registerFile(getFile File) {
            try {
                String response;
                if (this.server.contains("http")) {
                    response = requestManager(this.productKey);
                } else {
                    response = requestManager2(this.productKey);
                }
                if (!response.contains("{"))
                    return new String[]{ "1", "ODD_RESULT", "420" };
                int respLength = response.length();
                String hash = null;
                String version = null;
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response);
                String neekeri = json.get("status_msg").toString();
                String status = json.get("status_overview").toString();
                String statusCode = json.get("status_code").toString();
                if (status.contains("success")) {
                    hash = json.get("status_id").toString();
                    version = json.get("version").toString();
                }
                if (hash != null && version != null) {
                    String[] aa = hash.split("694201337");
                    String hashed = aa[0];
                    String decoded = new String(Base64.getDecoder().decode(hashed));
                    if (!decoded.equals(this.productKey.substring(0, 2) + this.productKey.substring(this.productKey.length() - 2) + this.authorization.substring(0, 2)))
                        return new String[]{ "1", "FAILED_AUTHENTICATION", statusCode, String.valueOf(false) };
                    String time = String.valueOf(Instant.now().getEpochSecond());
                    String unix = time.substring(0, time.length() - 2);
                    long t = Long.parseLong(unix);
                    long hashT = Long.parseLong(aa[1]);
                    if (Math.abs(t - hashT) > 1L)
                        return new String[]{ "1", "FAILED_AUTHENTICATION", statusCode, String.valueOf(false) };
                }
                int statusLength = status.length();
                if (version != null && !version.equals(this.plugin.getDescription().getVersion()) && status
                        .contains("success") && response.contains("success") &&
                        String.valueOf(statusLength).equals("7"))
                    return new String[]{ "3", "OUTDATED_VERSION#" + version, statusCode, String.valueOf(true) };
                statusLength = status.length();
                if (!registerFileLength(statusLength))
                    return new String[]{ "1", neekeri, statusCode, String.valueOf(false) };
                boolean valid = (status.contains("success") && response.contains("success") && String.valueOf(statusLength).equals("7"));
                return new String[]{ valid ? "2" : "1", neekeri, statusCode, String.valueOf(valid) };
            } catch (IOException | org.json.simple.parser.ParseException ex) {
                if (ex.getMessage().contains("429"))
                    return new String[]{ "1", "ERROR", "You are being rate limited because of sending too many requests", String.valueOf(false) };
                ex.printStackTrace();
                return new String[]{ "1", "ERROR", ex.getMessage(), String.valueOf(false) };
            }
        }

        public boolean registerFileLength(int reps) {
            return (reps == 7);
        }

        public String getMac() throws SocketException {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            StringBuilder sb = new StringBuilder();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = networkInterfaces.nextElement();
                byte[] hardwareAddress = ni.getHardwareAddress();
                if (hardwareAddress != null) {
                    for (byte address : hardwareAddress) {
                        sb.append(String.format("%02X", new Object[]{ Byte.valueOf(address) }));
                    }
                    return sb.toString();
                }
            }
            return null;
        }

        public static String getHWID() {
            try {
                if (isWindows())
                    return getWindowsIdentifier();
                if (isMac())
                    return getMacOsIdentifier();
                if (isLinux())
                    return getLinuxMacAddress();
                return "unknown";
            } catch (Exception e) {
                return "unknown";
            }
        }

        private static boolean isWindows() {
            return OS.contains("win");
        }

        private static boolean isMac() {
            return OS.contains("mac");
        }

        private static boolean isLinux() {
            return OS.contains("inux");
        }

        private static String getLinuxMacAddress() throws FileNotFoundException, NoSuchAlgorithmException {
            File machineId = new File("/var/lib/dbus/machine-id");
            if (!machineId.exists())
                machineId = new File("/etc/machine-id");
            if (!machineId.exists())
                return "unknown";
            Scanner scanner = null;
            try {
                scanner = new Scanner(machineId);
                String id = scanner.useDelimiter("\\A").next();
                return hexStringify(sha256Hash(id.getBytes()));
            } finally {
                if (scanner != null)
                    scanner.close();
            }
        }

        private static String getMacOsIdentifier() throws SocketException, NoSuchAlgorithmException {
            NetworkInterface networkInterface = NetworkInterface.getByName("en0");
            byte[] hardwareAddress = networkInterface.getHardwareAddress();
            return hexStringify(sha256Hash(hardwareAddress));
        }

        private static String getWindowsIdentifier() throws IOException, NoSuchAlgorithmException {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(new String[]{ "wmic", "csproduct", "get", "UUID" });
            String result = null;
            InputStream is = process.getInputStream();
            Scanner sc = new Scanner(process.getInputStream());
            try {
                while (sc.hasNext()) {
                    String next = sc.next();
                    if (next.contains("UUID")) {
                        result = sc.next().trim();
                        break;
                    }
                }
            } finally {
                is.close();
            }
            return (result == null) ? "unknown" : hexStringify(sha256Hash(result.getBytes()));
        }

        public static byte[] sha256Hash(byte[] data) throws NoSuchAlgorithmException {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(data);
        }

        public static String hexStringify(byte[] data) {
            StringBuilder stringBuilder = new StringBuilder();
            for (byte singleByte : data)
                stringBuilder.append(Integer.toString((singleByte & 0xFF) + 256, 16).substring(1));
            return stringBuilder.toString();
        }
    }
}*/