package com.azure.cosmos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.StringJoiner;

public final class Predicate {
	private final StringJoiner predicateJoiner;
	private Predicate() {
		this.predicateJoiner = new StringJoiner(" ");
	}
	public static PredicateBuilder builder() {
		return new PredicateBuilder();
	}
	public static Predicate and(Predicate... predicates) {
		StringJoiner andJoiner = new StringJoiner(" AND ");
		for (Predicate value : predicates) {
			andJoiner.add(value.toQuery());
		}
		Predicate predicate = new Predicate();
		predicate.appendToPredicate(andJoiner.toString());
		return predicate;
	}
	public static Predicate or(Predicate... predicates) {
		StringJoiner orJoiner = new StringJoiner(" OR ");
		for (Predicate value : predicates) {
			orJoiner.add(value.toQuery());
		}
		Predicate predicate = new Predicate();
		predicate.appendToPredicate(orJoiner.toString());
		return predicate;
	}
	private void appendToPredicate(CosmosFilter<? extends BaseFilter> cosmosFilter) {
		String key = getEntityField(cosmosFilter.getFilter().getFilterKey());
		String value = getValue(cosmosFilter.getValues());
		Operator operator = cosmosFilter.getOperator();
		predicateJoiner.add(MessageFormat.format(operator.getPredicateFormat(), key, value));
	}
	private void appendToPredicate(String subPredicate) {
		predicateJoiner.add(subPredicate);
	}
	public String toQuery() {
		return this.predicateJoiner.toString();
	}
	public Predicate and(Predicate predicate) {
		this.predicateJoiner.add("AND").add(predicate.toQuery());
		return this;
	}
	public Predicate or(Predicate predicate) {
		this.predicateJoiner.add("OR").add(predicate.toQuery());
		return this;
	}
	private String getEntityField(String field) {
		return "c." + field;
	}
	private String getValue(String[] values) {
		final String valueFormat = "''{0}''";
		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			sb.append(MessageFormat.format(valueFormat, value)).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	@Getter
	@AllArgsConstructor
	public static enum Operator {
		IN("{0} IN ({1})"), OFFSET("offset {1}"), LIMIT("limit {1}"), EQ("{0}={1}"), ASC("{0} ASC"), DESC("{0} DESC"),
		GTE("{0} >= {1}"), LTE("{0} <= {1}");

		private final String predicateFormat;

		public static String createOperatorBasedQueryFilter(Operator operator, String value) {
			return MessageFormat.format(operator.name().toLowerCase() + "({0})", value);
		}
	}
	public static final class PredicateBuilder {
		private final Predicate predicate;
		private PredicateBuilder() {
			this.predicate = new Predicate();
		}
		public PredicateBuilder withCosmosFilter(CosmosFilter<? extends BaseFilter> cosmosFilter) {
			this.predicate.appendToPredicate(cosmosFilter);
			return this;
		}
		public PredicateBuilder withOperator(Operator operator, String field, String value) {
			String key = "c." + field;
			predicate.appendToPredicate(MessageFormat.format(operator.getPredicateFormat(), key, value));
			return this;
		}
		public Predicate toPredicate() {
			return this.predicate;
		}
	}
}
