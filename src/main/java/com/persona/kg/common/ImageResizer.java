package com.persona.kg.common;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class ImageResizer {
	private String source;
	private String target;
	private Log logger=LogFactory.getLog(ImageResizer.class);
	public ImageResizer(String source, String target) {
		this.source=source;
		this.target=target;
	}
	public boolean createThumbnailold()    
	{   
		boolean result=false;
		try {
		BufferedImage originalImage=ImageIO.read(new File(source));
		int x=originalImage.getWidth(null);
		int y=originalImage.getHeight(null);
		int marginX=0;
		int marginY=0;
		int dim=x;
		if(x>y){
			marginX=(x-y)/2;
			dim=y;
		}else{
			marginY=(y-x)/2;
			dim=x;
		}
		Image img=null;//;createImage(new FilteredImageSource(originalImage.getSource(),new CropImageFilter(marginX,marginY,dim,dim)));
		Image rescaled = img.getScaledInstance( 80, 80, Image.SCALE_SMOOTH) ;
		BufferedImage biRescaled = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = biRescaled.createGraphics();
		g.drawImage(rescaled, 0, 0, null);
        g.dispose();
        ImageIO.write(biRescaled, "png", new FileOutputStream(target));
        result=true;
		} catch (Exception e) {
			logger.warn("Thumbnail exception",e);
		} 
        return result;
	}
	public boolean createThumbnail()
	{
		boolean result=false;
		try {
		BufferedImage originalImage=ImageIO.read(new File(source));
	    BufferedImage bicubic = new BufferedImage(80, 80, originalImage.getType());
	    Graphics2D bg = bicubic.createGraphics();
	    bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    float sx = (float)80 / originalImage.getWidth();
	    float sy = (float)80 / originalImage.getHeight();
	    bg.scale(sx, sy);
	    bg.drawImage(originalImage, 0, 0, null);
	    bg.dispose();
	    ImageIO.write(bicubic, "png", new FileOutputStream(target));
	    result=true;
		}catch (Exception e) {
			logger.warn("Thumbnail exception",e);
		}
	    return result;
	}
}
