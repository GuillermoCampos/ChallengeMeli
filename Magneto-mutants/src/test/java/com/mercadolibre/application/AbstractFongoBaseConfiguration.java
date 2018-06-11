package com.mercadolibre.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
/**
 * @author Guille Campos
 */
public abstract class AbstractFongoBaseConfiguration extends AbstractMongoConfiguration{


    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return env.getRequiredProperty("spring.data.mongodb.database");
    }
    
	@Override
	public MongoClient mongoClient() {
		 return new Fongo(getDatabaseName()).getMongo();
	}
}