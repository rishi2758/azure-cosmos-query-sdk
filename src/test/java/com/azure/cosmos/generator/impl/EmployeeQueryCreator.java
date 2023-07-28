package com.azure.cosmos.generator.impl;

import com.azure.cosmos.*;
import com.azure.cosmos.CosmosQuery.*;
import com.azure.cosmos.Predicate.Operator;
import org.apache.commons.collections4.MultiValuedMap;

import java.util.List;
import java.util.stream.Collectors;

import static com.azure.cosmos.CosmosQuery.builder;

public class EmployeeQueryCreator extends CosmosQueryCreator<EmployeeFilter.Filter>{
	private static final int DEFAULT_PAGE_SIZE = 10;

	public EmployeeQueryCreator(QueryParser<EmployeeFilter.Filter> queryParser) {
		super(queryParser);
	}

	@Override
	public String toCosmosQuery(MultiValuedMap<String, String> filters) {
		List<CosmosFilter<EmployeeFilter.Filter>> cosmosFilters = this.getQueryParser().parse(filters);
		List<Predicate> predicates = cosmosFilters.stream()
				.map(filter -> Predicate.builder().withCosmosFilter(filter).toPredicate())
				.collect(Collectors.toList());
		CosmosQueryBuilder builder = builder(BaseQuery.SELECT_QUERY);
		if (!predicates.isEmpty()) {
			builder.withCriteria(Criteria.WHERE);
			builder.withPredicate(
					Predicate.and(predicates.toArray(new Predicate[0])));
		}
		return builder
				.withCriteria(Criteria.ORDER_BY)
				.withPredicate(Predicate.builder()
						.withOperator(Operator.DESC, "lastUpdatedAt", "")
						.toPredicate())
				.build().toCosmosQuery();
	}

	@Override
	public String toCosmosPagedQuery(MultiValuedMap<String, String> filters, int page) {
		List<CosmosFilter<EmployeeFilter.Filter>> cosmosFilters = this.getQueryParser().parse(filters);
		List<Predicate> predicates = cosmosFilters.stream()
				.map(filter -> Predicate.builder().withCosmosFilter(filter).toPredicate())
				.collect(Collectors.toList());
		CosmosQueryBuilder builder = CosmosQuery.builder(BaseQuery.SELECT_QUERY);
		if (!predicates.isEmpty()) {
			builder.withCriteria(Criteria.WHERE);
			builder.withPredicate(
					Predicate.and(predicates.toArray(new Predicate[0])));
		}
		return builder.withCriteria(Criteria.ORDER_BY)
				.withPredicate(Predicate.builder().withOperator(Operator.DESC, "lastUpdatedAt", "")
						.toPredicate())
				.withPredicate(Predicate.builder()
						.withOperator(Operator.OFFSET, "offset",
								String.valueOf(page * DEFAULT_PAGE_SIZE))
						.withOperator(Operator.LIMIT, "limit",
								String.valueOf(DEFAULT_PAGE_SIZE))
						.toPredicate())
				.build().toCosmosQuery();
	}
}
