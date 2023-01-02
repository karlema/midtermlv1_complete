package com.example.hanghaememo.service;

import com.example.hanghaememo.dto.RegisterRequestDto;
import com.example.hanghaememo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.hanghaememo.entity.User;
import com.example.hanghaememo.dto.LoginRequestDto;
import java.util.Optional;
import com.example.hanghaememo.entity.UserRoleEnum;

import javax.servlet.http.HttpServletResponse;
import com.example.hanghaememo.jwt.JwtUtil;

@RequiredArgsConstructor // 의존성 주입
@Service

public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    @Transactional
    public User regster(RegisterRequestDto registerRequestDto)
    {
        String userName = registerRequestDto.getUserName();
        String userPwd = registerRequestDto.getPwd();
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (registerRequestDto.isAdmin()) {
            if (!registerRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(userName,userPwd,role);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public boolean checkRegister(RegisterRequestDto registerRequestDto)
    {
        String userName = registerRequestDto.getUserName();
        Optional<User> found = userRepository.findByUsername(userName);
        if (found.isPresent()) {
            System.out.println("중복된 사용자가 존재합니다.");
            return false;
        }
        return true;
    }

    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response)
    {
        String userName = loginRequestDto.getUserName();
        String userPwd = loginRequestDto.getPwd();
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        if(user != null)
        {
            if(user.getPwd().equals(userPwd))
            {
                System.out.println("로그인 성공");
                response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
            }
            else
            {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다. 로그인 실패");
            }
        }
    }
}
