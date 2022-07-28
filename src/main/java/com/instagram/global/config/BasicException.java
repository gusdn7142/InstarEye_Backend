package com.instagram.global.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BasicException extends Exception{
    private BasicResponseStatus status;
}
