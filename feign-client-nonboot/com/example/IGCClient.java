package com.example;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;



public class IGCClient {

	private DefaultHttpClient httpClient;
	private List<Cookie> cookies;	
	
	
	public IGCClient() {
	}

	public void login() throws ClientProtocolException, IOException, AuthenticationException{
		String url = "https://igc-bccldvwas01.paas.bmogc.net:9445/ibm/iis/igc-rest/v1/types";

		
		/*
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials
		 = new UsernamePasswordCredentials("user1", "user1Pass");
		provider.setCredentials(AuthScope.ANY, credentials);

		HttpClient client = HttpClientBuilder.create()
		  .setDefaultCredentialsProvider(provider)
		  .build();*/
	    HttpParams params = new BasicHttpParams();

	    Credentials credentials = new UsernamePasswordCredentials("IGCIDPDATDEVSVC", "t7v@fGS6zx%d");
	    DefaultHttpClient client = new DefaultHttpClient(params);
	    client.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);		

		httpClient = wrapClient(client);
		
		HttpGet getRequest = new HttpGet(url);
		//getRequest.addHeader(new BasicScheme().authenticate(credentials, getRequest));

		
		HttpResponse response = httpClient.execute(getRequest  );
		

		cookies = httpClient.getCookieStore().getCookies();
		
		log(response);
		
		getRequest.releaseConnection();
				
	}
	
	private void log(HttpResponse response){
		int statusCode = response.getStatusLine()
				  .getStatusCode();
				
		System.out.println(response.getStatusLine().getStatusCode());
		Header[] headers = response.getAllHeaders(); 
		for (Header header:headers){
			System.out.println(header.getName()+":"+header.getValue());
		}		
	}
	
	public void logout() throws ClientProtocolException, IOException{
		String url = "https://igc-bccldvwas01.paas.bmogc.net:9445/ibm/iis/igc-rest/v1/logout/";
		HttpGet getRequest = new HttpGet(url);
	BasicCookieStore reqCookieStore = new BasicCookieStore();

		for ( Cookie aCK: cookies){
			reqCookieStore.addCookie(aCK);
		}
		httpClient.setCookieStore(reqCookieStore);
		
		HttpResponse response = httpClient.execute(getRequest);
		log(response);
		getRequest.releaseConnection();
		
	}
	
	 public static DefaultHttpClient wrapClient(DefaultHttpClient base) {
	        try {
	            SSLContext ctx = SSLContext.getInstance("TLS");
	            X509TrustManager tm = new X509TrustManager() {
	 
	                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
	                }
	 
	                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
	                }
	 
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	            };
	            X509HostnameVerifier verifier = new X509HostnameVerifier() {
	 
	                @Override
	                public void verify(String string, SSLSocket ssls) throws IOException {
	                }
	 
	                @Override
	                public void verify(String string, X509Certificate xc) throws SSLException {
	                }
	 
	                @Override
	                public void verify(String string, String[] strings, String[] strings1) throws SSLException {
	                }
	 
	                @Override
	                public boolean verify(String string, SSLSession ssls) {
	                    return true;
	                }
	            };
	            ctx.init(null, new TrustManager[]{tm}, null);
	            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
	            ssf.setHostnameVerifier(verifier);
	            ClientConnectionManager ccm = base.getConnectionManager();
	            SchemeRegistry sr = ccm.getSchemeRegistry();
	            sr.register(new Scheme("https", ssf, 443));
	            DefaultHttpClient newHttpClient = new DefaultHttpClient(ccm, base.getParams());
	            newHttpClient.setCredentialsProvider(base.getCredentialsProvider());
	            return newHttpClient;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return null;
	        }
	    }

	  public static void main(String...strings) throws ClientProtocolException, IOException, AuthenticationException{
		  IGCClient igcClient = new IGCClient();
		  igcClient.login();
		  
		  igcClient.logout();
	  }
	 
}
