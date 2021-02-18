package me.giskard.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class MindDevCounter extends MindDevUtils.DevMonitor implements Iterable<Map.Entry<Object, Long>> {
    Map<Object, Long> counts;
    
    public MindDevCounter(String name, boolean sorted, long interval) {
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
            sb = MindUtils.sbAppend(sb, ", ", false, e.getKey());
            MindUtils.sbAppend(sb, "", false, ": ", e.getValue());
        }
        
        return MindUtils.toString(sb);
    }
    
    @Override
    public Iterator<Entry<Object, Long>> iterator() {
        return counts.entrySet().iterator();
    }
}
