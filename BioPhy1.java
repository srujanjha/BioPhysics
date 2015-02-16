import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.StringTokenizer;


public class BioPhy1 {

	public static void main(String[] args) {
		String location="C:\\Users\\Srujan\\Desktop\\";
		String file="2D5Z";
		String fileName=location+file+".pdb";
		int no=4;
		try{
		    FileOutputStream fout=new FileOutputStream(location+file+"_atom.pdb");
		    FileInputStream faout[]=new FileInputStream[no];
		    for(int j=0;j<no;j++)faout[j]=new FileInputStream(location+file+"_"+j+".pdb");
		    int i=0;String line="";
		    while(true)
		    {
		    		while((i=faout[0].read())!='\n')line+=(char)i;
			    	line+="\n";
			    	if(line.startsWith("END"))break;
			    	byte b[]=line.getBytes();
		    		fout.write(b);
		    		//System.out.println(line);
		    		line="";
		    }
		    int n=1186;
		    for(int j=1;j<no;j++)
		    {
		    	while((i=faout[j].read())!=-1){ 
		    	line=""+(char)i;
		    	while((i=faout[j].read())!='\n')line+=(char)i;
		    	line+="\n";
		    	if(line.startsWith("ATOM"))
		    	{
			    	String sp3="TER\n";
			    	byte b[]=sp3.getBytes();
		    		fout.write(b);
		    		while(true)
				    {
		    			sp3="";
					    	if(line.startsWith("ATOM"))
					    	{
					    		n++;
					    		sp3=line.substring(11);
					    		sp3="ATOM"+String.format("%7d",n)+sp3;
					    	}
					    	else if(line.startsWith("TER"))
					    	{n++;line="TER"+String.format("%8d", n)+line.substring(11);}
					    	else if(line.startsWith("HETATM"))
					    	{
					    		n++;
					    		sp3=line.substring(11);
					    		sp3="HETATM"+String.format("%5d",n)+sp3;
					    	}
					    	else if(line.startsWith("CONECT"))
					    	{
					    		StringTokenizer st=new StringTokenizer(line);
					    		st.nextToken();
					    		while(st.hasMoreTokens())
					    		{	int x=Integer.parseInt(st.nextToken().trim());
					    		System.out.print(x);
					    		sp3+=String.format("%5d",j*1186+x);}
					    		sp3="CONECT"+sp3+"\n";
					    		System.out.println();
					    	}
					    	else if(line.startsWith("MASTER"))
					    	{
					    		StringTokenizer st=new StringTokenizer(line);
					    		st.nextToken();
					    		while(st.hasMoreTokens())
					    		{	int x=Integer.parseInt(st.nextToken().trim());
					    		sp3+=String.format("%5d",j*1186+x);}
					    		sp3="MASTER"+sp3+"\n";
					    	}
					    	else sp3=line;
					    	if(line.startsWith("END"))
					    		break;
				    		//System.out.println(sp3);
					    	 b=sp3.getBytes();
				    		fout.write(b);
				    		i=faout[j].read();
				    		if(i!=-1){ line=""+(char)i;
					    	while((i=faout[j].read())!='\n')line+=(char)i;
					    	line+="\n";}
				    }
		    	}
		    }
		    }
    		byte b[]="END".getBytes();
    		fout.write(b);
  		    fout.close();  
  		  for(int j=0;j<no;j++)faout[j].close();  
		}catch(Exception e){System.out.println(e);}  
	}

}
