package com.example.hanghaememo.repository;


import com.example.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllById(Long id);

    List<Memo> findAllByOrderByModifiedAtDesc();
}