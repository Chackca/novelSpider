package novel.spider.impl.novel;

import novel.spider.NovelSiteEnum;
import novel.spider.entitys.Novel;
import novel.spider.util.NovelSpiderUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 笔下文学网站的书籍列表爬取
 *  其抓取路径为该网址的某一个字母目录的网址，该网址里面有很多书籍Novel的信息
 */
public class BxwxNovelSpider extends AbstractNovelSpider{


    public BxwxNovelSpider(){

    }

    @Override
    public List<Novel> getNovel(String url,Integer maxTryTimes) {
        List<Novel> novelList = new ArrayList<>();
        try {
            Elements trs = super.getsTr(url,maxTryTimes);
            //跳过第一个tr
            for (int index=1,size = trs.size();index<size;index++){
                Element tr = trs.get(index);
                Elements tds = tr.getElementsByTag("td");
                Novel novel = new Novel();
                novel.setName(tds.first().text());
                novel.setUrl(tds.first().getElementsByTag("a")
                        .first().absUrl("href")
                        .replace(".htm","/index.html")
                        .replace("binfo","b"));
                novel.setLastUpdateChapter(tds.get(1).text());
                novel.setLastUpdateChapterUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
                novel.setAuthor(tds.get(2).text());
                novel.setLastUpdateTime(NovelSpiderUtil.getDate(tds.get(4).text(),"yy-mm-dd"));
                novel.setStatus(NovelSpiderUtil.getNovelStatus(tds.get(5).text()));
                novel.setPlatformId(NovelSiteEnum.getEnumByUrl(url).getId());
                //novel.setAddTime();
                novelList.add(novel);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("BxwxNovelSpider：笔下文学的书籍列表爬取出现异常");
        }
        return novelList;
    }



}
