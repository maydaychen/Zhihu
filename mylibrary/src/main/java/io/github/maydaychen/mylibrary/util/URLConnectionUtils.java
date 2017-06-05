package io.github.maydaychen.mylibrary.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class URLConnectionUtils {

	public static String URL="http://221.133.238.110:9080";
//	public static String URL="http://180.166.161.70:9998";
	/**get方式  map传�?头部参数  参数可null **/
	public static String sendRequestByGet(String url,Map<String,String> map) throws Exception{
		HttpURLConnection httpURLConnection;
		InputStream input=null;
		BufferedReader reader=null;
		try {
			URL sendUrl = new URL(url);
			httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setDoOutput(true);        //指示应用程序要将数据写入URL连接,其�?默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(30000); //30秒连接超�?
			httpURLConnection.setReadTimeout(30000);    //30秒读取超�?
			httpURLConnection.setRequestProperty("contentType", "utf-8");
			if(map != null){
				//设置头部参数
				Set<String> keys=map.keySet();
				for(String key:keys){
					httpURLConnection.setRequestProperty(key,map.get(key));
				}
			}
			httpURLConnection.connect();
			input=httpURLConnection.getInputStream();
			reader=new BufferedReader(new InputStreamReader(input,"UTF-8"));
			StringBuilder builder=new StringBuilder();
			String s="";
			while ((s = reader.readLine()) != null) {  
		        	builder.append(s);  
		    }
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				input.close();
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "{\"status\":\"0\",\"msg\":\"请求异常\"}";
	  }
	
	/**post方式  map传�?头部参数  参数可null  json字符串参�?可为null **/
	public static String sendRequestByPost(String url,Map<String,String> map,String json) throws Exception{
		HttpURLConnection httpURLConnection;
		InputStream input = null;
		BufferedReader reader=null;
		try {
			URL sendUrl = new URL(url);
			httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);        //指示应用程序要将数据写入URL连接,其�?默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(15000); //15秒连接超�?
			httpURLConnection.setReadTimeout(15000);    //15秒读取超�?
			httpURLConnection.setRequestProperty("contentType", " application/json;charset=UTF-8");
			if(map != null){
				//设置头部参数
				Set<String> keys=map.keySet();
				for(String key:keys){
					httpURLConnection.setRequestProperty(key,map.get(key));
				}
			}
			httpURLConnection.connect();
			//传入参数
			if(json != null && !"".equals(json)){
				OutputStream output=httpURLConnection.getOutputStream();
				output.write(json.getBytes());
				output.flush();
				output.close();
			}
			input=httpURLConnection.getInputStream();
			reader=new BufferedReader(new InputStreamReader(input,"UTF-8"));
			StringBuilder builder=new StringBuilder();
			String s="";
			while ((s = reader.readLine()) != null) {  
		        	builder.append(s);  
		    }
			if(reader != null){
				reader.close();
			}
			if(input != null){
				input.close();
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"status\":\"0\",\"msg\":\"请求异常\"}";
	  }
	
	public static void main(String[] args) throws Exception {
		
		String resutl=sendRequestByPost("http://221.133.238.110:9080/emp_service/app/user/getUserMsgList", null, "{\"account\":\"18112345679\"}");
		System.out.println(resutl);
	}
	
}
