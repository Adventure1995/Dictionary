/**
 * ���﷭�뷽�����������룬��ý��
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
		
		if (tmp != null)   //result��Ϊ�գ�������ΪӢ�ģ�����Ӣ�Ķ������ı�������Ӧ��¼
		{
			result.add("Ӣ��");  //���ñ�ǣ�������Ӣ�ļ��
			result.add(tmp.getENVocabulary());
			result.add(tmp.getCNVocbulary());
			result.add(tmp.getSoundmark());
		}
		else             //resultΪ�գ�����ƴд���󣬿����޴˼�¼
		{        
			Iterator<Entry<String, InfoColumn>> iterator = dictionary.getCNVocbularyIndex().entrySet().iterator();
			result.add("����");        //���ñ�ǣ�������Ӣ�ļ��
			while (iterator.hasNext())
			{
				Entry<String, InfoColumn> tmpSet = iterator.next();
				if (input.equals("")) break;
				if (tmpSet.getKey().contains(input))
					result.add(tmpSet.getValue().getENVocabulary());
				if (result.size() >= 10) break;      //Ĭ����ʾ���10������
			}
			if (result.size() == 1)
			{
				result.clear();
				result.add("�޶�Ӧ��");
			}
		}
		
	}
	
	public void showResult(JTextArea outputArea) {
		
		if (result.get(0).equals("Ӣ��"))
		{
			outputArea.setText("Ӣ�ģ�" + result.get(1) + "\n������˼��" + result.get(2) + "\n����" + result.get(3));
		}
		
		else if (result.get(0).equals("����")) {
			outputArea.setText("�����Ҫ�ҵ�Ӣ��:\n");
			for (int i = 1; i < result.size(); i++)
			{
				outputArea.append(result.get(i) + "\n");
			}
		}
		
		else if (result.get(0).equals("�޶�Ӧ��")) {
			outputArea.setText("�޶�Ӧ��");
		}
	}

	private DictCacheManager dictionary = DictCacheManager.getInstance();
	
	private String input;
	private Vector<String> result;
	
}

