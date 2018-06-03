package novel.spider.junit;

import novel.spider.NovelSiteEnum;
import novel.spider.configuration.Configuration;
import novel.spider.entitys.Chapter;
import novel.spider.entitys.Novel;
import novel.spider.impl.chapter.DefaultChapterDetailSpider;
import novel.spider.impl.chapter.DefaultChapterSpider;
import novel.spider.impl.download.NovelDownload;
import novel.spider.interfaces.IChapterDetailSpider;
import novel.spider.interfaces.IChapterSpider;
import novel.spider.interfaces.INovelDownload;
import novel.spider.interfaces.INovelSpider;
import novel.spider.util.NovelSpiderFactory;
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
        //https://www.bxwx9.org/b/176/176884/index.html  笔下文学
        List<Chapter> chapters = spider.getChapter("https://www.bxwx9.org/b/176/176884/index.html");
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
        //笔下文学
        System.out.println(NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl("https://www.bxwx9.org/b/176/176884/index.html")));

    }

    @Test
    public void testGetChapterDetail() {
        IChapterDetailSpider spider = new DefaultChapterDetailSpider();
        //笔趣阁
        //System.out.println(spider.getChapterDetail("http://www.biquge.com.tw/18_18186/8828943.html"));
        //顶点小说
        //System.out.println(spider.getChapterDetail("https://www.x23us.com/html/68/68792/31124471.html"));
        //看书中
        //System.out.println(spider.getChapterDetail("http://www.kanshuzhong.com/book/118300/26105362.html"));
        //笔下文学
        System.out.println(spider.getChapterDetail("https://www.bxwx9.org/b/176/176884/29540104.html"));
    }


    @Test
    public void testDownload() {
        INovelDownload download = new NovelDownload();
        Configuration config = new Configuration();
        config.setPath("G:\\example\\tempDir\\novelSpider");
        config.setSize(50);
        config.setTryTimes(4);
        //download.download("http://www.kanshuzhong.com/book/120155/",config);
        //https://www.bxwx9.org/b/176/176884/index.html  笔下文学
        System.out.println("下载好了，文件保存在："+download.download("https://www.bxwx9.org/b/176/176884/index.html",config));
    }

    @Test
    public void testMultiFileMerge(){
        NovelSpiderUtil.multiFileMerge("G:\\example\\tempDir\\novelSpider",null,false);
    }

    @Test
    public void testGetNovel(){
        INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider("http://www.kanshuzhong.com/map/A/1/");
        List<Novel> novelList = novelSpider.getNovel("http://www.kanshuzhong.com/map/A/1/");

        //INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider("https://www.bxwx9.org/binitialA/0/1.htm");
        //List<Novel> novelList = novelSpider.getNovel("https://www.bxwx9.org/binitialA/0/1.htm");
        for (Novel novel : novelList){
            System.out.println(novel);
        }
    }
}