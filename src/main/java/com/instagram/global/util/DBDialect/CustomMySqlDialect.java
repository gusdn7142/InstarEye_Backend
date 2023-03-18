package com.instagram.global.util.DBDialect;

import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;

public class CustomMySqlDialect extends MySQL8Dialect {

    public CustomMySqlDialect(){
        super();
        this.registerFunction("group_concat", new StandardSQLFunction("group_concat", new StringType()));
    }
}
