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
		System.out.print("Enter the number of SYMRTY Matrices in your PDB file:");
		int no=sc.nextInt();
		String fileName=location+file+".pdb";
		double sym[][][]=new double[no][3][3];
		double tra[][]=new double[no][3];
		String line="";
		try{  
		    FileInputStream fin=new FileInputStream(fileName);
		    FileOutputStream fout=new FileOutputStream(location+file+"_atom.pdb");
		    FileOutputStream faout[]=new FileOutputStream[no];
		    for(int j=0;j<no;j++)faout[j]=new FileOutputStream(location+file+"_"+j+".pdb");
		    int i=0,n=0,s=0,t=0;
		    while((i=fin.read())!=-1){ 
		    	line=""+(char)i;
		    	while((i=fin.read())!='\n')line+=(char)i;
		    	line+="\n";
		    	if(line.contains("SMTRY"))
		    	{
		    		int xx=line.indexOf('.',0);
            		int yy=line.indexOf('.',xx+1);
            		int zz=line.indexOf('.',yy+1);
            		int ww=line.indexOf('.',zz+1);
            		String tempx=line.substring(0,xx+7);
            		tempx=tempx.substring(tempx.lastIndexOf(' ')+1);
            		String tempy=line.substring(xx+7,yy+7);
            		tempy=tempy.substring(tempy.lastIndexOf(' ')+1);
            		String tempz=line.substring(yy+7,zz+7);
            		tempz=tempz.substring(tempz.lastIndexOf(' ')+1);
            		String tempw=line.substring(zz+7,ww+6);
            		tempw=tempw.substring(tempw.lastIndexOf(' ')+1);
            		sym[s][t][0]=Double.parseDouble(tempx);
            		sym[s][t][1]=Double.parseDouble(tempy);
            		sym[s][t][2]=Double.parseDouble(tempz);
            		tra[s][t]=Double.parseDouble(tempw);
            		System.out.println(sym[s][t][0]+" "+sym[s][t][1]+" "+sym[s][t][2]+" "+tra[s][t]);
            		if(t<2)t++;else {t=0;s++;}
            		byte b[]=line.getBytes();
    				fout.write(b); 
            		for(int j=0;j<no;j++)
        			{
        				b=line.getBytes();
        				faout[j].write(b); 
        			}
		    	}
		    	else if(line.startsWith("ATOM"))
            	{
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
            		String s1="";
            		byte b[];
            		for(int j=0;j<no;j++)
            		{
            			n++;
            			double x1=x*sym[j][0][0]+y*sym[j][0][1]+z*sym[j][0][2]+tra[j][0];
            			double y1=x*sym[j][1][0]+y*sym[j][1][1]+z*sym[j][1][2]+tra[j][1];
            			double z1=x*sym[j][2][0]+y*sym[j][2][1]+z*sym[j][2][2]+tra[j][2];
            			String sp1="",sp2="",sp3="";
            			//x=x1;y=y1;z=z1;
            			sp2=line.substring(11,f).trim();
            			sp1=line.substring(0,f).trim();
            			s1=sp1+String.format( "%12.3f",x1)+String.format( "%8.3f",y1)+String.format( "%8.3f",z1)+line.substring(zz+4);
            			sp3="ATOM"+String.format("%7d",n)+"  "+sp2+String.format( "%12.3f",x1)+String.format( "%8.3f",y1)+String.format( "%8.3f",z1)+line.substring(zz+4);
            			b=s1.getBytes();
            			faout[j].write(b); 
            			b=sp3.getBytes();
            			fout.write(b);
            		}
            		b=line.getBytes();
    				faout[0].write(b); 
            	}
		    	else {
		    		byte b[]=line.getBytes();
    				fout.write(b); 
    				for(int j=0;j<no;j++)
    			{
    					b=line.getBytes();
    				faout[j].write(b); 
    			}
		    	}
		    } 
  		    fout.close();  
		    fin.close();  
		  }catch(Exception e){System.out.println(e);}  
	}

}
