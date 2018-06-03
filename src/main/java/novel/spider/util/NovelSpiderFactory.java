package novel.spider.util;

import novel.spider.NovelSiteEnum;
import novel.spider.impl.novel.BxwxNovelSpider;
import novel.spider.impl.novel.KanShuZhongNovelSpider;
import novel.spider.interfaces.INovelSpider;

/**
 * 生产书籍列表的实现类
 */
public final class NovelSpiderFactory {
    private NovelSpiderFactory(){}

    public static INovelSpider getNovelSpider(String url){
        NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
        switch (novelSiteEnum){
            case BiXiaWenXue: return new BxwxNovelSpider();
            case KanShuZhong: return new KanShuZhongNovelSpider();
            default:throw new RuntimeException(novelSiteEnum + "的生产书籍列表暂时不被支持");
        }
    }
}
