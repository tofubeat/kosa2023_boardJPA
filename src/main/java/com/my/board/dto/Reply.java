package com.my.board.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
@Builder
public class Reply {
	private Integer replyNo;
	private Integer replyBoardNo;
	private Integer replyParentNo;
	private String replyContent;
	private String replyId;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date replyDt;
	private Integer level;
}
