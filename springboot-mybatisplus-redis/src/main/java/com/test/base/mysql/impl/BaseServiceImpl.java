package com.test.base.mysql.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.base.mysql.BaseService;


public class BaseServiceImpl<M extends BaseMapper<T> ,T> extends ServiceImpl<BaseMapper<T>,T> implements BaseService<T> {


}
