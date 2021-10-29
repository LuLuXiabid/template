package xiao.li.com.base.mysql;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import xiao.li.com.base.user.CurrentUserThreadLocalUtils;

import java.util.Date;

/**
 * Descriptions <br>
 *
 * @author taotao <br>
 * @version 1.0 <br>
 * @createDate 2020/06/01 9:34 上午 <br>
 * @see com.changhong.mcc.base.mysql <br>
 */
@Slf4j
@Component
public class MccMetaObjectHandler implements MetaObjectHandler{
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "operateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "supplementTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "orderDate", Date.class, new Date());
        if(CurrentUserThreadLocalUtils.get()!=null){
            this.strictInsertFill(metaObject,"creator",Long.class, CurrentUserThreadLocalUtils.get().getUserId());
            this.strictInsertFill(metaObject,"operator",Long.class, CurrentUserThreadLocalUtils.get().getUserId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }


}
