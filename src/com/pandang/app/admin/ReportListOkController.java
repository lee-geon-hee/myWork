package com.pandang.app.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pandang.app.Execute;
import com.pandang.app.report.dao.ReportDAO;
import com.pandang.app.report.vo.ReportVO;

public class ReportListOkController implements Execute{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReportDAO reportDAO = new ReportDAO();
		int total = reportDAO.getTotal();
		
//      처음 게시판 페이지에 진입하면 페이지에 대한 정보가 없다.
//      그러므로 temp에는 null이 들어가게 된다.
      String temp = req.getParameter("page");
      
//      null인 경우는 게시판에 처음 이동하는 것이므로 1페이지를 띄워주면 된다.
      int page = temp == null ? 1 : Integer.valueOf(temp);
      
//      한 페이지에 몇 개의 게시물? 10개
      int rowCount = 10;
//      페이지 버튼 세트는? 5개씩
      int pageCount = 5;
      
//       * 0, 10 -> 1페이지
//       * 10, 10 -> 2페이지
//       * 20, 10 -> 3페이지
      int startRow = (page-1) * rowCount;
      
//      Math.ceil() 올림처리
      int endPage = (int)(Math.ceil(page/(double)pageCount) * pageCount);
//      endPage는 페이지 세트 당 마지막 번호를 의미한다.
      
      int startPage = endPage - (pageCount - 1);
//      startPage는 페이지 세트 당 첫 번째 번호를 의미한다.
      
      int realEndPage = (int)Math.ceil(total / (double)rowCount);
//      realEndPage는 전체 페이지 중 가낭 마지막 번호를 의미한다.
      
      endPage = endPage > realEndPage ? realEndPage : endPage;
//      첫 번째 페이지 세트가 1~5
//      두 번째 페이지 세트가 6~10이어도
//      realEndPage가 7이라면 두 번째 페이지 세트의 마지막 번호는 7이어야 한다.
      
      boolean prev = startPage > 1;
      boolean next = endPage != realEndPage;
      
      
      
      
      Map<String, Integer> pageMap = new HashMap<>();
      pageMap.put("startRow", startRow);
      pageMap.put("rowCount", rowCount);
      
      List<ReportVO> reports = reportDAO.selectAll(pageMap);
      
      req.setAttribute("reportList", reports);
      req.setAttribute("page", page);
      req.setAttribute("startPage", startPage);
      req.setAttribute("endPage", endPage);
      req.setAttribute("prev", prev);
      req.setAttribute("next", next);
      
      req.getRequestDispatcher("/app/admin/reportList.jsp").forward(req, resp);
	}
}
