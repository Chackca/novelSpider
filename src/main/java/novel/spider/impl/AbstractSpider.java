package novel.spider.impl;

import novel.spider.NovelSiteEnum;
import novel.spider.util.NovelSpiderUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class AbstractSpider {

    /**
        抓取指定小说网站的内容
     */
    protected String crawl(String url) throws Exception{
        try (   //JDK1.7,写在try括号里的代码可以自动关闭连接，无需手动释放，最后被调用的最先关闭
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse httpResponse = httpClient.execute( new HttpGet(url))
        ){
            //HttpGet httpGet = new HttpGet(url);
            //CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            String result = EntityUtils.toString(httpResponse.getEntity(), NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl(url)).get("charset"));
            return result;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
