package com.company.security.service.impl;

import com.company.security.dto.LoginDTO;
import com.company.security.dto.RegisterDTO;
import com.company.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Henry
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

   @Override
   public String login(LoginDTO dto) {


   }

   @Override
   @Transactional(rollbackFor = Exception.class)
   public void register(RegisterDTO dto) {

   }

   @Override
   public void logout(String token) {

   }
}
