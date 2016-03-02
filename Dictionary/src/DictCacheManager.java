/**
 * 使用单例设计方法，设计字典缓存管理器器
 * 保证在程序运行时只读取一次字典库，加快运行速度
 * @author anonymousXY
 *
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class DictCacheManager {
	
	private DictCacheManager() {
		ENVocabularyIndex = new TreeMap<>();
		CNVocbularyIndex = new TreeMap<>();
		try {
			readFile("Vocabulary.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DictCacheManager getInstance() {
		//单例设计获得唯一对象方法
		if (management == null)
		{
			management = new DictCacheManager();
			return management;
		}
		else
			return management;
		
	}
	
	private void readFile(String path) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		String line = null;
		
		int isCompleted = 0;
		
		int index[] = new int[3];  //设定分隔符数量，用来分割词典
		
		while ((line = in.readLine()) != null)
		{
			int j = 0;
			
			//用于检测   0,aback,"adv.向后地,朝后地",[ə'bæk]
			//其中，分号内的要一起读，逗号为分隔符
			for (int i = 0; i < line.length(); i++)   
			{
				if (line.charAt(i) == 44 && isCompleted != 1)
				{
					index[j++] = i;
				}
				if (line.charAt(i) == 34) //扫描到分号
				{
					isCompleted++;
					if (isCompleted == 2)
						isCompleted = 0;
				}
			}
			
			InfoColumn infoColumn = new InfoColumn(line.substring(index[0] + 1, index[1]), 
					line.substring(index[1] + 2, index[2] - 1).toLowerCase(), line.substring(index[2] + 1));
			ENVocabularyIndex.put(infoColumn.getENVocabulary(), infoColumn);
			CNVocbularyIndex.put(infoColumn.getCNVocbulary(), infoColumn);
		}
		in.close();
	}
	
	public TreeMap<String, InfoColumn> getENVocabularyIndex() {
		return ENVocabularyIndex;
	}
	
	public TreeMap<String, InfoColumn> getCNVocbularyIndex() {
		return CNVocbularyIndex;
	}


	
	private static DictCacheManager management = null;
	

	private TreeMap<String, InfoColumn> CNVocbularyIndex;  //用于存储英文单词和词性
	private TreeMap<String, InfoColumn> ENVocabularyIndex;
	
}
