package me.giskard.tools;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class GisToolsTranslator<Left, Right> {
    HashMap<Left, Right> l2r = new HashMap<>();
    HashMap<Right, Left> r2l = new HashMap<>();
    
    public GisToolsTranslator() {
    }

    public GisToolsTranslator(Left[] lefts, Right[] rights) {
        int l = lefts.length;
        
        if ( l != rights.length ) {
            throw new RuntimeException("Different lengths!");
        }
        for ( int i = 0; i < l; ++i ) {
            add(lefts[i], rights[i]);
        }
    }

    public void add(Left l, Right r) {
        l2r.put(l, r);
        r2l.put(r, l);
    }
    
    public <RetType> RetType getLeft(Right r) {
        return (RetType) r2l.get(r);
    }
    
		public <RetType> RetType  getRight(Left l) {
        return (RetType) l2r.get(l);
    } 
    
    public boolean contains(Object o) {
        return r2l.containsKey(o) || l2r.containsKey(o);
    }
    
    public boolean containsRight(Right r) {
        return r2l.containsKey(r);
    }
    
    public boolean containsLeft(Left l) {
        return l2r.containsKey(l);
    }

	public void clear() {
		l2r.clear();
		r2l.clear();
	}

	public Iterable<Left> getLeftAll() {
		return l2r.keySet();
	}

	public Iterable<Right> getRightAll() {
		return r2l.keySet();
	}

	public boolean isEmpty() {
		return l2r.isEmpty();
	}
}