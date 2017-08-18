package com.xmg.p2p.base.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.UploadUtil;
import com.xmg.p2p.base.util.UserContext;

/**
 * 用户风控材料(前台)
 * 
 * @author xmg
 *
 */
@Controller
public class UserFileController {

	@Autowired
	private ServletContext ctx;

	@Autowired
	private IUserFileService userFileService;

	@Autowired
	private ISystemDictionaryItemService itemService;

	/**
	 * 点击风控材料
	 */
	@RequestMapping("userFile")
	public String userFilePage(Model model,HttpSession session) {
		List<UserFile> userFiles = this.userFileService.listSelectTypeUserFiles(UserContext.getCurrent().getId(),
				false);
		if (userFiles.size() > 0) {
			model.addAttribute("userFiles", userFiles);
			model.addAttribute("fileTypes", this.itemService.listItemBySn("userFileType"));
			return "userFiles_commit";
		} else {
			model.addAttribute("userFiles",
					this.userFileService.listSelectTypeUserFiles(UserContext.getCurrent().getId(), true));
			model.addAttribute("sessionid",session.getId());
			return "userFiles";
		}
	}

	/**
	 * 上传风控材料图片
	 */
	@RequestMapping("applyUserFile")
	@ResponseBody
	public String applyUserFile(MultipartFile img) {
		String fileName = UploadUtil.upload(img, ctx.getRealPath("/upload"));
		fileName = "/upload/" + fileName;
		this.userFileService.apply(fileName);
		return fileName;
	}

	/**
	 * 处理用户选择风控材料分类
	 */
	@RequestMapping("userFile_selectType")
	@ResponseBody
	public AjaxResult choiceUserFileType(Long[] id, Long[] fileType) {
		if (id != null && fileType != null && id.length == fileType.length) {
			this.userFileService.choiceUserFileType(id, fileType);
		}
		return new AjaxResult();
	}

}
