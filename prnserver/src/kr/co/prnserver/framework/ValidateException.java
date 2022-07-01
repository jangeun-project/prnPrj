package kr.co.prnserver.framework;

public class ValidateException  extends RuntimeException
{
    private String message;
    public ValidateException(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return this.message;
    }
}
