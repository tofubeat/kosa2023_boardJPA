package com.my.board.control;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.board.dto.Board;
import com.my.board.dto.Reply;
import com.my.board.entity.BoardEntity;
import com.my.board.entity.ReplyEntity;
import com.my.board.service.BoardService;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;

@RestController //return값 json으로 응답함
@RequestMapping("/board") //요청방식에 상관없이 공통된 URI 
public class BoardController {
	private Logger log = LoggerFactory.getLogger(this.getClass()); 
	//this.getClass()=BoardController.java
	@Autowired
	private BoardService service;
	
	@GetMapping("/list") //게시글 전체보기
	public List<Board> list() throws FindException {
		return service.findAll();
	}
	
	@GetMapping("/{boardNo}") //1번 게시글 상세보기
	public Board info(@PathVariable int boardNo) throws FindException { 
					//ㄴ{}안에 쓴 것=매개변수이름, @PathVariable: URI의 패스값을 얻어 옴
		return service.findByBoardNo(boardNo);
	}

	//POST /board
	@PostMapping(value="", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> write(@RequestBody Board board) throws AddException { 
					//ㄴ요청시 json으로 받으려면 @RequestBody를 써야함(@RequestParam X)
		try{
			service.addBoard(board);
			return new ResponseEntity<>(HttpStatus.OK);			
		} catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//PUT /board/1
	@PutMapping(value="/{boardNo}", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> modify(@PathVariable int boardNo, @RequestBody Board board) throws ModifyException {
		board.setBoardNo(boardNo);
		//게시글 내용만 수정가능
		try{
			service.modifyBoard(board);
			return new ResponseEntity<>(HttpStatus.OK);			
		} catch(ModifyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//DELETE /board/1
	@DeleteMapping(value="/{boardNo}", produces="application/json;charset=UTF-8")
	public ResponseEntity<?> delete(@PathVariable int boardNo) throws RemoveException {
		try{
			service.removeBoard(boardNo);
			return new ResponseEntity<>(HttpStatus.OK);			
		} catch(RemoveException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//POST /board/reply/1/9  /board/reply/1
	@PostMapping(value={"reply/{boardNo}/{parentNo}", "reply/{boardNo}"})
	public ResponseEntity<?> writeReply(@PathVariable int boardNo,
									    @PathVariable(name="parentNo") Optional<Integer> optParentNo,
									    //@PathVariable은 @RequestParam과 달리 defaultValue설정이 없음 =>Optional씀
									    @RequestBody Reply reply) throws AddException {
		reply.setReplyBoardNo(boardNo);
		Integer parentNo = null;
		if(optParentNo.isPresent()) {
			parentNo = optParentNo.get(); 
			//아래에 바로 셋팅하기엔 dto에 그냥 integer라 타입 달라서 안됨
		}
		reply.setReplyParentNo(parentNo);
		try {
			service.addReply(reply);
			return new ResponseEntity<>(HttpStatus.OK);			
		}catch(AddException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//PUT /board/reply/15
	@PutMapping(value="/reply/{replyNo}")
	public ResponseEntity<?> modifyReply(@PathVariable int replyNo, 
										 @RequestBody Reply reply) throws ModifyException{
		reply.setReplyNo(replyNo);
		try {
			service.modifyReply(reply);
			return new ResponseEntity<>(HttpStatus.OK);			
		}catch(ModifyException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//DELETE /board/reply/9
	@DeleteMapping(value="/reply/{replyNo}")
	public ResponseEntity<?> removeReply(@PathVariable int replyNo) throws RemoveException{
		try {			
			service.removeReply(replyNo);
			return new ResponseEntity<>(HttpStatus.OK);			
		}catch(RemoveException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
