package com.my.board.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter

@Entity
@Table(name="board_reply_tbl")
@SequenceGenerator(name="REPLY_SEQ_GENERATOR", sequenceName="reply_tbl_seq", 
				   initialValue=1, allocationSize=1)
@DynamicInsert
public class ReplyEntity {
	@Id
	@Column(name="reply_no")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REPLY_SEQ_GENERATOR")
	private Integer replyNo;
	
	@Column(name="reply_board_no")
	private Integer replyBoardNo;
	
	@JoinColumn(name="reply_no")
	private Integer replyParentNo;
	
	@Column(name="reply_content")
	private String replyContent;
	
	@Column(name="reply_id")
	private String replyId;
	
	@Column(name="reply_dt")
	@ColumnDefault(value = "SYSDATE")
	private Date replyDt;
	
	@Transient
	private Integer level;
	
//	@JoinColumn(name="board_no")
//	private Board b;
}
