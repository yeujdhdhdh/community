package life.study.community.provider;

import life.study.community.dto.AccessTokenDto;
import life.study.community.unilt.SslUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


@Component
public class Provide {

    public  String httpGet(String accessToken) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse=null;
        String finalString = null;
        HttpGet httpGet = new HttpGet("https://api.github.com/user");
        /**公共参数添加至httpGet*/

            /**header中通用属性*/
            httpGet.setHeader("Accept","*/*");
            httpGet.setHeader("Accept-Encoding","gzip, deflate");
            httpGet.setHeader("Cache-Control","no-cache");
            httpGet.setHeader("Connection", "keep-alive");
            httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpGet.setHeader("Authorization","token "+accessToken);

            try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            finalString= EntityUtils.toString(entity, "UTF-8");
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(finalString);

        return finalString;
    }
    public  String sendPost( AccessTokenDto accessTokenDto) {
        String url="https://github.com/login/oauth/access_token?client_id=fd1d5ffc3e73e6451389&client_secret=9f03ff12c788b6b001bad9d2d55b1a1418d890d6&redirect_uri=http://localhost:8080/callback&state=1&code="+accessTokenDto.getCode();
        String param="";
        System.out.println(url);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";


        try {
            URL realUrl = new URL(url);
            if("https".equalsIgnoreCase(realUrl.getProtocol())){
                SslUtils.ignoreSsl();
            }
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(result);
        String token = result.split("&")[0].split("=")[1];
        System.out.println(token);
        return token;
    }

}
