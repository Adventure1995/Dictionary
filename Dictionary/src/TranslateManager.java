/**
 * 词语翻译方法，包括翻译，获得结果
 * @author anonymousXY
 *
 */
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JTextArea;



public class TranslateManager{
	
	public TranslateManager(String input) {
		// TODO Auto-generated constructor stub
		this.input = input; 
		result = new Vector<>();
	}

	public void translate() {
		// TODO Auto-generated method stub
		InfoColumn tmp = dictionary.getENVocabularyIndex().get(input);
		
		if (tmp != null)   //result不为空，即单词为英文，且在英文对照中文表中有相应记录
		{
			result.add("英文");  //设置标记，方便中英文检测
			result.add(tmp.getENVocabulary());
			result.add(tmp.getCNVocbulary());
			result.add(tmp.getSoundmark());
		}
		else             //result为空，可能拼写错误，可能无此记录
		{        
			Iterator<Entry<String, InfoColumn>> iterator = dictionary.getCNVocbularyIndex().entrySet().iterator();
			result.add("中文");        //设置标记，方便中英文检测
			while (iterator.hasNext())
			{
				Entry<String, InfoColumn> tmpSet = iterator.next();
				if (input.equals("")) break;
				if (tmpSet.getKey().contains(input))
					result.add(tmpSet.getValue().getENVocabulary());
				if (result.size() >= 10) break;      //默认显示最多10个单词
			}
			if (result.size() == 1)
			{
				result.clear();
				result.add("无对应项");
			}
		}
		
	}
	
	public void showResult(JTextArea outputArea) {
		
		if (result.get(0).equals("英文"))
		{
			outputArea.setText("英文：" + result.get(1) + "\n中文意思：" + result.get(2) + "\n音标" + result.get(3));
		}
		
		else if (result.get(0).equals("中文")) {
			outputArea.setText("你可能要找的英文:\n");
			for (int i = 1; i < result.size(); i++)
			{
				outputArea.append(result.get(i) + "\n");
			}
		}
		
		else if (result.get(0).equals("无对应项")) {
			outputArea.setText("无对应项");
		}
	}

	private DictCacheManager dictionary = DictCacheManager.getInstance();
	
	private String input;
	private Vector<String> result;
	
}

