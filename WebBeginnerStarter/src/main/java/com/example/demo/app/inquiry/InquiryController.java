package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryNotFoundException;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	
	private final InquiryService inquiryService;
	
	public InquiryController(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Inquiry> list = inquiryService.getAll();
		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "Inquiry Index");
		
//		Inquiry inquiry = new Inquiry();
//		inquiry.setId(4);
//		inquiry.setName("Jamei");
//		inquiry.setEmail("sample@example.com");
//		inquiry.setContents("Hello");
//		
//		inquiryService.update(inquiry);
		
//		try {
//			inquiryService.update(inquiry);
//		}catch (InquiryNotFoundException e) {
//			model.addAttribute("message", e);
//			return "error/CustomPage";
//		}

		return "inquiry/index_boot";
	}

	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, Model model,
			@ModelAttribute("complete") String complete) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form_boot";
	}

	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form_boot";
	}
	
	@PostMapping("/update")
	public String update(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/index_boot";
		}
		model.addAttribute("title", "更新フォーム");
		return "inquiry/form_boot";
	}

	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form_boot";
		}
		model.addAttribute("title", "確認ページ");
		return "inquiry/confirm_boot";
	}

	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form_boot";
		}
		
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());

		if(inquiryForm.getId() == 0) {	
			inquiryService.save(inquiry);
		} else {
			inquiry.setId(inquiryForm.getId());			
			inquiryService.update(inquiry);
		}
		redirectAttributes.addFlashAttribute("complete", "Completed!");
		return "redirect:/inquiry/form";
	}
	
//	@ExceptionHandler(InquiryNotFoundException.class)
//	public String HandleException(InquiryNotFoundException e, Model model) {
//		model.addAttribute("message", e);
//		return "error/CustomPage";
//	}

}
