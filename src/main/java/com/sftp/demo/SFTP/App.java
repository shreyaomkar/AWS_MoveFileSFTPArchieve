package com.sftp.demo.SFTP;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Hello world!
 *
 */
public class App 
{
	 public static void main( String[] args ) throws IOException
	 {
		 JSch jsch = new JSch();
	        Session session = null;
	        try 
	        {
	        	System.out.println("Hi");
	            session = jsch.getSession("root", "127.0.0.1", 22);
	            session.setConfig("StrictHostKeyChecking", "no");
	            session.setPassword("password");
	            System.out.println("Before session");
	            session.connect();
	            System.out.println("After session");
	            Channel channel = session.openChannel("sftp");
	            channel.connect();
	            ChannelSftp sftpChannel = (ChannelSftp) channel;
	            sftpChannel.cd("./");
	            System.out.println(sftpChannel.getHome());
	            System.out.println(sftpChannel.getId());
	            
	            
	            String inputFilePath = "C://Program Files//JSCAPE MFT Server//users//sftpserver//root//test/test.txt";
	            
	            InputStream stream=new BufferedInputStream(new FileInputStream(inputFilePath));
	            sftpChannel.put(stream,"Sample/Check.txt");
	            stream.close();
	           
	          
	           
	            Vector filelist=sftpChannel.ls("./Sample");
	            for (int i = 0; i < filelist.size(); i++) 
	            {
	                System.out.println(filelist.get(i).toString());
	                
	                
	                if(filelist.get(i).toString().contains("Check.txt"))
	                {
	                	System.out.println("File is available");
	                	//stream.close();
	                	//destinationFolder.close();
	                	InputStream destinationFolder=new BufferedInputStream(new FileInputStream(inputFilePath));
	     	            sftpChannel.put(destinationFolder, "test/archive/Check.txt");
	     	            destinationFolder.close();
	                	sftpChannel.rm("test/test.txt");
	                }
	            }
	            try 
	            {
	                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
	                String line;
	                while ((line = br.readLine()) != null) 
	                {
	                    System.out.println(line);
	                }

	            }
	            catch (IOException io) 
	            {
	                System.out.println("Exception occurred during reading file from SFTP server due to " + io.getMessage());
	                io.getMessage();

	            }
	            catch (Exception e) 
	            {
	                System.out.println("Exception occurred during reading file from SFTP server due to " + e.getMessage());
	                e.getMessage();

	            }

	            sftpChannel.exit();
	            session.disconnect();
		        }
		        catch (JSchException e) 
		        {
		            e.printStackTrace();
		        }
		        catch (SftpException e) 
		        {
		            e.printStackTrace();
		        }
	    	
	    }
	
}
