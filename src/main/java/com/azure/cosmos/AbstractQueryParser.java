package com.azure.cosmos;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractQueryParser<T extends BaseFilter> implements QueryParser<T> {
	private final Logger logger = Logger.getLogger(AbstractQueryParser.class.getName());
	protected CosmosFilter<T> parseOperatorAndValue(T filter, String query) {
		if (Objects.nonNull(query)) {
			int openIndex = query.indexOf('(');
			int closeIndex = query.lastIndexOf(')');
			try {
				Predicate.Operator operator = Predicate.Operator.valueOf(query.substring(0, openIndex).toUpperCase());
				String[] values = query.substring(openIndex + 1, closeIndex).split(",");
				CosmosFilter<T> cosmosFilter = new CosmosFilter<>();
				cosmosFilter.setFilter(filter);
				cosmosFilter.setOperator(operator);
				cosmosFilter.setValues(values);
				return cosmosFilter;
			} catch (Exception e) {
				logger.log(Level.SEVERE,"invalid/unsupported operator {}", query);
			}
		}
		return null;
	}
}
