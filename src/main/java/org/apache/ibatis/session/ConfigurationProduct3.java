package org.apache.ibatis.session;


import java.util.Collection;
import org.apache.ibatis.builder.CacheRefResolver;
import java.util.LinkedList;
import org.apache.ibatis.builder.ResultMapResolver;
import org.apache.ibatis.builder.IncompleteElementException;
import java.util.Iterator;

public class ConfigurationProduct3 {
	private final Collection<CacheRefResolver> incompleteCacheRefs = new LinkedList<>();
	private final Collection<ResultMapResolver> incompleteResultMaps = new LinkedList<>();

	public Collection<CacheRefResolver> getIncompleteCacheRefs() {
		return incompleteCacheRefs;
	}

	public Collection<ResultMapResolver> getIncompleteResultMaps() {
		return incompleteResultMaps;
	}

	public void buildAllStatements_extracted() {
		parsePendingResultMaps();
		if (!incompleteCacheRefs.isEmpty()) {
			synchronized (incompleteCacheRefs) {
				incompleteCacheRefs.removeIf(x -> x.resolveCacheRef() != null);
			}
		}
	}

	public void parsePendingResultMaps() {
		if (incompleteResultMaps.isEmpty()) {
			return;
		}
		synchronized (incompleteResultMaps) {
			boolean resolved;
			IncompleteElementException ex = null;
			ex = parsePendingResultMaps_extracted(ex);
			if (!incompleteResultMaps.isEmpty() && ex != null) {
				throw ex;
			}
		}
	}

	public IncompleteElementException parsePendingResultMaps_extracted(IncompleteElementException ex) {
		boolean resolved;
		do {
			resolved = false;
			Iterator<ResultMapResolver> iterator = incompleteResultMaps.iterator();
			while (iterator.hasNext()) {
				try {
					iterator.next().resolve();
					iterator.remove();
					resolved = true;
				} catch (IncompleteElementException e) {
					ex = e;
				}
			}
		} while (resolved);
		return ex;
	}
}