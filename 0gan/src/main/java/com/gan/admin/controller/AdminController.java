package com.gan.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gan.admin.dao.AdminDao;
import com.gan.admin.vo.AdmAnsVo;
import com.gan.admin.vo.AdmVo;
import com.gan.admin.vo.FaqVo;
import com.gan.admin.vo.NotiVo;
import com.gan.admin.vo.ThemeVo;
import com.gan.admin.vo.UserVo;
import com.gan.util.RenameUtil;

@Controller
public class AdminController {

	@Autowired
	private AdminDao dao;

	public void setDao(AdminDao dao) {
		this.dao = dao;
	}

	@RequestMapping(value = "/adminJoin.do", method = RequestMethod.GET)
	public ModelAndView joinForm() {
		ModelAndView mav = new ModelAndView("/admin/join");
		return mav;
	}

	@RequestMapping(value = "/adminJoin.do", method = RequestMethod.POST)
	public ModelAndView adminJoin(AdmVo admin) {
		ModelAndView mav = new ModelAndView("/admin/join");
		admin.setAdm_pwd(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(admin.getAdm_pwd()));
		int re = dao.insertAdmin(admin);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1) {
			mav.setViewName("redirect:/adminLogin.do");
		}
		return mav;
	}

	@RequestMapping(value = "/adminLogin.do", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		ModelAndView mav = new ModelAndView("/admin/login");
		return mav;
	}

	@RequestMapping(value = "/adminLoginOK.do")
	public ModelAndView adminLoginOK(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String id = user.getUsername();
		AdmVo admin = dao.selectAdmin(id);
		session.setAttribute("admin", admin);
		mav.setViewName("redirect:/admin/notice.do");
		return mav;
	}

	/**
	 * ????????? ?????????????????? ????????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/admin/notice.do")
	public ModelAndView selectAdminNoti(HttpSession session) {
		ModelAndView mav = new ModelAndView("/admin/board/notice");
		List<NotiVo> list = dao.selectAllNoti();
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * ???????????? ?????? form
	 */
	@RequestMapping(value = "/admin/noticeInsert.do", method = RequestMethod.GET)
	public ModelAndView insertNotiForm() {
		ModelAndView mav = new ModelAndView("/admin/board/noticeInsert");
		return mav;
	}

	/**
	 * ???????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/noticeInsert.do", method = RequestMethod.POST)
	public ModelAndView insertAdminNoti(NotiVo noti, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		MultipartFile uploadFile = noti.getUploadFile();
		String noti_file = uploadFile.getOriginalFilename();
		if (uploadFile != null && !"".equals(noti_file)) {
			FileOutputStream fos = null;
			noti_file = RenameUtil.getRename(noti_file, path);
			try {
				byte[] data = uploadFile.getBytes();
				fos = new FileOutputStream(path + "/" + noti_file);
				fos.write(data);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fos != null)
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		noti.setNoti_file(noti_file);
		int re = dao.insertNori(noti);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1)
			mav.setViewName("redirect:/admin/notice.do");
		return mav;
	}

	/**
	 * ???????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/noticeUpdate.do", method = RequestMethod.GET)
	public ModelAndView updateNotiForm(int noti_num) {
		ModelAndView mav = new ModelAndView("/admin/board/noticeUpdate");
		mav.addObject("noti", dao.selectNoti(noti_num));
		return mav;
	}

	@RequestMapping(value = "/admin/noticeUpdate.do", method = RequestMethod.POST)
	public ModelAndView updateAdminNoti(NotiVo noti, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String oldNotiFile = noti.getNoti_file();
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");

		MultipartFile uploadFile = noti.getUploadFile();
		String noti_file = uploadFile.getOriginalFilename();
		if (!"".equals(noti_file)) {
			noti_file = RenameUtil.getRename(noti_file, path);
			noti.setNoti_file(noti_file);
		}

		int re = dao.updateNoti(noti);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1) {
			if (!"".equals(noti_file)) {
				FileOutputStream fos = null;
				try {
					byte[] data = uploadFile.getBytes();
					fos = new FileOutputStream(path + "/" + noti_file);
					fos.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (fos != null)
							fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				File file = new File(path + "/" + oldNotiFile);
				file.delete();
			}
			mav.setViewName("redirect:/admin/notice.do");
		}
		return mav;
	}

	/**
	 * ???????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/admin/noticeDelete.do")
	public ModelAndView deleteAdminNoti(int noti_num, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/board/noticeDelete");
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		String noti_file = dao.selectNoti(noti_num).getNoti_file();
		int re = dao.deleteNoti(noti_num);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1) {
			mav.setViewName("redirect:/admin/notice.do");
			File file = new File(path + "/" + noti_file);
			file.delete();
		}
		return mav;
	}

	/**
	 * ???????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/admin/noticeDetail.do")
	public ModelAndView detailAdminNoti(int noti_num) {
		ModelAndView mav = new ModelAndView("/admin/board/noticeDetail");
		mav.addObject("noti", dao.selectNoti(noti_num));
		return mav;
	}

	/**
	 * localhost/admin_faq.do -> admin/faq.jsp ????????? ??????????????? ????????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/admin/faq.do")
	public ModelAndView selectAdminFaq() {
		ModelAndView mav = new ModelAndView("/admin/board/faq");
		List<FaqVo> list = dao.selectAllFaq();
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * ????????? ?????? form
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/faqInsert.do", method = RequestMethod.GET)
	public ModelAndView insertFaqForm() {
		ModelAndView mav = new ModelAndView("/admin/board/faqInsert");
		return mav;
	}

	/**
	 * ????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/faqInsert.do", method = RequestMethod.POST)
	public ModelAndView insertAdminFaq(FaqVo faq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/board/faqInsert");
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		MultipartFile uploadFile = faq.getUploadFile();
		String faq_file = uploadFile.getOriginalFilename();
		if (uploadFile != null && !"".equals(faq_file)) {
			FileOutputStream fos = null;
			faq_file = RenameUtil.getRename(faq_file, path);
			try {
				byte[] data = uploadFile.getBytes();
				fos = new FileOutputStream(path + "/" + faq_file);
				fos.write(data);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null)
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		faq.setFaq_file(faq_file);
		int re = dao.insertFaq(faq);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1)
			mav.setViewName("redirect:/admin/faq.do");
		return mav;
	}

	/**
	 * ????????? ?????? form by ?????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/faqUpdate.do", method = RequestMethod.GET)
	public ModelAndView updateFaqForm(int faq_num) {
		ModelAndView mav = new ModelAndView("/admin/board/faqUpdate");
		mav.addObject("faq", dao.selectFaq(faq_num));
		return mav;
	}

	/**
	 * ????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/faqUpdate.do", method = RequestMethod.POST)
	public ModelAndView updateAdminFaq(FaqVo faq, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/board/faqUpdate");
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		String oldFileNmae = faq.getFaq_file();

		MultipartFile uploadFile = faq.getUploadFile();
		String faq_file = uploadFile.getOriginalFilename();
		if (!"".equals(faq_file)) {
			faq_file = RenameUtil.getRename(faq_file, path);
			faq.setFaq_file(faq_file);
		}

		int re = dao.updateFaq(faq);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1) {
			if (!"".equals(faq_file)) {
				FileOutputStream fos = null;
				try {
					byte[] data = uploadFile.getBytes();
					fos = new FileOutputStream(path + "/" + faq_file);
					fos.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (fos != null)
							fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				File file = new File(path + "/" + oldFileNmae);
				file.delete();
			}
			mav.setViewName("redirect:/admin/faqDetail.do?faq_num=" + faq.getFaq_num());
		}
		return mav;
	}

	/**
	 * ????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/admin/faqDelete.do")
	public ModelAndView deletesAdminFaq(int faq_num, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/board/faqDelete");
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		String faq_file = dao.selectFaq(faq_num).getFaq_file();
		int re = dao.deleteFaq(faq_num);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1) {
			File file = new File(path + "/" + faq_file);
			file.delete();
			mav.setViewName("redirect:/admin/faq.do");
		}
		return mav;
	}

	/**
	 * ????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/admin/faqDetail.do")
	public ModelAndView detailAdminFaq(int faq_num) {
		ModelAndView mav = new ModelAndView("/admin/board/faqDetail");
		mav.addObject("faq", dao.selectFaq(faq_num));
		return mav;
	}

	/**
	 * loaclhost/admin_answer.do -> admin/admAns.jsp ????????? 1???1???????????? ????????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/admin/answer.do")
	public ModelAndView selectAdminAns() {
		ModelAndView mav = new ModelAndView("/admin/board/admAns");
		List<AdmAnsVo> list = dao.selectAllAdmQue();
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 1???1?????? ?????? by ?????????
	 * 
	 * @param adm_que_num
	 * @return
	 */
	@RequestMapping("/admin/answerDetail.do")
	public ModelAndView insertAnsForm(int adm_que_num) {
		ModelAndView mav = new ModelAndView("/admin/board/admAnsDetail");
		mav.addObject("question", dao.selectAdmQue(adm_que_num));
		AdmAnsVo admAns = dao.selectAdmAns(adm_que_num);
		if (admAns != null)
			mav.addObject("answer", dao.selectAdmAns(adm_que_num));
		return mav;
	}

	/**
	 * 1???1 ?????? ?????? ?????? form by ?????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/answerInsert.do", method = RequestMethod.GET)
	public ModelAndView insertAnsForm(int adm_que_num, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/board/admAnsInsert");
		mav.addObject("question", dao.selectAdmQue(adm_que_num));
		return mav;
	}

	/**
	 * 1???1?????? ?????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/answerInsert.do", method = RequestMethod.POST)
	public ModelAndView insertAdminAns(AdmAnsVo admAns, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/board/admAnsInsert");
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		MultipartFile uploadFile = admAns.getUploadFile();
		String adm_ans_file = uploadFile.getOriginalFilename();
		if (uploadFile != null && !"".equals(adm_ans_file)) {
			FileOutputStream fos = null;
			adm_ans_file = RenameUtil.getRename(adm_ans_file, path);
			try {
				byte[] data = uploadFile.getBytes();
				fos = new FileOutputStream(path + "/" + adm_ans_file);
				fos.write(data);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null)
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		admAns.setAdm_ans_file(adm_ans_file);

		int re1 = dao.insertAdmAns(admAns);
		int re2 = dao.updateAdmQueCheck(admAns.getAdm_que_num());
		mav.setViewName("redirect:/adminError.do");
		if (re1 == 1 && re2 == 1)
			mav.setViewName("redirect:/admin/answer.do");

		return mav;
	}

	/**
	 * 1???1?????? ?????? ?????? form by ?????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/answerUpdate.do", method = RequestMethod.GET)
	public ModelAndView updateAnsForm(int adm_que_num) {
		ModelAndView mav = new ModelAndView("/admin/board/admAnsUpdate");
		mav.addObject("question", dao.selectAdmQue(adm_que_num));
		mav.addObject("answer", dao.selectAdmAns(adm_que_num));
		return mav;
	}

	/**
	 * 1???1?????? ?????? ?????? by ?????????
	 * 
	 * @param admAns
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/answerUpdate.do", method = RequestMethod.POST)
	public ModelAndView updateAdminAns(AdmAnsVo admAns, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String oldFileName = admAns.getAdm_ans_file();
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		MultipartFile uploadFile = admAns.getUploadFile();
		String adm_ans_file = uploadFile.getOriginalFilename();
		if (!"".equals(adm_ans_file)) {
			adm_ans_file = RenameUtil.getRename(adm_ans_file, path);
			admAns.setAdm_ans_file(adm_ans_file);
		}

		int re = dao.updateAdmAns(admAns);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1) {
			FileOutputStream fos = null;
			if (!"".equals(adm_ans_file)) {
				try {
					byte[] data = uploadFile.getBytes();
					fos = new FileOutputStream(path + "/" + adm_ans_file);
					fos.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (fos != null)
							fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} // try-finally
				File file = new File(path + "/" + oldFileName);
				file.delete();
			} // if
			mav.setViewName("redirect:/admin/answerDetail.do?adm_que_num=" + admAns.getAdm_que_num());
		}
		return mav;
	}

	/**
	 * ????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/admin/theme.do")
	public ModelAndView selectAdminTheme() {
		ModelAndView mav = new ModelAndView("/admin/board/theme");
		mav.addObject("list", dao.selectAllTheme());
		return mav;
	}

	@RequestMapping(value = "/admin/themeInsert.do", method = RequestMethod.GET)
	public ModelAndView insertThemeForm() {
		ModelAndView mav = new ModelAndView("/admin/board/themeInsert");
		return mav;
	}

	/**
	 * ????????? ?????? by ?????????
	 * 
	 * @param theme
	 * @return
	 */
	@RequestMapping(value = "admin/themeInsert.do", method = RequestMethod.POST)
	public ModelAndView insertAdminTheme(ThemeVo theme, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/board/themeInsert");
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		MultipartFile uploadFile = theme.getUploadFile();
		String theme_file = uploadFile.getOriginalFilename();
		if (uploadFile != null && !"".equals(theme_file)) {
			FileOutputStream fos = null;
			theme_file = RenameUtil.getRename(theme_file, path);
			try {
				byte[] data = uploadFile.getBytes();
				fos = new FileOutputStream(path + "/" + theme_file);
				fos.write(data);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null)
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} // if

		theme.setTheme_file(theme_file);
		int re = dao.insertTheme(theme);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1)
			mav.setViewName("redirect:/admin/theme.do");
		return mav;
	}

	/**
	 * ????????? ?????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/admin/themeDelete.do")
	public ModelAndView deleteAdminTheme(int theme_num, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/board/themeDelete");
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		String theme_file = dao.selectTheme(theme_num).getTheme_file();
		int re = dao.deleteTheme(theme_num);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1) {
			File file = new File(path + "/" + theme_file);
			file.delete();
			mav.setViewName("redirect:/admin/theme.do");
		}
		return mav;
	}

	/**
	 * ????????? ?????? ?????? by ?????????
	 * 
	 * @param theme_num
	 * @return
	 */
	@RequestMapping(value = "/admin/themeUpdate.do", method = RequestMethod.GET)
	public ModelAndView updateThemeForm(int theme_num) {
		ModelAndView mav = new ModelAndView("/admin/board/themeUpdate");
		mav.addObject("theme", dao.selectTheme(theme_num));
		mav.addObject("place", dao.selectPlace(theme_num));
		mav.addObject("theme_place", dao.selectAllThemePlace(theme_num));
		return mav;
	}

	/**
	 * ????????? ?????? ?????? by ?????????
	 * 
	 * @param theme
	 * @param reuqest
	 * @return
	 */
	@RequestMapping(value = "/admin/themeUpdate.do", method = RequestMethod.POST)
	public ModelAndView updateAdminTheme(ThemeVo theme, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/admin/board/themeUpdate");
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		String oldFilename = theme.getTheme_file();
		MultipartFile uploadFile = theme.getUploadFile();
		String theme_file = uploadFile.getOriginalFilename();
		if (!"".equals(theme_file)) {
			theme_file = RenameUtil.getRename(theme_file, path);
			theme.setTheme_file(theme_file);
		}
		int re = dao.updateTheme(theme);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1) {
			if (!"".equals(theme_file)) {
				FileOutputStream fos = null;
				try {
					byte[] data = uploadFile.getBytes();
					fos = new FileOutputStream(path + "/" + theme_file);
					fos.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (fos != null)
							fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} // try-finally
				File file = new File(path + "/" + oldFilename);
				file.delete();
			} // if
			mav.setViewName("redirect:/admin/themeUpdate.do?theme_num=" + theme.getTheme_num());
		}
		// if
		return mav;
	}

	/**
	 * ????????? ?????? ?????? by ?????????
	 * 
	 * @param theme_num
	 * @param place_num
	 * @return
	 */
	@RequestMapping("/admin/themePlaceInsert.do")
	public ModelAndView insertThemePlace(int theme_num, int place_num) {
		ModelAndView mav = new ModelAndView("/admin/board/themePlaceInsert");
		int re = dao.insertThemePlace(theme_num, place_num);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1)
			mav.setViewName("redirect:/admin/themeUpdate.do?theme_num=" + theme_num);
		return mav;
	}

	/**
	 * ????????? ?????? ?????? by ?????????
	 * 
	 * @param theme_num
	 * @param place_num
	 * @return
	 */
	@RequestMapping("/admin/themePlaceDelete.do")
	public ModelAndView deleteThemePlace(int theme_num, int place_num) {
		ModelAndView mav = new ModelAndView("/admin/board/themePlaceDelete");
		int re = dao.deleteThemePlace(theme_num, place_num);
		mav.setViewName("redirect:/adminError.do");
		if (re == 1)
			mav.setViewName("redirect:/admin/themeUpdate.do?theme_num=" + theme_num);
		return mav;
	}

	/**
	 * 
	 * /** ????????? ??????????????? by ?????????
	 * 
	 * @return
	 */
	@RequestMapping("/adminError.do")
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView("/admin/error");
		return mav;
	}

	/**
	 * ?????? ????????????<br>
	 * jsp?????? jstl??? ???????????? ?????? ?????? ??? a href??? link<br>
	 * ???????????? ?????? ?????? not null, ???????????? ?????? ?????? null by ?????????
	 * 
	 * @param filename
	 * @param request
	 * @param response
	 */
	@RequestMapping("/file/filedownload")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		String fname = request.getParameter("filename"); // QueryString?????? ???????????? ???????????? ????????????
		File file = new File(path + "/" + fname);
		FileInputStream fis = null; // ?????? ????????????
		BufferedInputStream bis = null; // ?????? ????????????
		ServletOutputStream sos = null; // InputStream?????? ???????????? ???????????? ServletOutputStream??? ???????????? ??????
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			sos = response.getOutputStream();
			String reFilename = ""; // ???????????? ????????? ?????? ??????
			// IE??? ????????? ????????? ?????? ????????? ???????????? ??????.
			// IE??? request??? ????????? MSIE ?????? Trident??? ???????????? ??????.
			// IE??? ????????? ?????? ??????????????? ????????? ?????? ????????? ????????? ?????? ???????????? ?????? ?????????
			boolean isMSIE = request.getHeader("user-agent").indexOf("MSIE") != -1
					|| request.getHeader("user-agent").indexOf("Trident") != -1;
			if (isMSIE) {
				reFilename = URLEncoder.encode(fname, "utf-8");
				reFilename = reFilename.replaceAll("\\+", "%20");
			} else {
				reFilename = new String(fname.getBytes("utf-8"), "ISO-8859-1");
			}
			// response??? ContentType??? ContentLength??? ???????????? ????????? ????????????.
			response.setContentType("application/octet-stream;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + reFilename + "\"");
			response.setContentLength((int) file.length());
			int read = 0;
			while ((read = bis.read()) != -1) {
				sos.write(read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (sos != null)
					sos.close();
				if (bis != null)
					bis.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ????????? ????????????
	 * by ?????????
	 */
	@RequestMapping("/admin/userList.do")
	public ModelAndView admUserList() {
		ModelAndView mav = new ModelAndView("/admin/mgt/userList");
		mav.addObject("uList", dao.selectAllUser());
		return mav;
	} 

	/**
	 * ????????? ?????? ????????????
	 * by ?????????
	 */
	@RequestMapping("/admin/userListDetail.do")
	public ModelAndView admUserListDetail(int user_num) {
		ModelAndView mav = new ModelAndView("/admin/mgt/userListDetail");
		HashMap map = new HashMap();
		if(user_num != 0) {
			map.put("user_num", user_num);
		}
		mav.addObject("uVo", dao.selectOneUser(user_num));
		mav.addObject("login", dao.selectOneUserInfo(map));
		mav.addObject("rsvt", dao.selectUserRsvt(map));
		return mav;
	} 
	
	/**
	 * ????????? ?????? ???????????? ??????
	 * by ?????????
	 */
	@RequestMapping(value = "/admin/userListUpdate.do", method = RequestMethod.GET)
	public ModelAndView updateAdmUserList(int user_num) {
		ModelAndView mav = new ModelAndView("/admin/mgt/userListUpdate");
		HashMap map = new HashMap();
		if(user_num != 0) {
			map.put("user_num", user_num);
		}
		mav.addObject("uVo", dao.selectOneUser(user_num));
		mav.addObject("login", dao.selectOneUserInfo(map));
		mav.addObject("rsvt", dao.selectUserRsvt(map));
		return mav;
	} 

	@RequestMapping(value = "/admin/userListUpdate.do", method = RequestMethod.POST)
	public ModelAndView updateUserGrade(UserVo uVo) {
		ModelAndView mav = new ModelAndView();
		int re = dao.updateUserGrade(uVo);
		if(re == 1) {
			mav.setViewName("redirect:/userListDetail.do?user_num="+uVo.getUser_num());
		}else {
			mav.setViewName("redirect:/adminError.do");
		}
		
		return mav;
	} 
	
	/**
	 * ????????? ????????????
	 * by ?????????
	 */
	@RequestMapping("/admin/placeList.do")
	public ModelAndView admPlaceList(@RequestParam(value = "place_num", defaultValue = "0")  int place_num) {
		ModelAndView mav = new ModelAndView("/admin/mgt/placeList");
		HashMap map = new HashMap();
		if(place_num != 0) {
			map.put("place_num", place_num);
		}
		mav.addObject("pList", dao.selectAllPlace(map));
		return mav;
	} 

	/**
	 * ????????? ????????????
	 * by ?????????
	 */
	@RequestMapping("/admin/rsvtList.do")
	public ModelAndView admRsvtList(@RequestParam(value = "user_num", defaultValue = "0")  int user_num) {
		ModelAndView mav = new ModelAndView("/admin/mgt/rsvtList");
		HashMap map = new HashMap();
		if(user_num != 0) {
			map.put("user_num", user_num);
		}
		mav.addObject("rList", dao.selectAllRsvt(map));
		return mav;
	} 
	
	/**
	 * ????????? ????????????
	 * by ?????????
	 */
	@RequestMapping("/admin/totalSales.do")
	public ModelAndView admTotalSales() {
		ModelAndView mav = new ModelAndView("/admin/sales/totalSales");
		HashMap map = new HashMap();
		mav.addObject("tSales", dao.selectTotalSales(map));
		return mav;
	} 
	
	/**
	 * ????????? ????????? ??????
	 * by ?????????
	 */
	@RequestMapping("/admin/placeSales.do")
	public ModelAndView admPlaceSales(@RequestParam(value = "place_num", defaultValue = "0")  int place_num) {
		ModelAndView mav = new ModelAndView("/admin/sales/placeSales");
		HashMap map = new HashMap();
		if(place_num != 0) {
			map.put("place_num", place_num);
		}
		mav.addObject("pList", dao.selectAllPlace(map));
		return mav;
	} 

	/**
	 * ????????? ????????? ????????????
	 * by ?????????
	 */
	@RequestMapping("/admin/placeSalesDetail.do")
	public ModelAndView admPlaceSalesDetail(@RequestParam(value = "place_num", defaultValue = "0") int place_num) {
		ModelAndView mav = new ModelAndView("/admin/sales/placeSalesDetail");
		HashMap map = new HashMap();
		if(place_num != 0) {
			map.put("place_num", place_num);
		}
		mav.addObject("pSales", dao.selectPlaceSalesDetail(map));
		return mav;
	} 
	
	/**
	 * ????????? ???????????? ??????
	 * by ?????????
	 */
	@RequestMapping("/admin/hostSales.do")
	public ModelAndView admHostSales(@RequestParam(value = "host_user_num", defaultValue = "0")  int host_user_num) {
		ModelAndView mav = new ModelAndView("/admin/sales/hostSales");
		HashMap map = new HashMap();
		if(host_user_num != 0) {
			map.put("host_user_num", host_user_num);
		}
		mav.addObject("hList", dao.selectHostSales(map));
		return mav;
	} 
	
	/**
	 * ????????? ???????????? ?????? ??????
	 * by ?????????
	 */
	@RequestMapping("/admin/hostSalesPlace.do")
	public ModelAndView admHostSalesPlace(int host_user_num) {
		ModelAndView mav = new ModelAndView("/admin/sales/hostSalesPlace");
		HashMap map = new HashMap();
		if(host_user_num != 0) {
			map.put("host_user_num", host_user_num);
		}
		mav.addObject("hPlaceList", dao.selectHostPlace(map));
		return mav;
	} 

	/**
	 * ????????? ???????????? ????????????
	 * by ?????????
	 */
	@RequestMapping("/admin/hostSalesDetail.do")
	public ModelAndView admHostSalesDetail(int place_num) {
		ModelAndView mav = new ModelAndView("/admin/sales/hostSalesDetail");
		HashMap map = new HashMap();
		if(place_num != 0) {
			map.put("place_num", place_num);
		}
		mav.addObject("pSales", dao.selectPlaceSalesDetail(map));
		return mav;
	} 
}
