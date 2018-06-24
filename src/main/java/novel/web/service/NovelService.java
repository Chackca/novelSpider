package novel.web.service;

import java.util.List;

import novel.spider.entitys.Novel;

public interface NovelService {
	/**
	 * ͨ����ѯ�ؼ��ʣ�ȥ���ݿ��в�ѯ�����Ȼ�󷵻���Ҫ������
	 * @param keyword
	 * @return
	 */
	public List<Novel> getsNovelByKeyword(String keyword);
	/**
	 * ���Ҷ�Ӧƽ̨�����С˵
	 * @param keyword
	 * @param platformId
	 * @return
	 */
	public List<Novel> getsNovelByKeyword(String keyword, int platformId);
}
