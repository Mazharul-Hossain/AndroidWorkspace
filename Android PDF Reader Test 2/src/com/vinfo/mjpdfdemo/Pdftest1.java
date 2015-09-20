package com.vinfo.mjpdfdemo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import net.sf.andpdf.nio.ByteBuffer;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.decrypt.PDFAuthenticationFailureException;

public class Pdftest1 {

	private static Bitmap bArr;

	public Pdftest1() {
		PdfFind obj = new PdfFind();
		try {
			obj.parsePDF("/sdcard/FirstPdf.pdf");
			if (obj.showPage(3, 1) != null)
				bArr = obj.showPage(3, 1);
		} catch (PDFAuthenticationFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Bitmap getBitmapImages() {
		return bArr;
	}

	private class PdfFind {
		private PDFFile mPdfFile;
		private PDFPage mPdfPage;

		private void parsePDF(String filename)
				throws PDFAuthenticationFailureException {

			try {
				File f = new File(filename);
				long len = f.length();
				if (len == 0) {
					Log.i("No File", "File length is 0");
				} else {
					openFile(f);
					Log.i("ParsePDF", "parse pdf called f =" + f.toString());
				}
			} catch (PDFAuthenticationFailureException e) {
				throw e;
			} catch (Throwable e) {
				e.printStackTrace();
				Log.i("Error", "Cant Read File");
			}
		}

		public void openFile(File file) throws IOException {
			// first open the file for random access
			RandomAccessFile raf = new RandomAccessFile(file, "r");

			// extract a file channel
			FileChannel channel = raf.getChannel();

			// now memory-map a byte-buffer
			ByteBuffer bb = ByteBuffer.NEW(channel.map(
					FileChannel.MapMode.READ_ONLY, 0, channel.size()));
			// create a PDFFile from the data
			mPdfFile = new PDFFile(bb);
			Log.i("File",
					"mPdfFile Obj created pages = " + mPdfFile.getNumPages());
		}

		private Bitmap showPage(int page, int zoom) throws Exception {
			try {
				// free memory from previous page

				System.gc();

				mPdfPage = mPdfFile.getPage(page, true);
				Log.i("File Page",
						"mPdfPage creates width = " + mPdfPage.getWidth());
				float wi = mPdfPage.getWidth();
				float hei = mPdfPage.getHeight();

				RectF clip = null;
				Bitmap bi = mPdfPage.getImage((int) (wi * 1.25),
						(int) (hei * 1.25), clip, true, true);
				Log.i("PAge Image", "Image Created width = " + bi.getWidth());
				return bi;
			} catch (Throwable e) {
				Log.e("Error", e.getMessage(), e);
			}
			return null;
		}
	}

}