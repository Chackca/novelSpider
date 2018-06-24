package novel.storage;

import novel.storage.impl.BxwxNovelStorageImpl;
import novel.storage.impl.KanShuZhongNovelStorageImpl;

import java.io.FileNotFoundException;

public class BootStrap {
    public static void main(String[] args) throws FileNotFoundException {
        Processor processor = new KanShuZhongNovelStorageImpl();
        processor.process();
        processor = new BxwxNovelStorageImpl();
        processor.process();
    }
}
