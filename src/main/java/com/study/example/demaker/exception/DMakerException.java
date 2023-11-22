package com.study.example.demaker.exception;

import lombok.Getter;

@Getter
public class DMakerException extends RuntimeException{
    private DmakerErrorCode demakerErrorCode;
    private String detailMessage;

    public DMakerException(DmakerErrorCode errorCode){
        super(errorCode.getMessage());
        this.demakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public DMakerException(DmakerErrorCode errorCode, String detailMessage){
        super(detailMessage);
        this.demakerErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
