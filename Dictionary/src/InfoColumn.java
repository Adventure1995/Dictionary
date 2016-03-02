/**
 * 用于存储单个单词的所有信息
 * @author anonymousXY
 *
 */
public class InfoColumn {

	public InfoColumn(String ENVocabulary, String CNVocbulary, String soundmark) {
		// TODO Auto-generated constructor stub
		this.ENVocabulary = ENVocabulary;
		this.CNVocbulary = CNVocbulary;
		this.soundmark = soundmark;
	}
	
	public String getENVocabulary() {
		return ENVocabulary;
	}


	public String getCNVocbulary() {
		return CNVocbulary;
	}


	public String getSoundmark() {
		return soundmark;
	}


	private String ENVocabulary;
	private String CNVocbulary;
	private String soundmark;
	
}
