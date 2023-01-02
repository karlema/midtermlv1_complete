package com.example.hanghaememo.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.hanghaememo.dto.MemoRequestDto;

@Getter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String pwd;

    public Memo(MemoRequestDto requestDto) {
        this.username = requestDto.getUserName();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.pwd = requestDto.getPwd();
    }

    public void update(MemoRequestDto requestDto) {
        this.username = requestDto.getUserName();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.pwd = requestDto.getPwd();

    }
}