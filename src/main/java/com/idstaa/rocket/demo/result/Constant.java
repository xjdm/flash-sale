/**
 * Copyright (c) 2014 ShanghaiMed iKang,Inc. All rights reserved.
 */
package com.idstaa.rocket.demo.result;


/**
 * 本地项目常量定义.
 */
public abstract class Constant {
	public enum ACCESS_CODE {
		ERROR(0, "错误提示"),
		SUCCESS(1, "操作成功"),
		FAILED(2, "操作失败"),
		DENIED(3, "拒绝访问");
		private int code;
		private String name;

		ACCESS_CODE(int code, String name) {
			this.code = code;
			this.name = name;
		}
		public int getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}

}
