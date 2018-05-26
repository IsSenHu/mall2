package com.husen.mall2.global;

/**
 * 全局常量类
 * @author 11785
 */
public class GlobalVar {

    /**
     * 商家未被认证
     */
    public static final String NO_AUTHENTICATE = "noAuthenticate";


    /**
     * 正在审核中
     */
    public static final String AUTHENTICATEING = "authenticateing";

    /**
     * 商家已被认证
     */
    public static final String HAVE_AUTHENTICATE = "haveAuthenticate";

    /**
     * 商家认证不通过
     */
    public static final String FAILE_AUTHENTICATE = "faileAuthenticate";

    /**
     * 状态为上架
     */
    public static final String UP = "up";


    /**
     * 状态为下架
     */
    public static final String DOWN = "down";

    /**
     * 订单是删除状态
     */
    public static final String ORDER_ISDELETE = "orderIsDelete";

    /**
     * 订单不是删除状态
     */
    public static final String ORDER_NODELETE = "orderNoDelete";

    /**
     * 刚出库
     */
    public static final String LOGISTICS_START = "logisticsStart";

    /**
     * 路上
     */
    public static final String LOGISTICS_MIDDLE = "logisticsMiddle";

    /**
     * 已签收
     */
    public static final String LOGISTICS_END = "logisticsEnd";

    /**
     * 包邮
     */
    public static final String NO_POSTAGE = "noPostage";

    /**
     * 不包邮
     */
    public static final String HAVE_POSTAGE = "havePostage";
}
