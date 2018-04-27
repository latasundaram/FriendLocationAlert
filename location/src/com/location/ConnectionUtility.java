package com.location;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ConnectionUtility {
	static String url = "//192.168.1.72:9090/FrienderLocationServer/";
	static String res;

	static String send(String url) {
		try {
			URI uri = new URI("http", url, null);
			System.out.println(url + "11111111111111111");
			String finalurl = uri.toASCIIString();
			URL u = new URL(finalurl);
			HttpURLConnection connection = (HttpURLConnection) u
					.openConnection();
			System.out.println("data base open");
			InputStream is = connection.getInputStream();
			int i = 0;
			StringBuffer data = new StringBuffer("");
			while ((i = is.read()) != -1) {
				data.append((char) i);
			}
			return data.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "error";
	}

}
