package novel.spider.interfaces;

import novel.spider.entitys.Chapter;

import java.util.List;

public interface IChapterSpider {

    //给一个完整的url即返回所有的章节列表
    public List<Chapter> getChapter(String url);
}
