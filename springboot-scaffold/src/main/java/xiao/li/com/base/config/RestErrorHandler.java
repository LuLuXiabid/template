package xiao.li.com.base.config;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
* Description <br>
*
* @param  <br>
* @return  <br>
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2021/10/29 10:57 <br>
**/
public class RestErrorHandler extends   DefaultResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return false;
    }
}
