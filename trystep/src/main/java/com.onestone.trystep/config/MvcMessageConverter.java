package com.onestone.trystep.config;

import com.holly.bean.User;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/3 11:41
 */

public class MvcMessageConverter extends AbstractHttpMessageConverter<User> {
    public MvcMessageConverter(){
        super(new MediaType("application","x-user", Charset.forName("UTF-8")));
    }
    @Override
    protected boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    protected User readInternal(Class<? extends User> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String userStr = StreamUtils.copyToString(inputMessage.getBody(),Charset.forName("UTF-8"));
        String[] userArr = userStr.split("-");
        User user = new User();
        user.setUserId(Integer.valueOf(userArr[0]));
        user.setName(userArr[1]);
        return user;
    }

    @Override
    protected void writeInternal(User user, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String out = "hello:"+user.getName();
        outputMessage.getBody().write(out.getBytes());
    }
}
