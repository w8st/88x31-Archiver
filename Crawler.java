/*
MIT License

Copyright (c) 2021 Some-RandomProgrammer

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.jsoup.select.Elements;
	 
	public class Crawler {
		public static String IMAGE_FOLDER = System.getProperty("user.home") + "/buttons/";
	    public static void main(String[] args) throws IOException {
	    	
	       System.out.println("Files are saved at: "+IMAGE_FOLDER);
	       Scanner in = new Scanner(System.in);
	       System.out.println("Eneter Web Page to be scanned: \n");
	        String strURL = in.nextLine();
	        in.close();
			File folder = new File(IMAGE_FOLDER);
			if(!folder.exists()) {
				folder.mkdir();
			}
	        if(! strURL.contains("://")) {
	        	strURL = "http://"+strURL;
	        }
	        Document document = Jsoup
	                .connect(strURL)
	                .userAgent("88x31 Archiver/1.0")
	                .timeout(10 * 1000)
	                .get();
	        Elements imageElements = document.select("img");
	        for(Element imageElement : imageElements){
	            String strImageURL = imageElement.attr("abs:src");
		            try {
					Thread.sleep(7500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            downloadImage(strImageURL);
	        }
	 
	    }
	    

		private static void downloadImage(String strImageURL){
	        
	        //get file name from image path
	        String strImageName = strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );
	        
	        
	        try {
	            URL urlImage = new URL(strImageURL);
	            BufferedImage image = ImageIO.read(urlImage);
	            int height = image.getHeight();
	            int width = image.getWidth();
	            if (height == 31 && width == 88){

		            InputStream in = urlImage.openStream();
		            
		            byte[] buffer = new byte[4096];
		            int n = -1;

		            OutputStream os = 
		                new FileOutputStream( IMAGE_FOLDER + strImageName );
		            
		            //write bytes to the output stream
		            while ( (n = in.read(buffer)) != -1 ){
		                os.write(buffer, 0, n);
		            }
		            
		            //close the stream
		            os.close();
		            
		            System.out.println("Image saved");
		            System.out.println(strImageURL);
	            }
	            else {
	            	return;
	            }

	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return;
	        
	    }
	}
	

