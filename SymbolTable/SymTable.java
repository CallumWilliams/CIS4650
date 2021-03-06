package SymbolTable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

public class SymTable
{
    static HashMap<String, Entry> hashMap = new HashMap<String, Entry>();
    
    public static Boolean insert(String name, int type, int dim, int scope, int offset, int pc)
    {
        Entry entry = new Entry(type, scope, dim, null, offset, pc);
        
        Entry existingEntry = lookup(name);
        
        if(existingEntry == null)
        {
            hashMap.put(name, entry);
        }
        else if(existingEntry.scope == scope)
        {
            return false;
        }
        else
        {
            entry.next = existingEntry;
            hashMap.put(name, entry);

        }
        return true;

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
    
    public static int getOffset(String name)
    {
        Entry e = lookup(name);
        if(e != null)
            return e.offset;
        else
            return -99; //Some random error code. There's probably a better way
    }
    
    
    
    
    
    public static void removeScope(int scope)
    {
        class Pair
        {
            String name;
            Entry e;
        }
        List<Pair> toAdd = new ArrayList<Pair>();
        
        
        Iterator entries = hashMap.entrySet().iterator();
        while (entries.hasNext()) 
        {
            Map.Entry mapEntry = (Map.Entry) entries.next();
            String key = (String)mapEntry.getKey();
            Entry e = (Entry)mapEntry.getValue();
            
            if(e.scope == scope)
            {
                Pair p = new Pair();
                p.name = key;
                p.e = e.next;
                toAdd.add(p);
                entries.remove();
            }
            else
            {
                Entry prev = e;
                e = e.next;
                while( e != null)
                {       
                    if(e.scope == scope)
                    {
                        prev.next = e.next;
                        
                    }  
                    prev = e;
                    e = e.next; 
                }
            }
        }
        
        for( Pair p : toAdd)
        {
            if(p.e != null)
                hashMap.put(p.name, p.e);
        }
        
        
    }
    

    public static void print()
    {
        class Pair
        {
            String text;
            int scope;
        }
        List<Pair> toPrint = new ArrayList<Pair>();
        
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
                Integer dimensions = e.dim;
                Integer scope = e.scope;
                Integer offset = e.offset; //Debugging
                Pair p = new Pair();
                p.scope = scope;
                if (type == 0) {
                    p.text = key + ", INT";
                    if (dimensions > 1) p.text += "[" + dimensions + "]";
                } else if (type == 1) {
                    p.text = key + ", VOID";
                    if (dimensions > 1) p.text += "[" + dimensions + "]";
				}
				
	/*			if(scope == 0)
				    p.text += "|gp offset:" + offset; //Debugging
				else
				    p.text += "|fp offset:" + offset; //Debugging    */
				toPrint.add(p);
                e = e.next;
            }

        }
        
        
        for(int i = 0; i < 50; i++)
        {
            for(Pair p : toPrint)
            {
                if(p.scope == i)
                {
                    TableGenerator.indent(TableGenerator.SPACES*i);
                    System.out.println(p.text);
                }
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
