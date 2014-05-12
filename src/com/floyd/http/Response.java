/**
 *
 */
package com.floyd.http;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import android.util.Log;

/**
 * api调用返回结果<br>
 * 调用是否成功可以通过{@link Response#isSuccess()}方法来判断<br>
 * 如果调用失败，则可以通过{@link #getRequestError()}获取错误的异常信息和错误描述(如果有的话)
 *
 * @author floydchenxf
 */
public class Response {
	/**
	 * 获取的内容，如果是图片是字节流。如果是文本是String
	 */
    private byte[] content;
    private RequestError requestError;
    private Map<String, List<String>> headerFields;
    private int responseCode;
    private String responseMessage;
    private String chatset = "UTF-8";

    public Response() {

    }
    
    public void setChatset(String cs) {
    	this.chatset = cs;
    }

    public Response(byte[] originalContent) {
        this.content = originalContent;
    }

    public Response(RequestError requestError) {
        this.requestError = requestError;
    }

    public static Response fromError(Exception e, ApiErrorInfo errorInfo) {
        RequestError requestErr = new RequestError(e, errorInfo);
        return new Response(requestErr);
    }

    /**
     * 如果{@link #getRequestError()}返回为null，则表示请求成功
     *
     * @return
     */
    public boolean isSuccess() {
        return requestError == null;
    }

    public RequestError getRequestError() {
        return requestError;
    }

    public void setRequestError(RequestError requestError) {
        this.requestError = requestError;
    }

    public byte[] getContent() {
        return content;
    }
    
    
    public String getContentString() {
    	String result = null;
    	if (this.content == null || this.content.length <= 0) {
    		return result;
    	}
    	try {
			result = new String(this.content, chatset);
		} catch (UnsupportedEncodingException e) {
			Log.e(Response.class.getSimpleName(), e.toString());
		}
    	
    	return result;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * @return the headerFields
     */
    public Map<String, List<String>> getHeaderFields() {
        return headerFields;
    }

    /**
     * @param headerFields the headerFields to set
     */
    public void setHeaderFields(Map<String, List<String>> headerFields) {
        this.headerFields = headerFields;
    }

    /**
     * @return the responseCode
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the responseMessage
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * @param responseMessage the responseMessage to set
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Response [content=" + content
                + ", requestError=" + requestError + ", responseCode=" + responseCode
                + ", responseMessage=" + responseMessage + ", \n headerFields="
                + headerFields + "]";
    }

}
