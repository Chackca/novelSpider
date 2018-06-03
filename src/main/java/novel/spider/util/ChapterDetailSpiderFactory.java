package novel.spider.util;

import novel.spider.NovelSiteEnum;
import novel.spider.impl.chapter.DefaultChapterDetailSpider;
import novel.spider.interfaces.IChapterDetailSpider;

public final class ChapterDetailSpiderFactory {
    //不允许被实例化
    private ChapterDetailSpiderFactory(){}

    /**
     * 通过给定的url，返回一个实现了IChapterDetailSpider接口的实现类
     * @param url
     * @return
     */
    public static IChapterDetailSpider getChapterDetailSpider(String url){
        NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
        IChapterDetailSpider chapterDetailSpider = null;
        switch (novelSiteEnum){
            case BiXiaWenXue:
//                chapterDetailSpider = new BxwxChapterDetailSpider();
//                break;
            case DingDianXiaoShuo:
            case KanShuZhong:
            case BiQuGe:
                chapterDetailSpider = new DefaultChapterDetailSpider();
                break;
            default:
                System.out.println("对于获取章节详情，该网站可能不支持");
        }
        return chapterDetailSpider;
    }
}
