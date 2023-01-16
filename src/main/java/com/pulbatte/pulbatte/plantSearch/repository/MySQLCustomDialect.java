package com.pulbatte.pulbatte.plantSearch.repository;

import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySQLCustomDialect extends MySQL8Dialect {
    public MySQLCustomDialect() {
        super();

        registerFunction(
                "match",
                new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "match(?1) against (?2 in boolean mode)")
        );
    }
}
