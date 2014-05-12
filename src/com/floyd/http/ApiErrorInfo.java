/**
 *
 */
package com.floyd.http;


/**
 * 错误信息Bean
 *
 * @author floydchenxf
 */
public class ApiErrorInfo {

    private Integer errorCode;
    private String errorDesc;
    private Object obj;

    /**
     *
     */
    public ApiErrorInfo() {
    }

    /**
     * @param errorCode
     */
    public ApiErrorInfo(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @param errorDesc
     */
    public ApiErrorInfo(String errorDesc) {
        super();
        this.errorDesc = errorDesc;
    }

    /**
     * @param errorCode
     * @param errorDesc
     */
    public ApiErrorInfo(int errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    /**
     * @param requestError
     */
    public ApiErrorInfo(RequestError requestError) {
        if (requestError != null) {
            ApiErrorInfo info = requestError.getErrorInfo();
            if (info != null) {
                this.errorCode = info.errorCode;
                this.errorDesc = info.errorDesc;
                this.obj = info.obj;
            }
        }
    }

    /**
     * @return the errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the obj
     */
    public Object getObj() {
        return obj;
    }

    /**
     * @param obj the obj to set
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }

    /**
     * @return the errorDesc
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * @param errorDesc the errorDesc to set
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ErrorInfo [errorCode=" + errorCode + ", errorDesc=" + errorDesc
                + ", obj=" + obj + "]";
    }

}
