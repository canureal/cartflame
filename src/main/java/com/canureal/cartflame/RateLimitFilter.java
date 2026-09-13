package com.canureal.cartflame;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {
    private final StringRedisTemplate redisTemplate;

    // 100 req per sec
    private static final int MAX_REQUESTS = 100;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String clientIp = getClientIp(request);
        String key = "rate-limit: " + clientIp;

        Long currentCount = redisTemplate.opsForValue().increment(key);

        if (currentCount != null && currentCount ==1) {
            redisTemplate.expire(key, WINDOW);
        }

        if(currentCount != null && currentCount > MAX_REQUESTS) {
            response.setStatus(429); // too many requests
            response.getWriter().write("too many requests, please try again later.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0]; // little regex magic huh?
    }
}
