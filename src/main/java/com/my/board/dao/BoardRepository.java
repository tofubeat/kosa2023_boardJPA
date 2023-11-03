package com.my.board.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.board.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>{
	
	@Query(nativeQuery=true,
		   value="SELECT *\r\n"
		   		+ "FROM board_tbl b LEFT JOIN \r\n"
		   		+ "    (SELECT level,r1.* \r\n"
		   		+ "	 FROM board_reply_tbl r1 \r\n"
		   		+ "	 START WITH reply_parent_no IS NULL \r\n"
		   		+ "	 CONNECT BY PRIOR reply_no = reply_parent_no \r\n"
		   		+ "	 ORDER SIBLINGS BY reply_no DESC)r \r\n"
		   		+ "ON b.board_no = r.reply_board_no\r\n"
		   		+ "WHERE board_no = :param")
	public List<Object[]> findByBoardNo(int param);

}
