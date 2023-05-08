package kr.co.hb.group.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.hb.group.dto.GroupBoardDTO;
import kr.co.hb.group.dto.OpenChatDTO;
import kr.co.hb.group.service.OpenChatService;

@Controller
public class OpenChatController {
	
	@Autowired OpenChatService service;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping (value={"/openchat.go", "/listmsg.go"})
	public String openchatForm(Model model, HttpSession session, HttpServletRequest req) {		
		
		String loginId = (String)session.getAttribute("loginId");
		int gidx = (int) session.getAttribute("gidx");
		
		String uri = req.getRequestURI();
		String ctx = req.getContextPath(); 
		String addr = uri.substring(ctx.length());
		
		if(addr.equals("/openchat.go")) {
		
		logger.info("addr : " + addr);	
			
		int chk = service.memchk(loginId, gidx);
		logger.info("chk : " + chk);
		
		if(chk == 0) {
			
			int row = service.openlist(loginId, gidx);
		}
		
		ArrayList<OpenChatDTO> list = service.openchatlist(gidx);
		
		model.addAttribute("list", list);
		
		ArrayList<GroupBoardDTO> listmsg = service.clist(gidx);
		
		model.addAttribute("listmsg", listmsg);
		
		}
		
		else {
			
		logger.info("addr : " + addr);
		
		ArrayList<GroupBoardDTO> listmsg = service.clist(gidx);
		
		model.addAttribute("listmsg", listmsg);
		
		}
		
		return "openChat";
	}
	
	@RequestMapping(value="/sendmsg.do")
	public String sendmsg(@RequestParam HashMap<String, String> params, HttpSession session, HttpServletRequest req) {		
		
		logger.info("params : " + params);
		int gidx = (int) session.getAttribute("gidx");
		int row = service.sendmsg(params, gidx);
		
		
		return "redirect:/listmsg.go";
	}
	
	/*
	 * @RequestMapping(value="/clist") public String list(Model model) {
	 * logger.info("listcall"); ArrayList<GroupBoardDTO> listmsg = service.clist();
	 * 
	 * model.addAttribute("listmsg", listmsg); return "openChat"; }
	 */
	/*
	 * @RequestMapping(value="/listmsg.ajax") public HashMap<String, Object>
	 * listmsg(HashMap<String, Object> params) {
	 * 
	 * HashMap<String, Object> map = new HashMap<String, Object>();
	 * ArrayList<OpenChatDTO> listmsg = service.listmsg(); map.put("listmsg",
	 * listmsg);
	 * 
	 * return map; }
	 */
	 
}
