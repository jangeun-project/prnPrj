package kr.co.prnserver.prnlist.model;

import java.util.Date;

/**
 * 스캔로그 모델
 * 
 * @author DongHwan Kim(dh14.kim@gmail.com)
 *
 */
public class ScanLog {

	private Long	logId;								// 로그ID
	private Date	usageTime;							// 사용시각
	private String	imageFilePath;						// 이미지파일경로
	private Long	imageFileSize;						// 이미지파일용량
	private String	thumbNailPath;						// 썸네일경로
	private String imageLogPath;						// 이미지 로그 경로
	private String imageFileName;						// 이미지 파일 이름 
	private Integer	pageCount;							// 페이지수
	private String	destination;						// 목적지
	private Boolean	statsApplyYn;						// 통계적용여부
	private Boolean	deleteYn;							// 삭제여부
	private Integer	mfpId;								// 복합기ID
	
	private String	serialNumber;						// 시리얼넘버
	private String	modelName;							// 모델명
	private Integer	userId;								// 사용자ID
	private String	loginId;							// 로그인ID
	private String	userName;							// 사용자명
	private Integer	deptId;								// 부서ID
	private String	deptName;							// 부서명
	private String	fullDeptName;						// 전체부서명
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public Date getUsageTime() {
		return usageTime;
	}
	public void setUsageTime(Date usageTime) {
		this.usageTime = usageTime;
	}
	public String getImageFilePath() {
		return imageFilePath;
	}
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
	public Long getImageFileSize() {
		return imageFileSize;
	}
	public void setImageFileSize(Long imageFileSize) {
		this.imageFileSize = imageFileSize;
	}
	public String getThumbNailPath() {
		return thumbNailPath;
	}
	public void setThumbNailPath(String thumbNailPath) {
		this.thumbNailPath = thumbNailPath;
	}
	public String getImageLogPath(){
		return imageLogPath;
	}
	public void setImageLogPath(String imageLogPath){
		this.imageLogPath=imageLogPath;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Boolean getStatsApplyYn() {
		return statsApplyYn;
	}
	public void setStatsApplyYn(Boolean statsApplyYn) {
		this.statsApplyYn = statsApplyYn;
	}
	public Boolean getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(Boolean deleteYn) {
		this.deleteYn = deleteYn;
	}
	public Integer getMfpId() {
		return mfpId;
	}
	public void setMfpId(Integer mfpId) {
		this.mfpId = mfpId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getFullDeptName() {
		return fullDeptName;
	}
	public void setFullDeptName(String fullDeptName) {
		this.fullDeptName = fullDeptName;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "ScanLog [logId=" ).append( logId ).append( ", usageTime=" ).append( usageTime )
			.append( ", imageFilePath=" ).append( imageFilePath ).append( ", imageFileSize=" ).append( imageFileSize )
			.append( ", thumbNailPath=" ).append( thumbNailPath ).append( ", pageCount=" ).append( pageCount )
			.append( ", destination=" ).append( destination )
			.append( ", statsApplyYn=" ).append( statsApplyYn ).append( ", deleteYn=" ).append( deleteYn )
			.append( ", mfpId=" ).append( mfpId ).append( ", serialNumber=" ).append( serialNumber )
			.append( ", modelName=" ).append( modelName ).append( ", userId=" ).append( userId )
			.append( ", loginId=" ).append( loginId ).append( ", userName=" ).append( userName )
			.append( ", deptId=" ).append( deptId ).append( ", deptName=" ).append( deptName )
			.append( ", fullDeptName=" ).append( fullDeptName )
			.append( "]" );
		
		return sb.toString();
	}
	
	
}
