package kr.co.prnserver.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;


public class PathUtil {
	private static final int BUFFER_SIZE = 4096;

	private String filePath = null;
	private String contentType = "application/octet-stream";
	private String fileName = null;
	
	public PathUtil( String filePath ) {
		this.filePath = filePath;
	}
	
	public PathUtil setContentType( String contentType ) {
		this.contentType = contentType;
		return this;
	}
	
	public PathUtil setFileName( String fileName ) {
		this.fileName = fileName;
		return this;
	}
	
	public void write( HttpServletResponse response ) throws IOException {
		File file = new File( filePath );
		
		response.setContentType( contentType );
		response.setHeader( "Content-Length", Long.toString( file.length() ) );
		
		if ( fileName == null )
			fileName = file.getName();
		
		response.setHeader( "Content-Disposition", "attachment; filename=\"" + fileName + "\";" );
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			fis = new FileInputStream( file );
			bis = new BufferedInputStream( fis );
			bos = new BufferedOutputStream( response.getOutputStream() );
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ( ( bytesRead = bis.read( buffer ) ) != -1 ) {
				bos.write( buffer, 0, bytesRead );
			}
			bos.flush();
			
		} finally {
			if ( bos != null ) {
				try { bos.close(); } catch ( Exception e ) { /* Ignore */ } finally { bos = null; }
			}
			if ( bis != null ) {
				try { bis.close(); } catch ( Exception e ) { /* Ignore */ } finally { bis = null; }
			}
			if ( fis != null ) {
				try { fis.close(); } catch ( Exception e ) { /* Ignore */ } finally { fis = null; }
			}
		}
	}
}
