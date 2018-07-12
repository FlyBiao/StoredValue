package com.sw.android.storedvalue.base;


/**
 * Created by FGB on 2016/3/5.
 */
public class BaseBean {
	protected long id;
	public boolean IsSuccess;
	public boolean IsNotSuccess;
	public String Message;
	public String ResultNo;
	public int RecordCount;
	public int PageCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isSuccess() {
		return IsSuccess;
	}

	public void setSuccess(boolean success) {
		IsSuccess = success;
	}

	public boolean isNotSuccess() {
		return IsNotSuccess;
	}

	public void setNotSuccess(boolean notSuccess) {
		IsNotSuccess = notSuccess;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getResultNo() {
		return ResultNo;
	}

	public void setResultNo(String resultNo) {
		ResultNo = resultNo;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public int getPageCount() {
		return PageCount;
	}

	public void setPageCount(int pageCount) {
		PageCount = pageCount;
	}
}
