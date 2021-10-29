package xiao.li.com.base.util;

import xiao.li.com.base.exception.BaseException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * PrintUtil
 * 打印工具类
 *
 * @author hao.wang
 * @version 1.0
 * @createDate 2020/10/9 14:02
 */
public class PrintUtil {

    /**
     * 获取生产日期 yyyyMMdd:20201009
     *
     * @param produceDate 生产日期
     * @return java.lang.String
     * @author hao.wang
     * @createDate 2020/10/9 15:42
     **/
    public static String getProduceDate(Date produceDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(produceDate);
    }

    /**
     * 获取数据源title
     *
     * @param clazz 类
     * @return java.lang.String
     * @author hao.wang
     * @createDate 2020/10/9 14:03
     **/
    public static <T> String getTitle(Class<T> clazz) {
        return getDataFields(clazz).replace("[", "").replace("]", "");
    }

    /**
     * 构建数据源列表
     *
     * @param snList sn列表
     * @param entity 类
     * @return java.lang.String
     * @author hao.wang
     * @createDate 2020/10/9 14:12
     **/
    public static <T> List<String> buildDataList(List<String> snList, T entity) {
        Class clazz = entity.getClass();
        String dataFields = getDataFields(clazz);
        return snList.stream().map(
                records -> {
                    String data = dataFields;
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String name = field.getName();
                        String value = "";
                        if (Objects.equals(name, "sn")) {
                            value = records;
                        } else {
                            try {
                                value = (String) field.get(entity);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                throw new BaseException("反射值转换异常");
                            }
                        }
                        data = data.replace("[" + name + "]", value);
                    }
                    return data;
                }
        ).collect(Collectors.toList());
    }

    /**
     * 获取数据源字段
     *
     * @param clazz 类
     * @return java.lang.String
     * @author hao.wang
     * @createDate 2020/10/9 14:03
     **/
    private static <T> String getDataFields(Class<T> clazz) {
        StringBuilder dataFields = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            dataFields.append("[");
            dataFields.append(name);
            dataFields.append("]");
            dataFields.append(",");
        }
        int lastIndex = dataFields.lastIndexOf(",");
        return dataFields.toString().substring(0, lastIndex);
    }
}
