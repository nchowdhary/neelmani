package com.awesome.nc;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;

public class CompressPDF
{

	protected static String folderPath = "/Users/neelmani/TIBCO Google Drive/work/ebx_docs/ebx6/";

	protected static String sourceFile = "What's New in EBX 6.0.0.pdf";
	protected static String targetFile = sourceFile.substring(0, sourceFile.length() - 4)
		+ " Compressed.pdf";

	/** The resulting PDF file. */
	//public static String RESULT = "results/part4/chapter16/resized_image.pdf";
	/** The multiplication factor for the image. */
	public static float FACTOR = 0.5f;

	/**
	 * Manipulates a PDF file src with the file dest as result
	 * @param src the original PDF
	 * @param dest the resulting PDF
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public void manipulatePdf(String src, String dest) throws IOException, DocumentException
	{
		PdfName key = new PdfName(sourceFile);
		PdfName value = new PdfName(sourceFile);
		// Read the file
		PdfReader reader = new PdfReader(src);
		int n = reader.getXrefSize();
		PdfObject object;
		PRStream stream;
		// Look for image and manipulate image stream
		for (int i = 0; i < n; i++)
		{
			object = reader.getPdfObject(i);
			if (object == null || !object.isStream())
				continue;
			stream = (PRStream) object;
			// if (value.equals(stream.get(key))) {
			PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
			// System.out.println(stream.type());
			if (pdfsubtype != null && pdfsubtype.toString().equals(PdfName.IMAGE.toString()))
			{
				PdfImageObject image = new PdfImageObject(stream);
				
				BufferedImage bi = null;
				try
				{
					bi = image.getBufferedImage();
				}
				catch (Exception e)
				{

				}
				if (bi == null)
					continue;
				int width = (int) (bi.getWidth() * FACTOR);
				int height = (int) (bi.getHeight() * FACTOR);
				BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
				Graphics2D g = img.createGraphics();
				g.drawRenderedImage(bi, at);
				ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
				ImageIO.write(img, "JPG", imgBytes);
				stream.clear();
				stream.setData(imgBytes.toByteArray(), false, PRStream.BEST_COMPRESSION);
				stream.put(PdfName.TYPE, PdfName.XOBJECT);
				stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
				stream.put(key, value);
				stream.put(PdfName.FILTER, PdfName.DCTDECODE);
				stream.put(PdfName.WIDTH, new PdfNumber(width));
				stream.put(PdfName.HEIGHT, new PdfNumber(height));
				stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
				stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
			}
		}
		// Save altered PDF
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		stamper.close();
		reader.close();
	}

	/**
	 * Main method.
	 *
	 * @param    args    no arguments needed
	 * @throws DocumentException 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, DocumentException
	{

		//createPdf(RESULT);
		new CompressPDF().manipulatePdf(folderPath + sourceFile, folderPath + targetFile);
		System.out.println(folderPath + targetFile + " generated.");
	}

}