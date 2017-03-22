package SymbolTable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SymTable
{
    // <HashedName, Type, Scope>
    static HashMap<String, Entry> hashMap = new HashMap<String, Entry>();
    
    public static void insert(String name, int type, int scope)
    {
        Entry entry = new Entry(type, scope, null);
        hashMap.put(name, entry);
    }    
    
    public static Entry lookup(String name)
    {
        return hashMap.get(name);
    }
    
    
    public static void removeScope(int scope)
    {

        Iterator entries = hashMap.entrySet().iterator();
        while (entries.hasNext()) 
        {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String)entry.getKey();
            Entry e = (Entry)entry.getValue();
            Integer type = e.type;
            Integer entryScope = e.scope;
            if(entryScope == scope)
                entries.remove();
        }
    }
    
    
    
    
    
    
    
    
    
    public static void print()
    {
        System.out.println("*********************");
        Iterator entries = hashMap.entrySet().iterator();
        while (entries.hasNext()) 
        {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String)entry.getKey();
            Entry e = (Entry)entry.getValue();
            Integer type = e.type;
            Integer scope = e.scope;
            if (type == 0) {
				TableGenerator.indent(TableGenerator.SPACES*scope);
				System.out.println("[" + key + ", INT]");
            } else if (type == 1) {
				TableGenerator.indent(TableGenerator.SPACES*scope);
				System.out.println("[" + key + ", VOID]");
			}
        }
        System.out.println("*********************");
    
    }
    

/*    
    private static int hash(String name)
    {
        int MAX = 19937;
        int SHIFT = 4;
        
        int temp = 0;
       
        for(char c: name.toCharArray())
        {
            temp = ((temp << SHIFT) + c) % MAX;
        }
        return temp;
    
    }
 */   
    
    
    
    

    
}
