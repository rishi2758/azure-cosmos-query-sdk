package com.azure.cosmos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.StringJoiner;

public final class Predicate {

	private StringJoiner predicateJoiner;

	private Predicate() {
		this.predicateJoiner = new StringJoiner(" ");
	}

	public static PredicateBuilder builder() {
		return new PredicateBuilder();
	}

	private void appendToPredicate(CosmosFilter<BaseFilter> cosmosFilter) {
		String key = getEntityField(cosmosFilter.getFilter().getFilterKey());
		String value = getValue(cosmosFilter.getValues());
		Operator operator = cosmosFilter.getOperator();
		predicateJoiner.add(MessageFormat.format(operator.getPredicateFormat(), key, value));
	}

	private void appendToPredicate(String subPredicate) {
		predicateJoiner.add(subPredicate);
	}

	public String toQuery() {
		return new String(this.predicateJoiner.toString());
	}

	@Getter
	@AllArgsConstructor
	public static enum Operator {
		IN("{0} IN ({1})"), OFFSET("offset {1}"), LIMIT("limit {1}"), EQ("{0}={1}"), ASC("{0} ASC"), DESC("{0} DESC"),
		GTE("{0} >= {1}"), LTE("{0} <= {1}");

		private String predicateFormat;

		public static String createOperatorBasedQueryFilter(Operator operator, String value) {
			StringBuilder sb = new StringBuilder(operator.name().toLowerCase()).append("({0})");
			return MessageFormat.format(sb.toString(), value);
		}
	}

	public static final class PredicateBuilder {

		private Predicate predicate;

		private PredicateBuilder() {
			this.predicate = new Predicate();
		}

		public PredicateBuilder withCosmosFilter(CosmosFilter<BaseFilter> cosmosFilter) {
			this.predicate.appendToPredicate(cosmosFilter);
			return this;
		}

		public PredicateBuilder withOperator(Operator operator, String field, String value) {
			String key = new StringBuilder("c.").append(field).toString();
			predicate.appendToPredicate(MessageFormat.format(operator.getPredicateFormat(), key, value));
			return this;
		}

		public Predicate toPredicate() {
			return this.predicate;
		}
	}

	public Predicate and(Predicate predicate) {
		this.predicateJoiner.add("AND").add(predicate.toQuery());
		return this;
	}

	public Predicate or(Predicate predicate) {
		this.predicateJoiner.add("OR").add(predicate.toQuery());
		return this;
	}

	public static Predicate and(Predicate... predicates) {
		StringJoiner andJoiner = new StringJoiner(" AND ");
		for (int i = 0; i < predicates.length; i++) {
			andJoiner.add(predicates[i].toQuery());
		}
		Predicate predicate = new Predicate();
		predicate.appendToPredicate(andJoiner.toString());
		return predicate;
	}

	public static Predicate or(Predicate... predicates) {
		StringJoiner orJoiner = new StringJoiner(" OR ");
		for (int i = 0; i < predicates.length; i++) {
			orJoiner.add(predicates[i].toQuery());
		}
		Predicate predicate = new Predicate();
		predicate.appendToPredicate(orJoiner.toString());
		return predicate;
	}

	private String getEntityField(String field) {
		return new StringBuilder("c.").append(field).toString();
	}

	private String getValue(String[] values) {
		final String valueFormat = "''{0}''";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			sb.append(MessageFormat.format(valueFormat, values[i])).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
