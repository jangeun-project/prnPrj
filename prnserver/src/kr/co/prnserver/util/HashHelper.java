package kr.co.prnserver.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashHelper {

	/**
	 * 메시지에 대한 MD5 해시값을 구한다.
	 * 
	 * @param message 메시지
	 * @return MD5로 해시된 무자열
	 */
	public static String getMD5( String message ) {
		if( message == null || message.length() < 1 )
			throw new IllegalArgumentException( "value error" );
		
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance( "MD5" );
			md.update( message.getBytes() );
			byte[] hashBytes = md.digest();
			
			for( int i = 0, n = hashBytes.length; i < n; i++ ) {
				sb.append( String.format( "%02x", hashBytes[i] ) );
			}
		}
		catch( NoSuchAlgorithmException e ) {
			return null;
		}
		
		return sb.toString();
	}
	
}
