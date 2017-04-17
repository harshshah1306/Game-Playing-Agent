import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class gamePlayingAgent {
    public static Scanner x;
    public static String algorithm;
    public static String s;
    public static int boardsize;
    public static int depth;
    public static int statedepth;
    public static char player;
    public static char opponent;
    public static char states[][]=new char[40][40];
    public static int cell[][]=new int[40][40];
    public static int row,column;
    public static boolean raid=false;
    public static boolean[][] arrayforraid=new boolean[40][40];
    public static int stake=1;
    public static int count=0,prunec=0, a_r = 0, a_c = 0;
    String type="";
    static int dotcount=0;
    static int loopvariable=0;
    
    public static void main(String[] args) throws IOException
   {
        readfiles();   
   }
    public static void openfile()
    {
        try
        {
            x=new Scanner(new File("1.in"));
        }
        catch(Exception e)
        {
                    System.out.println("could not find file");
        }
    }
    
    public static void readfiles() throws IOException
    {   
         openfile();
         boardsize=Integer.parseInt(x.next());
         algorithm=x.next();
         String tp=x.next();
         player=tp.charAt(0);
         if(player=='X')
         {
             opponent='O';
         }
         else
         {
             opponent='X';
         }
         depth=Integer.parseInt(x.next());
         //System.out.printf("%s %s %s %s \n",boardsize,algorithm,player,depth);
         for (int i=1;i<=boardsize;i++)
         {  
             for (int j=1;j<=boardsize;j++)
             {
                 cell[i][j]= Integer.parseInt(x.next());
             }
         }
         for (int i=0;i<=boardsize+1;i++)
         {  
             if(i==0 || i==boardsize+1)
             {
                for (int j=0;j<=boardsize+1;j++)
                {
                    states[i][j]='.';
                }
             }
             else
             {
                s=x.next();
                for (int j=0;j<=boardsize+1;j++)
                {   
                    if(j==0 || j==boardsize+1)
                    {
                        states[i][j]='.';
                    }
                    else
                    {
                        states[i][j]=s.charAt(j-1);
                    }
                    
                }
             }
             
         }
         for(int i=1;i<=boardsize;i++)
         {
             for (int j=1;j<=boardsize;j++)
             {
                 if(states[i][j]=='.')
                 {
                     dotcount=dotcount+1;
                 }
             }
         }
         if(depth>dotcount)
         {
             depth=dotcount;
         }
         //System.out.println(depth);
         //System.out.println("dotcount is "+ dotcount);
         
         
         
         boolean arrayraids;
         //printcell();
         //printstates(states);
         for (int p=1;p<=boardsize;p++)
        {
            for (int q=1;q<=boardsize;q++)
            {
                if(states[p][q]=='.')
                {
                states[p][q]=player;
                arrayraids=arrayraid(states,p,q);
                arrayforraid[p][q]=arrayraids;
                states[p][q]='.';
                }
            }
        }
          //System.out.println(algorithm.toUpperCase());
 
         //System.out.println("____________________________________________________");
         if (algorithm.toUpperCase().compareTo("ALPHABETA")==0)
         {
             findbestmovealphabeta(states);
         }
         if(algorithm.toUpperCase().compareTo("MINIMAX")==0)
         {
             findbestmove(states);
         }
         printans(states);
        
    }
    
    public static void printans(char[][] states) throws IOException
    {
        
        
        if(stake==1)
         {      
             states[row][column]=player;
             char c=(char)(column+64);
             states[row][column]=player;
             //System.out.println(c+""+row+""+"Stake");
             printstates(states);
         }
         else
         {   //printstates(states);
             states[row][column]=player;
             states=checkraid(states,row,column);
             char c=(char)(column+64);
             //System.out.println(c+""+row+""+"Raid");
             printstates(states);
         }
         
        
    }
    
    public static boolean arrayraid(char b[][],int i,int j)
    {
        for (int p=0;p<=boardsize+1;p++)
        {
            for (int q=0;q<=boardsize+1;q++)
            {
                states[p][q]=b[p][q];
            }
        }
        boolean arrayraids=false;
        char raidopponent;
        if (states[i][j]=='X')
        {
            raidopponent='O';
        }
        else
        {
            raidopponent='X';
        }
        if(states[i-1][j]==states[i][j] || states[i][j-1]==states[i][j] || states[i+1][j]==states[i][j] || states[i][j+1]==states[i][j])
        {
            if (states[i-1][j]==raidopponent || states[i][j-1]==raidopponent || states[i+1][j]==raidopponent || states[i][j+1]==raidopponent)
            {
                arrayraids=true;
            }
        }
        
        
        return arrayraids;
    }
    
    
    public static void findbestmove(char b[][])
    {
        int bestvalue=-9999;
        int movevalue=-9999;
        char[][] states=new char[40][40];
         char[][] new_state=new char[40][40];
        for (int i=0;i<=boardsize+1;i++)
        {
            for (int j=0;j<=boardsize+1;j++)
            {
                states[i][j]=b[i][j];
            }
        }
        row=-1;
        column=-1;
        statedepth=1;
        for (int i=1;i<=boardsize;i++)
        {
            for (int j=1;j<=boardsize;j++)
            {
                if(states[i][j]=='.')
                {
                    for (int p=0;p<=boardsize+1;p++)
                        {
                            for (int q=0;q<=boardsize+1;q++)
                                {
                                     new_state[p][q]=states[p][q];
                                }
                        }
                    new_state[i][j]=player;
                    //new_state=checkraid(new_state,i,j);
                    movevalue=minimax(new_state,statedepth,false);
                    //System.out.println(movevalue+" "+bestvalue);
                    if(movevalue>bestvalue)
                    {    
                        row=i;
                        column=j;
                        bestvalue=movevalue;
                    }
                }
            }
        }
        statedepth=1;
        //System.out.println(bestvalue);
        for (int i=1;i<=boardsize;i++)
        {
            for (int j=1;j<=boardsize;j++)
            {
                
                if(arrayforraid[i][j])
                {
                    if(states[i][j]=='.')
                    {   
                        for (int p=0;p<=boardsize+1;p++)
                            {
                                for (int q=0;q<=boardsize+1;q++)
                                {
                                     new_state[p][q]=states[p][q];
                                }
                            }
                     new_state[i][j]=player;
                     new_state=checkraid(new_state,i,j);
                     //printstates(new_state);
                     movevalue=minimax(new_state,statedepth,false);
                    //System.out.println(movevalue+" "+bestvalue
                    
                    if(movevalue>bestvalue)
                    { //System.out.println("-------------here---------------");
                        stake=0;
                        row=i;
                        column=j;
                        bestvalue=movevalue;
                    }   
                        
                }
            }
       
    }
        }
    }
     
    public static int minimax(char b[][],int statedepth,boolean checkifmaxmin)
    {
        //System.out.println(statedepth+" dkdkfkkfkjfff"+depth);
        char states[][]=new char[40][40];
        char new_state[][]=new char[40][40];
        for (int i=0;i<=boardsize+1;i++)
        {
            for (int j=0;j<=boardsize+1;j++)
            {
                states[i][j]=b[i][j];
            }
        }
        int checktermination=0;
        int terminalscore=score(states);
        //System.out.println("terminalscore "+terminalscore);
        if(statedepth>=depth)
        {   //System.out.println("should get terminated");
            checktermination=1;
        }
        
        if (checktermination==1)
                {
                    //System.out.println("reached termination");
                    return terminalscore;
                }
        
        if (checkifmaxmin)
        {
            int best=-9999;
            for(int i=1;i<=boardsize;i++)
            {
                for (int j=1;j<=boardsize;j++)
                {
                    if(states[i][j]=='.')
                    {
                        for (int p=0;p<=boardsize+1;p++)
                        {
                            for (int q=0;q<=boardsize+1;q++)
                                {
                                     new_state[p][q]=states[p][q];
                                }
                        }
                        new_state[i][j]=player;
                        new_state=checkraid(new_state,i,j);
                        //System.out.println("+++++++++++++");
                        //printstates(states);
                        best=Math.max(best,minimax(new_state,statedepth+1,false));
                        //System.out.println(best+"-----------------");
                    }
                    
                }
            }
            return best;
            
         }
         else
        {
            int best=9999;
            for(int i=1;i<=boardsize;i++)
            {
                for (int j=1;j<=boardsize;j++)
                {
                    if(states[i][j]=='.')
                    {   for (int p=0;p<=boardsize+1;p++)
                        {
                            for (int q=0;q<=boardsize+1;q++)
                                {
                                     new_state[p][q]=states[p][q];
                                }
                        }
                        new_state[i][j]=opponent;
                        new_state=checkraid(new_state,i,j);
                        best=Math.min(best,minimax(new_state,statedepth+1,true));
                        //System.out.println(best+"-----------------");
                    }
                    
                }
            }
        return best;
        }
    }
    public static void findbestmovealphabeta(char b[][])
    {
        int bestvalue=-9999;
        int movevalue=-9999;
        int alpha=-9999;
        int beta=9999;
        char[][] states=new char[40][40];
        char[][] new_state=new char[40][40];
        for (int i=0;i<=boardsize+1;i++)
        {
            for (int j=0;j<=boardsize+1;j++)
            {
                states[i][j]=b[i][j];
            }
        }
        row=-1;
        column=-1;
        statedepth=1;
        for (int i=1;i<=boardsize;i++)
        {
            for (int j=1;j<=boardsize;j++)
            {
                if(states[i][j]=='.')
                {
                    for (int p=0;p<=boardsize+1;p++)
                        {
                            for (int q=0;q<=boardsize+1;q++)
                                {
                                     new_state[p][q]=states[p][q];
                                }
                        }
                    new_state[i][j]=player;
                    //new_state=checkraid(new_state,i,j);
                    count=count+1;
                    movevalue=alphabeta(new_state,statedepth,false,alpha,beta);
                    //System.out.println(movevalue+" "+bestvalue);
                    if(movevalue>bestvalue)
                    { 
                        row=i;
                        column=j;
                        bestvalue=movevalue;
                    }
                    
                }
            }
        }
        statedepth=1;
        
        for (int i=1;i<=boardsize;i++)
        {
            for (int j=1;j<=boardsize;j++)
            {
                
                if(arrayforraid[i][j])
                {
                    if(states[i][j]=='.')
                    {  
                        for (int p=0;p<=boardsize+1;p++)
                            {
                                for (int q=0;q<=boardsize+1;q++)
                                {
                                     new_state[p][q]=states[p][q];
                                }
                            }
                     new_state[i][j]=player;
                     new_state=checkraid(new_state,i,j);
                     count=count+1;
                     //printstates(new_state);
                     movevalue=alphabeta(new_state,statedepth,false,alpha,beta);
                    //System.out.println(movevalue+" "+bestvalue
                    //System.out.println(movevalue);
                    if(movevalue>bestvalue)
                    { 
                        stake=0;
                        row=i;
                        column=j;
                        bestvalue=movevalue;
                    }   
                        
                }
            }
       
    }
        }
      
        
    }
    
    public static int alphabeta(char b[][],int statedepth,boolean checkifmaxmin,int alpha,int beta)
    {
        //System.out.println(statedepth+" dkdkfkkfkjfff"+depth);
        char states[][]=new char[40][40];
        char new_state[][]=new char[40][40];
        for (int i=0;i<=boardsize+1;i++)
        {
            for (int j=0;j<=boardsize+1;j++)
            {
                states[i][j]=b[i][j];
            }
        }
        int checktermination=0;
        int terminalscore=score(states);
        //System.out.println("terminalscore "+terminalscore);
        if(statedepth>=depth)
        {   //System.out.println("should get terminated");
            checktermination=1;
        }
        
        if (checktermination==1)
                {
                    //System.out.println("reached termination");
                    //System.out.println("here");
                    return terminalscore;
                }
        
        if (checkifmaxmin)
        {
            int best=-9999;
            for(int i=1;i<=boardsize;i++)
            {
                for (int j=1;j<=boardsize;j++)
                {
                    if(states[i][j]=='.')
                    {
                        for (int p=0;p<=boardsize+1;p++)
                        {
                            for (int q=0;q<=boardsize+1;q++)
                                {
                                     new_state[p][q]=states[p][q];
                                }
                        }
                        count++;
                        new_state[i][j]=player;
                        new_state=checkraid(new_state,i,j);
                        //System.out.println("+++++++++++++");
                        //printstates(states);
                        best=Math.max(best,alphabeta(new_state,statedepth+1,false,alpha,beta));
                        if(best>=beta)
                        {
                            prunec++;
                            return best;
                        }	
                        alpha=Math.max(alpha,best);
                        //System.out.println(best+"-----------------");
                        if(statedepth==0)
                        {
                            
                        }
                    }
                    
                }
            }
            return best;
            
         }
         else
        {
            int best=9999;
            for(int i=1;i<=boardsize;i++)
            {
                for (int j=1;j<=boardsize;j++)
                {
                    if(states[i][j]=='.')
                    {   for (int p=0;p<=boardsize+1;p++)
                        {
                            for (int q=0;q<=boardsize+1;q++)
                                {
                                     new_state[p][q]=states[p][q];
                                }
                        }
                        new_state[i][j]=opponent;
                        new_state=checkraid(new_state,i,j);
                        count++;
                        best=Math.min(best,alphabeta(new_state,statedepth+1,true,alpha,beta));
                        if(best<=alpha)
                        {
                            prunec++;
                            return best;
                        }
			beta=Math.min(beta,best);
                        
                        //System.out.println(best+"-----------------");
                    }
                    
                }
            }
        return best;
        }
    }
     
    public static char[][] checkraid(char b[][],int i,int j)
    {
        char states[][]=new char[40][40];
        for (int x=0;x<=boardsize+1;x++)
        {
            for (int y=0;y<=boardsize+1;y++)
            {
                states[x][y]=b[x][y];
            }
        }
        raid=false;
        char raidopponent;
        if (states[i][j]=='X')
        {
            raidopponent='O';
        }
        else
        {
            raidopponent='X';
        }
        if(states[i-1][j]==states[i][j] || states[i][j-1]==states[i][j] || states[i+1][j]==states[i][j] || states[i][j+1]==states[i][j])
        {
            if (states[i-1][j]==raidopponent || states[i][j-1]==raidopponent || states[i+1][j]==raidopponent || states[i][j+1]==raidopponent)
            {
                raid=true;
            }
        }
        
        if(raid==true)
        {
            
            if(states[i-1][j]==raidopponent)
            {
                states[i-1][j]=states[i][j];
            }
            if(states[i][j-1]==raidopponent)
            {
               states[i][j-1]=states[i][j]; 
            }
            if(states[i+1][j]==raidopponent)
            {
                states[i+1][j]=states[i][j];
            }
            if(states[i][j+1]==raidopponent)
            {  
                states[i][j+1]=states[i][j];
            }
        }
        return states;
    }
    public static int score(char b[][])
    {   
        //printstates(b);
        //System.out.println("------------------------");
        char states[][]=new char[40][40];
        for (int i=0;i<=boardsize+1;i++)
        {
            for (int j=0;j<=boardsize+1;j++)
            {
                states[i][j]=b[i][j];
            }
        }
        //printstates(states);
        int scorex=0;
        int scorey=0;
        for (int i=1;i<=boardsize;i++)
        {
            for (int j=1;j<=boardsize;j++)
            {
                if(states[i][j]=='X')
                {
                    scorex=scorex+cell[i][j];
                }
                if(states[i][j]=='O')
                {
                    scorey=scorey+cell[i][j];
                }
            }
        }
        if(player=='X')
        {   //System.out.println(scorex-scorey);
            return scorex-scorey;
        }
        else
        {   
            //System.out.println(scorey-scorex);
            return scorey-scorex;
            
        }
    }
    
    public static void printcell()
    {
        for (int i=1;i<=boardsize;i++)
        {
            for (int j=1;j<=boardsize;j++)
            {
                System.out.print(cell[i][j] + "\t");
            }
            System.out.println();
        }
    }
    
    public static void printstates(char b[][]) throws IOException
    {
        File file =new File("output.txt");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
        char c=(char)(column+64);
        //System.out.println("count is"+count+" pruncecount is"+prunec);
	if (stake==1)
        {
            //System.out.println(c+""+row+" "+"Stake");
            bw.write(c+""+row+" "+"Stake"+"\n");
        }
        else
        {
            //System.out.println(c+""+row+" "+"Raid");
            bw.write(c+""+row+" "+"Raid"+"\n");
        }
	//bw.close();
        
        for (int i=1;i<=boardsize;i++)
        {
            for (int j=1;j<=boardsize;j++)
            {
                bw.write(b[i][j]);
                //System.out.print(b[i][j]);
            }
            //System.out.println();
            bw.write("\n");
        }
        bw.close();
    }
    public static void closeFile()
    {
        x.close();
    }    
} 
