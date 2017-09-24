package com.example.dariusz.wyszukiwarkalekow.application.base;



public interface UseCase<ARGUMENT,RESULT> {

    RESULT execute( ARGUMENT argument ) throws Exception;

}
