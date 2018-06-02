package novel.spider.impl;

import novel.spider.NovelSiteEnum;
import novel.spider.configuration.Configuration;
import novel.spider.entitys.Chapter;
import novel.spider.entitys.ChapterDetail;
import novel.spider.interfaces.IChapterDetailSpider;
import novel.spider.interfaces.IChapterSpider;
import novel.spider.interfaces.INovelDownload;
import novel.spider.util.ChapterDetailSpiderFactory;
import novel.spider.util.ChapterSpiderFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.*;

/**
 * **如何实现多线程下载任意网站的小说**
 1. 拿到该网站的基本小说的所有章节，章节列表
 2. 通过计算，将这些章节分配给指定数量的线程，让他们去解析，然后保存到本地文件中
 3. 通知主线程，将这些零散的小文件合并成一个大文件，最后将那些分片的小文件删除掉
 */
public class NovelDownload implements INovelDownload{

    @Override
    public String download(String url, Configuration config) {
        IChapterSpider chapterSpider = ChapterSpiderFactory.getChapterSpider(url);
        List<Chapter> chapter = chapterSpider.getChapter(url);
        //某个线程下载完毕之后，得告诉主线程此线程下载好了
        int size = config.getSize();//每个线程能够下载的最大章节数量
        // 2010章 每个线程100章 需要21个线程
        //Math.ceil：向上取整
        int maxThreadSize = (int)Math.ceil(chapter.size()*1.0/size);//需要开启的线程数量
        //所有线程都下载好了，合并！！！
        Map<String,List<Chapter>> downloadTaskAllocMap = new HashMap<>();
        for (int i = 0 ; i < maxThreadSize; i++){
            //i = 0  0-100
            //i = 1  101-200
            int fromIndex = i * config.getSize();
            int toIndex = (i==maxThreadSize-1) ? chapter.size()-1:i * config.getSize()+config.getSize()-1;
            downloadTaskAllocMap.put(fromIndex+"-"+toIndex,chapter.subList(fromIndex,toIndex));
        }
        ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);//submit接受一个Callable对象
        Set<String> keySet = downloadTaskAllocMap.keySet();
        List<Future<String>> tasks = new ArrayList<>();
        for (String key : keySet){
            tasks.add(service.submit(  //config.getPath()/0-100.txt下载后的文件保存的基地址
                    new DownloadCallable(config.getPath() +"/"+key+".txt",downloadTaskAllocMap.get(key))));
        }
        //线程池不关闭会导致线程一直存在而线程泄露
        service.shutdown();//会等待所有线程都执行结束而关闭线程
        try {
            for (Future<String> future : tasks){
                System.out.println(future.get()+",下载完成");//如果没有执行完则停留在get()方法上
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class DownloadCallable implements Callable<String>{
    private String path ;
    private List<Chapter> chapters;
    public DownloadCallable(String path,List<Chapter> chapters){
        this.chapters =chapters;
        this.path = path;
    }
    @Override
    public String call() throws Exception {
        try(
                PrintWriter out = new PrintWriter(new File(path));
            ) {
            for (Chapter chapter : chapters){
                IChapterDetailSpider spider = ChapterDetailSpiderFactory.getChapterDetailSpider(chapter.getUrl());
                ChapterDetail chapterDetail = spider.getChapterDetail(chapter.getUrl());
                out.println(chapterDetail.getTitle());
                out.println(chapterDetail.getContent());
            }
        }catch (IOException e){

        }
        return null;
    }
}
