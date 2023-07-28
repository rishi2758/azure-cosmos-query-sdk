package com.azure.cosmos.generator;

import com.azure.cosmos.CosmosQueryCreator;
import com.azure.cosmos.QueryParser;
import com.azure.cosmos.generator.impl.EmployeeFilter;
import com.azure.cosmos.generator.impl.EmployeeQueryCreator;
import com.azure.cosmos.generator.impl.EmployeeQueryParser;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.Assert;
import org.junit.Test;

public class CosmosQueryGeneratorTests {

	@Test
	public void testEmployeeQueryIsCorrectlyGenerated() {

		QueryParser<EmployeeFilter.Filter> parser = new EmployeeQueryParser(new EmployeeFilter());
		CosmosQueryCreator<EmployeeFilter.Filter> queryCreator = new EmployeeQueryCreator(parser);

		MultiValuedMap<String,String> filters = new ArrayListValuedHashMap<>();
		filters.put("name","eq(rishi)");

		String query = queryCreator.toCosmosQuery(filters);

		System.out.println(query);
		Assert.assertNotNull(query);
	}

}
