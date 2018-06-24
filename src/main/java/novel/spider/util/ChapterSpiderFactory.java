package novel.spider.util;

import novel.spider.NovelSiteEnum;
import novel.spider.impl.chapter.BxwxChapterSpider;
import novel.spider.impl.chapter.DefaultChapterSpider;
import novel.spider.interfaces.IChapterSpider;

public final class ChapterSpiderFactory {
    //不允许被实例化
    private ChapterSpiderFactory(){ }

    /**
     * 通过给定的url，返回一个实现了IChapterSpider接口的实现类
     * @param url
     * @return
     */
    public static IChapterSpider getChapterSpider(String url){
        NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
        IChapterSpider chapterSpider = null;
        switch (novelSiteEnum){
            case BiXiaWenXue:
                chapterSpider = new BxwxChapterSpider();
                break;
            case DingDianXiaoShuo:
            case KanShuZhong:
            case BiQuGe:
                chapterSpider = new DefaultChapterSpider();
                break;
            default:
                System.err.println("对于获取所有章节列表，该网站可能不支持");
        }
        return chapterSpider;
    }
}
