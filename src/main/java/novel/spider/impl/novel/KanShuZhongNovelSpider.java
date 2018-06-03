package novel.spider.impl.novel;

import novel.spider.NovelSiteEnum;
import novel.spider.entitys.Novel;
import novel.spider.util.NovelSpiderUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 看书中网站的书籍列表爬取
 */
public class KanShuZhongNovelSpider extends AbstractNovelSpider{

    public KanShuZhongNovelSpider(){

    }

    @Override
    public List<Novel> getNovel(String url) {
        List<Novel> novelList = new ArrayList<>();
        try {
            Elements trs = super.getsTr(url);
            //跳过第一个tr
            for (int index=1,size = trs.size()-1;index<size;index++){
                Element tr = trs.get(index);
                Elements tds = tr.getElementsByTag("td");
                Novel novel = new Novel();
                novel.setName(tds.get(1).text());
                novel.setUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
                novel.setLastUpdateChapterName(tds.get(2).text());
                novel.setLastUpdateChapterUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));
                novel.setAuthor(tds.get(3).text());
                novel.setLastUpdateTime(NovelSpiderUtil.getDate(tds.get(4).text(),"mm-dd"));
                novel.setStatus(NovelSpiderUtil.getNovelStatus(tds.get(5).text()));
                novel.setPlatformId(NovelSiteEnum.getEnumByUrl(url).getId());
                //novel.setAddTime();
                novelList.add(novel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("KanShuZhongNovelSpider：看书中的书籍列表爬取出现异常");
        }
        return novelList;
    }
}
