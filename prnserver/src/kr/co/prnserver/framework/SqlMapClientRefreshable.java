package kr.co.prnserver.framework;

public interface SqlMapClientRefreshable {
    void refresh() throws Exception;
    

    void setCheckInterval(int ms);
}
