package novel.spider.junit;

import novel.spider.NovelSiteEnum;
import novel.spider.configuration.Configuration;
import novel.spider.entitys.Chapter;
import novel.spider.impl.DefaultChapterDetailSpider;
import novel.spider.impl.DefaultChapterSpider;
import novel.spider.impl.NovelDownload;
import novel.spider.interfaces.IChapterDetailSpider;
import novel.spider.interfaces.IChapterSpider;
import novel.spider.interfaces.INovelDownload;
import novel.spider.util.NovelSpiderUtil;
import org.junit.Test;

import java.util.List;

public class TestCase {

    @Test
    public void testGetChapter() throws Exception {
        IChapterSpider spider = new DefaultChapterSpider();
        //http://www.kanshuzhong.com/book/103251/   看书中
        //https://www.x23us.com/html/68/68505/      顶点小说
        //http://www.biquge.com.tw/16_16209/         笔趣阁
        List<Chapter> chapters = spider.getChapter("https://www.x23us.com/html/68/68505/");
        for (Chapter chapter : chapters) {
            System.out.println(chapter);
        }

    }

    @Test
    public void testGetSiteContext() {
        //顶点小说
        System.out.println(NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl("https://www.x23us.com/html/68/68505/")));
        //看书中
        System.out.println(NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl("http://www.kanshuzhong.com/book/103251/")));
        //笔趣阁
        System.out.println(NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl("http://www.biquge.com.tw/16_16209/")));
    }

    @Test
    public void testGetChapterDetail() {
        IChapterDetailSpider spider = new DefaultChapterDetailSpider();
        //笔趣阁
        //System.out.println(spider.getChapterDetail("http://www.biquge.com.tw/18_18186/8828943.html"));
        //顶点小说
        //System.out.println(spider.getChapterDetail("https://www.x23us.com/html/68/68792/31124471.html"));
        //看书中
        System.out.println(spider.getChapterDetail("http://www.kanshuzhong.com/book/118300/26105362.html"));
    }


    @Test
    public void testDownload() {
        INovelDownload download = new NovelDownload();
        Configuration configuration = new Configuration();
        download.download("",null);
    }
}