package com.luisovando.app.config;

import java.util.Set;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.luisovando.app.domain.models.Pet;

@Component
@ConditionalOnProperty(name = "app.dynamodb.init", havingValue = "true")
public class DynamoInitializer implements CommandLineRunner {

	private DynamoDBMapper dynamoDBMapper;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	private Set<Class<? extends Object>> dynamoMappingModels;

	public DynamoInitializer() {
		Reflections reflections = new Reflections("com.luisovando.app.domain.models");
		dynamoMappingModels = reflections.getTypesAnnotatedWith(DynamoDBTable.class);
	}

	private void createDynamoDBTables() throws ResourceInUseException {
		dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

		CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Pet.class);
		tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		amazonDynamoDB.createTable(tableRequest);
	}

	@Override
	public void run(String... args) {
		try {
			createDynamoDBTables();
		} catch (ResourceInUseException exception) {
			logger.error("The table was created");
		}
		
	}

}
