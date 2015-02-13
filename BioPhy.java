import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
public class BioPhy {

	public static void main(String[] args) {
		System.out.print("Enter the location of where the PDB files are stored:\nFor example: C:\\Users\\Srujan\\Downloads\\: ");
		Scanner sc=new Scanner(System.in);
		String location=sc.nextLine();
		System.out.print("Enter the PDB code and ensure that it is located in the above location: ");
		String file=sc.nextLine();
		String fileName=location+file+".pdb";
		double sym[][]={{0,0,0},{0,0,0},{0,0,0}};
		double tra[][]={{0,0,0},{0,0,0},{0,0,0}};
		String line="";
		try{  
		    FileInputStream fin=new FileInputStream(fileName);
		    FileOutputStream fout=new FileOutputStream(location+file+"_atom.pdb");
		    FileOutputStream faout[]=new FileOutputStream[8];
		    for(int j=0;j<8;j++)faout[j]=new FileOutputStream(location+file+"_"+j+".pdb");
		    int i=0,n=0,s=0,t=0;
		    while((i=fin.read())!=-1){ 
		    	line=""+(char)i;
		    	while((i=fin.read())!='\n')line+=(char)i;
		    	line+="\n";
		    	if(line.startsWith("ORIGX"))
		    	{
		    		int xx=line.indexOf('.',0);
            		int yy=line.indexOf('.',xx+1);
            		int zz=line.indexOf('.',yy+1);
            		String tempx=line.substring(0,xx+7);
            		tempx=tempx.substring(tempx.lastIndexOf(' ')+1);
            		String tempy=line.substring(xx+7,yy+7);
            		tempy=tempy.substring(tempy.lastIndexOf(' ')+1);
            		String tempz=line.substring(yy+7,zz+7);
            		tempz=tempz.substring(tempz.lastIndexOf(' ')+1);
            		sym[s][0]=Double.parseDouble(tempx);
            		sym[s][1]=Double.parseDouble(tempy);
            		sym[s][2]=Double.parseDouble(tempz);
            		s++;
            		for(int j=0;j<8;j++)
        			{
        				byte b[]=line.getBytes();
        				faout[j].write(b); 
        			}
		    	}
		    	else if(line.startsWith("SCALE"))
		    	{
		    		int xx=line.indexOf('.',0);
            		int yy=line.indexOf('.',xx+1);
            		int zz=line.indexOf('.',yy+1);
            		String tempx=line.substring(0,xx+7);
            		tempx=tempx.substring(tempx.lastIndexOf(' ')+1);
            		String tempy=line.substring(xx+7,yy+7);
            		tempy=tempy.substring(tempy.lastIndexOf(' ')+1);
            		String tempz=line.substring(yy+7,zz+7);
            		tempz=tempz.substring(tempz.lastIndexOf(' ')+1);
            		tra[t][0]=Double.parseDouble(tempx);
            		tra[t][1]=Double.parseDouble(tempy);
            		tra[t][2]=Double.parseDouble(tempz);
            		t++;
            		for(int j=0;j<8;j++)
        			{
        				byte b[]=line.getBytes();
        				faout[j].write(b); 
        			}
		    	}
		    	else if(line.startsWith("ATOM"))
            	{
		    		n++;
            		int l=line.length();
            		char atom=line.charAt(l-3);
            		int xx=line.indexOf('.',0);
            		int yy=line.indexOf('.',xx+1);
            		int zz=line.indexOf('.',yy+1);
            		String tempx=line.substring(0,xx+4);
            		int f=tempx.lastIndexOf(' ')+1;
            		tempx=tempx.substring(f);
            		String tempy=line.substring(xx+4,yy+4);
            		tempy=tempy.substring(tempy.lastIndexOf(' ')+1);
            		String tempz=line.substring(yy+4,zz+4);
            		tempz=tempz.substring(tempz.lastIndexOf(' ')+1);
            		double x=Double.parseDouble(tempx);
            		double y=Double.parseDouble(tempy);
            		double z=Double.parseDouble(tempz);
            		System.out.println(n+"\t"+atom+"\t"+x+"\t"+y+"\t"+z);
            		String s1=atom+"\t"+x+"\t"+y+"\t"+z+"\n";
            		byte b[]=s1.getBytes();
            		fout.write(b);
            		for(int j=1;j<8;j++)
            		{
            			double x1=x*sym[0][0]+y*sym[0][1]+y*sym[0][2]+tra[0][0];
            			double y1=x*sym[1][0]+y*sym[1][1]+y*sym[1][2]+tra[1][1];
            			double z1=x*sym[2][0]+y*sym[2][1]+y*sym[2][2]+tra[2][2];
            			x=x1;y=y1;z=z1;
            				s1=line.substring(0, f)+"\t"+String.format( "%.3f",x)+"\t"+String.format( "%.3f",y)+"\t"+String.format( "%.3f",z)+line.substring(zz+4);
            				b=s1.getBytes();
            				faout[j].write(b); 
            		}
            		b=line.getBytes();
    				faout[0].write(b); 
            		
            	}
		    	else {for(int j=0;j<8;j++)
    			{
    				byte b[]=line.getBytes();
    				faout[j].write(b); 
    			}
		    	}
		    } 
  		    fout.close();  
		    fin.close();  
		  }catch(Exception e){System.out.println(e);}  
	}

}
