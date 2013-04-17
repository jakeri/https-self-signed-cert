package net.jakeri.selfsignedhttps;

import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public Main() throws Exception {

		final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public void checkClientTrusted(final X509Certificate[] chain,
					final String authType) {
			}

			public void checkServerTrusted(final X509Certificate[] chain,
					final String authType) {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };

		// Install the all-trusting trust manager
		final SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
		// Create an ssl socket factory with our all-trusting manager
		final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

		ObjectMapper mapper = new ObjectMapper();

		URL urlObj = new URL("https://127.0.0.1:8000/json");
		HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
		conn.setSSLSocketFactory(sslSocketFactory);
		conn.setHostnameVerifier(new HostnameVerifier() {
			
			public boolean verify(String hostname, SSLSession session) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		System.out.println(urlObj.toString());
		JsonFactory factory = mapper.getFactory();
		JsonParser jp = factory.createJsonParser(conn.getInputStream());
		JsonNode actualObj = mapper.readTree(jp);

		System.out.println(actualObj);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new Main();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
