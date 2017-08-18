package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 风控材料审核控制器(后台)
 * 
 * @author xmg
 *
 */
@Controller
public class UserFileController {

	@Autowired
	private IUserFileService userFileService;

	@RequestMapping("userFileAuth")
	public String userFileList(@ModelAttribute("qo") UserFileQueryObject qo, Model model) {
		model.addAttribute("pageResult", this.userFileService.query(qo));
		return "userFileAuth/list";
	}
	
	/**
	 * 风控材料审核
	 */
	@RequestMapping("userFile_audit")
	@ResponseBody
	public AjaxResult audit(Long id,int state,int score,String remark){
		this.userFileService.audit(id,state,score,remark);
		return new AjaxResult();
	}

}
