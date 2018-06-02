package novel.spider.interfaces;

import novel.spider.configuration.Configuration;

/**
 * **如何实现多线程下载任意网站的小说**
 1. 拿到该网站的基本小说的所有章节，章节列表
 2. 通过计算，将这些章节分配给指定数量的线程，让他们去解析，然后保存到本地文件中
 3. 通知主线程，将这些零散的小文件合并成一个大文件，最后将那些分片的小文件删除掉
 */
public interface INovelDownload {
    /**
     * @param url 这个url是指某本小说的章节列表页面
     * @return  比如说我下载到D:/novel/biquge.tw/完美世界.txt
     */
    public String download(String url, Configuration config);
}
