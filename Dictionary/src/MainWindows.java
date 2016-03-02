/**
 * @author anonymousXY
 * �����棺
 * ʵ�ָ��ֽ������������ʵ���ı�������Զ���ʾ������ʾ����Ӣ���������밴ť
 *
 */

import java.awt.Button;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MainWindows extends JFrame{

	public MainWindows() {
		// TODO Auto-generated constructor stub
		setTitle("�����ʵ�");
		setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
		setResizable(false);
		
		
		
		dictCacheManager = DictCacheManager.getInstance();
		
		vocabularyList = new JList<>(getAllVocabulary());
		vocabularyList.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		vocabularyPanel = new JScrollPane(vocabularyList);
		
		inputField = new JTextField(25);
		inputField.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		outputArea = new JTextArea(8,25);
		outputArea.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		//�Զ���ʾ���
		model = new DefaultListModel<>();
		hintTable = new JList<>(model);
		hintScrollPanel = new JScrollPane(hintTable);
		hintTable.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		hintScrollPanel.setVisible(false);
		
		translateButton = new Button("����");
		translateButton.setFont(new Font("TimesRoman", Font.ITALIC | Font.BOLD, 12));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		BackPanel backPanel = new BackPanel("BackImage.jpg", DEFAULTWIDTH, DEFAULTHEIGHT);
		backPanel.setLayout(gridBagLayout);
		setMyLayout(gridBagLayout, backPanel);
		add(backPanel);
		
		vocabularyList.addMouseListener(ListSelectAction);
		hintTable.addMouseListener(ListSelectAction);
		
		inputField.addKeyListener(AutoHint);	
		
		
		//ʹ���˷���������ķ��밴ť�������������
		translateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TranslateManager tranlationManager = new TranslateManager(inputField.getText());
				tranlationManager.translate();
				tranlationManager.showResult(outputArea);
				hintScrollPanel.setVisible(false);
				repaint();
			}
		});
		
		
			
	}
	//�������ֺ��������ڸ��²���
	private void setMyLayout(GridBagLayout layout, JComponent parentComponent) {
		
		Font staticWord = new Font("TimesRoman", Font.ITALIC | Font.BOLD, 16);
		JLabel vocabularyLabel = new JLabel("���ʶԱ���");
		vocabularyLabel.setFont(staticWord);
		JLabel inputLabel = new JLabel("������");
		inputLabel.setFont(staticWord);
		JLabel outputLabel = new JLabel("������");
		outputLabel.setFont(staticWord);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 4;
		constraints.insets = new Insets(1, 20, 1, 20);
		constraints.anchor = GridBagConstraints.CENTER;
		parentComponent.add(vocabularyLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridheight = 12;
		constraints.fill = GridBagConstraints.BOTH;
		
		parentComponent.add(vocabularyPanel, constraints);
		
		constraints.gridx = 6;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 4;
		constraints.insets = new Insets(1, 20, 1, 0);
		parentComponent.add(inputLabel, constraints);
		
		constraints.gridx = 10;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(1, 0, 1, 20);
		parentComponent.add(translateButton, constraints);
		
		constraints.gridx = 6;
		constraints.gridy = 2;
		constraints.gridwidth = 6;
		constraints.insets = new Insets(0, 20, 0, 20);
		parentComponent.add(inputField, constraints);
		
		constraints.gridx = 6;
		constraints.gridy = 3;
		constraints.gridwidth = 6;
		parentComponent.add(hintScrollPanel, constraints);
		
		constraints.gridx = 6;
		constraints.gridy = 3;
		constraints.gridwidth = 4;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(1, 20, 1, 20);
		parentComponent.add(outputLabel, constraints);
		
		constraints.gridx = 6;
		constraints.gridy = 4;
		constraints.gridwidth = 6;
		constraints.gridheight = 10;
		parentComponent.add(outputArea, constraints);
		
	}
	//������е�Ӣ�ĵ��ʣ�
 	private String[] getAllVocabulary() {
		
		int length = dictCacheManager.getENVocabularyIndex().keySet().size();
		
		Iterator<String> iterator = dictCacheManager.getENVocabularyIndex().keySet().iterator();
		
		String[] vacabularies = new String[length];
		
		int i = 0;
		while (iterator.hasNext()) {
			vacabularies[i++] = iterator.next();
			
		}
		
		return vacabularies;
		
	}

	//��дkeyAdapter�����ڻ����������ݣ�ʵ���Զ���ʾ��
	private KeyAdapter AutoHint = new KeyAdapter() {
		
		public void keyReleased(KeyEvent e) {
			
			model.removeAllElements();
			
			hintScrollPanel.setVisible(true);
			
			show();
			
			Iterator<String> iterator = dictCacheManager.getENVocabularyIndex().keySet().iterator();
			
			while(iterator.hasNext())
			{
				String tmp = iterator.next();
				if (tmp.length() < inputField.getText().length()) continue;
				if (tmp.substring(0, inputField.getText().length()).equals(inputField.getText()))
				{
					model.addElement(tmp);
				}
				if (model.size() >= 10)                       //Ĭ���Զ���ʾ10������
					break;

			}
			
			if (inputField.getText().equals(""))
			{
				hintScrollPanel.setVisible(false);
				
				show();
			}
			
		}
		
	};

	//��дMouseAdapter�࣬ʹ˫���ʻ���ֵ���
	private MouseAdapter ListSelectAction = new MouseAdapter() {
		
		public void mouseClicked(MouseEvent e)
		{
			JList<String> list = (JList<String>) e.getSource();
			if (list.getSelectedIndex() != -1)
			{
				if (e.getClickCount() == 2)
				{
					TranslateManager translateManager = new TranslateManager(((JList<String>)(e.getSource())).getSelectedValue());
					if (e.getSource() == hintTable)  //�������Զ���ʾ����Ҫ����������
						inputField.setText(((JList<String>)(e.getSource())).getSelectedValue());
					translateManager.translate();
					translateManager.showResult(outputArea);
					
					hintScrollPanel.setVisible(false);
					repaint();
				}
				if (e.getClickCount() == 1 && (e.getSource()) == hintTable)
					inputField.setText(((JList<String>)e.getSource()).getSelectedValue());   //bug
					
			}
			
		}
		
		
	};

	
	
	private DictCacheManager dictCacheManager;
	
	private Button translateButton;
	private JList<String> vocabularyList;
	
	private JTextField inputField;
	private JTextArea outputArea;
	
	private JScrollPane vocabularyPanel;
	
	private JScrollPane hintScrollPanel;
	private JList<String> hintTable;
	
	private DefaultListModel<String> model;//�����Զ���ʾ��������޸�
	
	private static final int DEFAULTWIDTH = 500;
	private static final int DEFAULTHEIGHT = 400;
	
}

/*�̳���JPanel���࣬������ӱ���ͼ��*/
class BackPanel extends JPanel{
	
	public BackPanel(String path, int width, int height){
		this.path = path;
		this.width = width;
		this.height = height;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(image, 0, 0, width, height, this);
	}
	
	private Image image;
	
	private String path;
	private int width;
	private int height;
}
