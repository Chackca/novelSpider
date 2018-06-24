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
import novel.storage.Processor;
import novel.storage.impl.BxwxNovelStorageImpl;
import novel.storage.impl.KanShuZhongNovelStorageImpl;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
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


    /**
     * 测试下载某本书的所有章节，链接为该书的章节列表
     */
    @Test
    public void testDownload() {
        INovelDownload download = new NovelDownload();
        Configuration config = new Configuration();
        config.setPath("G:\\example\\tempDir\\novelSpider");
        config.setSize(50);
        config.setTryTimes(5);
        //download.download("http://www.kanshuzhong.com/book/120155/",config);
        //https://www.bxwx9.org/b/176/176884/index.html  笔下文学
        System.out.println("下载好了，文件保存在："+download.download("https://www.bxwx9.org/b/176/176884/index.html",config));
    }

    @Test
    public void testMultiFileMerge(){
        NovelSpiderUtil.multiFileMerge("G:\\example\\tempDir\\novelSpider",null,false);
    }

    /**
     * 测试获取某个网站的所有书类目的信息，返回包装后的每本书的信息
     */
    @Test
    public void testGetNovel(){
        INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider("http://www.kanshuzhong.com/map/A/1/");
        List<Novel> novelList = novelSpider.getNovel("http://www.kanshuzhong.com/map/A/1/",5);

        //INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider("https://www.bxwx9.org/binitialA/0/1.htm");
        //List<Novel> novelList = novelSpider.getNovel("https://www.bxwx9.org/binitialA/0/1.htm");
        for (Novel novel : novelList){
            System.out.println(novel);
        }
    }

    @Test
    public void testKanShuZhongIterator(){
        /*INovelSpider spider = NovelSpiderFactory.getNovelSpider("https://www.bxwx9.org/binitialE/0/1.htm");
        Iterator<List<Novel>> iterator = spideAbstractSpider.iterator("https://www.bxwx9.org/binitialE/0/1.htm",5);*/
        //利用工厂返回一个对应此链接的spider，此spider有getNovel（获取本页面中的所有Novel存储于一个list中），iterator（用于调用下一页的getNovel）等方法
        INovelSpider spider = NovelSpiderFactory.getNovelSpider("http://www.kanshuzhong.com/map/A/1/");
        //返回AbstractNovelSpider里面的NovelIterator类，其包含了下一页的所有Novel
        Iterator<List<Novel>> iterator = spider.iterator("http://www.kanshuzhong.com/map/A/1/",5);
        while (iterator.hasNext()){
            //每次调用next()，都会更新AbstractNovelSpider类里面的nextPage方法，保证其内部数据是变化的
            List<Novel> novelList = iterator.next();
            //输出下一个的地址
            System.out.println("URL:"+spider.next());
        }
    }


    /**
     * 以下为storage的test
     * @throws Exception
     */
    @Test
    public void testMybatisConnection() throws Exception {
        SqlSession session = new SqlSessionFactoryBuilder().build(new FileInputStream("conf/SqlMapConfig.xml")).openSession();
        System.out.println(session);
    }

    @Test
    public void testKanShuZhongProcess() throws Exception {
        Processor processor = new KanShuZhongNovelStorageImpl();
        processor.process();
    }

    @Test
    public void testBxwxProcess() throws FileNotFoundException {
        Processor processor = new BxwxNovelStorageImpl();
        processor.process();
    }
}