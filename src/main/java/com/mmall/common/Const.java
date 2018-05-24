/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Const
 * Author:   Administrator
 * Date:     2018/5/23/023 23:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mmall.common;



/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2018/5/23/023
 * @since 1.0.0
 */
public class Const {

    public static final String CURRENT_USER = "CurrentUser";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public interface Role{
        int ROLE_CUSTOMER = 0;  //普通用户
        int ROLE_ADMIN = 1;     //管理员
    }

}