package com.my.board.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Builder
public class Board {
	private Integer boardNo;
	private String boardTitle;
	
	@NotEmpty(message="글내용은 반드시 입력하세요") 
	//ㄴnull도 빈문자열도 아니어야함
	@Size(max=10, message="글내용은 최대10자리까지만 가능합니다")
	private String boardContent;
	@NotEmpty(message="글작성자 아이디는 반드시 입력하세요")
	private String boardId;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	//날짜) 숫자->날짜, json응답할 때만 쓰임
	private Date boardDt; //java.util.Date사용
	
	private List<Reply> replies; //답글목록
	private Integer replyCnt; //답글수
}
