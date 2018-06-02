package novel.spider.impl;

import novel.spider.NovelSiteEnum;
import novel.spider.entitys.Chapter;
import novel.spider.interfaces.IChapterSpider;
import novel.spider.util.NovelSpiderUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstracChaptertSpider extends AbstractSpider implements IChapterSpider{

    public List<Chapter> getChapter(String url) {
        try {
            String result = super.crawl(url);
            Document doc = Jsoup.parse(result);
            doc.setBaseUri(url);//解决不同网页绝对路径与相对路径的问题
            Elements elements = doc.select(NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl(url)).get("chapter-list-selector"));
            List<Chapter> chapters = new ArrayList<>();
            for (Element element : elements){
                Chapter chapter = new Chapter();
                chapter.setTitle(element.text());
                chapter.setUrl(element.absUrl("href"));//使用absUrl
                chapters.add(chapter);
            }
            return chapters;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
