package br.com.guaxinim.setup;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

public class Setup {

    @Resource(lookup="java:jboss/datasources/jooqDS")
    DataSource dataSource;

    @Produces @JooqContextProducer
    public DSLContext getJooqContext() {
        return DSL.using(dataSource, SQLDialect.POSTGRES_9_3);
    }

}
