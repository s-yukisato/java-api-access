package sample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Access {

	public static String getResult(String urlString) {
		HttpURLConnection conn = null;
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.connect();
			//			パラメータを送る
			//			PrintWriter out = new PrintWriter(conn.getOutputStream());
			//			out.write();
			//			out.flush();
			//			out.close();
			final int status = conn.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				final InputStream in = conn.getInputStream();
				String encoding = conn.getContentEncoding();
				if (null == encoding) {
					encoding = "UTF-8";
				}
				final InputStreamReader inReader = new InputStreamReader(in, encoding);
				final BufferedReader bufReader = new BufferedReader(inReader);
				String line = null;
				// 1行ずつテキストを読み込む
				while ((line = bufReader.readLine()) != null) {
					result.append(line);
				}
				bufReader.close();
				inReader.close();
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				// コネクションを切断
				conn.disconnect();
			}
		}
		return result.toString();
	}
}
