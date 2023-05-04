package kr.co.hb.board.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.hb.board.dao.BoardDAO;
import kr.co.hb.board.dto.BoardDTO;
@Service
public class BoardService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardDAO dao;
	
	public ArrayList<BoardDTO> list() {
		return dao.list();
	}

	public String fwrite(MultipartFile photo, HashMap<String, String> params) {
		
		BoardDTO dto = new BoardDTO();
		dto.setTitle(params.get("title"));
		dto.setId(params.get("id"));
		dto.setbContent(params.get("bContent"));
		
		int row = dao.fwrite(dto);
		logger.info("서비스까지 왔땅 dto" + row);
		
		int idx = dto.getFbNo();
		logger.info("idx : ", idx);
		
		String page = "redirect:/fdetail.do?fbNo="+idx;
		
		//만약 사진 있으면?
		if(!photo.getOriginalFilename().equals("")) {
			filesave(idx,photo);
		}
		return page;
	}

	private void filesave(int fbNo, MultipartFile photo) {
		logger.info("filesave 왔땅 dto");
		//이름명
		String orifilename=photo.getOriginalFilename();
		String ext = orifilename.substring(orifilename.lastIndexOf("."));
		String newfilename = System.currentTimeMillis()+ext;
		try {
			logger.info("filesave 왔땅 try 한당");
			//바이트 추출
			byte[] bytes = photo.getBytes();
			//경로와 이름 붙여서 지정
			Path path = Paths.get("C:/img/upload/"+newfilename);
			//파일로 저장(경로), 바이트
			Files.write(path, bytes);
			
			//db에 저장-dao
			logger.info("filesave dao 한당");
			dao.filesave(fbNo, orifilename, newfilename);
			logger.info("filesave dao 끝난당");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//경로 저장
		
	}

	public BoardDTO detail(int fbNo, String flag) {
		if (flag.equals("detail")) {
			dao.uphit();
		}
		return dao.detail(fbNo);
	}

	public void fdelete(int fbNo) {
		//파일 있다면
		String newfilename = dao.findFile(fbNo);
		logger.info(newfilename+"은 왔나?");
		//없으면 삭제
		int row = dao.fdelete(fbNo);
		//파일도 삭제
		if (newfilename!=null) {
			//글 삭제됐나?
			if (row>0) {
				//객체화 위치 지정
				File file = new File("C:/img/upload"+newfilename);
				if (file.exists()) {
					file.delete();
				}
			}
		}
	}

	public String fupdate(MultipartFile photo, HashMap<String, String> params) {
		int fbNo = Integer.parseInt(params.get("fbNo"));
		int row = dao.fupdate(params);
		logger.info("update service서 처리 중"+row);
		//사진도 수정해야 하니까 
		if (!photo.getOriginalFilename().equals("")) {
			filesave(fbNo,photo);
		}
		String page = row>0? "redirect:/fdetail.do?fbNo="+fbNo:"redirect:/flist.go";
		return page;
	}

	

	
		
		
		
		
		
		
		
		
	}

