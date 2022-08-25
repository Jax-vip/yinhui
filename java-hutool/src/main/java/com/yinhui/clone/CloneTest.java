package com.yinhui.clone;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.yinhui.model.UserInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yinhui
 * @date 2022/8/24
 */
@Slf4j
public class CloneTest {

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("yinhui");
        userInfo.setAge(23);

        String s = JSONUtil.toJsonStr(userInfo);
        log.info(s);

        UserInfo userInfo1 = ObjectUtil.cloneByStream(userInfo);
//        log.info(userInfo1.toString());

    }

}
