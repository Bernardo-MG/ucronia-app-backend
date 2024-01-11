/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.auth.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.auth.user.persistence.repository.UserMemberRepository;
import com.bernardomg.association.auth.user.service.DefaultUserMemberService;
import com.bernardomg.association.auth.user.service.UserMemberService;
import com.bernardomg.association.persistence.member.repository.MemberRepository;
import com.bernardomg.security.authentication.user.persistence.repository.UserRepository;

@Configuration
public class AssociationUserConfig {

    public AssociationUserConfig() {
        super();
    }

    @Bean("userMemberService")
    public UserMemberService getUserMemberServicee(final UserRepository userRepository,
            final MemberRepository memberRepository, final UserMemberRepository userMemberRepository) {
        return new DefaultUserMemberService(userRepository, memberRepository, userMemberRepository);
    }

}
