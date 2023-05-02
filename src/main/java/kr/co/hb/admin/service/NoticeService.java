package kr.co.hb.admin.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.hb.admin.dao.NoticeDAO;
import kr.co.hb.admin.dto.NoticeDTO;

@Service
public class NoticeService {

	@Autowired NoticeDAO dao;

	   public String noticeWrite(MultipartFile photo, HashMap<String, String> params) {
	      
	      String page = "redirect:/noticeList.go";
	      
	      NoticeDTO dto = new NoticeDTO();
	      
	      dto.setId(params.get("id"));
	      dto.setNotice_title(params.get("notice_title"));
	      dto.setNotice_content(params.get("notice_content"));
	      
	      dao.noticeWrite(dto);
	      
	      int idx = dto.getNotice_idx();
	      
	      if (!photo.getOriginalFilename().equals("")) {
	         noticeFileSave(idx, photo);
	      }
	      
	      return page;
	   }

	   private void noticeFileSave(int idx, MultipartFile file) {

	      String oriFileName = file.getOriginalFilename();
	      
	         String ext = oriFileName.substring(oriFileName.lastIndexOf("."));

	         String newFileName = System.currentTimeMillis()+ext;

	         try {

	            byte[] bytes = file.getBytes();

	            Path path = Paths.get("C:/img/upload/" + newFileName);
	    
	            Files.write(path, bytes);
	            
	            dao.noticeFileWrite(idx,oriFileName,newFileName);
	            
	            
	         } catch (IOException e) {

	            e.printStackTrace();
	         }

	      }

	   public NoticeDTO noticeDetail(String notice_idx, String flag) {
	      
	      if(flag.equals("noticeDetail")) {
	            // 조회수 증가
	            dao.noticeUpHit(notice_idx);
	         }
	      
	      return dao.noticeDetail(notice_idx);
	   }

	   public ArrayList<NoticeDTO> noticeList() {
	      
	      return dao.noticeList();
	   }

	   public String noticeUpdate(MultipartFile photo, HashMap<String, String> params) {
	      
	      int row = dao.noticeUpdate(params);
	      int idx = Integer.parseInt(params.get("notice_idx"));
	      
	      if (!photo.getOriginalFilename().equals("")) {
	         noticeFileSave(idx, photo);
	      }
	      
	      String page = row > 0 ? "redirect:/detail.do?idx=" + idx : "redirect:/list.go";
	             
	        return page;
	   }

	   public void noticeDelete(String notice_idx) {
	      
	      String newFileName = dao.noticeFindFile(notice_idx);
	      
	      int row = dao.noticeDelete(notice_idx);
	            
	      
	      if (newFileName != null) {
	         if (row>0) {
	            File file = new File("C:/img/upload/"+ newFileName);
	            if (file.exists()) {
	               file.delete();
	            }
	         }
	      }
	      
	      
	   }


}