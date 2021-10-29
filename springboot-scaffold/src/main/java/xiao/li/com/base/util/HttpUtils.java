package xiao.li.com.base.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xiao.li.com.base.exception.BaseException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author shuai.zhang@changhong.com
 * @projectName jiaxipera-cps
 * @modifier
 * @Description
 * @createTime 2021-02-17 11:23
 */
@Component
@Slf4j
public class HttpUtils {

    @Autowired
    private RestTemplate restTemplate;

    public <T> ResponseEntity<T> post(String uri, HttpHeaders httpHeaders, Object content, Class<T> resClass){

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity = null;
        try{
            requestEntity = new RequestEntity<>(
                    content,
                    httpHeaders,
                    HttpMethod.POST,
                    new URI(uri));
        }catch (URISyntaxException e){
            e.printStackTrace();
            throw new BaseException("uri格式异常");
        }

        return  restTemplate.exchange(requestEntity, resClass);
    }

    public <T> ResponseEntity<T> get(String uri, HttpHeaders httpHeaders, Class<T> resClass){
        HttpEntity entity  = new HttpEntity(httpHeaders);
        return  restTemplate.exchange(uri, HttpMethod.GET, entity, resClass);
    }

}
