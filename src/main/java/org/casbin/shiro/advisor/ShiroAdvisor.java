// Copyright 2021 The casbin Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.shiro.advisor;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.casbin.shiro.authorize.rbac.annotation.auth.*;
import org.casbin.shiro.authorize.rbac.annotation.auth.interceptor.*;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * Startup class of the custom permission annotation.
 *
 * @author shy
 * @since 2021/01/19
 */
public class ShiroAdvisor extends AuthorizationAttributeSourceAdvisor {
    public ShiroAdvisor() {
        setAdvice(new EnforcerAuthAopInterceptor());
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean matches(Method method, Class targetClass) {
        Method m = method;
        try {
            m = targetClass.getMethod(m.getName(), m.getParameterTypes());
            return this.isFrameAnnotation(m);
        } catch (NoSuchMethodException ignored) {
        }
        return super.matches(method, targetClass);
    }

    private boolean isFrameAnnotation(Method method) {
        return null != AnnotationUtils.findAnnotation(method, EnforcerAuth.class);
    }
}
