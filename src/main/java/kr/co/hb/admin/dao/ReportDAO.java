package kr.co.hb.admin.dao;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.hb.admin.dto.ReportDTO;

public interface ReportDAO {

	int totalCount();

	ArrayList<ReportDTO> reportPageList(int cnt, int offset);

	ReportDTO reportDetail(String rept_no);

	void commentWrite(HashMap<String, String> params);

	ReportDTO reportCommentDetail(int rept_no);

	ArrayList<ReportDTO> report_msg_profileList(int cnt, int offset);

	ArrayList<ReportDTO> report_fboardList(int cnt, int offset);

	ReportDTO report_fboardDetail(String rept_no);

	ReportDTO report_msg_profileDetail(String rept_no);

	ArrayList<ReportDTO> report_gboardList(int cnt, int offset);

	ReportDTO report_gboardDetail(String rept_no);

	void pcommentWrite(HashMap<String, String> params);

	void fcommentWrite(HashMap<String, String> params);

	void gcommentWrite(HashMap<String, String> params);


}
