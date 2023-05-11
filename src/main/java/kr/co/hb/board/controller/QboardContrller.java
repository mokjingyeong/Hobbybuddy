package kr.co.hb.board.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.hb.board.dto.QboardDTO;
import kr.co.hb.board.service.QboardService;

@Controller
public class QboardContrller {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired QboardService service;

	@RequestMapping(value = "/qboardList.ajax", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> noticeList(
			@RequestParam String page,
			@RequestParam String cnt			
			){
		
		return service.qboardPageList(Integer.parseInt(page), Integer.parseInt(cnt));
	}	
	
	
	@RequestMapping(value="/qboardList.go")
	public String qboardList(Model model) {		
				
		return "qBoardList"; 
	}
	
	@RequestMapping(value="/qBoardWrite.go")
	public String qBoardWriteForm(Model model) {		
		
		return "qBoardCreate"; 
	}
	
	@RequestMapping(value = "/qboard_write.ajax",method = RequestMethod.POST)
	// ajasx형태로 주고 받기 위해 작성
	@ResponseBody
	public HashMap<String, Object> join(MultipartFile photo ,@RequestParam HashMap<String, String> params){
		logger.info("정보들 컨트롤러에 도착 : {}", params);
		
		logger.info("parmas true + " + params.get("qboard_openchk"));
		return service.qboard_write(photo,params);
	}
	
	@RequestMapping(value = "/qboardDetail.go")
	   public String noticeDetail(Model model, @RequestParam String qboard_no) {
	      
	      String page = "redirect:/qboardList.go";
	      
	      QboardDTO dto = service.qboardDetail(qboard_no);
	      
	      if (dto != null) {
	         page = "qBoardDetail";
	         model.addAttribute("dto", dto);
	      }
	      
	      return page;
	   }
	 @RequestMapping(value = "/qboardUpdate.do", method = RequestMethod.POST)
	   public String noticeUpdate(MultipartFile photo,@RequestParam HashMap<String, String> params) {
	      
	      return service.qboardUpdate(photo,params);
	   }
	 
	 @RequestMapping(value = "/qboardUpdate.go")
	   public String updateForm(Model model, @RequestParam String qboard_no) {
	      
		 logger.info("업데이트 컨트롤러 등장");
		 
	      String page = "redirect:/qboardList.go";
	      
	      QboardDTO dto = service.qboardDetail(qboard_no);
	      
	      if (dto != null) {
	    	  logger.info("업데이트 가자는 컨트롤러");
	         page = "qBoardUpdate";
	         model.addAttribute("dto",dto);
	      }
	      
	      return page;
	   }	   
	   
	   @RequestMapping(value = "/qboardDelete.go")
	   public String noticeDelete(Model model,@RequestParam HashMap<String, String> params) {
	      		   
		   service.qboardDelete(params.get("qboard_no"));
	      return "redirect:/qboardList.go";
	   }
	   
	   @RequestMapping(value = "/QdeletePhoto.do")
     public String deletePhoto(@RequestParam String photoIdx, @RequestParam String qboard_no) {
		   logger.info("photoIdx={}, qboard_no={}", photoIdx, qboard_no);

		     
		   service.deletePhoto(photoIdx, qboard_no);
				   
	       return "redirect:/qboardUpdate.go?qboard_no=" + qboard_no;
	       
	   }
}
