package com.azure.cosmos.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.StringJoiner;


public final class CosmosQuery {

    private StringJoiner cosmosQuery;

    private CosmosQuery(String baseQuery) {
        this.cosmosQuery = new StringJoiner(" ");
        this.cosmosQuery.add(baseQuery);
    }

    public static CosmosQueryBuilder builder(BaseQuery baseQuery) {
        return new CosmosQueryBuilder(baseQuery);
    }

    private void appendToQuery(String subQuery) {
        cosmosQuery.add(subQuery);
    }

    public String toCosmosQuery() {
        return this.cosmosQuery.toString();
    }

    public static final class CosmosQueryBuilder {

        private CosmosQuery cosmosQuery;

        private CosmosQueryBuilder(BaseQuery baseQuery) {
            this.cosmosQuery = new CosmosQuery(baseQuery.getBaseQuery());
        }


        public CosmosQueryBuilder withCriteria(Criteria criteria) {
            this.cosmosQuery.appendToQuery(criteria.getCriteria());
            return this;
        }

        public CosmosQueryBuilder withPredicate(@NonNull Predicate... predicates) {
            for (Predicate predicate : predicates) {
                this.cosmosQuery.appendToQuery(predicate.toQuery());
            }
            return this;
        }

        public CosmosQuery build() {
            return this.cosmosQuery;
        }
    }

    @Getter
    @AllArgsConstructor
    public static enum BaseQuery {
        SELECT_QUERY("SELECT * FROM c");

        private String baseQuery;
    }

    @Getter
    @AllArgsConstructor
    public static enum Criteria {
        WHERE("WHERE"), ORDER_BY("ORDER BY");

        private String criteria;
    }
}
