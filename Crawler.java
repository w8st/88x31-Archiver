
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
	

