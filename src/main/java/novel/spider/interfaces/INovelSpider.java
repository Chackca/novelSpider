package novel.spider.interfaces;

import novel.spider.entitys.Novel;

import java.util.List;

/**
 * 爬取某个站点的小说列表
 */
public interface INovelSpider {
    public static final int MAX_TRY_TIMES = 3;
    /**
     * 给我一个url，我就给你一个小说实体的集合
     * @param url
     * @return
     */
    public List<Novel> getNovel(String url);
}
