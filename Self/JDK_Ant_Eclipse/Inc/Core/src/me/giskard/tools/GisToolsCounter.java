package me.giskard.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import me.giskard.GiskardUtils;

public class GisToolsCounter extends GisToolsUtils.DevMonitor implements Iterable<Map.Entry<Object, Long>> {
    Map<Object, Long> counts;
    
    public GisToolsCounter(String name, boolean sorted, long interval) {
    	super(name, interval);
        counts = sorted ? new TreeMap<>() : new HashMap<>();
    }
    
    public void reset() {
       if ( null != counts) {
      	 counts.clear();
       }
    }
    
    public void add(Object ob) {
        Long l = counts.get(ob);
        counts.put(ob, (null == l) ? 1 : l+1);
        step();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        
        for ( Map.Entry<Object, Long> e : this ) {
            sb = GiskardUtils.sbAppend(sb, ", ", false, e.getKey());
            GiskardUtils.sbAppend(sb, "", false, ": ", e.getValue());
        }
        
        return GiskardUtils.toString(sb);
    }
    
    @Override
    public Iterator<Entry<Object, Long>> iterator() {
        return counts.entrySet().iterator();
    }
}
