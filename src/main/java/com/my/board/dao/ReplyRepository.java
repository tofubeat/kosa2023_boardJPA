package com.my.board.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.board.entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer>{

}
