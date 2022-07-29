package com.instagram.global.error;


import com.instagram.global.error.BasicResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BasicException extends Exception{
    private BasicResponseStatus status;
}
