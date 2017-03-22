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
        
        Entry existingEntry = lookup(name);
        
        if(existingEntry == null)
        {
            hashMap.put(name, entry);
        }
        else
        {
            entry.next = existingEntry;
            hashMap.put(name, entry);
        }

    }    
    
    public static Entry lookup(String name)
    {
        int maxScope = -1;
        Entry maxEntry = null;
        
        Entry e = hashMap.get(name);
        if(e == null) return null;
            
        while(e != null)
        {
            if(e.scope > maxScope)
            {
                maxScope = e.scope;
                maxEntry = e;
            }
            e = e.next;
        }
        return maxEntry;
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
            while(e != null)
            {
                Integer type = e.type;
                Integer scope = e.scope;
                if (type == 0) System.out.print("[" + key + ", INT, " + scope + "] =>");
                else if (type == 1) System.out.print("[" + key + ", VOID, " + scope + "] =>");
                e = e.next;
            }
            System.out.println("");
 

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
