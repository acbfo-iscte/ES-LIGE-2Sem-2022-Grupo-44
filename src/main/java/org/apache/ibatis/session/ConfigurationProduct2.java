package org.apache.ibatis.session;


import java.util.Map;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration.StrictMap;
import java.util.Collection;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import java.util.LinkedList;
import org.apache.ibatis.builder.annotation.MethodResolver;

public class ConfigurationProduct2 {
	private final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>(
			"Mapped Statements collection")
			.conflictMessageProducer((savedValue, targetValue) -> ". please check " + savedValue.getResource() + " and "
					+ targetValue.getResource());
	private final Collection<XMLStatementBuilder> incompleteStatements = new LinkedList<>();
	private final Collection<MethodResolver> incompleteMethods = new LinkedList<>();

	public Collection<XMLStatementBuilder> getIncompleteStatements() {
		return incompleteStatements;
	}

	public Collection<MethodResolver> getIncompleteMethods() {
		return incompleteMethods;
	}

	public void addMappedStatement(MappedStatement ms) {
		mappedStatements.put(ms.getId(), ms);
	}

	public Collection<String> getMappedStatementNames(Configuration configuration) {
		buildAllStatements(configuration);
		return mappedStatements.keySet();
	}

	public Collection<MappedStatement> getMappedStatements(Configuration configuration) {
		buildAllStatements(configuration);
		return mappedStatements.values();
	}

	public MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements,
			Configuration configuration) {
		if (validateIncompleteStatements) {
			buildAllStatements(configuration);
		}
		return mappedStatements.get(id);
	}

	public boolean hasStatement(String statementName, boolean validateIncompleteStatements,
			Configuration configuration) {
		if (validateIncompleteStatements) {
			buildAllStatements(configuration);
		}
		return mappedStatements.containsKey(statementName);
	}

	public void buildAllStatements(Configuration configuration) {
		configuration.buildAllStatements_extracted();
		if (!incompleteStatements.isEmpty()) {
			synchronized (incompleteStatements) {
				incompleteStatements.removeIf(x -> {
					x.parseStatementNode();
					return true;
				});
			}
		}
		if (!incompleteMethods.isEmpty()) {
			synchronized (incompleteMethods) {
				incompleteMethods.removeIf(x -> {
					x.resolve();
					return true;
				});
			}
		}
	}
}