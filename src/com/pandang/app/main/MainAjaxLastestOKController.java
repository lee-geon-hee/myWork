package com.pandang.app.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pandang.app.Execute;
import com.pandang.app.main.dao.MainDAO;
import com.pandang.app.main.dto.MainDTO;
import com.pandang.app.main.vo.MainLikeViewVO;
import com.pandang.app.main.vo.MainVO;

public class MainAjaxLastestOKController implements Execute{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MainDAO mainDAO = new MainDAO();
		MainLikeViewVO mainLikeViewVO = new MainLikeViewVO();
		
		int memberNumber = 0;
		
		if(req.getSession().getAttribute("memberNumber") != null) {
			memberNumber = (Integer)req.getSession().getAttribute("memberNumber");
		}
		
		List<MainLikeViewVO> storeList = mainDAO.selectLastest(memberNumber);
		Gson gson = new Gson();
		System.out.println("=======================");
		mainDAO.selectLastest(memberNumber);
		
		resp.setContentType("application/json; charset=utf-8");
		
		PrintWriter out = resp.getWriter();
		JsonElement json = JsonParser.parseString(gson.toJson(mainDAO.selectLastest(memberNumber)));
		out.print(json.toString());
		out.close();
	}
}
