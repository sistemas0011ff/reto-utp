package com.utp.api.application.response.dto;

public class ApiResponseDTO<T> {

    private String message;
    private int status;
    private T data;  

    public ApiResponseDTO(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
 
    public static <T> ApiResponseDTO<T> success(T data, String message) {
        return new ApiResponseDTO<>(message, 200, data);  
    }
 
    public static <T> ApiResponseDTO<T> error(String message, int status) {
        return new ApiResponseDTO<>(message, status, null);
    }
 
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
