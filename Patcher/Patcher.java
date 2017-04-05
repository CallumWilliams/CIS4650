package Patcher;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.nio.charset.StandardCharsets;

import java.io.FileWriter;


public class Patcher
{
    public static void Run(String filename)
    {
        int[][] jumps = new int[99][2]; //[][0] == startPC [][1] == endPC
        String[] comments = new String[99];
        for(int i = 0; i < 99; i++)
        {   
            jumps[i][0] = -1;
            jumps[i][1] = -1;
        }
    
        
        List<String> lines = null;
        try
        {
            lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        } 
        catch(Exception ex)
        {}
        
        
        for(String s : lines)
        {
            
            if(isJumpLine(s))
            {   
                String[] split = s.split("\\|");
                int jumpNum = Integer.parseInt(split[1]);
                int pc = Integer.parseInt(split[2]);
                jumps[jumpNum][0] = pc;
                
                if(split.length == 4)
                {
                    comments[jumpNum] = split[3];
                }
                else
                {
                    comments[jumpNum] = "";
                }

            } 
            else if(s.contains("LAND"))
            {   
                String[] split = s.split("\\|");
                int jumpNum = Integer.parseInt(split[1]);
                int pc = Integer.parseInt(split[2]);
                jumps[jumpNum][1] = pc;
            }
        }
        
        
        for(int i = 0; i < lines.size(); i++)
        {
            String s = lines.get(i);
            if(isJumpLine(s))
            { 
                String[] split = s.split("\\|");
                String jumpCode = split[0];
                int jumpNum = Integer.parseInt(split[1]);
                int pc = Integer.parseInt(split[2]);
                if(jumps[jumpNum][0] != -1 && jumps[jumpNum][1] != -1)
                {
                    int difference = jumps[jumpNum][1] - jumps[jumpNum][0];
                    if(jumpCode.equals("JUMP"))
                        lines.set(i, pc + ":  LDA 7, " + (difference - 1) + "(7)      *" + comments[jumpNum]);
                    else
                        lines.set(i, pc + ":  " + jumpCode + " 7, " + (difference -1) + "(7)      *" + comments[jumpNum]); 
                }   
            }
            if(s.contains("LAND"))
            { 
                String[] split = s.split("\\|");
                int jumpNum = Integer.parseInt(split[1]);
                int pc = Integer.parseInt(split[2]);
                if(jumps[jumpNum][0] != -1 && jumps[jumpNum][1] != -1)
                {
                    lines.set(i, "");
                }   
            }
            
            
        }     
            
        try
        {
        
        FileWriter writer = new FileWriter("PATCHED.asm"); 
        for(String str: lines)
            writer.write(str + "\n");
        writer.close();   
        }
        catch(Exception ex)
        {}
        
        
        
        
    }
    
    private static Boolean isJumpLine(String s)
    {   

        return s.contains("JUMP|") || s.contains("JNE|") || s.contains("JEQ|") || 
               s.contains("JGE|")  || s.contains("JGT|") || s.contains("JLE|") ||
               s.contains("JLT|");
    
    
    }


    

}
