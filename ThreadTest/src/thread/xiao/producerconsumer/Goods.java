package thread.xiao.producerconsumer;

public class Goods {
    private Integer num;


    private Object objp ;
    private Object objc ;

    public Object getObjp() {
        return objp;
    }

    public Object getObjc() {
        return objc;
    }

    public void setObjc(Object objc) {
        this.objc = objc;
    }

    public void setObjp(Object objp) {
        this.objp = objp;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
