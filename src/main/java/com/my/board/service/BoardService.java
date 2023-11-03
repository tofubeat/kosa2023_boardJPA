package com.my.board.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.board.dao.BoardRepository;
import com.my.board.dao.ReplyRepository;
import com.my.board.dto.Board;
import com.my.board.dto.Reply;
import com.my.board.entity.BoardEntity;
import com.my.board.entity.ReplyEntity;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;

@Service
public class BoardService {
	@Autowired
	private BoardRepository repository;
	@Autowired
	private ReplyRepository rRepository;

	//게시판 dto->vo
	public BoardEntity BoardDtoToEntity(Board dto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE).setFieldMatchingEnabled(true);

		Object source = dto;
		Class<BoardEntity> destinationType = BoardEntity.class;
		BoardEntity entity = mapper.map(source, destinationType);
		return entity;
	}
	
	//게시판 vo->dto 전체검색
	public List<Board> BoardEntityToDto_All() {
		List<BoardEntity> entity = repository.findAll();
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STANDARD) 
			  .setFieldAccessLevel(AccessLevel.PRIVATE)
			  .setFieldMatchingEnabled(true);
		
		mapper.addMappings(new PropertyMap<BoardEntity, Board>() { 
			//이거써서 불필요한 데이터 안나오게 함
			@Override
			protected void configure() {
				skip(destination.getReplies());
			}
		});
		List<Board> listDTO = 
				mapper.map(entity, new TypeToken<List<Board>>() {}.getType());
		return listDTO;
	}
	
//	public Board findByBoardNo(int boardNo) throws FindException {
//		Optional<BoardEntity> entity = repository.findById(boardNo);
//		Board b = BoardEntityToDto_ById(entity);
//		int replyCnt = b.getReplies().size();
//		b.setReplyCnt(replyCnt);
//		return b;
//	} //시판 상세조회인데 이거 없이 아래 메서드로 끝내야함
	
	//게시판 vo->dto id검색
	public Board findByBoardNo(Integer boardNo) {
		List<Object[]> list=repository.findByBoardNo(boardNo);
		Object[] boardObj=list.get(0);
		
//		맵핑 하나씩 해주기
		Board boardDTO=Board.builder()
					   .boardNo(Integer.valueOf(String.valueOf(boardObj[0])))
					   .boardContent((String)boardObj[1])
					   .boardDt((Date)boardObj[2])
					   .boardId((String)boardObj[3])
					   .boardTitle((String)boardObj[4])
					   .build();
		
		List<Reply> replyList=new ArrayList<>();
		if(list.get(0)[5]==null) {
			return boardDTO;
		}
		
		for(Object[] objArr:list) {
			Reply replyDTO=Reply.builder()
						   .level(Integer.valueOf(String.valueOf(objArr[5])))
						   .replyNo(Integer.valueOf(String.valueOf(objArr[6])))
						   .replyBoardNo(Integer.valueOf(String.valueOf(objArr[7])))
						   .replyContent((String)objArr[8])
						   .replyDt((Date)objArr[9])
						   .replyId((String)objArr[10])
						   .build();
			if(objArr[11]!=null) {
				replyDTO.setReplyParentNo(Integer.valueOf(String.valueOf(objArr[11])));
			}
			replyList.add(replyDTO);
		}
		boardDTO.setReplies(replyList);
		boardDTO.setReplyCnt(replyList.size());
		return boardDTO;
	}
	
	
	//댓글 dto->vo
	public ReplyEntity ReplyDtoToEntity(Reply dto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD)
				.setFieldAccessLevel(AccessLevel.PRIVATE).setFieldMatchingEnabled(true);

		Object source = dto;
		Class<ReplyEntity> destinationType = ReplyEntity.class;
		ReplyEntity entity = mapper.map(source, destinationType);
		return entity;
	}


	// 게시글 조회/상세조회
	public List<Board> findAll() throws FindException {
		List<BoardEntity> entity = repository.findAll();
		List<Board> list = BoardEntityToDto_All();
		return list;
	}

	// 게시글 작성/수정/삭제
	public void addBoard(Board board) throws AddException {
		BoardEntity entity = BoardDtoToEntity(board);
		repository.save(entity);
	}

	public void modifyBoard(Board board) throws ModifyException {
		BoardEntity entity = BoardDtoToEntity(board);
		repository.save(entity);
	}

	public void removeBoard(int boardNo) throws RemoveException {
		repository.deleteById(boardNo);
	}


	// 답글 작성/수정/삭제
	public void addReply(Reply reply) throws AddException {
		ReplyEntity entity = ReplyDtoToEntity(reply);
		rRepository.save(entity);
	}

	public void modifyReply(Reply reply) throws ModifyException {
		ReplyEntity entity = ReplyDtoToEntity(reply);
		rRepository.save(entity);
	}

	public void removeReply(int replyNo) throws RemoveException {
		rRepository.deleteById(replyNo);
	}

}
