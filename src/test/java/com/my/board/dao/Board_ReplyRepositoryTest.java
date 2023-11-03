package com.my.board.dao;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.my.board.dto.Board;
import com.my.board.dto.Reply;
import com.my.board.entity.BoardEntity;
import com.my.board.entity.ReplyEntity;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class Board_ReplyRepositoryTest {
	@Autowired
	BoardRepository br;
	@Autowired
	ReplyRepository rr;
	
	@Test
	void test1InsertBoard() {
		BoardEntity b = new BoardEntity();
		b.setBoardTitle("제목3");
		b.setBoardContent("내용3");
		b.setBoardId("A");
		br.save(b);
	}

	@Test
	void test2InsertBoard_Reply() {
		ReplyEntity r = new ReplyEntity();
		r.setReplyBoardNo(3);
		r.setReplyContent("답글의답글1");
		r.setReplyParentNo(6);
		r.setReplyId("B");
		rr.save(r);
	}

	@Test
	@Transactional
	@Commit
	void test3DeleteById_Board() {
		int boardNo = 1;
		br.deleteById(boardNo);
	}
	
	@Test
	void test4DeleteById_Reply() {
		int replyNo = 2;
		rr.deleteById(replyNo);
	}
	
	@Test
	void test5BoardDtoToEntity() {
		Board dto = new Board();
//		dto.setBoardNo(3);
		dto.setBoardTitle("제목3");
		dto.setBoardContent("내용3");
		dto.setBoardId("B");
		dto.setBoardDt(new java.util.Date());
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STANDARD)
			  .setFieldAccessLevel(AccessLevel.PRIVATE)
			  .setFieldMatchingEnabled(true);
		
		Object source = dto;
		Class<BoardEntity> destinationType = BoardEntity.class;
		BoardEntity entity = mapper.map(source, destinationType);
	}
	
	@Test
	void test6ReplyDtoToEntity() {
		Reply dto = new Reply();
		dto.setReplyBoardNo(1);
//		dto.setReplyParentNo(null);
		dto.setReplyContent("답글3");
		dto.setReplyId("B");
		dto.setReplyDt(new java.util.Date());
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STANDARD)
			  .setFieldAccessLevel(AccessLevel.PRIVATE)
			  .setFieldMatchingEnabled(true);
		
		Object source = dto;
		Class<ReplyEntity> destinationType = ReplyEntity.class;
		ReplyEntity entity = mapper.map(source, destinationType);
	}
	
	@Test
	void test7FindAll_Board() {
		Iterable<BoardEntity> it = br.findAll();
		log.error("br.findAll(): {}"+it);
	}
	
	@Test
	void test8FindById_Board() {
		int boardNo = 3;
		Optional<BoardEntity> optB = br.findById(boardNo);
		log.error("br.findById(): {}"+optB);
	}
	
	@Test
	void test9BoardEntityToDto_All() {
		List<BoardEntity> entity = br.findAll();
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STANDARD) 
			  .setFieldAccessLevel(AccessLevel.PRIVATE)
			  .setFieldMatchingEnabled(true);
		
		List<Board> listDTO = 
				mapper.map(entity, new TypeToken<List<Board>>() {}.getType()); //타입토큰으로 3줄효과냄
	}
	
	@Test
	void test10BoardEntityToDto_ById() {
		int boardNo = 3;
		Optional<BoardEntity> optB = br.findById(boardNo);
		BoardEntity entity = optB.get();
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STANDARD) 
			  .setFieldAccessLevel(AccessLevel.PRIVATE)
			  .setFieldMatchingEnabled(true);
		
		Object source = entity;
		Class<Board> destinationType = Board.class;
		Board dto = mapper.map(source, destinationType); //VO -> DTO
		
		
		
		log.error("dto boardNo:{}, boardTitle:{}, boardContent:{}, boardId:{}, boardDt:{}, replies:{}",
				dto.getBoardNo(), dto.getBoardTitle(), dto.getBoardContent(), dto.getBoardId(), dto.getBoardDt(),
				dto.getReplies());
	}
}
