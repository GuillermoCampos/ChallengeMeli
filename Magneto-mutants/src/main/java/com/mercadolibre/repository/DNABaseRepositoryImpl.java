package com.mercadolibre.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import com.mercadolibre.exception.ServiceException;
import com.mercadolibre.model.Adn;
import com.mercadolibre.model.StatsResult;

/**
 * @author Guille Campos
 */
@Repository
public class DNABaseRepositoryImpl implements DNABaseRepository {

	private final MongoTemplate mongoTemplate;
	
   
	@Autowired
    public DNABaseRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
	@Override
	public List<StatsResult> getStats() throws ServiceException {
		try {
			Aggregation agg = Aggregation.newAggregation(				
	                Aggregation.group("mutant").count().as("total"),
	                Aggregation.project("_id").andInclude("total")	        
				   );		
			AggregationResults<StatsResult> groupResults= mongoTemplate.aggregate(agg, Adn.class,  StatsResult.class);
			return groupResults.getMappedResults();
		} catch (Exception e) {
			throw new ServiceException("Error to get table dna in database");
		}
	}

	@Override
	 public void save(Adn adn) {
		mongoTemplate.save(adn);
	    }

	@Override
	public Adn findByDna(String[] dna) throws ServiceException {
		try {
			return mongoTemplate.findOne(query(where("dna").is(dna)), Adn.class);
		} catch (Exception e) {
			throw new ServiceException("Error to get table dna in database");
		}
	}

}
