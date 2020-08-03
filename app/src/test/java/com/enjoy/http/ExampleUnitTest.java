package com.enjoy.http;


import com.enjoy.http.test_http.HttpCodec;
import com.enjoy.http.test_http.HttpUrl;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    /**
     * http通信
     *
     * @throws Exception
     */
    @Test
    public void testHttp() throws Exception {

        final HttpUrl httpUrl = new
                HttpUrl("http://restapi.amap.com/v3/weather/weatherInfo?city=长沙&key=13cb58f5884f9749287abbead9c658f2");
        System.out.println("protocol:" + httpUrl.getProtocol());
        System.out.println("ip:" + httpUrl.getHost());
        System.out.println("port:" + httpUrl.getPort());
        System.out.println("path:" + httpUrl.getPath());
        System.out.println("host:" + httpUrl.getHost());

        StringBuffer protocol = new StringBuffer();
        //请求行
        protocol.append("GET ");
        protocol.append(httpUrl.getPath());
        protocol.append(" ");
        protocol.append("HTTP/1.1");
        protocol.append("\r\n");

        //http请求头
        protocol.append("Host:");
        protocol.append(httpUrl.getHost());
        protocol.append("\r\n");
        protocol.append("xxx:");
        protocol.append(httpUrl.getHost());
        protocol.append("\r\n");

        //空行
        protocol.append("\r\n");

        //post请求体 get没有

        System.out.println("发送的http报文：" + protocol.toString());

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(httpUrl.getHost(), httpUrl.getPort()));
        //获得输入输出流
        final OutputStream os = socket.getOutputStream();
        final InputStream is = socket.getInputStream();


        new Thread() {
            @Override
            public void run() {
                HttpCodec httpCodec = new HttpCodec();

                try {
                    //读一行  响应行 \r\n
                    String responseLine = httpCodec.readLine(is);
                    System.out.println("响应行：" + responseLine);

                    //读响应头
                    Map<String, String> headers = httpCodec.readHeaders(is);
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }


                    //读响应体 ? 需要区分是以 Content-Length 还是以 Chunked分块编码
                    if (headers.containsKey("Content-Length")) {
                        int length = Integer.valueOf(headers.get("Content-Length"));
                        byte[] bytes = httpCodec.readBytes(is, length);
                        System.out.println("响应:" + new String(bytes));
                    } else {
                        //分块编码
                        String response = httpCodec.readChunked(is);
                        System.out.println("响应:" + response);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();


        System.out.println("发送报文：\n" + protocol);
        os.write(protocol.toString().getBytes());
        os.flush();

        while (true) {
        }
    }

    @Test
    public void testSSLPinLookup() {

        SSLPinLookup.lookup("https://restapi.amap.com/");


    }
}