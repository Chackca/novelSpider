package novel.spider.configuration;

import java.io.Serializable;

public class Configuration implements Serializable{

    /**
     * 每个线程默认可以下载的最大章节数量
     */
    private static final int DEFAULT_SIZE = 100;
    /**
     * 下载后的文件保存的基地址
     *  D：/opt/bixiawenxue.org/完美世界/1-2.txt
     */
    private String path;

    /**
     * 每个线程能够下载的最大章节数量
     */
    private int size;

    public Configuration(){
        this.size = DEFAULT_SIZE;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
