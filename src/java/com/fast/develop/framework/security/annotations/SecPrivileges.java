package com.fast.develop.framework.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限集合标注
 *
 * @author xjj
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecPrivileges {

    /**
     * 权限集合；
     *
     * @return
     */
    public SecPrivilege[] value();

}
