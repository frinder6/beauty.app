package com.beauty.util;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtil {


    /**
     *
     * @param response
     * @param obj
     * @throws IOException
     */
    public static void writeJSON(HttpServletResponse response, Object obj) throws IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(obj));
        out.flush();
        out.close();
    }


    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * @param request
     * @return boolean
     * @throws
     * @Title: isAjaxRequest
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author frinder_liu
     * @date 2015年5月24日 下午9:13:29
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxKey = String.valueOf(request.getHeader("X-Requested-With")).toUpperCase();
        return "XMLHttpRequest".toUpperCase().equals(ajaxKey);
    }

    /**
     * 获得get请求参数
     *
     * @param params
     * @return
     */
    public static String getParam(Map<String, String> params) {
        String param = "?";
        if (params == null) {
            return "";
        }
        int i = 0;
        // 参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (i == 0) {
                param = entry.getKey() + "=" + entry.getValue();
                ++i;
            } else {
                param = "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        if ("?".equals(param)) {
            return "";
        }
        return param;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, String> params) {
        String result = "";
        // 获取参数
        String param = getParam(params);

        BufferedReader in = null;
        try {
            String urlNameString = url + param;
            URL realUrl = new URL(urlNameString);
            // 打开和url之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有响应
            for (String key : map.keySet()) {
                System.out.println(key + "---->" + map.get(key));
            }
            // 定义BufferdReader输入流读取url响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            System.out.println("发送Get请求异常\n" + e);
            // e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和url之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // connection.setRequestProperty("Content-Type",
            // "text/xml; charset=UTF-8 ");
            connection.setRequestProperty("Content-Type",
                    "application/json; charset=UTF-8 ");

            // 发送post必须设置
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流缓冲
            out.flush();
            // 定义BufferedReader输入流读取响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送post请求异常\n" + e);
            // e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;

    }

}
