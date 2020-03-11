package ru.integrotech.airline.register;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import ru.integrotech.airline.utils.PropertyHolder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;


/*class for load data listOf registers holds in RegisterCache*/
public class RegisterLoader{

    private static RegisterLoader instance;
    private static ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static volatile Date lastServerCacheInfoQueryDate = new Date(0L);
    private static volatile Date lastKnownServerCacheDate = new Date(0L);
    private static volatile Date cacheDate = new Date(0L);
    private static final int MILLIS_IN_MINUTE = 60000;
    private static final Logger log = Logger.getLogger(RegisterLoader.class.getName());

    private String[] registerNames;

    private PropertyHolder props;

    private RegisterCache registers;

    private RegisterLoader(Properties properties, String[] registerNames) {
        this.props = new PropertyHolder(properties);
        this.registerNames = registerNames;
        this.registers = new RegisterCache();
        this.loadLocalRegisters();
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        try {
            Path propertiesFilePath = Paths.get(
                    System.getProperty("user.home"), ".ikm", "ikm.properties");
            File propertiesFile = new File(propertiesFilePath.toUri());
            InputStream is = null;
            if (propertiesFile.exists()) {
                is = new FileInputStream(propertiesFile);
                log.fine("Using properties from user home dir");
            } else {
                is = RegisterLoader.class.getClassLoader().getResourceAsStream(
                        "ikm.properties");
                log.fine("using properties from resources");
            }

            if (is != null) {
                props.load(is);
                is.close();

                StringBuilder sb = new StringBuilder("Loaded:\n");
                for (Object key : props.keySet()) {
                    sb.append(key).append('=').append(props.get(key))
                            .append("\n");
                }

                log.info(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

    public RegisterCache getRegisterCache() {
        return registers;
    }

    public static synchronized RegisterLoader getInstance(String...registerNames) {
        if (instance == null) {
            Properties properties = getProperties();
            instance = new RegisterLoader(properties, registerNames);
        }

        return instance;
    }

    /*method for tests only*/
    public static synchronized RegisterLoader updateInstance(String...registerNames) {
        instance = getInstance(registerNames);
        instance.registerNames = registerNames;
        instance.cleanCache();
        instance.updateCache();
        instance.loadLocalRegisters();
        return instance;
    }

    public void lock() {
        log.fine("Aqcuiring read lock ...");
        lock.readLock().lock();
        log.fine("Read lock acquired");
        if (!cacheValid()) {
            lock.readLock().unlock();
            log.fine("Aqcuiring write lock ...");
            lock.writeLock().lock();
            log.fine("Write lock acquired");
            // must recheck cache validity, because another thread might
            // have updated the cache already
            if (!cacheValid()) {
                updateCache();
            }
            lock.readLock().lock();
            lock.writeLock().unlock();
            log.fine("Write lock released");
        }
    }

    public void release() {
        log.fine("Releasing read lock ... ");
        lock.readLock().unlock();
        log.fine("Read lock released");
    }

    private boolean cacheValid() {

        log.fine("Checking cache validity");

        Date serverChacheDate = getServerCacheDate();

        if (cacheDate.equals(serverChacheDate)) {
            log.fine("Cache date have not changed. Return cache valid.");
            return true;
        } else {
            log.fine("Cache date have changed. Return cache invalid.");
            return false;
        }

    }

    private synchronized Date getServerCacheDate() {
        log.fine("Getting  server cache date.");
        Date now = new Date();
        long diffInMilis = now.getTime()
                - lastServerCacheInfoQueryDate.getTime();
        int diffInMinutes = (int) (diffInMilis / MILLIS_IN_MINUTE);
        if (diffInMinutes < this.props.getCacheRecheckMinutes()) {
            log.fine("Recheck period not expired from last server cache info query. Return last known server date.");
            return lastKnownServerCacheDate;
        }
        log.fine("Querying server cache info.");

        try {
            HttpURLConnection request = this.createRequest(this.props.getRegistryServiceBase() + "updateCache/info");
            request.connect();
            JsonElement root = this.parseJson(request.getContent());
            request.disconnect();
            String serverCacheDateStr = root.getAsJsonObject().get("updated")
                    .getAsString();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            lastServerCacheInfoQueryDate = now;
            lastKnownServerCacheDate = df.parse(serverCacheDateStr);
            log.fine("Got server cahe date from update info. Return "
                    + lastKnownServerCacheDate);
            return lastKnownServerCacheDate;
        } catch (Exception e) {
            e.printStackTrace();
            log.severe("Can not acquire server cache date. This should not happen in normal circumstances!!! Return "
                    + lastKnownServerCacheDate);
            return lastKnownServerCacheDate;
        }
    }

    private void updateCache() {
        log.info("Updating cache ...");

        try {
            for (String registerName : this.registerNames) {
                if (!registerName.startsWith("local")) {
                    log.info("Loading remote " + registerName);
                    this.registers.update(registerName, loadJson(registerName));
                }
            }
        } catch ( IOException
                | NullPointerException e) {
            e.printStackTrace();
        }

        cacheDate = lastKnownServerCacheDate;
        log.info("Registry cache updated. Cache buildResult date = " + cacheDate);
    }

    private void cleanCache() {
        log.info("Cleaning cache ...");
        this.registers.clean();
    }
    
    public void loadLocalRegisters() {
    	try {
            for (String registerName : this.registerNames) {
                if (registerName.startsWith("local")) {
                    log.info("Loading local " + registerName);
                    this.registers.update(registerName, loadLocalJson(registerName));
                }
            }
        } catch ( IOException
                | NullPointerException e) {
            e.printStackTrace();
        }
    }


    private JsonElement loadJson(String apiName) throws   JsonIOException, JsonSyntaxException, IOException {
        HttpURLConnection request = this.createRequest(props.getRegistryServiceBase() + apiName);
        request.connect();
        JsonElement result = this.parseJson(request.getContent());
        request.disconnect();
        return result;
    }
    
    private JsonElement loadLocalJson(String apiName) throws JsonIOException, JsonSyntaxException, IOException {
        InputStream is = RegisterCache.class.getClassLoader().getResourceAsStream(String.format("%s%s", apiName, ".json"));
            return this.parseJson(is);
    }

    private JsonElement parseJson(Object object) throws JsonIOException, JsonSyntaxException, IOException {
        JsonParser jp = new JsonParser();
        return jp.parse(new InputStreamReader((InputStream)object, this.props.getEncoding()));
    }

    private HttpURLConnection createRequest(String stringURL) throws IOException {
        URL url = new URL(stringURL);
        return (HttpURLConnection) url.openConnection();
    }



}
