package com.pz998.search.model.vo;

import java.util.HashMap;
import java.util.Map;

public class ResponseVo<T> {
	private String code;
	private String codeMsg;
	
	private T datas;
	
	public static Map<String,String> CODE_MAP = new HashMap<String,String>();
	public static final String CODE_COMMON_FAILED = "0";
	public static final String CODE_COMMON_SUCCESS = "1";
	public static final String CODE_ACCOUNT_NOT_EXIST = "10000001";
	public static final String CODE_ACCOUNT_PWD_NOT_MATCH = "10000002";
	public static final String CODE_USER_IDENTITY_ERROR = "10000003";
	public static final String CODE_VERFI_CODE_OUT_OF_DATE = "10000004";
	public static final String CODE_VERFI_CODE_INPUT_WRONG = "10000005";
	public static final String CODE_NAME_EXIST = "10000006";
	public static final String CODE_OLD_PWD_NOT_MATCH = "10000007";
	public static final String CODE_MOBILE_EXIST = "10000008";
	public static final String CODE_MOBILE_BINDED = "10000009";
	public static final String CODE_EMAIL_WRONG = "100000010";
	public static final String CODE_PWD_HAS_SETTING = "100000011";
	public static final String CODE_WITHDRAW_NOT_ENOUGH_MONEY = "100000012";
	public static final String CODE_WITHDRAW_PWD_NOT_MATCH = "100000013";
	public static final String CODE_EXIST_PROCESSING_WITHDRAW = "100000014";
	public static final String CODE_ACCOUNT_STOP = "100000015";
	public static final String CODE_NOT_WITHDRAW_PWD = "100000016";
	public static final String CODE_DUTY_OUT_OF_DATE = "100000017";
	public static final String CODE_CUSTOMER_ADDRESS_HAS_EXIST = "100000018";
	
	public static final String CODE_PARAMETER_ERROR = "100000019";
	
	public static final String CODE_REPEAT_SUBMIT_NULL_ORDER = "100000020";
	public static final String CODE_CANCEL_ORDER_NUMS_TWO_REMIND = "100000021";
	public static final String CODE_CANCEL_ORDER_MORE_THAN_THREE_REMIND = "100000022";
	public static final String CODE_ORDER_HASED_ACCEPT = "100000023";
	public static final String CODE_NOT_SERVICE_THIS_CITY_ORDER = "100000024";
	
	public static final String CODE_PAY_OUT_OF_DATE = "20000000";
	public static final String CODE_PAY_ORDER_NOT_EXIST = "20000001";
	public static final String CODE_PAY_ORDER_STATUS_ERROR = "20000002";
	public static final String CODE_PAY_ORDER_ID_NULL = "20000003";
	public static final String CODE_PAY_ORDER_HAS_PAY = "20000004";
	public static final String CODE_PAY_TRANS_RECORD_NOT_EXIST = "20000005";
	public static final String CODE_PAY_TRANS_EXISTED_TRANS_NOT_OUT_TRADE_NO = "20000006";
	public static final String CODE_PAY_HAS_EXIST_REFUND_RECORD = "20000007";
	public static final String CODE_PAY_APPLY_WX_REFUND_FAILD_RECORD = "20000008";
	public static final String CODE_PAY_MONEY_EXCEPTION = "20000009";
	
	public static final String CODE_INVITATION_NOT_NEW_USER = "30000001";
	public static final String CODE_INVITATION_EXIST_RED_PACKAGE_SPLIT_RECORD = "30000002";
	public static final String CODE_INVITATION_VALID_CODE = "30000003";
	public static final String CODE_INVITATION_SELF_NOT_INVITATION_SELF = "30000004";
	
	
	public static final String SMS_RETURN_CODE_FAILED = "-1";
	public static final String SMS_RETURN_CODE_SUCCESS_RLY = "000000";
	public static final String SMS_RETURN_CODE_SUCCESS = "0";
	
	public static final String CODE_INVALID_REQUEST = "-99999999";
	
	static {
		CODE_MAP.put(CODE_PARAMETER_ERROR, "参数错误");
		CODE_MAP.put(CODE_COMMON_FAILED, "调用失败");
		CODE_MAP.put(CODE_COMMON_SUCCESS, "success");
		CODE_MAP.put(CODE_ACCOUNT_NOT_EXIST, "账户名不存在");
		CODE_MAP.put(CODE_ACCOUNT_PWD_NOT_MATCH, "密码输入错误");
		CODE_MAP.put(CODE_USER_IDENTITY_ERROR, "用户身份错误");
		CODE_MAP.put(CODE_VERFI_CODE_OUT_OF_DATE, "验证码过期");
		CODE_MAP.put(CODE_VERFI_CODE_INPUT_WRONG, "验证码输入错误");
		CODE_MAP.put(CODE_NAME_EXIST, "用户名已经存在");
		CODE_MAP.put(CODE_OLD_PWD_NOT_MATCH, "老密码不正确");
		CODE_MAP.put(SMS_RETURN_CODE_FAILED, "短信发送失败");
		CODE_MAP.put(CODE_MOBILE_EXIST, "手机号已被注册");
		CODE_MAP.put(CODE_MOBILE_BINDED, "已绑定手机号");
		CODE_MAP.put(CODE_EMAIL_WRONG, "邮箱格式错误");
		CODE_MAP.put(CODE_PWD_HAS_SETTING, "密码已经设置");
		CODE_MAP.put(CODE_WITHDRAW_NOT_ENOUGH_MONEY, "提现余额不足");
		CODE_MAP.put(CODE_WITHDRAW_PWD_NOT_MATCH, "提现密码输入错误");
		CODE_MAP.put(CODE_INVALID_REQUEST, "非法请求");
		CODE_MAP.put(CODE_EXIST_PROCESSING_WITHDRAW, "存在流程中的提现记录");
		CODE_MAP.put(CODE_ACCOUNT_STOP, "帐号已停用");
		CODE_MAP.put(CODE_NOT_WITHDRAW_PWD, "未设置提现密码");
		CODE_MAP.put(CODE_DUTY_OUT_OF_DATE, "工单过期，不能接单，请联系客服处理");
		CODE_MAP.put(CODE_CUSTOMER_ADDRESS_HAS_EXIST, "顾客地址已经存在，请不要重复添加");
		CODE_MAP.put(CODE_REPEAT_SUBMIT_NULL_ORDER, "上次就诊资料尚未提交，请勿重复预约！");
		CODE_MAP.put(CODE_CANCEL_ORDER_NUMS_TWO_REMIND, "取消成功，24小时内您只有3次取消机会");
		CODE_MAP.put(CODE_CANCEL_ORDER_MORE_THAN_THREE_REMIND, "温馨提示：请勿频繁取消订单，以免账户冻结。客服电话400-6364-998");
		CODE_MAP.put(CODE_ORDER_HASED_ACCEPT, "很抱歉，订单已经被抢了！");
		CODE_MAP.put(CODE_NOT_SERVICE_THIS_CITY_ORDER, "很抱歉，您不能接该城市的订单！");
		
		
		CODE_MAP.put(CODE_PAY_OUT_OF_DATE, "支付已经过期,请重新下单");
		CODE_MAP.put(CODE_PAY_ORDER_NOT_EXIST, "支付的订单不存在");
		CODE_MAP.put(CODE_PAY_ORDER_STATUS_ERROR, "支付的订单状态不是待支付");
		CODE_MAP.put(CODE_PAY_ORDER_ID_NULL, "支付的订单ID为空");
		CODE_MAP.put(CODE_PAY_ORDER_HAS_PAY, "该订单已经支付");
		CODE_MAP.put(CODE_PAY_TRANS_RECORD_NOT_EXIST, "支付记录不存在");
		CODE_MAP.put(CODE_PAY_TRANS_EXISTED_TRANS_NOT_OUT_TRADE_NO, "支付流水已经存在，非第一次支付需要outTrandNo");
		CODE_MAP.put(CODE_PAY_HAS_EXIST_REFUND_RECORD, "退款记录已存在");
		CODE_MAP.put(CODE_PAY_APPLY_WX_REFUND_FAILD_RECORD, "申请微信退款失败");
		CODE_MAP.put(CODE_PAY_MONEY_EXCEPTION, "支付金额异常！");
		
		CODE_MAP.put(CODE_INVITATION_NOT_NEW_USER, "该福利只有新用户才能领取哦！");
		CODE_MAP.put(CODE_INVITATION_EXIST_RED_PACKAGE_SPLIT_RECORD, "已经接受其他人的邀请，请登录领取该福利哦！");
		CODE_MAP.put(CODE_INVITATION_VALID_CODE, "无效邀请码");
		CODE_MAP.put(CODE_INVITATION_SELF_NOT_INVITATION_SELF, "自己不能邀请自己哦！");
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeMsg() {
		return codeMsg;
	}

	public void setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
	}

	public T getDatas() {
		return datas;
	}

	public void setDatas(T datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "ResponseVoRpc [code=" + code + ", codeMsg=" + codeMsg + ", datas=" + datas + "]";
	}
	
}
