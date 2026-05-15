package com.kh.web.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.web.board.model.dto.NoticeDto;
import com.kh.web.common.model.dto.PageInfo;

public class NoticeDao {
    public int selectNoticeCount(SqlSession sqlSession) {
        return sqlSession.selectOne("noticeMapper.selectNoticeCount");
    }

    public List<NoticeDto> selectNoticeList(SqlSession sqlSession, PageInfo pi) {
        return sqlSession.selectList("noticeMapper.selectNoticeList", pi);
    }
}
