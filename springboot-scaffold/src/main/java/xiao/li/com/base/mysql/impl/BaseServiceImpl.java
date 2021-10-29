package xiao.li.com.base.mysql.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xiao.li.com.base.mysql.BaseService;

/**
 * Descriptions <br>
 *
 * @author taotao <br>
 * @version 1.0 <br>
 * @createDate 2020/05/29 5:47 下午 <br>
 * @see com.changhong.mcc.base.mysql.impl <br>
 */
public class BaseServiceImpl<M extends BaseMapper<T> ,T> extends ServiceImpl<BaseMapper<T>,T> implements BaseService<T> {


}
