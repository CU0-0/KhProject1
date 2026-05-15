package com.kh.web.board.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.web.board.model.dao.NoticeDao;
import com.kh.web.board.model.dto.NoticeDto;
import com.kh.web.common.Template;
import com.kh.web.common.model.dto.PageInfo;

public class NoticeService {
	private NoticeDao nd = new NoticeDao();

    public int selectNoticeCount() {
        SqlSession sqlSession = Template.getSqlSession();
        int listCount = nd.selectNoticeCount(sqlSession);
        sqlSession.close();
        return listCount;
    }

    public List<NoticeDto> selectNoticeList(PageInfo pi) {
        SqlSession sqlSession = Template.getSqlSession();
        List<NoticeDto> notices = nd.selectNoticeList(sqlSession, pi);
        sqlSession.close();
        return notices;
    }
}
