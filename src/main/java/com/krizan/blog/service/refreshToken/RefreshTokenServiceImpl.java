package com.krizan.blog.service.refreshToken;

import com.krizan.blog.exception.NotFoundException;
import com.krizan.blog.model.RefreshToken;
import com.krizan.blog.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateRefreshToken() {
        return refreshTokenRepository.save(new RefreshToken());
    }

    @Override
    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
