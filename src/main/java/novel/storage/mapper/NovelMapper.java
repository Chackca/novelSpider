package novel.storage.mapper;

import novel.spider.entitys.Novel;

import java.util.List;

public interface NovelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Novel record);

    int insertSelective(Novel record);

    Novel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Novel record);

    int updateByPrimaryKey(Novel record);

    void batchInsert(List<Novel> novels);

    List<Novel> getNovelByKeyword(String keyword);
}