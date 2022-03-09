package kr.co.studycafe.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import net.utility.UploadSaveManager;



@Controller
public class NoticeCont {
	private NoticeDAO dao = null;
	public NoticeCont() {
		dao=new NoticeDAO();
		System.out.println("=============Notice 실행");
	}
	  
	@RequestMapping("/notice/notice.do")
	public ModelAndView noticeList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/notice/notice");
		mav.addObject("noticeList", dao.list());
		return mav;
	}
	
	@RequestMapping(value="/notice/notice_view.do", method=RequestMethod.GET)
	public ModelAndView noticeView(int n_number) {
		NoticeDTO dto = dao.read(n_number);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/notice/notice_view");
		mav.addObject("dto", dto); 
		return mav;
	}
	
	@RequestMapping(value="/notice/notice_modify.do", method=RequestMethod.GET)
	public ModelAndView noticeModify(int n_number) {
		ModelAndView mav = new ModelAndView();
		NoticeDTO dto = dao.read(n_number);
		mav.setViewName("/notice/notice_modify");
		mav.addObject("dto", dto); 
		
		return mav;
	}
	
	@RequestMapping(value="/notice/notice_delete.do", method=RequestMethod.GET)
	public ModelAndView noticeDelete(int n_number) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/notice/noticeIns");
		int cnt = dao.delete(n_number);
		
		if(cnt == 0) {
			mav.addObject("msg", "공지사항이 삭제에 실패했습니.");
		}else {
			mav.addObject("msg", "공지사항이 삭제되었습니다.");
		}
		
		mav.addObject("url", "/notice/notice.do"); 		
		return mav;
	}
	
	@RequestMapping(value="/notice/notice_write.do", method=RequestMethod.GET)
	public ModelAndView noticeWrite(@SessionAttribute(name = "uid", required = false) String uid) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/notice/notice_write");		
		mav.addObject("storeList", dao.storelist(uid));
		return mav;
	}
	
	@RequestMapping(value="/notice/notice_write.do", method=RequestMethod.POST)
	public ModelAndView noticeProc(@ModelAttribute NoticeDTO dto) {
		ModelAndView mav = new ModelAndView();
		int cnt = dao.write(dto);
		mav.setViewName("/notice/noticeIns");
		if(cnt == 1) {
			mav.addObject("msg", "공지사항 등록 완료");
			mav.addObject("url", "notice.do");
		}else {
			mav.addObject("msg", "공지사항 등록 실패");
			mav.addObject("url", "notice.do");
		}
		return mav;
	}

}
