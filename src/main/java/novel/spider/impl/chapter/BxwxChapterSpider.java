package novel.spider.impl.chapter;

import novel.spider.entitys.Chapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 该方法用于对笔下文学（目标不支持）的章节进行排序，其他网站实际不需要
 */
public class BxwxChapterSpider extends AbstracChaptertSpider{

    @Override
    public List<Chapter> getChapter(String url) {
        List<Chapter> chapters = super.getChapter(url);
        Collections.sort(chapters, new Comparator<Chapter>() {
            @Override
            public int compare(Chapter o1, Chapter o2) {
                String o1Url = o1.getUrl();
                String o2Url = o2.getUrl();
                String o1Index = o1Url.substring(o1Url.lastIndexOf("/")+1,o1Url.lastIndexOf("."));
                String o2Index = o2Url.substring(o2Url.lastIndexOf("/")+1,o2Url.lastIndexOf("."));
                return o1Index.compareTo(o2Index);
            }
        });
        return chapters;
    }
}
