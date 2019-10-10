package ru.aeroflot.fmc.register;

import static ru.aeroflot.fmc.register.RegisterCache.Type.AIRLINES;
import static ru.aeroflot.fmc.register.RegisterCache.Type.AIRPORTS;
import static ru.aeroflot.fmc.register.RegisterCache.Type.BONUSES;
import static ru.aeroflot.fmc.register.RegisterCache.Type.BONUS_ROUTES;
import static ru.aeroflot.fmc.register.RegisterCache.Type.CITIES;
import static ru.aeroflot.fmc.register.RegisterCache.Type.COUNTRIES;
import static ru.aeroflot.fmc.register.RegisterCache.Type.FLIGHTS;
import static ru.aeroflot.fmc.register.RegisterCache.Type.LOYALTY;
import static ru.aeroflot.fmc.register.RegisterCache.Type.SERVICE_CLASS_LIMITS;
import static ru.aeroflot.fmc.register.RegisterCache.Type.TARIFFS;
import static ru.aeroflot.fmc.register.RegisterCache.Type.WORLD_REGIONS;
import static ru.aeroflot.fmc.register.RegisterCache.Type.WRONG_ROUTES;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ru.aeroflot.fmc.utils.PropertyHolder;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/*class for load data listOf registers holds in RegisterCache*/
public class RegisterLoader {

	private static RegisterLoader instance;
	private static ReadWriteLock lock = new ReentrantReadWriteLock(true);
	private static volatile Date lastServerCacheInfoQueryDate = new Date(0L);
	private static volatile Date lastKnownServerCacheDate = new Date(0L);
	private static volatile Date cacheDate = new Date(0L);
	private static final int MILLIS_IN_MINUTE = 60000;
	private static final Logger log = Logger.getLogger(RegisterLoader.class
			.getName());

	private PropertyHolder props;

	private RegisterCache registers;

	private RegisterLoader(Properties properties) {
		this.props = new PropertyHolder(properties);
		this.registers = new RegisterCache();
	}

	private static Properties getProperties() {
		Properties props = new Properties();
		try {
			Path propertiesFilePath = Paths.get(
					System.getProperty("user.home"), ".fmc", "fmc.properties");
			File propertiesFile = new File(propertiesFilePath.toUri());
			InputStream is = null;
			if (propertiesFile.exists()) {
				is = new FileInputStream(propertiesFile);
				log.fine("Using properties from user home dir");
			} else {
				is = RegisterLoader.class.getClassLoader().getResourceAsStream(
						"fmc.properties");
				log.fine("using properties from resources");
			}

			if (is != null) {
				props.load(is);
				is.close();

				StringBuilder sb = new StringBuilder("Loaded:\n");
				for (Object key : props.keySet()) {
					sb.append("      ").append(key).append('=')
							.append(props.get(key)).append("\n");
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

	public static synchronized RegisterLoader getInstance() {
		if (instance == null) {
			synchronized (RegisterLoader.class) {

				Properties properties = getProperties();
				instance = new RegisterLoader(properties);

			}
		}
		return instance;

	}

	public void lock() {
		log.fine("************* ************* *************");
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

	public Date getCacheDate() {
		return cacheDate;
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
			createSSLContext();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			HttpURLConnection request = this.createRequest(this.props
					.getRegistryServiceBase() + "updateCache/info");
			request.connect();
			JsonElement root = this.parseJson(request.getContent());
			request.disconnect();
			String serverCacheDateStr = root.getAsJsonObject().get("updated")
					.getAsString();
			SimpleDateFormat df = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ssXXX");
			/*
			 * SimpleDateFormat df = new
			 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			 * df.setTimeZone(TimeZone.getTimeZone("UTC"));
			 */
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
			createSSLContext();
			this.registers.update(AIRLINES, loadJson(props.getAirlinesApi()));
			this.registers.update(WORLD_REGIONS,
					loadJson(props.getWorldRegionsApi()));
			this.registers.update(COUNTRIES, loadJson(props.getCountriesApi()));
			this.registers.update(CITIES, loadJson(props.getCitiesApi()));
			this.registers.update(AIRPORTS, loadJson(props.getAirportApi()));
			this.registers.update(TARIFFS, loadJson(props.getTariffApi()));
			this.registers.update(BONUS_ROUTES,
					loadJson(props.getBonusRoutesApi()));
			this.registers.update(BONUSES, loadJson(props.getBonusesApi()));
			this.registers.update(WRONG_ROUTES,
					loadJson(props.getWrongRoutesApi()));
			this.registers.update(LOYALTY, loadJson(props.getLoyaltyApi()));
			this.registers.update(FLIGHTS, loadJson(props.getFlightsApi()));
			this.registers.update(SERVICE_CLASS_LIMITS,
					loadJson(props.getServiceClassLimitApi()));

		} catch (IOException | NoSuchAlgorithmException
				| KeyManagementException e) {
			e.printStackTrace();
		}

		cacheDate = lastKnownServerCacheDate;
		log.info(new Date() + "  Registry cache updated. Cache build date = "
				+ cacheDate);
	}

	private void createSSLContext() throws NoSuchAlgorithmException,
			KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				X509Certificate[] myTrustedAnchors = new X509Certificate[0];
				return myTrustedAnchors;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance(this.props.getSslContext());
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});
	}

	private JsonElement loadJson(String apiName) throws JsonIOException,
			JsonSyntaxException, IOException {
		HttpURLConnection request = this.createRequest(props
				.getRegistryServiceBase() + apiName);
		request.connect();
		JsonElement result = this.parseJson(request.getContent());
		request.disconnect();
		return result;
	}

	private JsonElement parseJson(Object object) throws JsonIOException,
			JsonSyntaxException, IOException {
		JsonParser jp = new JsonParser();
		return jp.parse(new InputStreamReader((InputStream) object, this.props
				.getEncoding()));
	}

	private HttpURLConnection createRequest(String stringURL)
			throws IOException {
		URL url = new URL(stringURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.setRequestProperty(this.props.getRegistryServiceKey(),
				this.props.getRegistryServiceValue());
		return request;
	}

}
