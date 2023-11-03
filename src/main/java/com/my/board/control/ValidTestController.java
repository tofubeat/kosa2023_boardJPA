package com.my.board.control;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.my.board.dto.Board;

@RestController
@Validated
public class ValidTestController {
	//유효성검사

	@GetMapping(value="/a", produces="text/html;charset=utf-8")
//	MediaType.TEXT_HTML_VALUE)은 json만 쓰기(html은 charset지원X)
	public String a(String no, String sal) {
		if(no == null) {
			return "no값을 반드시 입력하세요";
		}else if(no.length()<3) {
			return "no는 3자리 이상으로 입력하세요";
		}
		return "성공";
	}
	
	//@으로 유효성검사
	@GetMapping(value="/b", produces="text/html;charset=utf-8")
	public String b(@NotNull(message="no값을 반드시 입력하세요") 
					@Size(min = 3, message="no는 3자리 이상으로 입력하세요") 
					String no, int sal) {
		return "성공";
	}

	@GetMapping(value="/c", produces="text/html;charset=utf-8")
	public String c(@Validated Board b) {
		return "성공";
	}
	
	@GetMapping(value="/d", produces="text/html;charset=utf-8")
	public String d(@Validated @RequestBody Board b) {
		return "성공";
	}
	
	@GetMapping("value=/e/{no}")
	public String e(@Size(min=3, message="no는 3자리 이상으로 입력하세요")
					@PathVariable String no) {
		return "성공";
	}
	
}
