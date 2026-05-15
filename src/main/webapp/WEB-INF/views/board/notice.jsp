<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>

/* 게시판 헤더 고정바 숨김 */
header.masthead {
    display: none;
}

/* 컨텐츠 영역 최소 높이 확보 */
.notice-wrap {
    min-height: 700px;
}

/* 테이블 행 커서 */
tbody tr:hover {
    cursor: pointer;
    background-color: #f0f8ff !important;
}

/* 페이징 버튼 현재 페이지 강조 */
.paging-area .btn-current {
    background-color: #52b1ff;
    color: white;
    font-weight: bold;
}

/* 번호 열 (글번호 내림차순 표시용) */
.no-col {
    color: #555;
}

/* 제목 링크 */
.notice-title-link {
    color: #2d6fa3;
    text-decoration: none;
}
.notice-title-link:hover {
    text-decoration: underline;
    color: #52b1ff;
}

/* 검색 영역 */
#search-area {
    display: flex;
    gap: 6px;
    margin-top: 10px;
    max-width: 400px;
}
#search-area select,
#search-area input {
    flex: 1;
}

</style>

<br/><br/>

<jsp:include page="../include/header.jsp"/>

<!-- ── 공지사항 목록 ───────────────────────────── -->
<div class="container notice-wrap">
    <div class="row">
        <div class="col-lg-1"></div>
        <div class="col-lg-10">
            <div class="panel-body">

                <!-- 페이지 타이틀 + 글쓰기 버튼 -->
                <h2 class="page-header">
                    <span style="color: #52b1ff;">KH</span> 공지사항
                    <c:if test="${ not empty userInfo and userInfo.userRole eq 'ADMIN' }">
                        <a href="http://localhost:8088/kh/noticeInsertForm.do"
                           class="btn float-right"
                           style="background-color:#52b1ff; margin-top:0; height:40px;
                                  color:white; border:0; opacity:0.85;">
                            공지 작성
                        </a>
                    </c:if>
                </h2>

                <!-- ── 공지사항 테이블 ─────────────────────── -->
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr style="background-color:#52b1ff; color:white; opacity:0.85;">
                            <th width="80"  style="text-align:center;">번호</th>
                            <th width="130" style="text-align:center;">작성자</th>
                            <th>제목</th>
                            <th width="130" style="text-align:center;">작성일</th>
                            <th width="80"  style="text-align:center;">조회수</th>
                        </tr>
                    </thead>
                    <tbody>

                        <c:choose>
                            <%-- 게시글이 없을 때 --%>
                            <c:when test="${ empty notices }">
                                <tr>
                                    <td colspan="5" style="text-align:center; padding:30px; color:#999;">
                                        등록된 공지사항이 없습니다.
                                    </td>
                                </tr>
                            </c:when>

                            <%-- 게시글 목록 출력 --%>
                            <c:otherwise>
                                <%--
                                    글번호를 내림차순으로 계산해서 표시
                                    표시번호 = listCount - offset - (반복 인덱스)
                                --%>
                                <c:set var="displayNo" value="${ pi.listCount - pi.offset }"/>
                                <c:forEach var="notice" items="${ notices }" varStatus="vs">
                                    <tr>
                                        <td class="no-col" style="text-align:center;">
                                            ${ displayNo - vs.index }
                                        </td>
                                        <td style="text-align:center;">
                                            ${ notice.userName }
                                        </td>
                                        <td>
                                            <a class="notice-title-link"
                                               href="http://localhost:8088/kh/noticeDetail.do?noticeNo=${ notice.noticeNo }">
                                                ${ notice.noticeTitle }
                                            </a>
                                        </td>
                                        <td style="text-align:center;">
                                            ${ notice.createDate }
                                        </td>
                                        <td style="text-align:center;">
                                            ${ notice.count }
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                    </tbody>
                </table>

                <!-- ── 페이징 바 ───────────────────────────── -->
                <div class="paging-area text-center" style="margin-top:20px;">

                    <%-- 이전 그룹 버튼 : startPage가 1보다 클 때만 표시 --%>
                    <c:if test="${ pi.startPage gt 1 }">
                        <button class="btn btn-outline-primary"
                                style="color:#52b1ff;"
                                onclick="location.href='http://localhost:8088/kh/notice.bo?page=${ pi.startPage - 1 }'">
                            &laquo; 이전
                        </button>
                    </c:if>

                    <%-- 페이지 번호 버튼 --%>
                    <c:forEach var="i" begin="${ pi.startPage }" end="${ pi.endPage }">
                        <c:choose>
                            <c:when test="${ i eq pi.currentPage }">
                                <%-- 현재 페이지 강조 --%>
                                <button class="btn btn-current" disabled>${ i }</button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-outline-primary"
                                        style="color:#52b1ff;"
                                        onclick="location.href='http://localhost:8088/kh/notice.bo?page=${ i }'">${ i }</button>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <%-- 다음 그룹 버튼 : endPage가 maxPage보다 작을 때만 표시 --%>
                    <c:if test="${ pi.endPage lt pi.maxPage }">
                        <button class="btn btn-outline-primary"
                                style="color:#52b1ff;"
                                onclick="location.href='http://localhost:8088/kh/notice.bo?page=${ pi.endPage + 1 }'">
                            다음 &raquo;
                        </button>
                    </c:if>

                </div>
                <%-- 총 게시글 수 표시 --%>
                <div style="text-align:center; margin-top:8px; color:#aaa; font-size:0.85em;">
                    전체 ${ pi.listCount }건 &nbsp;|&nbsp;
                    ${ pi.currentPage } / ${ pi.maxPage } 페이지
                </div>

            </div>
        </div>
        <div class="col-lg-1"></div>
    </div>
</div>

<jsp:include page="../include/footer.jsp"/>
