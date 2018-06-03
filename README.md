#爬虫抓取小说网站内容的项目

###第一章
创建抓取网站章节的功能，在TestCase中有测试获取章节的代码
有AbstractSpider中的crawl：调用httpClient获取目标url，返回一个httpResponse，然后通过EntityUtils.toString方法对目标网址的按照其编码格式进行解析，最后解析出一个result字符串
再将这个result传递给Jsoup进行解析

AbstractSpider是AbstractChapterSpider与AbstractChapterDetailSpider的父类，因为他们都需要经过httpClient操作过后的结果
###第二章
创建抓取网站某一章节的具体detail的功能，包括有标题、内容、前一章url、后一章url
用一个实体类ChapterDetail接收

完善AbstracChaptertSpider的内容

###第三章
NovelSpiderUtil.init()，用于从配置文件中获取节点并存储在Map中，供给系统后序调用

新建一个Spider-Rule.xml规则，用于设置要抓取的网站的一些解析内容
并设置有NovelSiteEnum这个枚举类来存储id到url的映射

并完善AbstracChaptertDetailSpider的内容
本质就是用jsoup进行解析，然后用NovelSpiderUtil.getContext()拿到该地址的配置文件内容
根据配置的各种CSS解析器对网址内容进行解析（doc.select）。解析后取出对应的内容设置给实体类

注意：网站的章节列表与章节的具体内容属于不同层次的网站结构
尽管他们的配置文件在同一个site，但是不可以合在一起
需要不同的规则进行抓取（AbstractChapterSpider、AbstractChapterDetailSpider）

###第四章
**如何实现多线程下载任意网站的小说**
1. 拿到该网站的基本小说的所有章节，章节列表
2. 通过计算，将这些章节分配给指定数量的线程，让他们去解析，然后保存到本地文件中
3. 通知主线程，将这些零散的小文件合并成一个大文件，最后将那些分片的小文件删除掉

###第五章
多线程下载小说的支持
NovelDownload代码的书写
通过Configuration实体类传入参数（每个线程能够下载的最大章节数，下载的文件路径）
通过获取小说的所有章节数量size，计算出需要的线程数量，用线程池创建指定数量的线程，
传入实现Callable的类(参数：存储路径，章节List)。其call()方法就是遍历章节List执行
printWriter输出到磁盘中，最后调用get方法判断该线程是否执行完成并返回结果了，最后shutdown

###第六章
对多线程下载小说继续完善，上面存在两个bug，
1、每个线程下载会丢失一个章节
2、有些线程下载时间过长会一直卡顿着
对于第一个bug，通过修改计算线程下载的章节数量实现
对于第二个bug，加入超时时间，写一个类NodelSpiderHttpGet继承HttpGet
然后在里面写一个方法用来设置超时时间

修改下载的文件的地址存放的目录不够清晰的问题，增加爬虫抓取小说标题，然后在写磁盘时每本小说放在指定的标题目录下
