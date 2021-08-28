package com.github.black.hole.sboot.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @author hairen.long
 * @date 2020/11/19
 */
@Component
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static CloseableHttpClient httpClient;
    /** http协议名 */
    private static final String HTTP_VERSION = "http";
    /** https协议名 */
    private static final String HTTPS_VERSION = "https";
    /** ssl版本 */
    private static final String SSL_VERSION = "SSLv3";
    /** utf-8编码 */
    private static final String UTF8 = "UTF-8";
    /** 最大连接数 */
    private static final Integer MAX_CONNECTION = 200;
    /** 超时时间 */
    private static final Integer SOCKET_TIMEOUT = 3 * 1000;

    @PostConstruct
    public void initClient() {
        ConnectionSocketFactory plainSocketFactory =
                PlainConnectionSocketFactory.getSocketFactory();
        SSLContext sslcontext = null;
        try {
            sslcontext = createIgnoreVerifySSL();
        } catch (Exception e) {
            throw new RuntimeException("初始化HttpClient失败");
        }
        Registry<ConnectionSocketFactory> registry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register(HTTP_VERSION, plainSocketFactory)
                        .register(HTTPS_VERSION, new SSLConnectionSocketFactory(sslcontext))
                        .build();
        PoolingHttpClientConnectionManager manager =
                new PoolingHttpClientConnectionManager(registry);
        manager.setMaxTotal(MAX_CONNECTION);
        manager.setDefaultMaxPerRoute(MAX_CONNECTION);

        SocketConfig config =
                SocketConfig.custom().setSoKeepAlive(true).setSoTimeout(SOCKET_TIMEOUT).build();
        httpClient =
                HttpClients.custom()
                        .setConnectionManager(manager)
                        .setDefaultSocketConfig(config)
                        .build();
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String post(String url, Map<String, Object> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        config(httpPost);
        setPostParams(httpPost, params);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, UTF8);
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("http请求失败，e:{}", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("response close error,e:{}", e);
            }
        }
    }

    private static void config(HttpRequestBase httpRequestBase) {
        // 配置请求的超时设置
        RequestConfig requestConfig =
                RequestConfig.custom()
                        .setConnectionRequestTimeout(SOCKET_TIMEOUT)
                        .setConnectTimeout(SOCKET_TIMEOUT)
                        .setSocketTimeout(SOCKET_TIMEOUT)
                        .build();
        httpRequestBase.setConfig(requestConfig);
    }

    private static void setPostParams(HttpPost httpost, Map<String, Object> params)
            throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nameValuePairs.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nameValuePairs, UTF8));
        } catch (UnsupportedEncodingException e) {
            logger.error("setPostParams error,e:{}", e);
            throw new RuntimeException("设置请求参数失败");
        }
    }
    /**
     * 创建sslContext 信任https证书
     *
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     */
    static SSLContext createIgnoreVerifySSL()
            throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance(SSL_VERSION);

        // 实现一个X509TrustManager接口，用于绕过验证
        X509TrustManager trustManager =
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                            String paramString) {}

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                            String paramString) {}

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };

        sc.init(null, new TrustManager[] {trustManager}, null);
        return sc;
    }
}
