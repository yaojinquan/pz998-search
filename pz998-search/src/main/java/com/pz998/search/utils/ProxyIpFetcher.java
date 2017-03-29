package com.pz998.search.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection.KeyVal;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 代理ip抓取类
 * 
 * @author xxx
 * 2014年12月22日 下午3:12:07
 * @version V1.0
 */
public class ProxyIpFetcher {
    private static Logger LOGGER = LoggerFactory.getLogger(ProxyIpFetcher.class);
    
    private static ExecutorService threadPool = Executors.newFixedThreadPool(15);
    
//    /**
//     * 
//     * @author xxx
//     * 2014年12月24日 下午1:41:51
//     * @version V1.0
//     */
//    private static class ValidateCallable implements Callable<KeyVal> {
//        
//        String ip;
//        String port;
//        String targetUrl;
//        String[] keyWords;
//        
//        public ValidateCallable(String ip, String port, String targetUrl, String[] keyWords) {
//            this.ip = ip;
//            this.port = port;
//            this.targetUrl = targetUrl;
//            this.keyWords = keyWords;
//        }
//
//       // @Override
//        public KeyVal call() throws Exception {
//	    //使用代理请求页面源代码	
//            String htmlStr = GzipUtil.proxyGet(targetUrl, ip, port);
//            Document document = Jsoup.parse(htmlStr);
//            Elements kwMetas = document.head().select("meta[name=keywords]");
//            if (kwMetas != null && !kwMetas.isEmpty()) {
//                Element kwMate = kwMetas.first();
//                String kwCont = kwMate.attr("content");
//                
//                boolean contains = false;
//                for (String kw : keyWords) {
//                    if (contains = kwCont.contains(kw)) {
//                        break ;
//                    }
//                }
//                if (contains) {
//                    //包含关键时认为此代理可用
//                    return KeyVal.create(ip, port);
//                }
//            }
//            return null;
//        }
//        
//    }
    
    /**
      * 获取一个可用的代理ip
      * @param targetUrl 代理要访问的网站
      * @param keyWords 目标页面包含的关键字(用以验证代理确实请求到目标页面)
      * @return ip port pair
      */
    public static KeyVal fetchOne(final String targetUrl, final String[] keyWords) {
        String dlWebUrl = "http://www.kuaidaili.com/free/inha/1";
        Document doc = null;
        try {
            doc = Jsoup.connect(dlWebUrl).timeout(5000).get();
        } catch (Exception e) {
            LOGGER.error("connet {} fail", dlWebUrl);
            return null;
        }
        Element listDiv = doc.getElementById("list");
        Elements trs = listDiv.select("table tbody tr");
        
        List<Future<KeyVal>> futureList = new ArrayList<Future<KeyVal>>(trs.size());
        for (final Element tr : trs) {
            String ip = tr.child(0).text();
            String port = tr.child(1).text();
            System.out.println("ip:"+ip+" port:"+port);
//            futureList.add(threadPool.submit(new ValidateCallable(ip, port, 
//                    targetUrl, keyWords)));
        }
//        for (int i = 0; i < 3; i++) {
//            Thread.yield();//尝试启动验证线程
//        }
//        
//        for (Future<KeyVal> future : futureList) {
//            try {
//                KeyVal kv = future.get();
//                if (kv == null) {
//                    //继续验证,直到获取一个可用的代理
//                    continue ;
//                }
//                return kv;
//            } catch (InterruptedException e) {
//                LOGGER.error(e.getMessage());
//                //i don't what happened, so let it go.
//                
//            } catch (ExecutionException e) {
//                //call执行异常, 记录日志
//                LOGGER.error(e.getMessage());
//            }
//        }
        return null;
    }
    
    public static void main(String[] args) {
    	fetchOne("",null);
	}
}
